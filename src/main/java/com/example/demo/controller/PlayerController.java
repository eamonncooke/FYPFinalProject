/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.model.AccessToken;
import com.example.demo.model.Activity;
import com.example.demo.model.Player;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.StravaURL;
import com.example.demo.model.Testing;

import com.example.demo.service.ServiceForPlayer;
import com.example.demo.strava.model.Splits;
import com.example.demo.strava.model.StravaActivity;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author cooke
 */
@RestController
@RequestMapping("/player")
@SessionAttributes("player")
public class PlayerController {

    @Autowired
    ServiceForPlayer service;

    @GetMapping("/viewResults")
    public ModelAndView getTestResults(ModelMap model) {
        return new ModelAndView("/viewTestResultsList", "testList", service.getAllTest());
    }

    @GetMapping("/viewFitnessTestChart")
    public ModelAndView getFitnessTestChart(ModelMap model) {
        String jsonArray = chartData().toString().replace("'", "\\'");
        return new ModelAndView("/fitnessChart", "testResults", jsonArray);
    }

    @RequestMapping("/insertTest")
    public ModelAndView insertTest(ModelMap model, HttpServletRequest request) {
        if (request.isUserInRole("PLAYER")) {
            int testingId = service.getNewTestingNum();
            //model.addAttribute("playerID", getLoggedInPlayer());
            //System.out.println(getLoggedInPlayer());
            model.addAttribute("testingID", testingId);
            model.addAttribute("testResult", new Testing());

            return new ModelAndView("/insertTestResults", model);
        }else{
            return new ModelAndView("redirect:/coach/viewResults");
        }

    }

    @PostMapping("/insertNewTest")
    public ModelAndView editAgent(@Valid @ModelAttribute("testResult") Testing test, BindingResult result, ModelMap model) {
        Player player = getLoggedInPlayer();
        test.setPlayerId(player);
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return new ModelAndView("/insertTestResults");
        }
        player.getTestingCollection().add(test);
        service.updatePlayer(player);
        return new ModelAndView("redirect:/player/viewResults");
    }

    @RequestMapping("/activitiesPage")
    public ModelAndView activitiesPage(ModelMap model) throws IOException {

        if (getLoggedInPlayer().getStravaActive() == null) {
            String Url = StravaURL.getStravaAuthURL() + "?client_id="
                    + StravaURL.getClientId() + "&response_type=code&redirect_uri="
                    + StravaURL.getAppURL() + "&approval_prompt=force&scope="
                    + StravaURL.getScope();
            model.addAttribute("authUrl", Url);
            return new ModelAndView("/stravaActivities", model);
        } else {
            Player player = getLoggedInPlayer();
            List list;
            Collection test = player.getActivityCollection();
            if (test instanceof List) {
                list = (List) test;
            } else {
                list = new ArrayList(test);
            }
            return new ModelAndView("/viewTrainingRecorded", "trainingList", list);
        }
    }

    @GetMapping("/addStravaAccount")
    public ModelAndView addStravaCode(@QueryParam("code") String code, @QueryParam("scope") String scope, @QueryParam("error") String error, ModelMap model) throws IOException {
        if (error != null) {
            return new ModelAndView("redirect:/player/activitiesPage");
        } else {
            Player player = getLoggedInPlayer();
            getAccessTokenFirstTime(StravaURL.getClientId(), StravaURL.getClientSecret(), code);

            return new ModelAndView("redirect:/player/activitiesPage");
        }
    }

    @GetMapping("/viewIndividualActivity")
    public ModelAndView getActivity(ModelMap model, @QueryParam("id") String id) throws IOException {

        StravaActivity activity = getActivityDetailsByActivityId(id);
        String map = activity.getMap().replace("\\", "\\\\");

        activity.setMap(map);
        model.addAttribute("splits", activity.getSplit());
        model.addAttribute("activity", activity);
        return new ModelAndView("/viewActivityDetails", model);
    }

    @GetMapping("/removeStrava")
    public ModelAndView deletePlayer() {

        Player p = getLoggedInPlayer();

        service.deleteRefreshToken(service.getRefreshTokenById(p.getStravaUserId()).getId());
        service.deleteAccessToken(service.getAccessTokenById(p.getStravaUserId()).getId());

        if (!p.getActivityCollection().isEmpty()) {
            p.getActivityCollection().clear();
        }

        p.setStravaActive(null);
        p.setLastStravaUpdated(null);
        p.setStravaUserId(null);
        service.editPlayer(p);

        return new ModelAndView("redirect:/");
    }

    private JSONArray chartData() {
        List<Testing> testList = service.getAllTest();
        String name;
        String newDate, month, year;
        JSONArray ja = new JSONArray();
        for (Testing test : testList) {
            month = Integer.toString(test.getDate().getMonth() + 1);
            year = Integer.toString(test.getDate().getYear() + 1900);
            newDate = month + "-" + year;
            name = test.getPlayerId().getAuthUserId().getFirstName() + " " + test.getPlayerId().getAuthUserId().getSurname();

            JSONObject jo = new JSONObject();
            jo.put("name", name);
            jo.put("date", newDate);
            jo.put("time", Integer.toString(test.getTime()));

            ja.put(jo);
        }
        return ja;
    }

    private String getLoggedInUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private Player getLoggedInPlayer() {
        Player player;
        return player = service.getPlayersByUserId(service.getUserByEmail(getLoggedInUserName()));
    }

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public void getAccessTokenFirstTime(String clientId, String clientSecret, String code) throws IOException {

        HttpPost post = new HttpPost("https://www.strava.com/oauth/token");
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("client_id", clientId));
        urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try ( CloseableHttpClient httpClient = HttpClients.createDefault();  CloseableHttpResponse response = httpClient.execute(post)) {

            String json = EntityUtils.toString(response.getEntity());

            JSONObject StravaAccess = new JSONObject(json);
            int expiresAt = StravaAccess.getInt("expires_at");
            String refreshToken = StravaAccess.getString("refresh_token");
            String accessToken = StravaAccess.getString("access_token");
            JSONObject athlete = StravaAccess.getJSONObject("athlete");
            int athleteID = athlete.getInt("id");

            int newAccessTokenId = service.getNewAccessTokenNum();
            int newRefreshTokenId = service.getNewRefreshTokenNum();
            AccessToken at = new AccessToken(newAccessTokenId, athleteID, accessToken, expiresAt);
            RefreshToken rt = new RefreshToken(newRefreshTokenId, athleteID, refreshToken);
            Player player = getLoggedInPlayer();
            player.setStravaActive("Activated");
            player.setStravaUserId(athleteID);
            System.out.println(player.getAuthUserId().getFirstName());
            service.updatePlayer(player);
            service.addFirstAccessToken(at);
            service.addFirstRefreshToken(rt);
        }
        httpClient.close();
    }

    public void getNewAccessToken(String clientId, String clientSecret) throws IOException {
        int athleteId = getLoggedInPlayer().getStravaUserId();
        RefreshToken refreshToken = service.getRefreshTokenById(athleteId);
        AccessToken accessToken = service.getAccessTokenById(athleteId);

        HttpPost post = new HttpPost("https://www.strava.com/oauth/token");
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("client_id", clientId));
        urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));
        urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
        urlParameters.add(new BasicNameValuePair("refresh_token", refreshToken.getRefreshTokenCode()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try ( CloseableHttpClient httpClient = HttpClients.createDefault();  CloseableHttpResponse response = httpClient.execute(post)) {

            String json = EntityUtils.toString(response.getEntity());

            JSONObject StravaAccess = new JSONObject(json);
            int expiresAt = StravaAccess.getInt("expires_at");
            String newRefreshToken = StravaAccess.getString("refresh_token");
            String newAccessToken = StravaAccess.getString("access_token");

            accessToken.setAccessTokenCode(newAccessToken);
            accessToken.setExpiresAt(expiresAt);
            refreshToken.setRefreshTokenCode(newRefreshToken);

            service.updateNewAccessToken(accessToken);
            service.updateNewRefreshToken(refreshToken);
        }
        httpClient.close();
    }

    public StravaActivity getActivityDetailsByActivityId(String activityId) throws IOException {
        Activity a = service.getActivityByActivityID(activityId);
        Player player = getLoggedInPlayer();
        updateAccessToken(player);
        List<Splits> list = new ArrayList<Splits>();
        StravaActivity sa = new StravaActivity();
        HttpGet get = new HttpGet("https://www.strava.com/api/v3/activities/" + activityId + "?access_token="
                + service.getAccessTokenById(player.getStravaUserId()).getAccessTokenCode());

        try ( CloseableHttpClient httpClient = HttpClients.createDefault();  CloseableHttpResponse response = httpClient.execute(get)) {

            String json = EntityUtils.toString(response.getEntity());
            JSONObject activity = new JSONObject(json);

            try {
                float meters = activity.getFloat("distance");
                meters = meters / 1000;
                sa.setDistance(meters);
            } catch (Exception e) {
                int distance = -1;
            }

            try {
                int time = activity.getInt("moving_time");
                int min = time / 60;
                int sec = time % 60;
                float minutes = (min + ((float) sec / 100));
                sa.setElapsedTime(minutes);
            } catch (Exception e) {
                int time = -1;
            }
            try {
                float elevation = activity.getFloat("total_elevation_gain");
                sa.setTotalElevationGain(elevation);
            } catch (Exception e) {
                String elevation = null;
            }
            try {
                String activityType = activity.getString("type");
                sa.setType(activityType);
            } catch (Exception e) {
                String activityType = null;
            }
            try {
                Date date = convertStringToDate(activity.getString("start_date_local"));
                sa.setStartDate(date);
            } catch (Exception e) {
                Date date = new Date();
            }
            try {
                JSONObject map = activity.getJSONObject("map");
                String polyline = map.getString("polyline");
                sa.setMap(polyline);
            } catch (Exception e) {
                float averageSpeed = -1;
            }
            try {
                float averageSpeed = activity.getFloat("average_speed");
                sa.setAverageSpeed(averageSpeed);
            } catch (Exception e) {
                float averageSpeed = -1;
            }
            try {
                float maxSpeed = activity.getFloat("max_speed");
                sa.setMaxSpeed(maxSpeed);
            } catch (Exception e) {
                float maxSpeed = -1;
            }
            try {
                float averageHR = activity.getFloat("average_heartrate");
                sa.setAverageHeartrate(averageHR);
            } catch (Exception e) {
                float averageHR = -1;
            }
            try {
                float maxHR = activity.getFloat("max_heartrate");
                sa.setMaxHeartrate(maxHR);
            } catch (Exception e) {
                float maxHR = -1;
            }
            try {
                float calories = activity.getFloat("calories");
                sa.setCalories(calories);
            } catch (Exception e) {
                float calories = -1;
            }

            try {
                JSONArray split = activity.getJSONArray("splits_metric");
                for (int i = 0; i < split.length(); i++) {
                    JSONObject individualSplit = split.getJSONObject(i);
                    Splits splits = new Splits();
                    splits.setSplit(individualSplit.getInt("split"));

                    try {
                        float meters = individualSplit.getFloat("distance");
                        meters = meters / 1000;
                        splits.setDistance(meters);
                    } catch (Exception e) {
                        int distance = -1;
                    }
                    try {
                        int time = individualSplit.getInt("moving_time");
                        int min = time / 60;
                        int sec = time % 60;
                        float minutes = (min + ((float) sec / 100));
                        splits.setTime(minutes);
                    } catch (Exception e) {
                        int time = -1;
                    }
                    try {
                        float elevation = individualSplit.getFloat("elevation_difference");
                        splits.setElevation(elevation);
                    } catch (Exception e) {
                        String elevation = null;
                    }
                    try {
                        float averageSpeed = individualSplit.getFloat("average_speed");
                        splits.setAverageSpeed(averageSpeed);
                    } catch (Exception e) {
                        float averageSpeed = -1;
                    }
                    try {
                        float averageHR = individualSplit.getFloat("average_heartrate");
                        averageHR = Math.round(averageHR * 100) / 100;
                        splits.setAverageHeartrate(averageHR);
                    } catch (Exception e) {
                        float averageHR = -1;
                    }
                    list.add(splits);
                }
                sa.setSplit(list);
            } catch (Exception e) {
                float averageSpeed = -1;
            }

        }
        httpClient.close();
        return sa;
    }

    public Date convertStringToDate(String date) {
        Instant instant = Instant.parse(date);
        Date convertedDate = Date.from(instant);
        return convertedDate;
    }

    public void updateAccessToken(Player player) throws IOException {
        AccessToken at = service.getAccessTokenById(player.getStravaUserId());
        long now = Instant.now().getEpochSecond();

        if (now > at.getExpiresAt()) {
            getNewAccessToken(StravaURL.getClientId(), StravaURL.getClientSecret(), player.getStravaUserId());
        }

    }

    public void getNewAccessToken(String clientId, String clientSecret, int stravaId) throws IOException {

        RefreshToken refreshToken = service.getRefreshTokenById(stravaId);
        AccessToken accessToken = service.getAccessTokenById(stravaId);

        HttpPost post = new HttpPost("https://www.strava.com/oauth/token");
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("client_id", clientId));
        urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));
        urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
        urlParameters.add(new BasicNameValuePair("refresh_token", refreshToken.getRefreshTokenCode()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try ( CloseableHttpClient httpClient = HttpClients.createDefault();  CloseableHttpResponse response = httpClient.execute(post)) {

            String json = EntityUtils.toString(response.getEntity());

            JSONObject StravaAccess = new JSONObject(json);
            int expiresAt = StravaAccess.getInt("expires_at");
            String newRefreshToken = StravaAccess.getString("refresh_token");
            String newAccessToken = StravaAccess.getString("access_token");

            accessToken.setAccessTokenCode(newAccessToken);
            accessToken.setExpiresAt(expiresAt);
            refreshToken.setRefreshTokenCode(newRefreshToken);

            service.updateNewAccessToken(accessToken);
            service.updateNewRefreshToken(refreshToken);
        }
        httpClient.close();
    }
}

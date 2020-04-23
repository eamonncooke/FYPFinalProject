/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.model.AccessToken;
import com.example.demo.model.Activity;
import com.example.demo.model.AuthUser;
import com.example.demo.model.Player;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.StravaURL;
import com.example.demo.model.Testing;
import com.example.demo.service.ServiceForCoach;
import com.example.demo.strava.model.StravaActivity;
import com.example.demo.strava.model.Splits;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author cooke
 */
@RestController

@RequestMapping("/coach")
@SessionAttributes("coach")
public class CoachController {

    @Autowired
    ServiceForCoach service;

    @GetMapping("")
    public ModelAndView getPlayers(ModelMap model) {
        return new ModelAndView("/viewPlayers", "playerList", service.getAllPlayers());
    }

    @GetMapping("/addPlayer")
    public ModelAndView displayPlayerAddForm(ModelMap model) {
//        Player p = new Player();
//        p.setPlayerId(service.getNewPlayerId());
//        p.getAuthUserId().setRole("PLAYER");
//        p.getAuthUserId().setAuthUserId(service.getNewAuthUserId());

        model.addAttribute("authUserID", service.getNewAuthUserId());
        model.addAttribute("role", "PLAYER");
        model.addAttribute("player", new Player());
        model.addAttribute("playerID", service.getNewPlayerId());

        return new ModelAndView("/insertPlayer", model);
    }

    // @RequestMapping(value = "/addAgent", method = RequestMethod.POST)
    @PostMapping("/insertPlayer")
    public ModelAndView addAPlayer(@Valid @ModelAttribute("player") Player player, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return new ModelAndView("/insertPlayer");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(player.getAuthUserId().getPassword());
        player.getAuthUserId().setPassword(encodedPassword);
        service.addPlayer(player);
        return new ModelAndView("redirect:/coach");
    }
    

    @RequestMapping("/editPassword")
    public ModelAndView editPasswordForm(@QueryParam("id") int id) {
        Player p = service.getPlayerByPlayerId(id);
        p.getAuthUserId().setPassword(null);
        return new ModelAndView("/editPassword", "user", p.getAuthUserId());
    }

    @PostMapping("/insertPassword")
    public ModelAndView getNewPassword(@Valid @ModelAttribute("user") AuthUser user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return new ModelAndView("/editPassword");
        } else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            service.editUser(user);
            return new ModelAndView("redirect:/coach");
        }
    }

    @GetMapping("/viewResults")
    public ModelAndView getTestResults(ModelMap model) {
        return new ModelAndView("/viewTestResultsList", "testList", service.getAllTest());
    }
    @GetMapping("/deleteTest")
    public ModelAndView deleteTest(@QueryParam("id") int id) {
        service.deleteFitnessTest(id);
        return new ModelAndView("redirect:/coach/viewResults");
    }
    
    @GetMapping("/deletePlayer")
    public ModelAndView deletePlayer(@QueryParam("id") int id) {
        Player p = service.getPlayerByPlayerId(id);
        if(p.getStravaActive()=="Activated"){
            service.deleteRefreshToken(p.getStravaUserId());
            service.deleteAccessToken(p.getStravaUserId());
        }
        service.deletePlayer(id);
        return new ModelAndView("redirect:/coach");
    }

    @GetMapping("/updateRecordedTrainings")
    public ModelAndView getTraining(ModelMap model) throws IOException {
        List<Player> list = service.getAllPlayers();
        for (Player c : list) {
            if (c.getStravaActive() != null) {
                updateAccessToken(c);
                getListofActivitiesByPlayer(c);
            }
        }
        return new ModelAndView("/viewPlayers", "playerList", service.getAllPlayers());
    }

    @GetMapping("/viewActivity")
    public ModelAndView getActivityList(ModelMap model, @QueryParam("id") int id) {
        Player player = service.getPlayerByPlayerId(id);
        List list;
        Collection test = player.getActivityCollection();
        if (test instanceof List) {
            list = (List) test;
        } else {
            list = new ArrayList(test);
        }
        return new ModelAndView("/viewTrainingRecorded", "trainingList", list);
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

    private String getLoggedInUserName() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }

    @GetMapping("/viewAllActivity")
    public ModelAndView getAllActivityList(ModelMap model) {
        return new ModelAndView("/viewAllActivities", "trainingList", service.getActivityList());
    }

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

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
    @GetMapping("/viewFitnessTestChart")
    public ModelAndView getFitnessTestChart(ModelMap model) {
        JSONArray testResults = chartData();
        return new ModelAndView("/fitnessChart", "testResults", testResults);
    }

    public void getListofActivitiesByPlayer(Player player) throws IOException {
        HttpGet get = new HttpGet("https://www.strava.com/api/v3/athlete/activities/?access_token=" + service.getAccessTokenById(player.getStravaUserId()).getAccessTokenCode());

        try ( CloseableHttpClient httpClient = HttpClients.createDefault();  CloseableHttpResponse response = httpClient.execute(get)) {

            String json = EntityUtils.toString(response.getEntity());
            JSONArray activityList = new JSONArray(json);

            for (int i = 0; i < activityList.length(); i++) {
                JSONObject activity = activityList.getJSONObject(i);

                Date date = convertStringToDate(activity.getString("start_date_local"));

                if (player.getLastStravaUpdated() == null || player.getLastStravaUpdated().before(date)) {
                    JSONObject map = activity.getJSONObject("map");
                    String id = map.getString("id").substring(1);

                    Activity a = new Activity(service.getNewTrainingNum(), id, date);
                    try {
                        int meters = activity.getInt("distance");
                        float Km = (float) meters / 1000;
                        a.setDistance(Km);
                    } catch (Exception e) {
                        int distance = -1;
                    }

                    try {
                        int time = activity.getInt("moving_time");
                        int min = time / 60;
                        int sec = time % 60;
                        float minutes = (min + ((float) sec / 100));
                        a.setMovingTime(minutes);
                    } catch (Exception e) {
                        int time = -1;
                    }

                    try {
                        String activityType = activity.getString("type");
                        a.setType(activityType);
                    } catch (Exception e) {
                        String activityType = null;
                    }
                    try {
                        float averageSpeed = activity.getFloat("average_speed");
                        a.setAverageSpeed(averageSpeed);
                    } catch (Exception e) {
                        float averageSpeed = -1;
                    }
                    try {
                        float maxSpeed = activity.getFloat("max_speed");
                        a.setMaxSpeed(maxSpeed);
                    } catch (Exception e) {
                        float maxSpeed = -1;
                    }
                    try {
                        float averageHR = activity.getFloat("average_heartrate");
                        a.setAverageHeartrate(averageHR);
                    } catch (Exception e) {
                        float averageHR = -1;
                    }
                    try {
                        float maxHR = activity.getFloat("max_heartrate");
                        a.setMaxHeartrate(maxHR);
                    } catch (Exception e) {
                        float maxHR = -1;
                    }

                    a.setPlayerId(player);
                    player.getActivityCollection().add(a);
                    service.updatePlayer(player);

                } else {
                    break;
                }
            }
            Date d = new Date();
            player.setLastStravaUpdated(d);
            service.updatePlayer(player);
        }
        httpClient.close();
    }

    public StravaActivity getActivityDetailsByActivityId(String activityId) throws IOException {
        Activity a = service.getActivityByActivityID(activityId);
        Player player = a.getPlayerId();
        List<Splits> list = new ArrayList<Splits>();
        updateAccessToken(player);
        HttpGet get = new HttpGet("https://www.strava.com/api/v3/activities/" + activityId + "?access_token=" + service.getAccessTokenById(player.getStravaUserId()).getAccessTokenCode());

        try ( CloseableHttpClient httpClient = HttpClients.createDefault();  CloseableHttpResponse response = httpClient.execute(get)) {

            String json = EntityUtils.toString(response.getEntity());
            JSONObject activity = new JSONObject(json);
            StravaActivity sa = new StravaActivity();

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
            httpClient.close();
            return sa;
        }
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
    private JSONArray chartData() {
        List<Testing> testList = service.getAllTest();
        String name;
        String newDate, month, year;
        JSONArray ja = new JSONArray();
        for (Testing test : testList) {
            month = Integer.toString(test.getDate().getMonth() + 1);
            year = Integer.toString(test.getDate().getYear()+1900);
            System.out.println(year);
            newDate = month + "-" + year;
            name = test.getPlayerId().getAuthUserId().getFirstName() + " " + test.getPlayerId().getAuthUserId().getSurname();

            JSONObject jo = new JSONObject();
            jo.put("name", name);
            jo.put("date", newDate);
            jo.put("time", Integer.toString(test.getTime()));
            jo.put("postion", test.getPlayerId().getPostion());

            ja.put(jo);
        }
        return ja;
    }
}

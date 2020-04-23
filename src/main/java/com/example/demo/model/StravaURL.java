/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

/**
 *
 * @author cooke
 */
public class StravaURL {

    private static String appURL = "http://localhost:8080/player/addStravaAccount";
    private static String stravaAuthURL = "http://www.strava.com/oauth/authorize";
    private static String clientId = "44475";
    private static String clientSecret = "1963bb5164a4925dd1a40128d5e3138cd22bc75d";
    private static String scope = "activity:read_all";

    public static String getClientSecret() {
        return clientSecret;
    }

    public static String getAppURL() {
        return appURL;
    }

    public static String getStravaAuthURL() {
        return stravaAuthURL;
    }

    public static String getClientId() {
        return clientId;
    }

    public static String getScope() {
        return scope;
    }

    
    
}

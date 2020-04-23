/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.strava.model;

import com.example.demo.model.Activity;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cooke
 */
public class StravaActivity {
    private Float distance;
    private Float elapsedTime;
    private Float totalElevationGain;
    private String type;
    private Date startDate;
    private String map;
    private Float averageSpeed;
    private Float maxSpeed;
    private Float averageHeartrate;
    private Float maxHeartrate;
    private Float calories;
    private List<Splits> Split;

    public StravaActivity() {
    }

    
    public StravaActivity(Float distance, Float elapsedTime, Float totalElevationGain, String type, Date startDate, String map, Float averageSpeed, Float maxSpeed, Float averageHeartrate, Float maxHeartrate, Float calories, List<Splits> Split) {
        this.distance = distance;
        this.elapsedTime = elapsedTime;
        this.totalElevationGain = totalElevationGain;
        this.type = type;
        this.startDate = startDate;
        this.map = map;
        this.averageSpeed = averageSpeed;
        this.maxSpeed = maxSpeed;
        this.averageHeartrate = averageHeartrate;
        this.maxHeartrate = maxHeartrate;
        this.calories = calories;
        this.Split = Split;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Float getTotalElevationGain() {
        return totalElevationGain;
    }

    public void setTotalElevationGain(Float totalElevationGain) {
        this.totalElevationGain = totalElevationGain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public Float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    
    

    public Float getAverageHeartrate() {
        return averageHeartrate;
    }

    public void setAverageHeartrate(Float averageHeartrate) {
        this.averageHeartrate = averageHeartrate;
    }

    public Float getMaxHeartrate() {
        return maxHeartrate;
    }

    public void setMaxHeartrate(Float maxHeartrate) {
        this.maxHeartrate = maxHeartrate;
    }

    public Float getCalories() {
        return calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public List<Splits> getSplit() {
        return Split;
    }

    public void setSplit(List<Splits> Split) {
        this.Split = Split;
    }
    

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.strava.model;

/**
 *
 * @author cooke
 */
public class Splits {
    private int split;
    private float distance;
    private float time;
    private float elevation;
    private float averageSpeed;
    private float averageHeartrate;

    public Splits() {
    }

    
    public Splits(int split, float distance, float time, float elevation, float averageSpeed, float averageHeartrate) {
        this.split = split;
        this.distance = distance;
        this.time = time;
        this.elevation = elevation;
        this.averageSpeed = averageSpeed;
        this.averageHeartrate = averageHeartrate;
    }

    public int getSplit() {
        return split;
    }

    public void setSplit(int split) {
        this.split = split;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public float getAverageHeartrate() {
        return averageHeartrate;
    }

    public void setAverageHeartrate(float averageHeartrate) {
        this.averageHeartrate = averageHeartrate;
    }
    
    
}

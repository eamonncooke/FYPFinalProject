/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cooke
 */
@Entity
@Table(name = "activity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a"),
    @NamedQuery(name = "Activity.findById", query = "SELECT a FROM Activity a WHERE a.id = :id"),
    @NamedQuery(name = "Activity.findByActivityId", query = "SELECT a FROM Activity a WHERE a.activityId = :activityId"),
    @NamedQuery(name = "Activity.findByDate", query = "SELECT a FROM Activity a WHERE a.date = :date"),
    @NamedQuery(name = "Activity.findByDistance", query = "SELECT a FROM Activity a WHERE a.distance = :distance"),
    @NamedQuery(name = "Activity.findByMovingTime", query = "SELECT a FROM Activity a WHERE a.movingTime = :movingTime"),
    @NamedQuery(name = "Activity.findByType", query = "SELECT a FROM Activity a WHERE a.type = :type"),
    @NamedQuery(name = "Activity.findByAverageSpeed", query = "SELECT a FROM Activity a WHERE a.averageSpeed = :averageSpeed"),
    @NamedQuery(name = "Activity.findByMaxSpeed", query = "SELECT a FROM Activity a WHERE a.maxSpeed = :maxSpeed"),
    @NamedQuery(name = "Activity.findByAverageHeartrate", query = "SELECT a FROM Activity a WHERE a.averageHeartrate = :averageHeartrate"),
    @NamedQuery(name = "Activity.findByMaxHeartrate", query = "SELECT a FROM Activity a WHERE a.maxHeartrate = :maxHeartrate"),
    @NamedQuery(name = "Activity.findLastActivityNumber", query = "SELECT a FROM Activity a ORDER BY a.id DESC")})
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "activity_id")
    private String activityId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "distance")
    private Float distance;
    @Column(name = "moving_time")
    private Float movingTime;
    @Size(max = 10)
    @Column(name = "type")
    private String type;
    @Column(name = "average_speed")
    private Float averageSpeed;
    @Column(name = "max_speed")
    private Float maxSpeed;
    @Column(name = "average_heartrate")
    private Float averageHeartrate;
    @Column(name = "max_heartrate")
    private Float maxHeartrate;
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    @ManyToOne(optional = false)
    private Player playerId;

    public Activity() {
    }

    public Activity(Integer id) {
        this.id = id;
    }

    public Activity(Integer id, String activityId, Date date) {
        this.id = id;
        this.activityId = activityId;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getMovingTime() {
        return movingTime;
    }

    public void setMovingTime(Float movingTime) {
        this.movingTime = movingTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Player getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Player playerId) {
        this.playerId = playerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activity)) {
            return false;
        }
        Activity other = (Activity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Activity[ id=" + id + " ]";
    }
    
}

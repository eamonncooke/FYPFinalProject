/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author cooke
 */
@Entity
@Table(name = "player")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findByPlayerId", query = "SELECT p FROM Player p WHERE p.playerId = :playerId"),
    @NamedQuery(name = "Player.findByPostion", query = "SELECT p FROM Player p WHERE p.postion = :postion"),
    @NamedQuery(name = "Player.findByDob", query = "SELECT p FROM Player p WHERE p.dob = :dob"),
    @NamedQuery(name = "Player.findByHeight", query = "SELECT p FROM Player p WHERE p.height = :height"),
    @NamedQuery(name = "Player.findByWeight", query = "SELECT p FROM Player p WHERE p.weight = :weight"),
    @NamedQuery(name = "Player.findByStravaActive", query = "SELECT p FROM Player p WHERE p.stravaActive = :stravaActive"),
    @NamedQuery(name = "Player.findByStravaUserId", query = "SELECT p FROM Player p WHERE p.stravaUserId = :stravaUserId"),
    @NamedQuery(name = "Player.findByLastStravaUpdated", query = "SELECT p FROM Player p WHERE p.lastStravaUpdated = :lastStravaUpdated"),
    @NamedQuery(name = "Player.findLastPlayerNumber", query = "SELECT p FROM Player p ORDER BY p.playerId DESC")})

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "player_id")
    private Integer playerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "postion")
    private String postion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Basic(optional = false)
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "height")
    private Integer height;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight")
    private Float weight;
    @Size(max = 20)
    @Column(name = "strava_active")
    private String stravaActive;
    @Column(name = "strava_user_id")
    private Integer stravaUserId;
    @Column(name = "last_strava_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastStravaUpdated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerId")
    private Collection<Activity> activityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerId")
    private Collection<Testing> testingCollection;
    @JoinColumn(name = "auth_user_id", referencedColumnName = "auth_user_id")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private AuthUser authUserId;

    public Player() {
    }

    public Player(Integer playerId) {
        this.playerId = playerId;
    }

    public Player(Integer playerId, String postion, Date dob) {
        this.playerId = playerId;
        this.postion = postion;
        this.dob = dob;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getStravaActive() {
        return stravaActive;
    }

    public void setStravaActive(String stravaActive) {
        this.stravaActive = stravaActive;
    }

    public Integer getStravaUserId() {
        return stravaUserId;
    }

    public void setStravaUserId(Integer stravaUserId) {
        this.stravaUserId = stravaUserId;
    }

    public Date getLastStravaUpdated() {
        return lastStravaUpdated;
    }

    public void setLastStravaUpdated(Date lastStravaUpdated) {
        this.lastStravaUpdated = lastStravaUpdated;
    }

    @XmlTransient
    public Collection<Activity> getActivityCollection() {
        return activityCollection;
    }

    public void setActivityCollection(Collection<Activity> activityCollection) {
        this.activityCollection = activityCollection;
    }

    @XmlTransient
    public Collection<Testing> getTestingCollection() {
        return testingCollection;
    }

    public void setTestingCollection(Collection<Testing> testingCollection) {
        this.testingCollection = testingCollection;
    }

    public AuthUser getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(AuthUser authUserId) {
        this.authUserId = authUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playerId != null ? playerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.playerId == null && other.playerId != null) || (this.playerId != null && !this.playerId.equals(other.playerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Player[ playerId=" + playerId + " ]";
    }
    
}

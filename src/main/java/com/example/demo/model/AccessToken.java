/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cooke
 */
@Entity
@Table(name = "access_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessToken.findAll", query = "SELECT a FROM AccessToken a"),
    @NamedQuery(name = "AccessToken.findByAthleteId", query = "SELECT a FROM AccessToken a WHERE a.athleteId = :athleteId"),
    @NamedQuery(name = "AccessToken.findByAccessTokenCode", query = "SELECT a FROM AccessToken a WHERE a.accessTokenCode = :accessTokenCode"),
    @NamedQuery(name = "AccessToken.findByExpiresAt", query = "SELECT a FROM AccessToken a WHERE a.expiresAt = :expiresAt")})
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "athlete_id")
    private Integer athleteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 55)
    @Column(name = "access_token_code")
    private String accessTokenCode;
    @Column(name = "expires_at")
    private Integer expiresAt;

    public AccessToken() {
    }

    public AccessToken(Integer athleteId) {
        this.athleteId = athleteId;
    }

    public AccessToken(Integer athleteId, String accessTokenCode, Integer expiresAt) {
        this.athleteId = athleteId;
        this.accessTokenCode = accessTokenCode;
        this.expiresAt = expiresAt;
    }

    public Integer getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(Integer athleteId) {
        this.athleteId = athleteId;
    }

    public String getAccessTokenCode() {
        return accessTokenCode;
    }

    public void setAccessTokenCode(String accessTokenCode) {
        this.accessTokenCode = accessTokenCode;
    }

    public Integer getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Integer expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (athleteId != null ? athleteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessToken)) {
            return false;
        }
        AccessToken other = (AccessToken) object;
        if ((this.athleteId == null && other.athleteId != null) || (this.athleteId != null && !this.athleteId.equals(other.athleteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.AccessToken[ athleteId=" + athleteId + " ]";
    }
    
}

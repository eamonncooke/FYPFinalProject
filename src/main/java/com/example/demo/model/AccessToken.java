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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @NamedQuery(name = "AccessToken.findById", query = "SELECT a FROM AccessToken a WHERE a.id = :id"),
    @NamedQuery(name = "AccessToken.findByAthleteId", query = "SELECT a FROM AccessToken a WHERE a.athleteId = :athleteId"),
    @NamedQuery(name = "AccessToken.findByAccessTokenCode", query = "SELECT a FROM AccessToken a WHERE a.accessTokenCode = :accessTokenCode"),
    @NamedQuery(name = "AccessToken.findByExpiresAt", query = "SELECT a FROM AccessToken a WHERE a.expiresAt = :expiresAt"),
    @NamedQuery(name = "AccessToken.findLastAccessTokenNumber", query = "SELECT a FROM AccessToken a ORDER BY a.id DESC")})
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "athlete_id")
    private int athleteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 55)
    @Column(name = "access_token_code")
    private String accessTokenCode;
    @Column(name = "expires_at")
    private Integer expiresAt;

    public AccessToken() {
    }

    public AccessToken(Integer id) {
        this.id = id;
    }

    public AccessToken(Integer id, int athleteId, String accessTokenCode, int expiresAt) {
        this.id = id;
        this.athleteId = athleteId;
        this.accessTokenCode = accessTokenCode;
        this.expiresAt = expiresAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessToken)) {
            return false;
        }
        AccessToken other = (AccessToken) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.AccessToken[ id=" + id + " ]";
    }

}

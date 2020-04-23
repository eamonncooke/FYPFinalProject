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
@Table(name = "refresh_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RefreshToken.findAll", query = "SELECT r FROM RefreshToken r"),
    @NamedQuery(name = "RefreshToken.findByAthleteId", query = "SELECT r FROM RefreshToken r WHERE r.athleteId = :athleteId"),
    @NamedQuery(name = "RefreshToken.findByRefreshTokenCode", query = "SELECT r FROM RefreshToken r WHERE r.refreshTokenCode = :refreshTokenCode")})
public class RefreshToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "athlete_id")
    private Integer athleteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 55)
    @Column(name = "refresh_token_code")
    private String refreshTokenCode;

    public RefreshToken() {
    }

    public RefreshToken(Integer athleteId) {
        this.athleteId = athleteId;
    }

    public RefreshToken(Integer athleteId, String refreshTokenCode) {
        this.athleteId = athleteId;
        this.refreshTokenCode = refreshTokenCode;
    }

    public Integer getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(Integer athleteId) {
        this.athleteId = athleteId;
    }

    public String getRefreshTokenCode() {
        return refreshTokenCode;
    }

    public void setRefreshTokenCode(String refreshTokenCode) {
        this.refreshTokenCode = refreshTokenCode;
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
        if (!(object instanceof RefreshToken)) {
            return false;
        }
        RefreshToken other = (RefreshToken) object;
        if ((this.athleteId == null && other.athleteId != null) || (this.athleteId != null && !this.athleteId.equals(other.athleteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.RefreshToken[ athleteId=" + athleteId + " ]";
    }
    
}

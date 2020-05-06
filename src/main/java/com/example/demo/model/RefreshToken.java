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
@Table(name = "refresh_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RefreshToken.findAll", query = "SELECT r FROM RefreshToken r"),
    @NamedQuery(name = "RefreshToken.findById", query = "SELECT r FROM RefreshToken r WHERE r.id = :id"),
    @NamedQuery(name = "RefreshToken.findByAthleteId", query = "SELECT r FROM RefreshToken r WHERE r.athleteId = :athleteId"),
    @NamedQuery(name = "RefreshToken.findByRefreshTokenCode", query = "SELECT r FROM RefreshToken r WHERE r.refreshTokenCode = :refreshTokenCode"),
    @NamedQuery(name = "RefreshToken.findLastRefreshTokenNumber", query = "SELECT r FROM RefreshToken r ORDER BY r.id DESC")})
public class RefreshToken implements Serializable {

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
    @Column(name = "refresh_token_code")
    private String refreshTokenCode;

    public RefreshToken() {
    }

    public RefreshToken(Integer id) {
        this.id = id;
    }

    public RefreshToken(Integer id, int athleteId, String refreshTokenCode) {
        this.id = id;
        this.athleteId = athleteId;
        this.refreshTokenCode = refreshTokenCode;
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

    public String getRefreshTokenCode() {
        return refreshTokenCode;
    }

    public void setRefreshTokenCode(String refreshTokenCode) {
        this.refreshTokenCode = refreshTokenCode;
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
        if (!(object instanceof RefreshToken)) {
            return false;
        }
        RefreshToken other = (RefreshToken) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.RefreshToken[ id=" + id + " ]";
    }
    
}

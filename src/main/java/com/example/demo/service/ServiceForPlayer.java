/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.AccessToken;
import com.example.demo.model.Activity;
import com.example.demo.model.AuthUser;
import com.example.demo.model.DBUtil;
import com.example.demo.model.Player;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.Testing;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author cooke
 */
@Service
public class ServiceForPlayer {
    
    public AuthUser getUserByEmail(String email) {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        AuthUser user = (AuthUser) em.createNamedQuery("AuthUser.findByEmail")
                .setParameter("email", email)
                .getSingleResult();
        if(user == null)
            throw new UsernameNotFoundException("No user found");
        
        return user;
    }
    public Player getPlayersByUserId(AuthUser user) {

        //List<Player> list = repo.findAll();
        EntityManager em = DBUtil.getEmf().createEntityManager();
        List<Player> list = em.createNamedQuery("Player.findAll").getResultList();
        Player newP = new Player();
        for(Player p : list){
            if(p.getAuthUserId().getAuthUserId() == user.getAuthUserId())
                newP = p;
        }
        if(newP == null)
            throw new UsernameNotFoundException("No player found");
        
        return newP;
    }
    
    public void editPlayer(Player p) {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(p);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
    }
    
    public List<Testing> getAllTest() {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        List<Testing> list = em.createNamedQuery("Testing.findAll").getResultList();
        
        if(list.isEmpty())
            throw new UsernameNotFoundException("No Past Tests Found");
        
        return list;
    }
    public int getNewTestingNum()
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        Testing test = (Testing) em.createNamedQuery("Testing.findLastTestNumber")
                .setMaxResults(1).getSingleResult();      
        int listingNo = test.getTestId() + 1;
        return listingNo;
    }
    
    public void addNewTest(Testing test)
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(test);
        trans.commit();
    }
    
    public void addFirstAccessToken(AccessToken at)
    {
        System.out.println("addNewAccessToken");
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(at);
        trans.commit();
    }
    
    public void addFirstRefreshToken(RefreshToken rt)
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(rt);
        trans.commit();
    }
    
    public void updatePlayer(Player player)
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.merge(player);
        trans.commit();
    }
    
    public RefreshToken getRefreshTokenById(int athleteId){
        EntityManager em = DBUtil.getEmf().createEntityManager();
        RefreshToken rt = (RefreshToken) em.createNamedQuery("RefreshToken.findByAthleteId")
                .setParameter("athleteId", athleteId)
                .getSingleResult();
        if(rt == null)
            throw new UsernameNotFoundException("No user found");
        
        return rt;
    }
    
    public AccessToken getAccessTokenById(int athleteId){
        EntityManager em = DBUtil.getEmf().createEntityManager();
        AccessToken at = (AccessToken) em.createNamedQuery("AccessToken.findByAthleteId")
                .setParameter("athleteId", athleteId)
                .getSingleResult();
        if(at == null)
            throw new UsernameNotFoundException("No user found");
        
        return at;
    }
    
    public void updateNewAccessToken(AccessToken at){
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.merge(at);
        trans.commit();
    }
    
    public void updateNewRefreshToken(RefreshToken rt){
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.merge(rt);
        trans.commit();
    }
    
    public Activity getActivityByActivityID(String activityId){
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        Activity activity = (Activity) em.createNamedQuery("Activity.findByActivityId")
                .setParameter("activityId", activityId)
                .getSingleResult();
        
        if(activity == null)
            throw new UsernameNotFoundException("No activity found");
        
        return activity;
    }
    
    public void deleteAccessToken(int id) {
        EntityManager em = DBUtil.getEmf().createEntityManager();

        AccessToken t = em.find(AccessToken.class, id);

        EntityTransaction trans = em.getTransaction();

        trans.begin();
        em.remove(em.merge(t));
        trans.commit();
    }
    
    public void deleteRefreshToken(int id) {
        EntityManager em = DBUtil.getEmf().createEntityManager();

        RefreshToken t = em.find(RefreshToken.class, id);

        EntityTransaction trans = em.getTransaction();

        trans.begin();
        em.remove(em.merge(t));
        trans.commit();
    }
}

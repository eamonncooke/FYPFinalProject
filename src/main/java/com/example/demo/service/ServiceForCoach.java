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
public class ServiceForCoach {
    
    public List<Player> getAllPlayers() {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        List<Player> list = em.createNamedQuery("Player.findAll").getResultList();
        
        if(list.isEmpty())
            throw new UsernameNotFoundException("No Players found");
        
        return list;
    }
    public AuthUser getUserByEmail(String email) {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        AuthUser user = (AuthUser) em.createNamedQuery("AuthUser.findByEmail")
                .setParameter("email", email)
                .getSingleResult();
        if(user == null)
            throw new UsernameNotFoundException("No user found");
        
        return user;
    }
    public int getNewAuthUserId()
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        AuthUser user = (AuthUser) em.createNamedQuery("AuthUser.findLastAuthUserNumber")
                .setMaxResults(1).getSingleResult();      
        int userID = user.getAuthUserId()+ 1;
        return userID;
    }
    public int getNewPlayerId()
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        Player p = (Player) em.createNamedQuery("Player.findLastPlayerNumber")
                .setMaxResults(1).getSingleResult();      
        int playerId = p.getPlayerId()+ 1;
        return playerId;
    }
    
    public void editUser(AuthUser a) {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(a);
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
    
    public Player getPlayerByAuthUser(AuthUser user){
        EntityManager em = DBUtil.getEmf().createEntityManager();
        Player player = em.find(Player.class, user.getAuthUserId());
        if(player == null)
            throw new UsernameNotFoundException("No player found");
        
        return player;
    }
    
    public Player getPlayerByPlayerId(int playerId){
        EntityManager em = DBUtil.getEmf().createEntityManager();
        Player player = (Player) em.createNamedQuery("Player.findByPlayerId")
                .setParameter("playerId", playerId)
                .getSingleResult();
        if(player == null)
            throw new UsernameNotFoundException("No player found");
        
        return player;
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
    
    public void deleteFitnessTest(int id) {
        EntityManager em = DBUtil.getEmf().createEntityManager();

        Testing t = em.find(Testing.class, id);

        EntityTransaction trans = em.getTransaction();

        trans.begin();
        em.remove(em.merge(t));
        trans.commit();
    }
    
    public void deletePlayer(int id) {
        EntityManager em = DBUtil.getEmf().createEntityManager();

        Player p = em.find(Player.class, id);

        EntityTransaction trans = em.getTransaction();

        trans.begin();
        em.remove(em.merge(p));
        trans.commit();
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
    
    public void addNewTraining(Activity training)
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(training);
        trans.commit();
    }
    
    public void addPlayer(Player player)
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(player);
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
    
    public int getNewTrainingNum()
    {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        Activity a = (Activity) em.createNamedQuery("Activity.findLastActivityNumber")
                .setMaxResults(1).getSingleResult();      
        int listingNo = a.getId()+ 1;
        return listingNo;
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
    
    public List<Activity> getActivityList(){
        EntityManager em = DBUtil.getEmf().createEntityManager();
        List<Activity> list = em.createNamedQuery("Activity.findAll").getResultList();
        
        if(list.isEmpty())
            throw new UsernameNotFoundException("No activity found");
        
        return list;
    }
}

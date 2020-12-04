/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dto.TwitchAnalyticsDTO;
import dto.YouTubeAnalyticsDTO;
import entities.TwitchAnalytics;
import entities.User;
import entities.YouTubeAnalytics;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gamma
 */
public class SetupTestAnalytics {

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        setUpYTAnalytics(emf);
        setUpTwitchAnalytics(emf);
    }

    public static void setUpYTAnalytics(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        String userName = "user";

        //YouTube
        YouTubeAnalyticsDTO ytdto1 = new YouTubeAnalyticsDTO();
        YouTubeAnalyticsDTO ytdto2 = new YouTubeAnalyticsDTO();
        YouTubeAnalyticsDTO ytdto3 = new YouTubeAnalyticsDTO();

        ytdto1.setId("UC2C_jShtL725hvbm1arSV9w");
        ytdto1.setViews(1012);
        ytdto1.setSubscribers(50);
        ytdto1.setVideoCount(45);
        ytdto1.setSavedBy(userName);

        ytdto2.setId("UC2C_jShtL725hvbm1arSV9w");
        ytdto2.setViews(1102);
        ytdto2.setSubscribers(52);
        ytdto2.setVideoCount(46);
        ytdto2.setSavedBy(userName);

        ytdto3.setId("UC2C_jShtL725hvbm1arSV9w");
        ytdto3.setViews(1150);
        ytdto3.setSubscribers(55);
        ytdto3.setVideoCount(48);
        ytdto3.setSavedBy(userName);

        User user = em.find(User.class, userName);

        YouTubeAnalytics yt1 = new YouTubeAnalytics(ytdto1, user);
        YouTubeAnalytics yt2 = new YouTubeAnalytics(ytdto2, user);
        YouTubeAnalytics yt3 = new YouTubeAnalytics(ytdto3, user);

        em.persist(yt1);
        em.persist(yt2);
        em.persist(yt3);

        em.getTransaction().commit();
    }

    public static void setUpTwitchAnalytics(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        String userName = "user";

        //Twitch
        TwitchAnalyticsDTO twitchdto1 = new TwitchAnalyticsDTO();
        TwitchAnalyticsDTO twitchdto2 = new TwitchAnalyticsDTO();
        TwitchAnalyticsDTO twitchdto3 = new TwitchAnalyticsDTO();

        twitchdto1.setId("sivHD");
        twitchdto1.setViews(1012);
        twitchdto1.setFollowers(50);
        twitchdto1.setGame("Final Fantasy XIV");
        twitchdto1.setSavedBy(userName);

        twitchdto2.setId("SivHD");
        twitchdto2.setViews(1102);
        twitchdto2.setFollowers(52);
        twitchdto2.setGame("Nier Automata");
        twitchdto2.setSavedBy(userName);

        twitchdto3.setId("SivHD");
        twitchdto3.setViews(1150);
        twitchdto3.setFollowers(55);
        twitchdto3.setGame("Persona 5: Royal");
        twitchdto3.setSavedBy(userName);

        User user = em.find(User.class, userName);

        TwitchAnalytics twitch1 = new TwitchAnalytics(twitchdto1, user);
        TwitchAnalytics twitch2 = new TwitchAnalytics(twitchdto2, user);
        TwitchAnalytics twitch3 = new TwitchAnalytics(twitchdto3, user);

        em.persist(twitch1);
        em.persist(twitch2);
        em.persist(twitch3);

        em.getTransaction().commit();
    }
}

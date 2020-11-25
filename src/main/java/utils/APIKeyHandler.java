/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author gamma
 */
public class APIKeyHandler {

    private static String youTubeKey = null;
    private static String twitchKey = null;

    public static String getYouTubeKey() {
        if (youTubeKey == null) {
            if (System.getenv("YTKey") != null) {
                youTubeKey = System.getenv("YTKey");
            } else {
                try {
                    File yt = new File("yt.key");
                    Scanner scan = new Scanner(yt);
                    if(scan.hasNextLine()) {
                        youTubeKey = scan.nextLine();
                    }
                }catch (FileNotFoundException ex) {
                    System.out.println("An error occured reading yt.key ");
                    ex.printStackTrace();
                }
            }

        }
        return youTubeKey;
    }

    public static String getTwitchKey() {
        if (twitchKey == null) {
            boolean isDeployed = (System.getenv("DEPLOYED") != null);
            if (System.getenv("TWITCHKey") != null) {
                twitchKey = System.getenv("TWITCHKey");
            } else {
                try {
                    File twitch = new File("twitch.key");
                    Scanner scan = new Scanner(twitch);
                    if(scan.hasNextLine()) {
                        twitchKey = scan.nextLine();
                    }
                }catch (FileNotFoundException ex) {
                    System.out.println("An error occured reading twitch.key ");
                    ex.printStackTrace();
                }
            }

        }
        return twitchKey;
    }
    
    public static void main(String[] args) {
        System.out.println("twitch: " + getTwitchKey());
        System.out.println("yt: " + getYouTubeKey());
    }

}

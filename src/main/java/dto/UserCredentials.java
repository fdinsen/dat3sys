/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author gamma
 */
public class UserCredentials {
    private String username;
    private String password;

    public UserCredentials() {
    }

    public UserCredentials(String username, String password) {
        if(username != null) {
            this.username = username;
        }else {
            this.username = "";
        }
        if(password != null) {
            this.password = password;
        }else {
            this.password = "";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}

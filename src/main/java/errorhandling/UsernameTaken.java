/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author gamma
 */
public class UsernameTaken extends Exception {
    public UsernameTaken(String username) {
        super("Username " + username + " is already in use");
    }
}

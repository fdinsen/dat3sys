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
public class LoginInvalid extends Exception {
    public LoginInvalid(String reason) {
        super("Invalid username or password, " + reason);
    }
}

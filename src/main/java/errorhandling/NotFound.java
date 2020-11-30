/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author simon
 */
public class NotFound extends Exception {
    public NotFound(String id){
        super("No content found by id '" + id + "'");
    }
}

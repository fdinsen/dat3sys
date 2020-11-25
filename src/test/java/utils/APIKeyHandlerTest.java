/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gamma
 */
public class APIKeyHandlerTest {
    
    public APIKeyHandlerTest() {
    }

    /**
     * Test of getYouTubeKey method, of class APIKeyHandler.
     * Tests that the key is actually set, not whether it's set correctly
     */
    @Test
    public void testGetYouTubeKey() {
        String result = APIKeyHandler.getYouTubeKey();
        assertNotNull(result);
    }

    /**
     * Test of getTwitchKey method, of class APIKeyHandler.
     * Tests that the key is actually set, not whether it's set correctly
     */
    @Test
    public void testGetTwitchKey() {
        String result = APIKeyHandler.getTwitchKey();
        assertNotNull(result);
    }
    
}

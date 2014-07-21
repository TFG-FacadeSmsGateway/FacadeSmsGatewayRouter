/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.tests;

import com.preferya.facadesmsgatewayrouter.providers.TwilioProvider;

/**
 *
 * @author Sergio
 */
public class TestTwilioProvider {
    
    public static void main(String[] args) {
        TwilioProvider tp = new TwilioProvider();
        
        System.out.println(tp.toString());
    }
    
}

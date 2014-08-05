/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.tests;

import com.preferya.facadesmsgatewayrouter.providers.IProvider;

/**
 *
 * @author Sergio
 */
public class TestClassLoader {
    
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        ClassLoader classLoader = TestClassLoader.class.getClassLoader();
        Class providerClass = classLoader.loadClass("com.preferya.facadesmsgatewayrouter.providers."+"InfoSmsProvider");
        IProvider _provider = (IProvider) providerClass.newInstance();
        
        System.out.println(_provider.getNumIntentRemaining());
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

/**
 *
 * @author Sergio
 */
public interface IProvider {
    
    public double getCostByMessage();
    
    public int numMessageSendedActualMonth();
    
    public int getTimeSleepBetweenMessages();
    
    public void sendMessage(String phone, String code, String iso_lang);
    
    //private void errorOcurred();
    
    //private boolean checkTimeOut();
    
    //private int numIntents(String code);
    
    //private String systemCheapest();
    
    //private void sleep(int numMiliSecond);
    
}

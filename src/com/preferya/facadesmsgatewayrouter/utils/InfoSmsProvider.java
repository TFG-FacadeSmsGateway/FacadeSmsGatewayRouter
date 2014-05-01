/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

/**
 *
 * @author Sergio
 */
public class InfoSmsProvider implements IProvider{
    
    private static final String CURRENCY = "€";
    private static final String COSTBYSMS = "0.11";
    private static final String IDSMS = "084786679";
    private static final String PASS = "5468868";
    
    private MailUtils2 mailService;
    private Properties repository;
    
    public InfoSmsProvider() {
        this.mailService = new MailUtils2();
        this.repository = new Properties();
    }
    
    @Override
    public double getCostByMessage() {
        return Double.valueOf(this.COSTBYSMS);
    }

    @Override
    public int numMessageSendedActualMonth() {
        int _ret;
        _ret = (int) this.repository.get(String.valueOf(this.getActualMonth()));
        return _ret;
    }

    @Override
    public int getTimeSleepBetweenMessages() {
        //TODO:
        return 0;
    }

    @Override
    public void sendMessage(String phone, String code, String iso_lang) {
        //TODO: Send message.
        java.util.Date _date= new java.util.Date();
	//Timestamp _ts = new Timestamp(_date.getTime());
        
        String _ref ="+" +  phone + _date.getYear() + _date.getMonth() + _date.getDay() 
                + _date.getHours() + _date.getMinutes() + _date.getSeconds();   
                
        String _message = "#idsms " + IDSMS + "#\n"
                + "#referencia " + _ref + "#\n"
                + "#firma " + TokenUtil.md5(_ref) + "#\n"
                + "#texto Bienvenido a Preferya!, su código de validación es " + code + "#\n"
                + "#destinos " + "+" + phone + "#"; //Cuidado con el "+"!!
        this.mailService.sendMessage("sms@mx.infoe.es","Código de Verificación", _message);
    }

    private String getActualMonth() {
        Calendar fecha = new GregorianCalendar();
        return String.valueOf(fecha.get(Calendar.MONTH))+String.valueOf(fecha.get(Calendar.YEAR));
        
    }
    
}

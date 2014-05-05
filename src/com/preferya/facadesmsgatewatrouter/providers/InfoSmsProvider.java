/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewatrouter.providers;

import com.preferya.facadesmsgatewayrouter.utils.FileUtils;
import com.preferya.facadesmsgatewayrouter.utils.MailUtils;
import com.preferya.facadesmsgatewayrouter.utils.TokenUtil;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Sergio
 */
public class InfoSmsProvider implements IProvider{
    
    private String currency;
    private String costBySms;
    private String idsms;
    private String pass;
    private String saldo;
    private String iva;
    
    private MailUtils mailService;
    //private Properties repository;
    
    private int numIntentRemaining;
    
    public InfoSmsProvider() {
        this.mailService = new MailUtils();
        //this.repository = new Properties();
        
        FileUtils fu = new FileUtils("InfoSmsProvider.txt");
        Map<String, String> mapSetting = fu.getMapProviderPropSettings();
        
        this.currency = mapSetting.get("currency");
        this.costBySms = mapSetting.get("costbysms");
        this.idsms = mapSetting.get("idsms");
        this.pass = mapSetting.get("pass");
        this.saldo = mapSetting.get("saldo");
        this.iva = mapSetting.get("iva");
        
        Double _saldo = Double.valueOf(this.saldo);
        Double _iva = Double.valueOf(this.iva);
        Double _costBySms = Double.valueOf(this.costBySms);
        double aux = 1.0; //Para multiplicar por 1'21.
        this.numIntentRemaining = (int)(_saldo/(_costBySms*(aux+_iva)));
    }

    @Override
    public void sendMessage(String phone, String code, String iso_lang) {
        java.util.Date _date= new java.util.Date();
        
        String _ref ="+" +  phone + _date.getYear() + _date.getMonth() + _date.getDay() 
                + _date.getHours() + _date.getMinutes() + _date.getSeconds();   
                
        String _message = "#idsms " + this.idsms + "#\n"
                + "#referencia " + _ref + "#\n"
                + "#firma " + TokenUtil.md5(_ref) + "#\n"
                + "#texto Bienvenido a Preferya!, su código de validación es " + code + "#\n"
                + "#destinos " + "+" + phone + "#"; //Cuidado con el "+"!!
        this.mailService.sendMessage("sms@mx.infoe.es", _message);
    }

    private String getActualMonth() {
        Calendar fecha = new GregorianCalendar();
        return String.valueOf(fecha.get(Calendar.MONTH))+String.valueOf(fecha.get(Calendar.YEAR));
        
    }

    @Override
    public void turnOffProvider() {
        //TODO: actualizar fichero con el saldo restante segun el numero de intentos.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumIntentRemaining() {
        return this.numIntentRemaining;
    }
    
    public String toString(){
        return this.idsms + " - " + this.saldo + " - " + this.currency;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewatrouter.providers;

import com.preferya.facadesmsgatewayrouter.utils.FileDeleteUtils;
import com.preferya.facadesmsgatewayrouter.utils.FileReadUtils;
import com.preferya.facadesmsgatewayrouter.utils.FileWriteUtils;
import com.preferya.facadesmsgatewayrouter.utils.MailsUtils;
import com.preferya.facadesmsgatewayrouter.utils.TokenUtil;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
    
    private MailsUtils mailService;
    //private Properties repository;
    
    private int numIntentRemaining;
    
    public InfoSmsProvider() {
        this.mailService = new MailsUtils();
        //this.repository = new Properties();
        
        FileReadUtils fu = new FileReadUtils("InfoSmsProvider.txt");
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
        
        fu.closeFile();
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
        this.numIntentRemaining--;
    }

    @Override
    public void turnOffProvider() {
        //TODO: actualizar fichero con el saldo restante segun el numero de intentos.
        Map<String, String> mapSetting = new HashMap<String, String>();
        
        mapSetting.put("currency", this.currency);
        mapSetting.put("costbysms", this.costBySms);
        mapSetting.put("idsms", this.idsms);
        mapSetting.put("pass", this.pass);
        mapSetting.put("saldo", this.saldo);
        mapSetting.put("iva", this.iva);
        
        FileDeleteUtils fd = new FileDeleteUtils("InfoSmsProvider.txt");
        fd.deleteFile();
        fd.closeFile();
        
        FileWriteUtils fw = new FileWriteUtils("InfoSmsProvider.txt");
        fw.writeFile(mapSetting);
        fw.closeFile();
        
    }

    @Override
    public int getNumIntentRemaining() {
        return this.numIntentRemaining;
    }
    
    public String toString(){
        return this.idsms + " - " + this.saldo + " - " + this.currency;
    }
    
}

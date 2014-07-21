/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.providers;

import com.preferya.facadesmsgatewayrouter.utils.FileDeleteUtils;
import com.preferya.facadesmsgatewayrouter.utils.FileReadUtils;
import com.preferya.facadesmsgatewayrouter.utils.FileWriteUtils;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Sergio
 */
public class TwilioProvider implements IProvider {

    private String accountSID;
    private String authToken;
    private String defaultPhone;
    
    private String currency;
    private String costBySms;
    private String idsms;
    private String pass;
    private String saldo;
    private String iva;
    
    private int numIntentRemaining;
    
    public TwilioProvider() {
        
        FileReadUtils fu = new FileReadUtils("TwilioProvider.txt");
        Map<String, String> mapSetting = fu.getMapProviderPropSettings();
        
        //TODO: cargar atributos necesarios para este proveedor
        this.accountSID = mapSetting.get("accountSID");
        this.authToken = mapSetting.get("authToken");
        this.defaultPhone = mapSetting.get("defaultPhone");
        
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
    public void turnOffProvider() {
        //TODO: actualizar fichero con el saldo restante segun el numero de intentos.
        Map<String, String> mapSetting = new HashMap<String, String>();
        
        mapSetting.put("accountSID", this.accountSID);
        mapSetting.put("authToken", this.authToken);
        mapSetting.put("defaultPhone", this.defaultPhone);
        
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

    @Override
    public void sendMessage(String phone, String code, String iso_lang) {
        TwilioRestClient client = new TwilioRestClient(this.accountSID, this.authToken);
 
        // Build a filter for the MessageList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", "Bienvenido a Preferya!, su código de validación es " + code));
        params.add(new BasicNameValuePair("To", "+" + phone));
        params.add(new BasicNameValuePair("From", this.defaultPhone));
        //params.add(new BasicNameValuePair("MediaUrl", "http://www.example.com/hearts.png"));


        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message;
        try {
            message = messageFactory.create(params);
            this.numIntentRemaining--;
            System.out.println(message.getSid());
        } catch (TwilioRestException ex) {
            Logger.getLogger(TwilioProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

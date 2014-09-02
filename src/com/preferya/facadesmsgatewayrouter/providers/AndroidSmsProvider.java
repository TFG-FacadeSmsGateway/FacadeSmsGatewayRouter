/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.providers;

import com.preferya.facadesmsgatewayrouter.utils.FileDeleteUtils;
import com.preferya.facadesmsgatewayrouter.utils.FileReadUtils;
import com.preferya.facadesmsgatewayrouter.utils.FileWriteUtils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergio
 */
public class AndroidSmsProvider implements IProvider {

    private String host;
    private String port;
    private String target;
    private int intents;
    
    private URL url;
    HttpURLConnection connection;
    
    DataOutputStream wr;
    
    public AndroidSmsProvider() throws MalformedURLException, IOException{
        FileReadUtils fu = new FileReadUtils("AndroidSMSProvider.txt");
        Map<String, String> mapSetting = fu.getMapProviderPropSettings();
        
        host = mapSetting.get("host");
        port = mapSetting.get("port");
        target = mapSetting.get("target");
        intents = Integer.valueOf(mapSetting.get("intents"));
        
        this.url = new URL(target);
        this.connection = (HttpURLConnection) this.url.openConnection();
        
        this.connection.setRequestMethod("GET");
    }
    
    @Override
    public void turnOffProvider() {
        
        Map<String, String> mapSetting = new HashMap<String, String>();
        
        mapSetting.put("host", this.host);
        mapSetting.put("port", this.port);
        mapSetting.put("target", this.target);
        mapSetting.put("intents", String.valueOf(this.intents));
        
        FileDeleteUtils fd = new FileDeleteUtils("AndroidSMSProvider.txt");
        fd.deleteFile();
        fd.closeFile();
        
        FileWriteUtils fw = new FileWriteUtils("AndroidSMSProvider.txt");
        fw.writeFile(mapSetting);
        fw.closeFile();
        
        try {
            this.wr.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getNumIntentRemaining() {
        return this.intents;
    }

    @Override
    public void sendMessage(String phone, String code, String iso_lang) {
        try {
            this.wr = new DataOutputStream( this.connection.getOutputStream());
            String parameters = phone + "," + code + "," + iso_lang;
            wr.writeBytes(parameters);
            wr.flush();
            this.intents--;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.models;

import java.io.Serializable;

/**
 *
 * @author Sergio
 */
public class DataMessageEntity implements Serializable, IMessageEntity {
    
    private String phone;
    private String validactionCode;
    private String isoLang;
    
    public DataMessageEntity() {
        
    }
    
    public DataMessageEntity(String[] items) {
        this.phone = items[0];
        this.validactionCode = items[1];
        this.isoLang = items[2];
    }
    
    public DataMessageEntity(String phone, String validationCode, String isoLang) {
        this.phone = phone;
        this.validactionCode = validationCode;
        this.isoLang = isoLang;
    }
    
    public String toString() {
        String _ret = "";
        
        _ret += this.phone + ",";
        _ret += this.validactionCode + ",";
        _ret += this.isoLang;
        
        return _ret;
    }
    
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return the validactionCode
     */
    public String getValidactionCode() {
        return validactionCode;
    }

    /**
     * @return the isoLang
     */
    public String getIsoLang() {
        return isoLang;
    }

    @Override
    public String getAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValidationCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getArgs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

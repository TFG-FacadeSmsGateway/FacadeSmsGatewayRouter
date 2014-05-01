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
public class ControlMessageEntity implements Serializable, IMessageEntity {
    
    private String action;
    private String args;
    
    public ControlMessageEntity() {
        
    }
    
    public ControlMessageEntity(String[] items, int num) {
        this.action = items[0];
    }
    
    public ControlMessageEntity(String[] items) {
        this.action = items[0];
        this.args = items[1];
    }
    
    public ControlMessageEntity(String action, String args) {
        this.action = action;
        this.args = args;
    }
    
    public String toString() {
        String _ret = "";
        
        _ret += this.action + ",";
        _ret += this.args;
        
        return _ret;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }
    
    /**
     * @return the args
     */
    public String getArgs() {
        return args;
    }

    @Override
    public String getIsoLang() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPhone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValidationCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

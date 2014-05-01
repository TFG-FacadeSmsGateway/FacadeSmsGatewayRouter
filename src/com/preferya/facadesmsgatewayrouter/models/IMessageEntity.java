/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.models;

/**
 *
 * @author Sergio
 */
public interface IMessageEntity {

    public String getIsoLang();

    public String getAction();
    
    public String getPhone();
    
    public String getValidationCode();

    public String getArgs();
    
}

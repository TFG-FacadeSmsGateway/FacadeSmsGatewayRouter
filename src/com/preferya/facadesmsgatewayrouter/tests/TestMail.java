/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.tests;

import com.preferya.facadesmsgatewayrouter.utils.MailUtils;
import com.preferya.facadesmsgatewayrouter.utils.MailUtils;
import com.preferya.facadesmsgatewayrouter.utils.TokenUtil;
import java.sql.Timestamp;

/**
 *
 * @author Sergio
 */
public class TestMail {
    
    private static final String CURRENCY = "€";
    private static final String COSTBYSMS = "0.11";
    private static final String IDSMS = "084786679";
    private static final String PASS = "5468868";
    
    public static void main(String[] args) {
        MailUtils mail = new MailUtils();
        
        java.util.Date _date= new java.util.Date();
	//Timestamp _ts = new Timestamp(_date.getTime());
        
        String _ref = "+34661155992" + _date.getYear() + _date.getMonth() + _date.getDay() 
                + _date.getHours() + _date.getMinutes() + _date.getSeconds();  
                
        String _message = "#idsms " + IDSMS + "#\n"
                + "#referencia " + _ref + "#\n"
                + "#firma " + TokenUtil.md5(_ref) + "#\n"
                + "#texto Bienvenido a Preferya!, su código de validación es " + "12345" + "#\n"
                + "#destinos " + "+" + "34661155992" + "#"; //Cuidado con el "+"!!
        
        //System.out.println(_message);
        mail.sendMessage("sms@mx.infoe.es", _message);
        //mail.sendMessage("sergiorodriguezcalvo@gmail.com","Código de Verificación", _message);
        
        //System.out.println(IDSMS + _ref + PASS);
        //System.out.println(TokenUtil.md5(_ref));
        
        System.out.println("ENVIADO!");
    }
    
}

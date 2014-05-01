/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.tests;

import com.preferya.facadesmsgatewayrouter.utils.TokenUtil;

/**
 *
 * @author Sergio
 */
public class TestMD5 {
    
    public static void main(String[] args) {
        String _token = TokenUtil.md5("prueba");
                
        System.out.println(_token);
    }
    
}

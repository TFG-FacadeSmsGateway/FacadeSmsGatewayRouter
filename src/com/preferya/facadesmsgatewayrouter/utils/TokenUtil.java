/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 *
 * @author Sergio
 */
public class TokenUtil {
    
    private static final String IDSMS = "084786679";
    private static final String PASSWORD = "5468868";
    
    public static String md5(String _ref){
        try {
            String _textPlain = IDSMS + _ref + PASSWORD;
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(_textPlain.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
              sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
           }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    
    
    /*public static String md5(String _ref) {
        
        String _textPlain = IDSMS + _ref + PASSWORD;
        
        String _md5 = "";
        try
        {
            MessageDigest _crypt = MessageDigest.getInstance("MD5");
            _crypt.reset();
            _crypt.update(_textPlain.getBytes("UTF-8"));
            _md5 = byteToHex(_crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return _md5;
    }*/

    
    
    private static String byteToHex(final byte[] hash) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : hash) {
            builder.append(String.format("%02x", 0xFF & b));
        }
        return builder.toString();
        
        /*StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();*/
    }
    
    
    
    
    
}

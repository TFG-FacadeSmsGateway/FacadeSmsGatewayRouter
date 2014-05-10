/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

/**
 *
 * @author Sergio
 */
public class FileWriteUtils {
    
    private FileWriter fw;
    private PrintWriter pw;
    
    public FileWriteUtils(String filename){
        try{
            this.fw = new FileWriter(filename);
            this.pw = new PrintWriter(filename);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void writeFile(Map<String,String> mapFile){
        
    }
    
    public void closeFile(){
        try{
            this.fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}

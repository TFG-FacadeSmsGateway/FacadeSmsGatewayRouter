/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sergio
 */
public class FileWriteUtils {
    
    //private FileWriter fw;
    private PrintWriter pw;
    
    public FileWriteUtils(String filename){
        try{
            //this.fw = new FileWriter(filename);
            this.pw = new PrintWriter(filename);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void writeFile(Map<String,String> mapFile){
        try{

            for(String key : mapFile.keySet()){
                String linea = key + "=" + mapFile.get(key);
                pw.println(linea);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void closeFile(){
        try{
            this.pw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}

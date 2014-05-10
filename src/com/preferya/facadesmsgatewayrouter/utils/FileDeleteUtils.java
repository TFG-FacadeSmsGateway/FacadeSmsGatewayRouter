/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Sergio
 */
public class FileDeleteUtils {
    private File file;
    private FileReader fr;
    private BufferedReader br;
    
    public FileDeleteUtils(String fileName) {
        try{
            this.file = new File(fileName);
            this.fr = new FileReader(this.file);
            this.br = new BufferedReader(this.fr);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                /*if(null != this.fr){
                    this.fr.close();
                }*/
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
    }
    
    public void deleteFile(){
        this.file.delete();
    }
    
    public void closeFile(){
        try{
            this.fr.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

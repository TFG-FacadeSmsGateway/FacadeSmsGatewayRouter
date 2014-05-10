/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sergio
 */
public class FileReadUtils {
    
    private File file;
    private FileReader fr;
    private BufferedReader br;
    
    public FileReadUtils(String fileName) {
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
    
    public Map<String, String> getMapProviderPropSettings() {
        try{
            Map<String, String> _ret = new HashMap<String, String>();

            String _linea;
            while((_linea=this.br.readLine()) != null){
                String[] _splits = _linea.split("=");
                _ret.put(_splits[0], _splits[1]);
            }

            return _ret;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public void closeFile(){
        try{
            this.fr.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}

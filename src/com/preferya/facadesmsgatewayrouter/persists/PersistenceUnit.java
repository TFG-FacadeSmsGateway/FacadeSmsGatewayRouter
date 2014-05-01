/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.persists;

import com.preferya.facadesmsgatewayrouter.models.IMessageEntity;
import java.sql.*;

/**
 *
 * @author Sergio
 */
public class PersistenceUnit {
    
    private Connection conection;
    private String user;
    private String password;
    private String url;
    
    public PersistenceUnit() throws SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        this.user = "root";
        this.password = "root";
        this.url = "jdbc:mysql://localhost:3006/preferya";
        
        try{
            this.conection = DriverManager.getConnection (this.url, this.user, this.password);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public double getCurrentCost() {
        String _query = "SELECT sum(cost) FROM smssended WHERE month = ? and year = ?"; //TODO: meter el mes;
        double _ret = 0.0;
        java.util.Date _date= new java.util.Date();
	String _currentMonth = String.valueOf(_date.getMonth());
        String _currentYear = String.valueOf(_date.getYear());
        try {
            PreparedStatement _ps = this.conection.prepareStatement(_query);
            _ps.setString(1, _currentMonth);
            _ps.setString(2, _currentYear);
            ResultSet _rs = _ps.executeQuery();
            
            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (_rs.next())
            {
                _ret = _rs.getDouble("cost");
            }
            //this.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return _ret;
    }
    
    public void updateCost(double cost) {
        java.util.Date _date= new java.util.Date();
	String _currentMonth = String.valueOf(_date.getMonth());
        String _currentYear = String.valueOf(_date.getYear());
        String _insert = "insert into smssended values (" + cost + "," + _currentMonth 
                    + "," + _currentYear + ")";
        double _oldVal = this.oldValueCost(_currentMonth, _currentYear);
        String _update = "update smssended set (" + (_oldVal + cost) + "," + _currentMonth 
                    + "," + _currentYear + ")";
        try {
            if(exist(_currentMonth, _currentYear)){ //update
                PreparedStatement _ps = this.conection.prepareStatement(_update);
                _ps.executeUpdate(_update);
            } else { //insert
                PreparedStatement _ps = this.conection.prepareStatement(_insert);
                _ps.executeUpdate(_insert);
            }
            
            
            //_st.close();
            //this.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    //TODO: metodos
    
    private boolean exist(String _currentMonth, String _currentYear) {
        String _query = "SELECT cost FROM smssended WHERE month = ? and year = ?"; //TODO: meter el mes;
        boolean _ret = false;
        try {
            PreparedStatement _ps = this.conection.prepareStatement(_query);
            _ps.setString(1, _currentMonth);
            _ps.setString(2, _currentYear);
            ResultSet _rs = _ps.executeQuery();
            
            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (_rs.next())
            {
                _ret = true;
            }
            //this.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return _ret;
    }
    
     private double oldValueCost(String _currentMonth, String _currentYear) {
        String _query = "SELECT cost FROM smssended WHERE month = ? and year = ?"; //TODO: meter el mes;
        double _ret = 0.0;
        try {
            PreparedStatement _ps = this.conection.prepareStatement(_query);
            _ps.setString(1, _currentMonth);
            _ps.setString(2, _currentYear);
            ResultSet _rs = _ps.executeQuery();
            
            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (_rs.next())
            {
                _ret = _rs.getDouble("cost");
            }
            //this.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return _ret;
    }
    
    public void close() throws SQLException {
        this.conection.close();
    }
    
}

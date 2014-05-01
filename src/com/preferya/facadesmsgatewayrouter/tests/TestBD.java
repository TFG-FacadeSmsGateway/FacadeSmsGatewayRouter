/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.tests;

import com.preferya.facadesmsgatewayrouter.persists.PersistenceUnit;
import java.sql.SQLException;

/**
 *
 * @author Sergio
 */
public class TestBD {
    
    public static void main(String[] args) throws SQLException {
        PersistenceUnit _p = new PersistenceUnit();
        
        _p.updateCost(0.11);
        
        
    }
    
}

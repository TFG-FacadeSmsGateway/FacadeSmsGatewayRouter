/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.services;

import com.preferya.facadesmsgatewayrouter.models.ControlMessageEntity;
import com.preferya.facadesmsgatewayrouter.models.DataMessageEntity;
import com.preferya.facadesmsgatewayrouter.models.IMessageEntity;
import com.preferya.facadesmsgatewatrouter.providers.IProvider;
import com.preferya.facadesmsgatewatrouter.providers.InfoSmsProvider;
import com.preferya.facadesmsgatewayrouter.utils.RabbitMQUtils;
import com.preferya.facadesmsgatewayrouter.persists.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Sergio
 */
public class FacadeSmsGetawayRouter {
    
    public static final Double MAXMONTH = 10.0;
    
    public static void main(String[] args) throws IOException, InterruptedException, SQLException{
        
        String _country_iso_code = args[0];
        
        String[] _modules;
        
        if (args.length > 1) {
            //TODO: obtener los proveedores
            
            //= new String[args.length-1];
            //Copy modules from args
            //for(int _index = 1; _index < _modules.length; _index++){
            //    _modules[_index] = args[_index];
            //}
        }
        
        RabbitMQUtils _queue = new RabbitMQUtils(_country_iso_code);
        
        List<IProvider> _providers = new LinkedList<IProvider>();
        //TODO: cargar los proveedores
        //for(String module : _modules) {
        //    IProvider _provider;
        //    _provider = getProvider(module);
        //    _providers.add(_provider);
        //}
        
        boolean _stop = false;
        
        while(!_stop){
            //Read a message from queue
            String _message = _queue.reciveMessage();
            System.out.println(_message); //Borrar
            IMessageEntity _messageEntity = splitMessage(_message);
            
            if(_messageEntity instanceof ControlMessageEntity) { //Control Mssg case
                if (_messageEntity.getAction().equalsIgnoreCase("stop")) { //stop case
                    _stop = true;
                    System.out.println("STOP!");
                } else if(_messageEntity.getAction().equalsIgnoreCase("add_country")){
                    System.out.println("add_country");
                    //No hacer nada mas aqui
                }
                /*}else if (_messageEntity.getAction().equalsIgnoreCase("add_provider")) { //add case
                    if(!existProvider(_messageEntity, _providers)){
                        String[] _splits = _messageEntity.getArgs().split(",");
                        _providers.add(getProvider(_splits[1]));
                        System.out.println("ADD_PROVIDER");
                    }else {
                        System.out.println("PROVIDER HAS ALREADY BEEN ADDED.");
                    }
                }else if (_messageEntity.getAction().equalsIgnoreCase("remove_provider")) { //remove case
                    if(existProvider(_messageEntity, _providers)){
                        String[] _splits = _messageEntity.getArgs().split(",");
                        removeProvider(_providers, _splits[1]);
                        System.out.println("REMOVE_PROVIDER");
                    }else {
                        System.out.println("PROVIDER DOES NOT EXISTS");
                    }
                }*/
            }else {
                //TODO: Choose destination acording to routing table
                //System.out.println("Mensaje");
                
                //TODO: Send sms using Provider
                //IProvider _provider = new InfoSmsProvider(); //TODO: choose correct provider by parameters
                //Integer _numIntents = 0;
                /*double _expense = MAXMONTH - _persistence.getCurrentCost();
                double _cost = _provider.getCostByMessage();
                _expense = _expense - _cost;
                Boolean _st1 = _expense >= 0;
                if(_st1) {
                    _provider.sendMessage(_messageEntity.getPhone(), _messageEntity.getValidationCode(), _messageEntity.getIsoLang());
                    _persistence.updateCost(_cost);
                }*/
                System.out.println(_message.toString());
            }
            //_stop = true; //Borrar
        }
        
        _queue.closeConnection();
        
        System.out.println("Todo correcto!");
    }
      
/*    
    private static IProvider getProvider(String module) {
        IProvider _ret = null;
        
        if(module.equalsIgnoreCase("InfoSmsProvider")){
            _ret = new InfoSmsProvider();
        }
        
        return _ret;
    }
*/

    private static IMessageEntity splitMessage(String _message) {
        IMessageEntity _ret;
        String[] _splits = _message.split(",");
        
        if(_splits[0].equalsIgnoreCase("stop")) {
            _ret = new ControlMessageEntity(_splits); //Constructor for stop case
        }else if(_splits[0].equalsIgnoreCase("add_country")){
            _ret = new ControlMessageEntity(_splits);
        }else if(_splits[0].equalsIgnoreCase("add_provider")){
            _ret = new ControlMessageEntity(_splits);
        }else if(_splits[0].equalsIgnoreCase("remove_provider")){
            _ret = new ControlMessageEntity(_splits);
        }else {
            _ret = new DataMessageEntity(_splits);
        }
        
        return _ret;
    }
 /*
    private static boolean existProvider(IMessageEntity _messageEntity, List<IProvider> _providers) {
        boolean _ret = false;
        String _args = _messageEntity.getArgs();
        
        String[] splits = _args.split(",");
        String _provider = splits[1];
        
        for(IProvider _item:_providers) {
            if(_item.getClass().getCanonicalName().equalsIgnoreCase(_provider)){
                _ret = true;
            }
        }
        
        return _ret;
    }

    private static void removeProvider(List<IProvider> _providers, String _provider) {
        for(IProvider _item:_providers) {
            if(_item.getClass().getCanonicalName().equalsIgnoreCase(_provider)){
                _providers.remove(_item);
            }
        }
    }
  */  
}


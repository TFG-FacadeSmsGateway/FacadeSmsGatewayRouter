/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.services;

import com.preferya.facadesmsgatewayrouter.models.ControlMessageEntity;
import com.preferya.facadesmsgatewayrouter.models.DataMessageEntity;
import com.preferya.facadesmsgatewayrouter.models.IMessageEntity;
import com.preferya.facadesmsgatewayrouter.providers.IProvider;
import com.preferya.facadesmsgatewayrouter.providers.InfoSmsProvider;
import com.preferya.facadesmsgatewayrouter.providers.TwilioProvider;
import com.preferya.facadesmsgatewayrouter.utils.RabbitMQUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Sergio
 */
public class FacadeSmsGetawayRouter {
    //TODO: cambiar los println por un sistema de log
    
    public static void main(String[] args) throws IOException, InterruptedException{
        
        String _country_iso_code = args[0];
        
        List<IProvider> _providers = new LinkedList<IProvider>();
        
        if (args.length > 1) {
            //Copy modules from args
            for(int _index = 1; _index < args.length; _index++){
                _providers.add(getProvider(args[_index]));
            }
        }
        
        RabbitMQUtils _queue = new RabbitMQUtils(_country_iso_code);
        
        /*List<IProvider> _providers = new LinkedList<IProvider>();
        
        _providers.add(getProvider("InfoSmsProvider"));
        
        RabbitMQUtils _queue = new RabbitMQUtils("es_es");*/
        
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
                }else if (_messageEntity.getAction().equalsIgnoreCase("add_provider")) { //add case
                    System.out.println("ADD_PROVIDER");
                    if(!existProvider(_messageEntity, _providers)){
                        String[] _splits = _messageEntity.getArgs().split(",");
                        try{
                            ClassLoader _classLoader = FacadeSmsGetawayRouter.class.getClassLoader();
                            Class _providerClass = _classLoader.loadClass("com.preferya.facadesmsgatewayrouter.providers."+_splits[0]);
                            IProvider _provider = (IProvider) _providerClass.newInstance();
                            _providers.add(_provider);
                        }catch(Exception e){
                            System.out.println("CAN NOT LOAD CLASS FROM CLASSPATH");
                            e.printStackTrace();
                        }
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
                }
            }else {
                System.out.println("SEND MESSAGE");
                
                //Get next provider with less intents
                IProvider _provider = getNextProvider(_providers); 
                _provider.sendMessage(_messageEntity.getPhone(), _messageEntity.getValidationCode(), _messageEntity.getIsoLang());
                //System.out.println(_message.toString());
            }
        }
        
        _queue.closeConnection();
        
        System.out.println("Todo correcto!");
    }
       
    private static IProvider getProvider(String module) {
        IProvider _ret = null;
        
        if(module.equalsIgnoreCase("InfoSmsProvider")){
            _ret = new InfoSmsProvider();
        }else if(module.equalsIgnoreCase("TwilioProvider")){
            _ret = new TwilioProvider();
        }
        
        if(_ret == null){
            throw new NullPointerException();
            //TODO: log del problema cuando este montado el sistema de log
        }
        
        return _ret;
    }

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
 
    private static boolean existProvider(IMessageEntity _messageEntity, List<IProvider> _providers) {
        boolean _ret = false;
        String _args = _messageEntity.getArgs();
        
        String[] splits = _args.split(",");
        String _provider = splits[0];
        
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

    private static IProvider getNextProvider(List<IProvider> _providers) {
        IProvider _ret = new InfoSmsProvider();//Por defecto
        
        for(IProvider item:_providers){
            if(_ret.getNumIntentRemaining() < item.getNumIntentRemaining()){
                _ret = item;
            }
        }
        
        return _ret;
    }
 
}


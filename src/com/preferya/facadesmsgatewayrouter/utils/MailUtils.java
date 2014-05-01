/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author Sergio
 */
public class MailUtils {
    
    private final static String CONFIG_FILE = "mail.props.txt";
    private String sServidorCorreo;
    private String sCorreoOrigen;
    private String[] asCorreoDestino;
    
    public MailUtils() {
         this.init();
    }
    
    /**
     * Método para inicializar los valores del seridor de correo,
     * se cargan desde un fichero de configuración con los siguientes valores:
     * app.servidorCorreo=smtp.xxxx
     * app.correoOrigen=origenxxx@xxx.com
     * app.correoDestino=dst1@xxx.com,dst2@xxx.com,...,dstn@xxx.com
     */
    private void init () {
        try {
            Properties props = new Properties();
            props.load( new FileInputStream( CONFIG_FILE ) );

            sServidorCorreo = props.getProperty( "app.servidorCorreo" );
            sCorreoOrigen = props.getProperty( "app.correoOrigen" );
            String sTmp = props.getProperty( "app.correoDestino" );
            String[] asTmp = null;
            if( sTmp.indexOf( "," ) != 1 ) {
                asTmp = sTmp.split( "," );
            }
            else
            {
                asTmp = new String[1];
                asTmp[0] = sTmp;
            }
            asCorreoDestino = asTmp;
        }
        catch( Exception ex )
        {
            System.out.println( "No hay información de arranque!!" );
            System.exit( -2 );
        }

    }
    
    /**
     * Método público y estático que envía un correo a las direcciones
     * indicadas en el fichero de propiedades, desde la dirección indicada
     * también en el mismo fichero con el asunto y el contenido que se pasan
     * como parámetros.
     * 
     * @param sAsunto String
     * @param sTexto String
     * @return boolean
     */
    public boolean enviarEmail( String sAsunto, String sTexto )
    {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", sServidorCorreo );
            Session mailSesion = Session.getDefaultInstance(props, null);

            Message msg = new MimeMessage(mailSesion);

            msg.setFrom (new InternetAddress(sCorreoOrigen));
            msg.setSubject (sAsunto);
            msg.setSentDate (new java.util.Date());
            msg.setText (sTexto);

            InternetAddress address[] = new InternetAddress[asCorreoDestino.length];
            for(int i = 0; i != asCorreoDestino.length; i++) {
                address[i] = new InternetAddress (asCorreoDestino[i]);
            }         

            msg.setRecipients (Message.RecipientType.TO, address);

        // Modificación para envío de adjuntos

            /*Multipart m = new MimeMultipart();
            MimeBodyPart mb = new MimeBodyPart();
            mb.attachFile(archivo);
            m.addBodyPart(mb);
            msg.setContent(m);*/

        // Fin modificación

            Transport.send(msg);
          }
          catch( MessagingException e )
          {
              return false ;
          }

          return true ;
      }

}

package com.dev.marck.prom.mapboxtry.MapResources;

import android.util.Log;

import com.mapbox.mapboxsdk.geometry.LatLng;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLRecovery {
    private static final XMLRecovery instance = new XMLRecovery();
    private String file = null;
    private Document doc = null;

    public static XMLRecovery getInstance(){
        return instance;
    }

    private XMLRecovery(){
    }

    public XMLRecovery setFile( String file ){
        this.file = file;
        return this;
    }

    public LatLng getLastPoint() throws FileNotFoundException, TagNotFoundException {
        if ( doc == null )
            throw new FileNotFoundException( file + " no se ha encontrado" );
//        recuperamos una lista con los elementos de la etiqueta last-point
        NodeList elemst = doc.getElementsByTagName( "last-point" );
//        si hay contenido devolvemos algo
        if( elemst.getLength() > 0 ){
//            recuperamos el primer elemento, solo deberia haber uno
            Node element = elemst.item( 0 );
//            recuperamos el id
            String idstr = element.getAttributes().getNamedItem( "id" ).getTextContent();
            try {
                int id = Integer.parseInt( idstr.trim().substring( 0,1 ) );
                return Places.getPlace( id++ );
            }catch ( NumberFormatException e ){
                Log.e( "Format Exception", "Error al recuperar un lugar segun el id", e );
            }
        }
        throw new TagNotFoundException();
    }

    public void readDoc() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory fd = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fd.newDocumentBuilder();
        doc = builder.parse( file );
    }

    public class TagNotFoundException extends Throwable{}
}

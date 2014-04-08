package logic;

/**
 * Created by firkav on 2014-04-07.
 */

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import gen_gpx_files.*;
import gen_gpx_files.Gpx.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.io.*;

public class GpsXmlReader {
    private String posX;
    private String posY;
    public ArrayList positions;


    public GpsXmlReader() {
       try {

           ArrayList<BigDecimal> positions = new ArrayList<BigDecimal>();


           JAXBContext jaxbContext = JAXBContext.newInstance("gen_gpx_files");
           Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

           //Don't forget to add gpx file to right place.
           Gpx root = (Gpx) jaxbUnmarshaller.unmarshal(new File("c:/projects/20140402.gpx"));
           List<Trk> tracks = root.getTrk();

           for(Trk track : tracks) {
               for (Trk.Trkseg trkseg : track.getTrkseg()) {
                   for (Trk.Trkseg.Trkpt trkpt : trkseg.getTrkpt()) {

                       //positions.put(trkpt.getLat(),trkpt.getLon());
                       positions.add(trkpt.getLat());
                       positions.add(trkpt.getLon());


                       setPositions(positions);
                       //Thread.sleep(1000);
                   }
               }
           }

       } catch (JAXBException e) {
           e.printStackTrace();
       }
       //catch(InterruptedException  e){
       //    Thread.currentThread().interrupt();
     //  }
   }

    public ArrayList getPositions(){
        return positions;
    }
    public void setPositions(ArrayList positions){
        this.positions = positions;
    }
}

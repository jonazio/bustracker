package logic;

/**
 * Created by firkav on 2014-04-07.
 */

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import gen_gpx_files.*;
import gen_gpx_files.Gpx.*;

import java.util.ArrayList;

public class GpsXmlReader {
    private String posX;
    private String posY;
    public ArrayList positions;


    public GpsXmlReader(String gpxFile) {
       try {

           ArrayList<BigDecimal> positions = new ArrayList<BigDecimal>();


           JAXBContext jaxbContext = JAXBContext.newInstance("gen_gpx_files");
           Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

           //Don't forget to add gpx file to right place.
           //URL url = getClass().getResource("logic/20140402.gpx");
           //System.out.println(url);
           //File file = new File(url.getPath());
          // Gpx root = (Gpx) jaxbUnmarshaller.unmarshal(new File("fake.gpx"));
           Gpx root = (Gpx) jaxbUnmarshaller.unmarshal(new File(gpxFile));
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

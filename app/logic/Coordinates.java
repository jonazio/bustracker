package logic;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by firkav on 2014-05-08.
 */
public class Coordinates {

    private ArrayList<BigDecimal> posLine;

    public Coordinates(String filename){
        GpsXmlReader gpxLine = new GpsXmlReader(filename);
        posLine = gpxLine.getPositions();
    }

    public ArrayList getList(){
        return posLine;
    }

    public BigDecimal getIndex(int i){
        return posLine.get(i);
    }
}

package logic;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * Created by firkav on 2014-04-11.
 */
public class BusJson {


    private String messageType;
    private Long lineId;
    private Long busId;
    private BigDecimal lat;
    private BigDecimal lon;
    LinkedHashMap<String, Object> posHashMap;


    public BusJson(String messageType,
                   Long lineId,
                   Long busId,
                   BigDecimal lat,
                   BigDecimal lon) {
        this.messageType = messageType;
        this.lineId = lineId;
        this.busId = busId;
        this.lat = lat;
        this.lon = lon;

    }

    public String createBusJSON() {
        try {
            ObjectMapper posMapper = new ObjectMapper();
            posHashMap = new LinkedHashMap<String, Object>();

            posHashMap.put("messageType", this.messageType);
            posHashMap.put("lineId", this.lineId);
            posHashMap.put("busId", this.busId);
            posHashMap.put("lat",this.lat );
            posHashMap.put("lon", this.lon );
            return posMapper.writeValueAsString(posHashMap);

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();

        }

    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setlat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }
}





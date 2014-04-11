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
    private BigDecimal posX;
    private BigDecimal posY;
    private ObjectMapper posMapper;
    LinkedHashMap<String, Object> posHashMap;

    public BusJson(String messageType,
                   Long lineId,
                   Long busId,
                   BigDecimal posX,
                   BigDecimal posY) {
        this.messageType = messageType;
        this.lineId = lineId;
        this.busId = busId;
        this.posX = posX;
        this.posY = posY;

    }

    public String createBusJSON() {
        try {
            posMapper = new ObjectMapper();
            posHashMap = new LinkedHashMap<String, Object>();

            posHashMap.put("messageType", this.messageType);
            posHashMap.put("lineId", this.lineId);
            posHashMap.put("busId", this.busId);
            posHashMap.put("posX",this.posX );
            posHashMap.put("posY", this.posY );
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

    public BigDecimal getPosX() {
        return posX;
    }

    public void setPosX(BigDecimal posX) {
        this.posX = posX;
    }

    public BigDecimal getPosY() {
        return posY;
    }

    public void setPosY(BigDecimal posY) {
        this.posY = posY;
    }
}





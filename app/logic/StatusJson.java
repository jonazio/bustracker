package logic;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

/**
 * Created by firkav on 2014-04-11.
 */
public class StatusJson {

    private String messageType;
    private Long lineId;
    private Long busId;
    private String statusType;
    private String message;
    private String text;
    LinkedHashMap<String, Object> statHashMap;

    public StatusJson(String messageType,
                      Long lineId,
                      Long busId,
                      String statusType,
                      String message,
                      String text){
        this.messageType = messageType;
        this.lineId = lineId;
        this.busId = busId;
        this.statusType = statusType;
        this.message = message;
        this.text = text;


    }

    public String createStatusJSON() {
        try {
            ObjectMapper statMapper = new ObjectMapper();
            statHashMap = new LinkedHashMap<String, Object>();

            statHashMap.put("messageType", this.messageType);
            statHashMap.put("lineId", this.lineId);
            statHashMap.put("busId", this.busId);
            statHashMap.put("statusType",this.statusType );
            statHashMap.put("message", this.message );
            statHashMap.put("text", this.text );

            return statMapper.writeValueAsString(statHashMap);

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

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

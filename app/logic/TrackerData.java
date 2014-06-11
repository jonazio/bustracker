package logic;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by firkav on 2014-06-02.
 *
 * This is the implementation class of subject interface.
 * TrackerData is a concrete subject that maintains observers and notifies them when there is a change in  vehicleId, lineId, statusId, vehicleLat and vehicleLon parameters.
 */
public class TrackerData implements Subject {
    private ArrayList observers;
    private Long vehicleId;
    private Long lineId;
    private Long statusId;
    private BigDecimal lat;
    private BigDecimal lon;

    public TrackerData(){
        observers = new ArrayList();
    }

    public void registerObserver(Observer o){
        observers.add(o);
    }

    public void removeObserver(Observer o){
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer)observers.get(i);
            observer.update(vehicleId, lineId, statusId, lat, lon);
        }
    }

    public void parametersChanged(){
        notifyObservers();
    }

    public void setPositionParameters( Long vehicleId, Long lineId, Long statusId, BigDecimal lat, BigDecimal lon){
        this.vehicleId = vehicleId;
        this.lineId = lineId;
        this.statusId = statusId;
        this.lat = lat;
        this.lon = lon;
        parametersChanged();
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public Long getLineId() {
        return lineId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLon() {
        return lon;
    }
}

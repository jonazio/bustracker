package logic;


/**
 * Created by firkav on 2014-06-02.
 */
public interface CoordinateReceiver {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();

}

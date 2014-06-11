package logic;


/**
 * Created by firkav on 2014-06-02.
 *
 * Subject is the object that is being observed. It maintains a list of its dependents, called observers and  notifies them observers automatically of any state change.
 */
public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();

}

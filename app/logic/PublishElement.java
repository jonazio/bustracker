package logic;

/**
 * Created by firkav on 2014-06-02.
 *
 * All elements that publish data to a JMS topic implements PublishElement interface.
 */
public interface PublishElement {
    public void publish();
}

package logic;

import java.math.BigDecimal;

/**
 * Created by firkav on 2014-06-02.
 *
 * Observers are the objects that are registered to be notified in a change of state in Subject object.
 */
public interface Observer {
    public void update(Long vehicleId, Long lineId, Long statusId, BigDecimal lat, BigDecimal lon);
}

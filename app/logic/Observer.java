package logic;

import java.math.BigDecimal;

/**
 * Created by firkav on 2014-06-02.
 */
public interface Observer {
    public void update(Long vehicleId, Long lineId, Long statusId, BigDecimal lat, BigDecimal lon);
}

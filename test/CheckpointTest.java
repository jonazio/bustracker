
/**
 * Created by firkav on 2014-06-09.
 * This is the unit testing class for Checkpoint class.
 */

import logic.Checkpoint;
import org.junit.*;
import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import java.math.BigDecimal;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class CheckpointTest extends WithApplication {

    @Before
    public void setUp(){
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void vehicleOnCheckPointTest() {

            Checkpoint checkpoint = Checkpoint.getInstance();
            Long lineId = 1L;
            Long vehicleId = 1L;
            BigDecimal vehicleLat = BigDecimal.valueOf(59.3352937);
            BigDecimal vehicleLon = BigDecimal.valueOf(18.0624829);
            assertThat(checkpoint.vehicleOnCheckpoint(lineId, vehicleId, vehicleLat, vehicleLon)).isTrue();
    }


}

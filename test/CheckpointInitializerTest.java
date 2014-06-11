/**
 * Created by firkav on 2014-06-09.
 */

import logic.CheckpointInitializer;
import org.junit.*;
import play.test.*;

import static play.test.Helpers.*;

public class CheckpointInitializerTest extends WithApplication {

    @Before
    public void setUp(){
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void getAllCheckpointsTest(){
        CheckpointInitializer checkpointInitializer = new CheckpointInitializer();

        assertThat()
    }

}

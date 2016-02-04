import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.io.StringWriter;
import java.io.IOException;

public class ReportTest {
    @Test
    public void testReport() throws IOException {
        ArrayList<Machine> line = new ArrayList<Machine>();
        line.add(new Machine("mixer", "left"));

        Machine extruder = new Machine("extruder", "center");
        extruder.put("paste");
        line.add(extruder);

        Machine oven = new Machine("oven", "right");
        oven.put("chips");
        line.add(oven);

        Robot robot = new Robot();
        robot.moveTo(extruder);
        robot.pick();

        StringWriter out = new StringWriter();
        Report.report(out, line, robot);

        String expected =
            "FACTORY REPORT\n" +
            "Machine mixer\nMachine extruder\n" +
	    "Machine oven bin=chips\n\n" +
            "Robot location=extruder bin=paste\n" +
            "========\n";

        assertEquals(expected, out.toString());
    }
}


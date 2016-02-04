import static org.junit.Assert.*;
import org.junit.Test;

public class TestReport {
	@Test
    public void testEmptyReport() throws Exception {
        Schedule.deleteAll();
        Report report = new Report();

        StringBuffer buffer = new StringBuffer();

        report.write(buffer);

        assertEquals(
	    "Number of scheduled offerings: 0\n", 
	    buffer.toString());
    }

	@Test
    public void testReport() throws Exception {
        Schedule.deleteAll();

        Course cs101 = Course.create("CS101", 3);
        cs101.update();
        Offering off1 = Offering.create(cs101, "M10");
        off1.update();
        Offering off2 = Offering.create(cs101, "T9");
        off2.update();

        Schedule s = Schedule.create("Bob");
        s.add(off1);
        s.add(off2);
        s.update();

        Schedule s2 = Schedule.create("Alice");
        s2.add(off1);
        s2.update();

        Report report = new Report();

        StringBuffer buffer = new StringBuffer();

        report.write(buffer);

        String result = buffer.toString();
        String valid1 = "CS101 M10\n\tAlice\n\tBob\n" +
                        "CS101 T9\n\tBob\n" +
                        "Number of scheduled offerings: 2\n";

        String valid2 = "CS101 T9\n\tBob\n" +
                        "CS101 M10\n\tAlice\n\tBob\n" +
                        "Number of scheduled offerings: 2\n";
        
        assertTrue(result.equals(valid1) || result.equals(valid2));
    }
}


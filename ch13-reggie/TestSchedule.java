
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestSchedule  {
	@Test
	public void minCredits() {
        Schedule schedule = new Schedule("name");
        List<String> analysis = schedule.analysis();
        assertEquals(1, analysis.size());
        assertTrue(analysis.contains("Too few credits"));
    }

	@Test
    public void justEnoughCredits() {
        Course cs110 = new Course("CS110", 11);
        Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
        Schedule schedule = new Schedule("name");
        schedule.add(mwf10);
        List<String> analysis = schedule.analysis();
        assertEquals(1, analysis.size());
        assertTrue(analysis.contains("Too few credits"));

        schedule = new Schedule("name");
        Course cs101 = new Course("CS101", 12);
        Offering th11 = new Offering(1, cs101, "T11,H11");
        schedule.add(th11);
        analysis = schedule.analysis();
        assertEquals(0, analysis.size());
    }

	@Test
    public void maxCredits() {
        Course cs110 = new Course("CS110", 20);
        Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
        Schedule schedule = new Schedule("name");
        schedule.add(mwf10);
        List<String> analysis = schedule.analysis();
        assertEquals(1, analysis.size());
        assertTrue(analysis.contains("Too many credits"));

        schedule.authorizeOverload(true);
        analysis = schedule.analysis();
        assertEquals(0, analysis.size());
    }

	@Test
    public void justBelowMax() {
        Course cs110 = new Course("CS110", 19);
        Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
        Schedule schedule = new Schedule("name");
        schedule.add(mwf10);
        List<String> analysis = schedule.analysis();
        assertEquals(1, analysis.size());
        assertTrue(analysis.contains("Too many credits"));

        schedule = new Schedule("name");
        Course cs101 = new Course("CS101", 18);
        Offering th11 = new Offering(1, cs101, "T11,H11");
        schedule.add(th11);
        analysis = schedule.analysis();
        assertEquals(0, analysis.size());
    }

	@Test
    public void dupCourses() {
        Course cs110 = new Course("CS110", 6);
        Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
        Offering th11 = new Offering(1, cs110, "T11,H11");
        Schedule schedule = new Schedule("name");
        schedule.add(mwf10);
        schedule.add(th11);
        List<String> analysis = schedule.analysis();
        assertEquals(1, analysis.size());
        assertTrue(analysis.contains("Same course twice - CS110"));
    }

    @Test
    public void overlap() {
        Schedule schedule = new Schedule("name");

        Course cs110 = new Course("CS110", 6);
        Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
        schedule.add(mwf10);

        Course cs101 = new Course("CS101", 6);
        Offering mixed = new Offering(1, cs101, "M10,W11,F11");
        schedule.add(mixed);

        List<String> analysis = schedule.analysis();
        assertEquals(1, analysis.size());
        assertTrue(analysis.contains("Course overlap - M10"));

        Course cs102 = new Course("CS102", 1);
        Offering mixed2 = new Offering(1, cs102, "M9,W10,F11");
        schedule.add(mixed2);

        analysis = schedule.analysis();
        assertEquals(3, analysis.size());
        assertTrue(analysis.contains("Course overlap - M10"));
        assertTrue(analysis.contains("Course overlap - W10"));
        assertTrue(analysis.contains("Course overlap - F11"));
    }

    @Test
    public void courseCreate() throws Exception {
        Course c = Course.create("CS202", 1);
        assertEquals("CS202", c.getName());
        assertEquals(1, c.getCredits());
        
        Course c2 = Course.find("CS202");
        assertEquals("CS202", c2.getName());

        Course c3 = Course.find("Nonexistent");
        assertNull(c3);
    }

    @Test
    public void offeringCreate() throws Exception {
        Course c = Course.create("CS202", 2);
        Offering offering = Offering.create(c, "M10");
        assertNotNull(offering);
    }

    @Test
    public void persistentSchedule() throws Exception {
        Schedule s = Schedule.create("Bob");
        assertNotNull(s);
    }

    @Test
    public void scheduleUpdate() throws Exception {
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

        Schedule s3 = Schedule.find("Bob");
        assertEquals(2, s3.schedule.size());

        Schedule s4 = Schedule.find("Alice");
        assertEquals(1, s4.schedule.size());
    }
}

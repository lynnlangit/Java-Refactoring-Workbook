import org.junit.Test;
import static org.junit.Assert.*;

public class QueueTest {
	@Test
	public void testQ() {
		Queue q = new Queue();
		q.addRear("E1");
		q.addRear("E2");
		assertEquals("E1", q.removeFront());
		assertEquals("E2", q.removeFront());
		assertEquals(0, q.getSize());
	}

}
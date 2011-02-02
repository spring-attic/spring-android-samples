package org.springframework.android.showcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class HomeControllerTest {
	
	private HomeController controller = new HomeController();
	
	@Test
	public void homePageFetchStates() {		
		List<State> states = controller.fetchStatesJson();
		assertNotNull(states);
		assertEquals(50, states.size());
		assertEquals("ALABAMA", states.get(0).getName());
	}

	@Test
	public void homePageSubmitMessage() {
		String message = "unit test message";
		String response = controller.sendMessage(message);
		assertEquals("String message received! Your message: " + message, response);
	}

}

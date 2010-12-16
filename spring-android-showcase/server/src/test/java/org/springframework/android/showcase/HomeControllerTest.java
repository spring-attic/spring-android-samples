package org.springframework.android.showcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class HomeControllerTest {
	
	private HomeController controller = new HomeController();
	
	@Test
	public void homePageFetchStates() {		
		List<State> states = controller.fetchStates();
		assertNotNull(states);
		assertEquals(50, states.size());
		assertEquals("Alabama", states.get(0).getName());
	}

	@Test
	public void homePageSubmitMessage() {
		Map<String,String> body = new HashMap<String,String>();
		body.put("message", "unit test message");
		String response = controller.sendMessage(body);
		assertEquals("It worked!", response);
	}

}

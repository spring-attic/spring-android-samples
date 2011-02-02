/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.android.showcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

/**
 * @author Roy Clarkson
 */
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

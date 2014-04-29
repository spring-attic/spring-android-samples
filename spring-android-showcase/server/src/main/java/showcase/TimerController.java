/*
 * Copyright 2010-2014 the original author or authors.
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

package showcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roy Clarkson
 */
@RestController
public class TimerController {

	private static final Logger logger = LoggerFactory.getLogger(TimerController.class);

	/**
	 * Simulates a slow server response by delaying a specified number of seconds
	 * @return done message
	 */
	@RequestMapping(value = "delay/{seconds}", method = RequestMethod.GET)
	public String delay(@PathVariable String seconds) {

		int delay = 15;
		try {
			delay = Integer.parseInt(seconds);
			logger.info("Delaying response by " + delay + " seconds");
		} catch (NumberFormatException e) {
			logger.info("Bad input. Defaulting to " + delay + " seconds delay");
		}

		try {
			Thread.sleep(delay * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return "Response delayed by " + delay + " seconds";
	}

}

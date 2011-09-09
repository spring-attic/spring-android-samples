package org.springframework.android.showcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TimerController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simulates a slow server response by delaying a specified number of seconds
     * @return done message
     */
    @RequestMapping(value = "delay/{seconds}", method = RequestMethod.GET)
    public @ResponseBody String delay(@PathVariable String seconds) {

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

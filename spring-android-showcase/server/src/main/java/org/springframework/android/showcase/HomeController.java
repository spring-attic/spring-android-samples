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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 * 
 * @author Roy Clarkson
 */
@Controller
@RequestMapping("/*")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static List<State> states;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        logger.info("Spring Android Showcase");
        return "home";
    }

    /**
     * Retrieve a list of states. Accepts a GET request for JSON
     * @return A JSON array of states
     */
    @RequestMapping(value = "states", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody List<State> fetchStatesJson() {
        logger.info("fetching JSON states");
        return getStates();
    }

    /**
     * Retrieve a list of states. Accepts a GET request for XML
     * @return An XML array of states
     */
    @RequestMapping(value = "states", method = RequestMethod.GET, headers = "Accept=application/xml")
    public @ResponseBody StateList fetchStatesXml() {
        logger.info("fetching XML states");
        List<State> states = getStates();
        StateList stateList = new StateList(states);
        return stateList;
    }

    /**
     * Retrieve a single state. Accepts a GET request for JSON with a parameter for the state abbreviation
     * @param abbreviation contains the state abbreviation to use when finding the corresponding state
     * @return A JSON state
     */
    @RequestMapping(value = "state/{abbreviation}", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody State fetchStateJson(@PathVariable String abbreviation) {
        logger.info("fetching JSON state");
        return getStateByAbbreviation(abbreviation);
    }

    /**
     * Retrieve a single state. Accepts a GET request for XML with a parameter for the state abbreviation
     * @param abbreviation contains the state abbreviation to use when finding the corresponding state
     * @return An XML state
     */
    @RequestMapping(value = "state/{abbreviation}", method = RequestMethod.GET, headers = "Accept=application/xml")
    public @ResponseBody State fetchStateXml(@PathVariable String abbreviation) {
        logger.info("fetching XML state");
        return getStateByAbbreviation(abbreviation);
    }

    /**
     * Accepts a POST request with a plain text message parameter
     * @param body contains the body of the POST request
     * @return a string with the result of the POST
     */
    @RequestMapping(value = "sendmessage", method = RequestMethod.POST, headers = "Content-Type=text/plain")
    public @ResponseBody String sendMessage(@RequestBody String message) {
        logger.info("String message: " + message);
        return "String message received! Your message: " + message;
    }

    /**
     * Accepts a POST request with a JSON message parameter
     * @param message serialized Message object
     * @return a string with the result of the POST
     */
    @RequestMapping(value = "sendmessage", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public @ResponseBody String sendMessageJson(@RequestBody Message message) {
        logger.info("JSON message: " + message.toString());
        return "JSON message received! Your message: " + message.toString();
    }

    /**
     * Accepts a POST request with an XML message parameter
     * @param message serialized Message object
     * @return a string with the result of the POST
     */
    @RequestMapping(value = "sendmessage", method = RequestMethod.POST, headers = "Content-Type=application/xml")
    public @ResponseBody String sendMessageXml(@RequestBody Message message) {
        logger.info("XML message: " + message.toString());
        return "XML message received! Your message: " + message.toString();
    }

    /**
     * Accepts a POST request with an Map message parameter, and creates a new Message object from the Map parameters.
     * @param map serialized LinkedMultiValueMap<String, String> object
     * @return a string with the result of the POST
     */
    @RequestMapping(value = "sendmessagemap", method = RequestMethod.POST)
    public @ResponseBody String sendMessageMap(@RequestBody LinkedMultiValueMap<String, String> map) {
        Message message = new Message();

        try {
            message.setId(Integer.parseInt(map.getFirst("id")));
        } catch (NumberFormatException e) {
            message.setId(0);
        }

        message.setSubject(map.getFirst("subject"));
        message.setText(map.getFirst("text"));

        logger.info("Map message: " + message.toString());
        return "Map message received! Your message: " + message.toString();
    }

    // helper methods

    private State getStateByAbbreviation(String abbreviation) {
        List<State> states = getStates();

        for (State state : states) {
            if (state.getAbbreviation().compareToIgnoreCase(abbreviation) == 0) {
                return state;
            }
        }

        return null;
    }

    /**
     * @return a List of states
     */
    private List<State> getStates() {
        if (states == null) {
            states = new ArrayList<State>();
            states.add(new State("ALABAMA", "AL"));
            states.add(new State("ALASKA", "AK"));
            states.add(new State("ARIZONA", "AZ"));
            states.add(new State("ARKANSAS", "AR"));
            states.add(new State("CALIFORNIA", "CA"));
            states.add(new State("COLORADO", "CO"));
            states.add(new State("CONNECTICUT", "CT"));
            states.add(new State("DELAWARE", "DE"));
            states.add(new State("FLORIDA", "FL"));
            states.add(new State("GEORGIA", "GA"));
            states.add(new State("HAWAII", "HI"));
            states.add(new State("IDAHO", "ID"));
            states.add(new State("ILLINOIS", "IL"));
            states.add(new State("INDIANA", "IN"));
            states.add(new State("IOWA", "IA"));
            states.add(new State("KANSAS", "KS"));
            states.add(new State("KENTUCKY", "KY"));
            states.add(new State("LOUISIANA", "LA"));
            states.add(new State("MAINE", "ME"));
            states.add(new State("MARYLAND", "MD"));
            states.add(new State("MASSACHUSETTS", "MA"));
            states.add(new State("MICHIGAN", "MI"));
            states.add(new State("MINNESOTA", "MN"));
            states.add(new State("MISSISSIPPI", "MS"));
            states.add(new State("MISSOURI", "MO"));
            states.add(new State("MONTANA", "MT"));
            states.add(new State("NEBRASKA", "NE"));
            states.add(new State("NEVADA", "NV"));
            states.add(new State("NEW HAMPSHIRE", "NH"));
            states.add(new State("NEW JERSEY", "NJ"));
            states.add(new State("NEW MEXICO", "NM"));
            states.add(new State("NEW YORK", "NY"));
            states.add(new State("NORTH CAROLINA", "NC"));
            states.add(new State("NORTH DAKOTA", "ND"));
            states.add(new State("OHIO", "OH"));
            states.add(new State("OKLAHOMA", "OK"));
            states.add(new State("OREGON", "OR"));
            states.add(new State("PENNSYLVANIA", "PA"));
            states.add(new State("RHODE ISLAND", "RI"));
            states.add(new State("SOUTH CAROLINA", "SC"));
            states.add(new State("SOUTH DAKOTA", "SD"));
            states.add(new State("TENNESSEE", "TN"));
            states.add(new State("TEXAS", "TX"));
            states.add(new State("UTAH", "UT"));
            states.add(new State("VERMONT", "VT"));
            states.add(new State("VIRGINIA", "VA"));
            states.add(new State("WASHINGTON", "WA"));
            states.add(new State("WEST VIRGINIA", "WV"));
            states.add(new State("WISCONSIN", "WI"));
            states.add(new State("WYOMING", "WY"));
        }

        return states;
    }

}

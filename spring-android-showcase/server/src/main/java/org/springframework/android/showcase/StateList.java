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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Roy Clarkson
 */
@XmlRootElement(name="states")
public class StateList {
	private List<State> states;
	
	public StateList() { }
	
	public StateList(List<State> states) {
		this.states = states;
	}
	
	@XmlElement(name="state")
	public List<State> getStates() {
		return states;
	}
	
	public void setStates(List<State> states) {
		this.states = states;
	}
}

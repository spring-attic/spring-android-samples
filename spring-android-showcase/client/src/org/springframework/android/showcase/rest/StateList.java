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
package org.springframework.android.showcase.rest;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author Roy Clarkson
 */
@Root(name="states")
public class StateList {

   @ElementList(inline=true)
   private List<State> states;
   
   public StateList() { }
   
   public StateList(List<State> states)
   {
	   this.states = states;
   }

   public List<State> getStates() 
   {
      return states;
   }
   
   public void setStates(List<State> states)
   {
	   this.states = states;
   }
}

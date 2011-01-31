package org.springframework.android.showcase;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

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

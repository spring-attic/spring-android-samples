package org.springframework.android.showcase;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="state")
public class State 
{
	@Element
	private String name;
	
	@Element
	private String abbreviation;
	
	public State() { }
	
	public State(String name, String abbreviation)
	{
		this.name = name;
		this.abbreviation = abbreviation;
	}

	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getAbbreviation() 
	{
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) 
	{
		this.abbreviation = abbreviation;
	}
}

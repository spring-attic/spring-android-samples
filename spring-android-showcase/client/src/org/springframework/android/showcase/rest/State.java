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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Roy Clarkson
 */
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
	
	public String getFormattedName()
	{
		return this.getName() + " (" + this.getAbbreviation() + ")";
	}
}

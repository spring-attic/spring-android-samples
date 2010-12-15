package org.springframework.android.showcase;

public class State {
	private String name;
	private String abbreviation;
	
	public State(String name, String abbreviation) {
		this.name = name;
		this.abbreviation = abbreviation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}
}

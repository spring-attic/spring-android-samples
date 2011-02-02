package org.springframework.android.showcase;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Message 
{
	@Element
	private long id;
	
	@Element
	private String subject;
	
	@Element
	private String text;
	
	public Message() { }
	
	public Message(long id, String subject, String text)
	{
		this.id = id;
		this.subject = subject;
		this.text = text;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public long getId() 
	{
		return id;
	}

	public void setSubject(String subject) 
	{
		this.subject = subject;
	}

	public String getSubject() 
	{
		return subject;
	}

	public void setText(String text) 
	{
		this.text = text;
	}

	public String getText() 
	{
		return text;
	}
	
	public String toString() {
		return "[Id:] " + this.getId() + " [Subject:] " + this.getSubject() + " [Text:] "+ this.getText();
	}
}

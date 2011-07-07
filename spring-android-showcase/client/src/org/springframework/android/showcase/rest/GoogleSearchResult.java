package org.springframework.android.showcase.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleSearchResult {

	private String title;
	
	private String url;
	
	private String content;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
}

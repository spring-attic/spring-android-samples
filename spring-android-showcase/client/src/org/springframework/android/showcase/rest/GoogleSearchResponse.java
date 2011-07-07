package org.springframework.android.showcase.rest;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleSearchResponse {
	
	private ResponseData responseData;
	
	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}
	
	public ResponseData getResponseData() {
		return responseData;
	}


	@JsonIgnoreProperties(ignoreUnknown=true)
	static class ResponseData {
		
		private List<GoogleSearchResult> results;

		public void setResults(List<GoogleSearchResult> results) {
			this.results = results;
		}

		public List<GoogleSearchResult> getResults() {
			return results;
		}
		
	}
	
}

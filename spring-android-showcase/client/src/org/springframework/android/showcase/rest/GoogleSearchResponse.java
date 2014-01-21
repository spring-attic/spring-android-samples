/*
 * Copyright 2010-2014 the original author or authors.
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Roy Clarkson
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleSearchResponse {

	private ResponseData responseData;

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public ResponseData getResponseData() {
		return this.responseData;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class ResponseData {

		private List<GoogleSearchResult> results;

		public void setResults(List<GoogleSearchResult> results) {
			this.results = results;
		}

		public List<GoogleSearchResult> getResults() {
			return this.results;
		}

	}

}

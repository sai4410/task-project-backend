package com.teluguskillhub.taskproject.payload;

import lombok.Getter;

@Getter
public class JWTAuthResponse {
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	private String token;
	private String tokenType="Bearer";
	
	public JWTAuthResponse(String token) {
		this.token = token;
	}

}

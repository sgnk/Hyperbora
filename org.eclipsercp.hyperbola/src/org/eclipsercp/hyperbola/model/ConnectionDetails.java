package org.eclipsercp.hyperbola.model;

public class ConnectionDetails {
	private String userId, server, password;

	public ConnectionDetails(String userId, String server, String password) {
		this.userId = userId;
		this.server = server;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public String getServer() {
		return server;
	}

	public String getPassword() {
		return password;
	}

	public String getResource() {
		return String.valueOf(System.currentTimeMillis());
	}
}

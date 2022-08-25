package com.thingworx.sdk.batch;

import com.thingworx.communications.client.IPasswordCallback;

/**
 * Sample password callback to retrieve an app key from a specified environment variable.
 * 
 * Provided for demonstration purposes only. 
 * 
 * Not suitable for production environments.
 *
 */
public class BatchPasswordCallback implements IPasswordCallback {

	private String appKey = null;

	public BatchPasswordCallback(String appKey) {
		this.appKey = appKey;
	}

	@Override
	public char[] getSecret() {
		
		return appKey.toCharArray();
	}
}

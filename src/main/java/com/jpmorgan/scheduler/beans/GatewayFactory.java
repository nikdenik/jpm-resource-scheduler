package com.jpmorgan.scheduler.beans;

import java.util.concurrent.Semaphore;

import com.jpmorgan.scheduler.interfaces.Gateway;

public class GatewayFactory {

	
	public static Gateway createNewGatewayInstance(Semaphore resourceAvailable){
		return new GatewayImpl(resourceAvailable);
	}
	
}

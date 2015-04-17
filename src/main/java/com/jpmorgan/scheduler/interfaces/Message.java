package com.jpmorgan.scheduler.interfaces;

public interface Message {

	long getMessageID();
	
	String getGroupID();
	
	String getMesssageDetails();
	
	void completed();
}

package com.jpmorgan.scheduler.beans;

import com.jpmorgan.scheduler.interfaces.Message;

public class MessageFactory {

	public static Message createNewMesssage(String groupID, long messageID, String messageDetails) {
		return new MessageImpl(groupID, messageID, messageDetails);
	}

}

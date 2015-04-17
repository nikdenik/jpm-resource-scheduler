package com.jpmorgan.scheduler.beans;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jpmorgan.scheduler.interfaces.Message;

public class MessageImpl implements Message {

	
	private static final Logger LOG = LogManager.getLogger(Message.class.getName());


	private String groupID;
	private long messageID;
	private String messageDetails;

	protected MessageImpl(String groupID, long messageID, String messageDetails) {
		this.messageDetails = messageDetails;
		this.groupID = groupID;
		this.messageID = messageID;
	}

	@Override
	public long getMessageID() {
		return this.messageID;
	}
	
	@Override
	public String getGroupID() {
		return this.groupID;
	}

	@Override
	public String getMesssageDetails() {
		return this.messageDetails;
	}
	
	@Override
	public void completed() {
		LOG.info("Message " + this + " has been succesfully processed by the Gateway");
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupID == null) ? 0 : groupID.hashCode());
		result = prime * result + ((messageDetails == null) ? 0 : messageDetails.hashCode());
		result = prime * result + (int) (messageID ^ (messageID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageImpl other = (MessageImpl) obj;
		if (groupID == null) {
			if (other.groupID != null)
				return false;
		} else if (!groupID.equals(other.groupID))
			return false;
		if (messageDetails == null) {
			if (other.messageDetails != null)
				return false;
		} else if (!messageDetails.equals(other.messageDetails))
			return false;
		if (messageID != other.messageID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [groupID=").append(groupID).append(", messageID=").append(messageID).append(", messageDetails=").append(messageDetails)
				.append("]");
		return builder.toString();
	}


}

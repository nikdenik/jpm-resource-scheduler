package com.jpmorgan.scheduler.interfaces;

import java.util.concurrent.BlockingQueue;

public interface PriorityStrategy {

	Message getNextMessage(Message lastSentMessage, BlockingQueue<Message> queue) throws InterruptedException;
	
}

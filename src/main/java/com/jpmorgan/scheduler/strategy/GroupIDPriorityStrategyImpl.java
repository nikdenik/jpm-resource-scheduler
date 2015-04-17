package com.jpmorgan.scheduler.strategy;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import com.jpmorgan.scheduler.interfaces.Message;
import com.jpmorgan.scheduler.interfaces.PriorityStrategy;

/***
 * This strategy return the message with the same groupID (if it exists on the
 * queue) of the last message that has been sent to the gateway. If a message
 * with the same group does not exists in the queue, then the strategy returns
 * the first element of the queue
 * 
 * @author Nicola Ferrante
 *
 */
public class GroupIDPriorityStrategyImpl implements PriorityStrategy {

	private static PriorityStrategy instance;

	private GroupIDPriorityStrategyImpl() {
	}

	public static synchronized PriorityStrategy getInstance() {
		if (instance == null) {
			instance = new GroupIDPriorityStrategyImpl();
		}
		return instance;
	}

	@Override
	public Message getNextMessage(Message lastSentMessage, BlockingQueue<Message> queue) throws InterruptedException {
		Message result = null;
		if (lastSentMessage == null) {
			result = queue.take();
		} else {
			synchronized (queue) {
				for (Iterator<Message> iterator = queue.iterator(); iterator.hasNext() && result == null;) {
					Message message = iterator.next();
					if (lastSentMessage.getGroupID().equals(message.getGroupID())) {
						iterator.remove();
						result = message;
					}
				}
			}
		}
		return (result == null) ? queue.take() : result;
	}
}

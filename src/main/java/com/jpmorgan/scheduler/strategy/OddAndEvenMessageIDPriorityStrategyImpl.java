package com.jpmorgan.scheduler.strategy;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import com.jpmorgan.scheduler.interfaces.Message;
import com.jpmorgan.scheduler.interfaces.PriorityStrategy;

/***
 * This strategy get the new message basing on the message ID of the last sent
 * message. If the last sent message has an odd ID a message with odd ID is
 * selected from the queue and viceversa if the last sent message has an even ID
 * a message with even ID is selected from the queue If a message with the odd
 * or even ID does not exists in the queue, then the strategy returns the first
 * element of the queue
 * 
 * 
 * @author Nicola Ferrante
 *
 */
public class OddAndEvenMessageIDPriorityStrategyImpl implements PriorityStrategy {

	private static PriorityStrategy instance;

	private OddAndEvenMessageIDPriorityStrategyImpl() {
	}

	public static synchronized PriorityStrategy getInstance() {
		if (instance == null) {
			instance = new OddAndEvenMessageIDPriorityStrategyImpl();
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
					if (lastSentMessage.getMessageID() % 2 == message.getMessageID() % 2) {
						iterator.remove();
						result = message;
					}
				}
			}
		}
		return (result == null) ? queue.take() : result;
	}

}

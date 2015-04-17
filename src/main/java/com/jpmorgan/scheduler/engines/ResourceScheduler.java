package com.jpmorgan.scheduler.engines;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jpmorgan.scheduler.beans.GatewayFactory;
import com.jpmorgan.scheduler.beans.MessageFactory;
import com.jpmorgan.scheduler.interfaces.Gateway;
import com.jpmorgan.scheduler.interfaces.Message;
import com.jpmorgan.scheduler.interfaces.PriorityStrategy;
import com.jpmorgan.scheduler.strategy.PriorityStrategyFactory;

public class ResourceScheduler implements Runnable {

	private static final Logger LOG = LogManager.getLogger(ResourceScheduler.class.getName());
	private final PriorityStrategy strategy;

	private final BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<Message>();
	private final Semaphore availableResources;
	private final Set<String> groupIdBlackList = new ConcurrentSkipListSet<String>();
	private final Gateway gateway;
	private final List<Message> historyOfSentMessages = new LinkedList<Message>();


	public ResourceScheduler(int availableResources) {
		this.availableResources = new Semaphore(availableResources, true); // fairness=true
		this.strategy = PriorityStrategyFactory.getGroupPriorityStrategy(); // the
																			// default
																			// strategy
		this.gateway = GatewayFactory.createNewGatewayInstance(this.availableResources);
		new Thread(this).start();
	}

	public ResourceScheduler(int availableResources, PriorityStrategy strategy) {
		this.availableResources = new Semaphore(availableResources, true); // fairness=true
		this.gateway = GatewayFactory.createNewGatewayInstance(this.availableResources);
		this.strategy = strategy;
		new Thread(this).start();
	}

	public void addToInboundQueue(Message msg) {
		if (!this.groupIdBlackList.contains(msg.getGroupID())) {
			try {
				this.inputQueue.put(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void cancelGroup(String groupID) {
		this.groupIdBlackList.add(groupID);
		synchronized (this.inputQueue) {
			for (Iterator<Message> iterator = this.inputQueue.iterator(); iterator.hasNext();) {
				Message message = iterator.next();
				if (message.getGroupID().equals(groupID)) {
					iterator.remove();
				}
			}
		}
	}
	

	@Override
	public void run() {
		Message msgToSend = null;

		while (true) {
			try {
				msgToSend = this.strategy.getNextMessage(msgToSend, this.inputQueue);
				availableResources.acquire();
				LOG.info("Sending the message " + msgToSend + " to the Gateway");
				this.gateway.send(msgToSend);
				this.historyOfSentMessages.add(msgToSend);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public List<Message> getHistory(){
		return this.historyOfSentMessages;
	}

	
	public static void main(String[] args) {

		ResourceScheduler scheduler = new ResourceScheduler(2);
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 1, "first message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 2, "second message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group4", 3, "third message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 4, "fourth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 5, "fifth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group2", 6, "sixth message"));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(scheduler.getHistory().toString());

	}

}

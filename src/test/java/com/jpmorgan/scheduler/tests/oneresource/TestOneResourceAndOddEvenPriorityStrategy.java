package com.jpmorgan.scheduler.tests.oneresource;

import org.junit.Assert;
import org.junit.Test;

import com.jpmorgan.scheduler.beans.MessageFactory;
import com.jpmorgan.scheduler.engines.ResourceScheduler;
import com.jpmorgan.scheduler.strategy.PriorityStrategyFactory;


public class TestOneResourceAndOddEvenPriorityStrategy {

	private ResourceScheduler scheduler = new ResourceScheduler(1, PriorityStrategyFactory.getOddAndEvenMessageIDPriorityStrategy());
	
	
	@Test
	public void testSendMoreMessagesOneResourceAndOddEvenPriorityStrategy() {
		
		scheduler.getHistory().clear();
		
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 1, "first message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 2, "second message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group4", 3, "third message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 4, "fourth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 5, "fifth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 6, "sixth message"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(6, scheduler.getHistory().size());
		Assert.assertEquals(1, scheduler.getHistory().get(0).getMessageID());
		Assert.assertEquals(3, scheduler.getHistory().get(1).getMessageID());
		Assert.assertEquals(5, scheduler.getHistory().get(2).getMessageID());
		Assert.assertEquals(2, scheduler.getHistory().get(3).getMessageID());
		Assert.assertEquals(4, scheduler.getHistory().get(4).getMessageID());
		Assert.assertEquals(6, scheduler.getHistory().get(5).getMessageID());
	}
	
	@Test
	public void testSendMoreMessagesOneResourceAndOddEvenPriorityStrategyWithCancellation() {
		
		scheduler.getHistory().clear();
		
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 1, "first message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 2, "second message"));
		scheduler.cancelGroup("group3");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group4", 3, "third message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 4, "fourth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 5, "fifth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 6, "sixth message"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(4, scheduler.getHistory().size());
		Assert.assertEquals(1, scheduler.getHistory().get(0).getMessageID());
		Assert.assertEquals(3, scheduler.getHistory().get(1).getMessageID());
		Assert.assertEquals(5, scheduler.getHistory().get(2).getMessageID());
		Assert.assertEquals(4, scheduler.getHistory().get(3).getMessageID());
	}
	
	
	@Test
	public void testSendMoreMessagesOneResourceAndOddEvenPriorityStrategyWithCancellationBeforeStart() {
		
		scheduler.getHistory().clear();
		
		scheduler.cancelGroup("group1");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 1, "first message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 2, "second message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group4", 3, "third message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 4, "fourth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 5, "fifth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 6, "sixth message"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(3, scheduler.getHistory().size());
		Assert.assertEquals(2, scheduler.getHistory().get(0).getMessageID());
		Assert.assertEquals(6, scheduler.getHistory().get(1).getMessageID());
		Assert.assertEquals(3, scheduler.getHistory().get(2).getMessageID());
	}

}

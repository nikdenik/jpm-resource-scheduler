package com.jpmorgan.scheduler.tests.manyresources;

import org.junit.Assert;
import org.junit.Test;

import com.jpmorgan.scheduler.beans.MessageFactory;
import com.jpmorgan.scheduler.engines.ResourceScheduler;
import com.jpmorgan.scheduler.strategy.PriorityStrategyFactory;


public class TestManyResourcesAndOddEvenPriorityStrategy {

	private ResourceScheduler scheduler = new ResourceScheduler(6, PriorityStrategyFactory.getOddAndEvenMessageIDPriorityStrategy());
	
	
	@Test
	public void testSendMoreMessagesToManyResourcesAndOddEvenPriorityStrategy() {
		
		scheduler.getHistory().clear();
		
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 1, "first message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 2, "second message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group4", 3, "third message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 4, "fourth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 5, "fifth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 6, "sixth message"));
		try {
			Thread.sleep(400);
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
	
	
	

}

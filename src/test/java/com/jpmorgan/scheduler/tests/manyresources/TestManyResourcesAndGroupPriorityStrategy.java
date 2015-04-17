package com.jpmorgan.scheduler.tests.manyresources;

import org.junit.Assert;
import org.junit.Test;

import com.jpmorgan.scheduler.beans.MessageFactory;
import com.jpmorgan.scheduler.engines.ResourceScheduler;


public class TestManyResourcesAndGroupPriorityStrategy {

	private ResourceScheduler scheduler = new ResourceScheduler(6);
	
	
	@Test
	public void testSendMoreMessagesToManyResourcesAndGroupPriorityStrategy() {
		
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
		Assert.assertEquals("group1", scheduler.getHistory().get(0).getGroupID());
		Assert.assertEquals("group1", scheduler.getHistory().get(1).getGroupID());
		Assert.assertEquals("group1", scheduler.getHistory().get(2).getGroupID());
		Assert.assertEquals("group3", scheduler.getHistory().get(3).getGroupID());
		Assert.assertEquals("group3", scheduler.getHistory().get(4).getGroupID());
		Assert.assertEquals("group4", scheduler.getHistory().get(5).getGroupID());
	}
	
	
	@Test
	public void testSendMoreMessagesToManyResourcesAndGroupPriorityStrategyWithCancellation() {
		
		scheduler.getHistory().clear();
		
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 2, "second message"));
		scheduler.cancelGroup("group1");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group4", 3, "third message"));
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 4, "fourth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 5, "fifth message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 6, "sixth message"));
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	@Test
	public void testSendTwoMessagesToManyResourceAndGroupPriorityStrategy() {
		
		scheduler.getHistory().clear();

		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group1", 1, "first message"));
		scheduler.addToInboundQueue(MessageFactory.createNewMesssage("group3", 2, "second message"));
		
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(2, scheduler.getHistory().size());

	}


}

package com.jpmorgan.scheduler.beans;

import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jpmorgan.scheduler.interfaces.Gateway;
import com.jpmorgan.scheduler.interfaces.Message;

public class GatewayImpl implements Gateway, Runnable {

	private static final Logger LOG = LogManager.getLogger(Gateway.class.getName());

	private Semaphore resourceAvailable;
	private Message messageToProcess;

	protected GatewayImpl(Semaphore resourceAvailable) {
		this.resourceAvailable = resourceAvailable;
	}

	private GatewayImpl(Semaphore resourceAvailable, Message messageToProcess) {
		this.resourceAvailable = resourceAvailable;
		this.messageToProcess = messageToProcess;
	}

	@Override
	public void send(Message msg) {
		new Thread(new GatewayImpl(this.resourceAvailable, msg)).start();
	}

	@Override
	public void run() {
		// here we simulate the message processing sleeping the thread
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOG.info("Gateway is going to process the message: [" + this.messageToProcess + "]");
		this.messageToProcess.completed();
		this.resourceAvailable.release();
	}

}

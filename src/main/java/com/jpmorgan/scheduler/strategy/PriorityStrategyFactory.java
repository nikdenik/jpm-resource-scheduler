package com.jpmorgan.scheduler.strategy;

import com.jpmorgan.scheduler.interfaces.PriorityStrategy;

public class PriorityStrategyFactory {
	
	public static PriorityStrategy getGroupPriorityStrategy() {
		return GroupIDPriorityStrategyImpl.getInstance();
	}
	
	public static PriorityStrategy getOddAndEvenMessageIDPriorityStrategy() {
		return OddAndEvenMessageIDPriorityStrategyImpl.getInstance();
	}

}

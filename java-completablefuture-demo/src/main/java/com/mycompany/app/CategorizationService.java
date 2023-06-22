package com.mycompany.app;

public class CategorizationService {
	public static Category categorizeTransaction(Transaction transaction) {
		delay();
		return new Category("Category_" + transaction.getId());
	}
	
	public static void delay() {
		try {
			Thread.sleep(1000L);
			
		}catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}

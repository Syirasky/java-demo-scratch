package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoOne {

	public static void main(String[] args) {
		standardProcess();
		parallelProcess();		
		completablefutureProcess();
	}
	public static void standardProcess() {
		System.out.println("standard process started.");
		long start = System.currentTimeMillis();
		List<Category> categories = Stream.of(
				new Transaction("1","description 1"),
				new Transaction("2","description 2"),
				new Transaction("3","description 3"))
				.map(CategorizationService::categorizeTransaction)
				.collect(Collectors.toList());
		long end = System.currentTimeMillis();
		
		System.out.printf("The operation took %s ms%n", end - start);
		System.out.println("Categories are: " + categories);
		
	}

	public static void parallelProcess(){
		System.out.println("parallel process started.");
		long start = System.currentTimeMillis();
		List<Category> categories = Stream.of(
			new Transaction("1","description 1"),
			new Transaction("2","description 2"),
			new Transaction("3","description 3"))
			.parallel()
			.map(CategorizationService::categorizeTransaction)
			.collect(Collectors.toList());

		long end = System.currentTimeMillis();
		System.out.printf("The operation took %s ms%n", end - start);
		System.out.println("Categories are: " + categories);
		
	}

	public static void completablefutureProcess(){
		// create thread to execute completablefuture
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		long start = System.currentTimeMillis();
		List<CompletableFuture<Category>> futureCategories = new ArrayList<>();
		List<Transaction> transactions = Arrays.asList(
			new Transaction("1", "description 1"),
            new Transaction("2", "description 2"),
            new Transaction("3", "description 3"),
            new Transaction("4", "description 4"),
            new Transaction("5", "description 5"),
            new Transaction("6", "description 6"),
            new Transaction("7", "description 7"),
            new Transaction("8", "description 8"),
            new Transaction("9", "description 9"),
            new Transaction("10", "description 10"));
			
		// execute the process in multiple thread
		for (Transaction transaction : transactions){
			CompletableFuture<Category> futureCategory = 
				CompletableFuture.supplyAsync(() -> CategorizationService.categorizeTransaction(transaction), executor);
			futureCategories.add(futureCategory);
		}	

		// get the completablefuture results
		List<Category> categories = futureCategories.stream().map(CompletableFuture::join).collect(Collectors.toList());
	
		// shutdown/kill the threads
		executor.shutdown();
		long end = System.currentTimeMillis();
		System.out.printf("The operation took %s ms%n", end - start);
    	System.out.println("Categories are: " + categories);
	}


}

package com.thread;

import java.util.ArrayList;
import java.util.List;

public class PubSubCaller {
	
	public static void main(String[] args) throws InterruptedException {
		
		List<String> list = new ArrayList<>();
		String toBeProcessed = "raghav";
		
		Thread thread= new Thread(new PublisherThread(list, toBeProcessed), "PUB");
		Thread thread2 =  new Thread(new SubscriberThread(list), "SUB");
		thread.start();
		thread2.start();
		
		
	}


}

class PublisherThread implements Runnable {
	
	public List<String> queue;
	private String processMe;
	
	public PublisherThread(List<String> queue, String processMe) {
		this.queue = queue;
		this.processMe = processMe;
	}

	@Override
	public void run() {
		
		synchronized (queue) {
			int k=0;
			for (int i=0; i< processMe.length(); i++) {
				k++;
				longRunningProcess();
				queue.add(String.valueOf(processMe.charAt(i)));
				if (k % 2== 0 ) {
					try {
						queue.wait();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				System.out.println("notify consumer of items added");
				queue.notify();
			}
			
			System.out.println("Everything processed ... exiting ");
			System.exit(0);
		}
		
	}

	private void longRunningProcess() {
		for (int i=0; i < 100 ; i ++) {
			for (int j=0; j< 100 ; j ++) {
				System.out.print("");
			}
		}
		
	}
	
}

class SubscriberThread implements Runnable {

	public List<String> queue;
	public SubscriberThread(List<String> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
		int count=0;
		
		while (true) {
			synchronized (queue) {
				while (queue.isEmpty()) {
					
					try {
						System.out.println(Thread.currentThread().getName() + ":    " + "waiting to process items...");
						queue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//						
				}
				String item = queue.remove(0);
				count++;
				if (null!= item) {
					System.out.print(item + " , ");
				}
				if (count % 2 ==0 ) {
					queue.notify();
				}
			}
				
		}
	}
		
}
	


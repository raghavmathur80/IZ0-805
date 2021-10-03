package com.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PubSubCallerBlockingQueue {
	
	public static void main(String[] args) throws InterruptedException {
		
		BlockingQueue<String> queue =  new ArrayBlockingQueue<>(6);
		String toBeProcessed = "raghav";
		
		Thread thread= new Thread(new PublisherThreadBQ(queue, toBeProcessed), "PUB");
		Thread thread2 =  new Thread(new SubscriberThreadBQ(queue), "SUB");
		
		
		thread.start();
		thread2.start();
		
		thread2.join();
	}


}

class PublisherThreadBQ implements Runnable {
	
	public BlockingQueue<String> queue;
	private String processMe;
	
	public PublisherThreadBQ(BlockingQueue<String> queue, String processMe) {
		this.queue = queue;
		this.processMe = processMe;
	}

	@Override
	public void run() {
		
			int k=0;
			for (int i=0; i< processMe.length(); i++) {
				k++;
				System.out.println(String.valueOf(processMe.charAt(i)) + "     added");
				try {
					queue.put(String.valueOf(processMe.charAt(i)));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (k % 2== 0 ) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		
	}

	private void longRunningProcess() {
		for (int i=0; i < 100 ; i ++) {
			for (int j=0; j< 100 ; j ++) {
				for (int k=0; k< 100 ; k ++) {
					System.out.print("");
				}
			}
		}
		
	}
	
}

class SubscriberThreadBQ implements Runnable {

	public BlockingQueue<String> queue;
	
	public SubscriberThreadBQ(BlockingQueue<String> queue) {

		this.queue = queue;
	}
	
	@Override
	public void run() {
			System.out.println("SUB run invoked");
				while(!queue.isEmpty()) {
					String item;
					try {
						Thread.sleep(10);
						item = queue.take();
						System.out.print(item + " , ");
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			
		}	
}
	


package com.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PubSubCallerCondition {
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		String toBeProcessed = "raghav";
		
		Lock lock = new ReentrantLock();
		Condition giveMe = lock.newCondition();
		Condition takeIt = lock.newCondition();		
		
		Thread thread= new Thread(new PublisherThreadCondition(list, toBeProcessed, giveMe,takeIt, lock), "PUB");
		Thread thread2 =  new Thread(new SubscriberConditionThread(list,giveMe, takeIt, lock), "SUB");
		
		thread.start();
		thread2.start();
		
		System.out.println("Executing in PubSubCallerCondition");
		
	}


}

class PublisherThreadCondition implements Runnable {
	
	public List<String> queue;
	private String processMe;
	private Condition giveMe;
	private Condition takeIt;
	private Lock lock;
	
	public PublisherThreadCondition(List<String> queue, String processMe, Condition giveMe, Condition takeIt, Lock lock) {
		this.queue = queue;
		this.processMe = processMe;
		this.takeIt = takeIt;
		this.giveMe = giveMe;
		this.lock = lock;
	}

	@Override
	public void run() {
		
		lock.lock();
			int k=0;
			for (int i=0; i< processMe.length(); i++) {
				k++;
				longRunningProcess();
				queue.add(String.valueOf(processMe.charAt(i)));
				if (k % 2== 0 ) {
					try {
						giveMe.await();
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				System.out.println("notify consumer of items added");
				takeIt.signal();
			}
			
			System.out.println("Everything processed ... exiting ");
			System.exit(0);
		lock.unlock();
		
	}

	private void longRunningProcess() {
		for (int i=0; i < 100 ; i ++) {
			for (int j=0; j< 100 ; j ++) {
				System.out.print("");
			}
		}
		
	}
	
}

class SubscriberConditionThread implements Runnable {

	public List<String> queue;
	private Condition giveMe;
	private Condition takeIt;
	private Lock lock;
	
	public SubscriberConditionThread(List<String> queue, Condition giveMe, Condition takeIt, Lock lock) {
		this.queue = queue;
		this.giveMe = giveMe;
		this.takeIt = takeIt;
		this.lock = lock;
	}
	
	@Override
	public void run() {
		
		int count=0;
		
		while (true) {
			lock.lock();
				while (queue.isEmpty()) {
					
					try {
						System.out.println(Thread.currentThread().getName() + ":    " + "waiting to process items...");
						takeIt.await();
					} catch (InterruptedException e) {
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
					giveMe.signal();
				}
			lock.unlock();
		}
	}
		
}
	


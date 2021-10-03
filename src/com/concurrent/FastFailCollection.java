package com.concurrent;

import java.util.ArrayList;
import java.util.List;

public class FastFailCollection {
	
	
	public static void main(String[] args) throws InterruptedException {
		
		
		List<String> list =  new ArrayList<>();
		Thread t1 = new Thread(new CollectionWriter(list));
		Thread t2 = new Thread(new CollectionReader(list));
		
		t1.start();
		t2.start();
		
		
	}
	
	

}

class CollectionWriter extends Thread {
	
	private List<String> list;
	
	public CollectionWriter (List<String> list) {
		this.list = list;
	}
	
	public void run() {
		
		for (int i=0; i< 50; i ++) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list.add(String.valueOf(i));
		}
	}

	
}

class CollectionReader extends Thread {
	
	private List<String> list;
	
	public CollectionReader(List<String> list) {
		this.list = list;
	}
	
	public void run() {
		
		while(!list.isEmpty()) {
				System.out.println(list.remove(0));
			}
		}
	
}
	
	
	

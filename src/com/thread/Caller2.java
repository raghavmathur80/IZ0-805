package com.thread;

public class Caller2 {
		
	public static void main(String[] args) throws InterruptedException {
		WaitNotify wt = new WaitNotify();
		System.out.print("counter starting...");	
		for (int i=0;i< 100;i++){
			
			if (i == 99){
				wt.lock();
				System.out.println("waiting....");
			}
		}
	}
}

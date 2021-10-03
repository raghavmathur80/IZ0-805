package com.thread;

public class WaitNotify {
	private boolean lock = false;
	
	public synchronized void lock( ) {
		this.lock = true;
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void unlock() {
		this.lock = false;
		notify();
	}

}

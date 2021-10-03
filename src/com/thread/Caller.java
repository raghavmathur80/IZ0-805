package com.thread;

public class Caller {
	
	public static void main(String[] args) throws InterruptedException {
		
		int[] numbers = new int[99000];
		int threadCount = 3;
		
		StringBuffer buffer = new StringBuffer("a");
//		Thread thread1 =  new Thread(new ThreadWriter(buffer)); 
//		Thread thread2 =  new Thread(new ThreadWriter(buffer)); 
//		Thread thread3 =  new Thread(new ThreadWriter(buffer)); 
//		
//		thread1.start();
//		thread2.start();
//		thread3.start();
		int start = 0;
		int end = 0;
		for (int i=1; i<= threadCount; i++) {
			start = i;
			end = (numbers.length/threadCount) *i;
			
			Thread thread = new Thread(new NumberPopulator(numbers, i, end), "ABC".concat(String.valueOf(i)));
			thread.start();
			start = end;
			
//			thread.join();
			
		}
		
		
//		System.out.println(numbers[66767]);
		
	}
}

	class NumberPopulator implements Runnable{

		private int[] num;
		private int start;
		private int end;
		
		public NumberPopulator(int[] numbers, int start, int end ) {
			this.num = numbers;
			this.start = start;
			this.end = end;
		}
		
		@Override
		public void run() {
			
			System.out.println("CURRENT THREAD RUNNING:         " + Thread.currentThread().getName());
			
			for (int i= start;i<=end-1; i++ ) {
				num[i] = i;
 			}
			
			System.out.println(":::::::::::"+num[98999]);
		}
		
	}
	class ThreadWriter implements Runnable{
		
		private StringBuffer buffer;
		
		public ThreadWriter(StringBuffer buffer) {
			this.buffer = buffer;
		}
		/**
		 * Predictable sequence of alphabets  since called from Sync block
		 */
		@Override
		public void run() {
			char current = '\0';
			synchronized (buffer) {
				for (int i=0; i< buffer.length(); i++) {
					for(int j=1;j<=100;j++) {
					}
					current = buffer.charAt(i);
					System.out.print(current);
				}
				if (current == 'a') {
					buffer.replace(0, 1, "b");
				}

				if (current == 'b') {
					buffer.replace(0, 1, "c");
				}
			}
			
		}
		
		/**
		 * Unpredictable sequence of alphabets 
		 */
		
//		@Override
//		public void run() {
//			char current = '\0';
//				for (int i=0; i< buffer.length(); i++) {
//					for(int j=1;j<=100;j++) {
//					}
//					current = buffer.charAt(i);
//					System.out.print(current);
//				}
//				if (current == 'a') {
//					buffer.replace(0, 1, "b");
//				}
//
//				if (current == 'b') {
//					buffer.replace(0, 1, "c");
//				}
//			
//		}

		
	}

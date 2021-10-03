package com.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ExecutorRecursionCaller {
	
	private static int[] unsortedNumbers = { 9, 2, 4, 5, 8, 3, 1, 7, 6, 10, 100, 99, 89, 88,199,290};
	static int low = 0;
	static int high = unsortedNumbers.length - 1;
	static int []aux = new int[unsortedNumbers.length];
	
	public static void main(String[] args) {
		
		
		ForkJoinPool forkJoinPool =  new ForkJoinPool(2);
		ExecutorRecursionCaller.ExecutorRecursionAction action =  new ExecutorRecursionCaller().new ExecutorRecursionAction(aux, unsortedNumbers, low, high);
		forkJoinPool.invoke(action);
		print();
	}
	
	private static void print() {
		for (int i = 0; i < aux.length; i++) {
			System.out.print(aux[i] + " , ");
		}
	}
	
	private class ExecutorRecursionAction extends RecursiveAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int[]  aux;
		int[] data;
		int low;
		int high;
		
		public ExecutorRecursionAction (int aux[], int[] data, int low, int high) {
			this.aux = aux;
			this.data = data;
			this.low = low;
			this.high = high;
		}
		
		/**
		 *  This method is called recursively via invokeAll 
		 */
		@Override
		protected void compute() {
			
			if (low >= high) {
				return;
			}
			
			int mid = low + (high - low) / 2;
//			DIVIDE THE TASKS USING INVOKEALL WHICH DOES THE FORKING AND JOINING. ALTERNATIVELY CAN BE DONE VIA FORK, COMPUTE AND JOIN METHODS
			ExecutorRecursionAction left = new ExecutorRecursionAction(aux, data, low, mid);
			ExecutorRecursionAction right = new ExecutorRecursionAction(aux, data, mid+1, high);

//			invokeAll(new ExecutorRecursionAction(aux, data, low, mid), new ExecutorRecursionAction(aux, data, mid+1, high));
			left.fork();
			right.compute();
			left.join();			
			
			merge(aux, low, mid, high);
		}
		
		public void merge(int[]aux, int lo, int mid, int hi) {

	        // copy to aux[]
	        for (int k = lo; k <= hi; k++) {
	            aux[k] = data[k]; 
	        }

	        // merge back to unsortedNumbers from aux[]
	        int i = lo, j = mid+1;
	        for (int k = lo; k <= hi; k++) {
//	         
	        	// if i(left side)  gores out of bounds 
	        	if (i > mid) {
	        		data[k] = aux[j++];
	        	}
	        	
	        	// if j (mid +1, right side) gores out of bounds
	        	else if (j > hi) {
	        		data[k] = aux[i++];
	        	}
	        	
	        	//copy the value of index j and increment 
	        	else if (aux[j]< aux[i]) {
	        		data[k] = aux[j++];
	        	}
	        	// copy the value of index i and increment
	        	else {
	        		data[k] = aux[i++];
	        	}
	        	
	        }
		

	}
		
	}

}





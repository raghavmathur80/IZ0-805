package com.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ExecutorTaskRecursionCaller {
	
	private static int[] unsortedNumbers = { 9, 2, 4, 5, 8, 3, 1, 7, 6, 10, 100, 99, 89, 88};
	static int low = 0;
	static int high = unsortedNumbers.length - 1;
	static int []aux = new int[unsortedNumbers.length];
	
	public static void main(String[] args) {
		
		
		ForkJoinPool forkJoinPool =  new ForkJoinPool(2);
//		ExecutorTaskRecursionCaller.ExecutorRecursionTask action =  new ExecutorTaskRecursionCaller().new ExecutorRecursionTask(aux, unsortedNumbers, low, high);
//		forkJoinPool.invoke(action);
		print();
	}
	
	private static void print() {
		for (int i = 0; i < aux.length; i++) {
			System.out.print(aux[i] + " , ");
		}
	}
	
	private class ExecutorRecursionTask extends RecursiveTask<Integer> {

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ExecutorRecursionTask () {
			
		}

		@Override
		protected Integer compute() {
			// TODO Auto-generated method stub
			return null;
		}
		
		

	}
		
	}







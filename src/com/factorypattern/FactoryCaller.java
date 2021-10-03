package com.factorypattern;

import java.util.Optional;
import java.util.function.Supplier;

public class FactoryCaller<T> {

	public  T getInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		
		return clazz.newInstance();
	}
	
	public  static <T> T getInstanceStatically(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		
		return clazz.newInstance();
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Optional.of(FactoryCaller.getInstanceStatically(TomatoPizza.class))
			.get().getPizza();

		System.out.println("worked");
				
	}
	
}

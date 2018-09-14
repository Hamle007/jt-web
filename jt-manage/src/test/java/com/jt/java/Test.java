package com.jt.java;

public class Test {

	static final int max = Integer.MAX_VALUE;
	
	static final int start = max - 100;
	
	public static void main(String[] args) {
		int j = 0;
		for (int i = start; i < max; i++) {
			j++;
		}
		System.out.println(j);
	}
}

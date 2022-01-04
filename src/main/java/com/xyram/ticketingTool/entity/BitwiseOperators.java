package com.xyram.ticketingTool.entity;


import java.util.Scanner;

public class BitwiseOperators {
	private static Scanner sc;
	public static void main(String[] args) {
		int a, b,c;
		sc = new Scanner(System.in);
		System.out.println(" Please Enter two integer Value: ");
		a = sc.nextInt();
		b = sc.nextInt();
		c=sc.nextInt();
		
		System.out.format(" Bitwise AND Operator: %d & %d = %d \n", a, b, a & b);
		System.out.format(" Bitwise OR Operator: %d | %d | %d = %d \n", a, b,c, a | b | c);
		System.out.format(" Bitwise EXCLUSIVE OR: Operator %d ^ %d = %d \n", a, b, a ^ b);
		System.out.format(" Bitwise NOT Operator: %d = %d \n", a, a);
		
		System.out.format(" LEFT SHIFT Operator: %d << 1 = %d \n", a, a << 1);
		System.out.format(" RIGHT SHIFT Operator: %d >> 1 = %d \n", b, b >> 1);
	}
}
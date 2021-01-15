package com.crm.controller;

public class Demo {
public static void main(String[] args) {
	int i = -153423646;
	String s = "9646324351";
	StringBuilder sb  = new StringBuilder();
	sb.append(Math.abs(i)).reverse().toString();
	System.out.println(sb);
	int val = i<0? Integer.parseInt(sb.toString())*-1:0;
	System.out.println(val);
}
}

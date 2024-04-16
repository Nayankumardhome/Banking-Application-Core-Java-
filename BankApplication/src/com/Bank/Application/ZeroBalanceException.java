package com.Bank.Application;

@SuppressWarnings("serial")
public class ZeroBalanceException extends Exception{

	public ZeroBalanceException() {
		// TODO Auto-generated constructor stub
	}

	public ZeroBalanceException(String message) {
		super(message);
	}
	
}

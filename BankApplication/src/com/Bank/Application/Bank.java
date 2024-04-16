package com.Bank.Application;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Bank {
	
	public static void main(String[] args) {
		
		Account[] ref = new Account[10000];
		
		int choice = 0;
		int accountCount = 0;
		
		Scanner sc = new Scanner(System.in);
		
		while(choice != 9) {
			boolean fine = false;
			while(fine == false) {
				System.out.println("1.Create Account");
				System.out.println("2.Close Account");
				System.out.println("3.deposite");
				System.out.println("4.withdraw");
				System.out.println("5.Apply for Loan");
				System.out.println("6.Calculate Intrest");
				System.out.println("7.Show Account");				
				System.out.println("8.Today's Bank Report");
				System.out.println("9.Exit");
				try {
					choice = sc.nextInt();
					switch(choice) {
					
						case 1:{
							System.out.println("1.Saving Account.");
							System.out.println("2.Salary Account.");
							System.out.println("3.Current Account.");
							System.out.println("4.Loan Account.");
							
							int type = sc.nextInt();
							long accountNumber;
							boolean check = false;
							do {
							    accountNumber = generateUniqueAccountNumber();
							    check = false;
							    for (int i = 0; i < accountCount; i++) {
							        if (ref[i].getAccountNumber() == accountNumber) {
							            check = true;
							            break;
							        }
							    }
							} while(check);
						  
						    System.out.print("Enter account holder name: ");
						    String name = sc.next();

						    System.out.print("Enter initial balance: ");
						    double balance = sc.nextDouble();
							
							switch (type) {
							    case 1:
							    	if (balance >= SavingAccount.minimumBalance) {
						                if (accountCount < ref.length) {
						                    ref[accountCount++] = new SavingAccount(accountNumber, name, balance);
						                    System.out.println("Account Number : " + accountNumber);
						                    System.out.println("Saving Account is Successfully Created");
						                } else {
						                    System.out.println("Cannot create more accounts. Array is full.");
						                }
						            } else {
						                System.out.println("This is a Saving Account initial Minimum balance must be 10000");
						            }
							        break;
							    case 2:
							        if (balance >= SalaryAccount.minimumBalance) {
							            ref[accountCount++] = new SalaryAccount(accountNumber, name, balance);
							            System.out.println("Account Number : " + accountNumber);
							            System.out.println("Salary Account is Successfully Created");
							        } else {
							            System.out.println("This is a Salary Account initial Minimum balance must be 10000");
							        }
							        break;
							    case 3:
							        if (balance >= CurrentAccount.minimumBalance) {
							            ref[accountCount++] = new CurrentAccount(accountNumber, name, balance);
							            System.out.println("Account Number : " + accountNumber);
							            System.out.println("Current Account is Successfully Created");
							        } else {
							            System.out.println("This is a Current Account initial Minimum balance must be 10000");
							        }
							        break;
							    case 4:
							        if (balance == 0) {
							            ref[accountCount++] = new LoanAccount(accountNumber, name, balance);
							            System.out.println("Account Number : " + accountNumber);
							            System.out.println("Loan Account is Successfully Created");
							        } else {
							            System.out.println("This is a Loan Account initial balance must be Zero");
							        }
							        break;
							    default:
							        System.out.println("Invalid choice");
							        break;
							}
							
							break;
						}
						case 2:{
							
							System.out.print("Enter account number to Close: ");
						    long accountNumberToClose = sc.nextLong();

						    System.out.print("Enter account holder name to Close: ");
						    String accountHolderNameToClose = sc.next();
						    
						    Account accountToClose = searchAccount(ref, accountCount, accountNumberToClose);
						    if (accountToClose != null && (accountToClose.getName().equals(accountHolderNameToClose))) {
						    	closeAccount(accountToClose);
						    } else {
						        System.out.println("Account with account number " + accountNumberToClose + " not found.");
						    }
						    break;
						}
						case 3: {
							System.out.print("Enter account number to deposit: ");
						    long accountNumberToDeposit = sc.nextLong();

						    Account accountToDeposit = searchAccount(ref, accountCount, accountNumberToDeposit);
						    if (accountToDeposit != null && accountToDeposit.getStatus().equals("Active")) {
						        try {
						            System.out.print("Enter the deposit amount: ");
						            double depositAmount = sc.nextDouble();
						            accountToDeposit.deposit(depositAmount);
						            System.out.println("Deposit successful. Updated balance: " + accountToDeposit.getBalance());
						        } catch (InputMismatchException e) {
						            System.out.println("Invalid input for deposit amount. Please enter a valid number.");
						            sc.nextLine();
						        }
						    } else {
						        System.out.println("Account with account number " + accountNumberToDeposit + " not found.");
						    }
						    break;
						}
						case 4: {
							System.out.print("Enter account number to withdraw: ");
						    long accountNumberToWithdraw = sc.nextLong();

						    Account accountToWithdraw = searchAccount(ref, accountCount, accountNumberToWithdraw);
						    if (accountToWithdraw != null && accountToWithdraw.getStatus().equals("Active")) {
						        try {
						            System.out.print("Enter the withdrawal amount: ");
						            double withdrawalAmount = sc.nextDouble();
						            accountToWithdraw.withdraw(withdrawalAmount);
						            System.out.println("Withdrawal successful. Updated balance: " + accountToWithdraw.getBalance());
						        } catch (InputMismatchException e) {
						            System.out.println("Invalid input for withdrawal amount. Please enter a valid number.");
						            sc.nextLine();
						        }
						    } else {
						        System.out.println("Account with account number " + accountNumberToWithdraw + " not found.");
						    }
						    break;
						}
						case 5:{
							System.out.println("1. Apply For Loan Process");
							System.out.println("2. Repayment of Loan in EMI");
							System.out.println("Enter Your Choice");
							int ch = sc.nextInt();
							
							System.out.print("Enter account number for loan process : ");
							long loanAccount = sc.nextLong();
							Account accountToLoan = searchAccount(ref, accountCount, loanAccount);

							if(ch == 1)
							{
								System.out.println("Enter Your Loaner Name : ");
								String loanerName = sc.next();
								if(accountToLoan != null && (accountToLoan.getName().equals(loanerName))&& accountToLoan instanceof LoanAccount) {
									
									System.out.println("Enter Your income : ");
									double income = sc.nextDouble();
									
									if(((LoanAccount) accountToLoan).loanProcess(income)) {
										System.out.println("Enter How much loan you needed..");
										double loanAmount = sc.nextDouble();
										if(loanAmount <= LoanAccount.sactionLoan) {
											accountToLoan.withdraw(loanAmount);
											accountToLoan.display();
//											System.out.println("Sanction Loan : " + LoanAccount.sactionLoan + loanAmount);
										}
										else {
											System.out.println("Loan Amount is Higher than Sanction Loan");
											System.out.println("Sanction Loan : " + LoanAccount.sactionLoan + "Loan Ammount" + loanAmount);
										}
									}
									else {
										System.out.println("Income is too Low!!");
										System.out.println("You are Not eligible...");
									}
								}
								else {
									System.out.println("Account is Not Found.");
								}
							}
							else if(ch == 2)
								{
									
									
									if(accountToLoan != null && accountToLoan instanceof LoanAccount) {
											
											LoanAccount loanAccountref = (LoanAccount) accountToLoan;
											double EMI = -(loanAccountref.calculateEmiMonth());
											System.out.println("Your Repayment Amount on Monthly EMI : " + EMI);
									

											double amt = 0;
											String ch2 = "Yes" ;
											while(ch2.equals("Yes"))
											{
												System.out.println("Enter amount to pay :");
												amt = sc.nextDouble();
												if(amt < EMI)
												{
													System.out.println("Your credit amount is less than EMI Amount your loan period will increase...");
													System.out.println("If you want to re-enter Amount press (Yes/No) :");
													ch2 = sc.next();
												}
												else
												{
													break;
												}
											}
											accountToLoan.deposit(amt);
											System.out.println(amt + " is Successfully Credited in Your Loan Acccount Number " + loanAccount);
											System.out.println("New Balance : " + accountToLoan.getBalance());
											
										}
									else
									{
								        System.out.println("EMI Repayment is not applicable to this type of account.");
									}
									
								}
							else
							{
								System.out.println("Enter valid choice");
							}
							break;			
							
							}
							
						case 6: {
						    System.out.print("Enter account number to calculate interest for: ");
						    long accountNumberToCalculateInterest = sc.nextLong();

						    Account accountToCalculateInterest = searchAccount(ref, accountCount, accountNumberToCalculateInterest);
						    if (accountToCalculateInterest != null) {
						    	int choiceTimePeriod = 0;
						    	boolean allisfine = false;
						    	while(allisfine == false) {
						    		System.out.println("1.Month");
							    	System.out.println("2.Anual");
							    	try {
							    		choiceTimePeriod = sc.nextInt();
							    		allisfine = true;
							    	}
							    	catch(InputMismatchException ime) {
							    		System.out.println("Invalid Input..");
							    		sc.next();
							    	}
							    	catch(Exception e) {
							    		System.out.println("Something is Wrong..");
							    		sc.next();
							    	}
						    	}
						    	double interest = 0;
						    	if(choiceTimePeriod==1){
						    		interest = accountToCalculateInterest.showCalculateRateOfInterest(choiceTimePeriod);
						    		System.out.println("Interest calculated. Interest amount (Monthly Basis): " + interest);
						    	}
						    	else if(choiceTimePeriod==2) {
						    		interest = accountToCalculateInterest.showCalculateRateOfInterest(choiceTimePeriod);
						    		System.out.println("Interest calculated. Interest amount (Anual Basis): " + interest);
						    	}
						    	else {
						    		System.out.println("Invalid choice..");
						    	}
						        System.out.println("Interest calculated. Interest amount: " + interest);
						    } else {
						        System.out.println("Account with account number " + accountNumberToCalculateInterest + " not found.");
						    }
						    break;
						}
						case 7:{
							System.out.print("Enter account number to display details for: ");
						    long accountNumberToShowDetails = sc.nextLong();

						    Account accountToDisplay = searchAccount(ref, accountCount, accountNumberToShowDetails);
						    if (accountToDisplay != null) {
						    	accountToDisplay.display();
						    } else {
						        System.out.println("Account with account number " + accountNumberToShowDetails + " not found.");
						    }
						    break;
						}
						case 8:{
							showTodayTransactions(ref, accountCount);
						    break;
						}
						case 9:{
							System.out.println("Exit..");
							break;
						}
						default :{
							System.out.println("Invlaid Choice..");
						}
					}
					fine = true;
				}
				catch(InputMismatchException ime) {
		    		System.out.println("Invalid Input..");
		    		sc.next();
		    	}
		    	catch(Exception e) {
		    		System.out.println("Something is Wrong..");
		    		sc.next();
		    	}
			}
		}
	sc.close();
	}
	
	private static void deleteAccount(Account[] accounts, long accountCount, long accountNumberToDelete) {
	    int deletedAccountIndex = -1;

	    for (int i = 0; i < accountCount; i++) {
	        if (accounts[i].getAccountNumber() == accountNumberToDelete) {
	            deletedAccountIndex = i;
	            break;
	        }
	    }

	    if (deletedAccountIndex != -1) {
	        for (int i = deletedAccountIndex; i < accountCount - 1; i++) {
	            accounts[i] = accounts[i + 1];
	        }

	        accountCount--;

	        System.out.println("Account with account number " + accountNumberToDelete + " deleted successfully.");
	    }
	}
	
	private static boolean hasLastTransactionBefore(Account account, LocalDate date) {
	    if (account.getTransactionCount() > 0) {
	        Transaction lastTransaction = account.getTransactions()[account.getTransactionCount() - 1];
	        return lastTransaction.getTransactionDate().isBefore(date);
	    }
	    return false;
	}
	
	private static void handleClosedAccount(Account[] accounts, long accountCount, Account account) {
	    LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);
	    if (hasLastTransactionBefore(account, twoMonthsAgo)) {
	        deleteAccount(accounts, accountCount, account.getAccountNumber());
	        System.out.println("Account closed due to no transactions for more than 2 months.\n");
	    }
	}

	private static void handleFrozenSalaryAccount(Account account) {
	    LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);
	    if (hasLastTransactionBefore(account, twoMonthsAgo)) {
	        account.setStatus("Frozen");
	        System.out.println("Salary account frozen due to no transactions for more than 2 months.\n");
	    }
	}

	private static void showTodayTransactions(Account[] accounts, int accountCount) {
	    LocalDate today = LocalDate.now();

	    System.out.println("Today's Transactions");
	    System.out.println("==========================================================================");

	    for (int i = 0; i < accountCount; i++) {
	        Account account = accounts[i];

	        if (account.getStatus().equalsIgnoreCase("Frozen") || account.getStatus().equalsIgnoreCase("Closed")) {
	            boolean transactionsFound = false;

	            for (int j = 0; j < account.transactionCount; j++) {
	                Transaction transaction = accounts[i].transactions[j];

	                if (transaction.getTransactionDate().isEqual(today)) {
	                    if (!transactionsFound) {
	                        System.out.println("Account Number: " + account.getAccountNumber() +
	                                " is " + account.getStatus().toLowerCase() + "\n");
	                        System.out.println("\nTransactions Today:");
		                    System.out.println("Account Holder Name : " + account.getName());
	                        transactionsFound = true;
	                    }
	                    System.out.println("Transaction ID: " + transaction.getTransactionID());
	                    System.out.println("Transaction Type: " + transaction.getTransactionType());
	                    System.out.println("Amount: " + transaction.getAmount());
	                    System.out.println("Timestamp: " + transaction.getTimestamp());
	                    System.out.println("------------------------------------------------------------------------------------");
//	                    accounts[i].updateRateOfInterest();
	                }
	            }

	            if (account.getStatus().equalsIgnoreCase("Closed") && transactionsFound) {
	                continue;
	            }

	            if (account.getStatus().equalsIgnoreCase("Closed")) {
	                handleClosedAccount(accounts, accountCount, account);
	            }

	            continue;
	        }

	        System.out.println("Account Number: " + account.getAccountNumber());
	        System.out.println("Account Holder Name: " + account.getName());
	        System.out.println("Account Status: " + account.getStatus());

	        boolean transactionsFound = false;

	        for (int j = 0; j < account.transactionCount; j++) {
	            Transaction transaction = accounts[i].transactions[j];

	            if (transaction.getTransactionDate().isEqual(today)) {
	                if (!transactionsFound) {
	                    System.out.println("\nTransactions Today:");
	                    transactionsFound = true;
	                }

	                System.out.println("Transaction ID: " + transaction.getTransactionID());
	                System.out.println("Transaction Type: " + transaction.getTransactionType());
	                System.out.println("Amount: " + transaction.getAmount());
	                System.out.println("Timestamp: " + transaction.getTimestamp());
	                System.out.println("------------------------------------------------------------------------------------");
	                accounts[i].updateRateOfInterest();
	            }
	        }

	        if (account instanceof SalaryAccount) {
	        	handleFrozenSalaryAccount(account);
	        }

	        if (!transactionsFound) {
	            System.out.println("No transactions today for this account.\n");
	            
	        }
	    }
	}

	
	public static void closeAccount(Account account) {
	    if (account.getStatus().equals("Active")) {
	        Scanner sc = new Scanner(System.in);
	        try {
	            boolean caccount = false;
	            String input = null;
	            while (!caccount) {
	                System.out.println("Do you want to withdraw your money? (Y/N)");
	                input = sc.nextLine().trim();
	                if (!input.isEmpty() && (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N"))) {
	                    caccount = true;
	                } else {
	                    System.out.println("Please enter either Y or N.");
	                }
	            }
	            if (input.equalsIgnoreCase("Y")) {
	                Transaction withdrawTransaction = new Transaction("Withdraw", account.getBalance());
	                account.setBalance(0);
	                account.getTransactions()[account.getTransactionCount()] = withdrawTransaction;
	                account.setTransactionCount(account.getTransactionCount() + 1);
	            }
	            account.setStatus("Closed");
	            System.out.println("Account closed successfully.");
	        } catch (Exception e) {
	            System.out.println("Something went wrong.");
	        }
	    } else {
	        System.out.println("Account is already closed.");
	    }
	}
	
	private static Account searchAccount(Account[] accounts, int accountCount, long accountNumber) {
	    for (int i = 0; i < accountCount; i++) {
	        if (accounts[i] != null && accounts[i].getAccountNumber() == accountNumber) {
	            return accounts[i];
	        }
	    }
	    return null;
	}
	
    private static long generateUniqueAccountNumber() {    	
        Random random = new Random();
        long max = 99999999999999L;
        long min = 10000000000000L;
        long generatedNumber = (long) (random.nextDouble() * (max - min) + min);
        return generatedNumber;
    	
    }
}
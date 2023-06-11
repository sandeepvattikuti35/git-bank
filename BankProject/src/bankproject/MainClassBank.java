package bankproject;

import java.util.Scanner;

import bankproject.Bank;
import bankproject.BankDaoImp;

public class MainClassBank
{
	public static void main(String[] args)
	{
		//implicitly up-casting
		//Bank bank = new BankDaoImp();
		//bank.credit();
		
		//Explicitly up-casting
		//Bank bank = (Bank) new BankDaoImp();
		//((Bank)bank).credit();
				
		//object creating for only Class not for interface 
		// so here we are creating object for Implementation class 
		Bank bank =  new BankDaoImp();
		//bank.credit();
		//bank.debit();
		boolean status = true;
		while (status) 
		{
			
		
		System.out.println("Welcome to TECA40 bank");
		 
		System.out.println("Enter \n 1.Credit  \n 2.debit \n 3.mobileTransaction \n 4.bankDetails");
		Scanner scan = new Scanner(System.in);
		 System.out.println("chose 1 or 2 or 3  or 4 any one for do that operation ");
		 int n=scan.nextInt();
		 switch (n) {
		case 1:
				bank.credit();
				break;
		case 2:
				bank.debit();
				break;
		case 3:
				bank.mobileTransaction();
				break;
		case 4: 
				bank.bankDetails();
				break;
		default:
			System.out.println("Invalid option");
		}
		 
		System.out.println("Do you want to continue \n yes   \n no ");
		String response = scan.next();
		 
		if (response.equalsIgnoreCase("yes"))
		{
			status=true;
		}
		else
		{
			status=false;
			System.out.println(" Thank 💕  you 😎 ✔️");
		}		 
		}
	}
}  

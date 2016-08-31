package lab06;

import java.io.*;
import java.util.*;
import lab06.LocInfo;
import stuff.MyClass;

public class Shipping {
	private ArrayList<LocInfo> locList = new ArrayList<LocInfo>();
	private LocInfo newLoc;
	private String[] addrDest, addrRet;
	private String temp, zip;
	private double price, weight, cost;
	private char choice;
	private Scanner inFile, kbd = new Scanner(System.in);

	public void getLocInfo() { //Get location info from file
		try {
			inFile = new Scanner(new File("prices.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Prices file not found.");
		}
		while (inFile.hasNext()) {
			try {
				newLoc = new LocInfo(inFile.nextLine(),Double.parseDouble(inFile.nextLine())); //create a new locInfo object using the next zip and price
			} catch (NoSuchElementException e) {
				System.out.println("Incomplete entry encountered in prices file.");
				return;
			} catch (NumberFormatException e) {
				System.out.println("Prices file is formatted incorrectly.");
				return;
			}
			locList.add(newLoc); //just learned about ArrayList--sounded much more attractive than calling a function just to determine the number of entries!
		}
		inFile.close();
	}

	public void showMenu() { //Do shipping utility menu
		do {
			getChoice();
			doChoice();
		} while (choice!='q');
	}

	private void getChoice() { //Get menu choice
		System.out.printf("%s\n%s\n%s\n","(L)ook up price","(P)rint shipping label","(Q)uit");
		while (true) {
			System.out.print("Enter choice: ");
			temp = kbd.nextLine();
			if (!temp.isEmpty()) {
				choice = temp.toLowerCase().charAt(0);
				if (choice=='l' || choice=='p' || choice=='q')
					return;
			}
			System.out.println("Invalid entry.");
		}
	}

	private void doChoice() { //Do menu choice
		switch (choice) {
		case 'l':
			priceLook();
			return;
		case 'p':
			printLabel();
		}
	}

	private void priceLook() { //Do shipping price look-up
		getSearchStr();
		getMatch();
	}

	private void getSearchStr() { //Get location from user
		System.out.println();
		while (true) {
			System.out.print("ZIP code: ");
			zip = kbd.nextLine();
			if (!zip.isEmpty())
				return;
			System.out.println("Invalid entry.");
		}
	}

	private void getMatch() { //Get matching location info
		if (findMatch()) //is this acceptable practice?
			showMatch();
	}

	private boolean findMatch() { //Get matching price
		for (LocInfo locCurrent:locList) { //love this
			if (zip.equals(locCurrent.getZip())) {
				price = locCurrent.getPrice();
				return true;
			}
		}
		System.out.printf("Price for ZIP %s not found.\n\n",zip);
		return false;
	}

	private void showMatch() { //Show matching price
		System.out.printf("%s%.2f%s\n\n","Price: $",price,"/lb");
	}

	private void printLabel() { //Create shipping label
		getShipInfo();
		if (getShipCost())
			showLabel();
	}

	private void getShipInfo() { //Get shipping info from user
		addrDest = new String[4];
		addrRet = new String[5];
		System.out.println("\nDestination address");
		addrDest[0] = MyClass.sprompt("Name: ");
		addrDest[1] = MyClass.sprompt("Street: ");
		addrDest[2] = MyClass.sprompt("City: ");
		addrDest[3] = MyClass.sprompt("State: ");
		zip = MyClass.sprompt("ZIP: ");
		System.out.println("Return address");
		addrRet[0] = MyClass.sprompt("Name: ");
		addrRet[1] = MyClass.sprompt("Street: ");
		addrRet[2] = MyClass.sprompt("City: ");
		addrRet[3] = MyClass.sprompt("State: ");
		addrRet[4] = MyClass.sprompt("ZIP: ");
		weight = MyClass.dprompt("Parcel weight (lbs): ");
	}

	private boolean getShipCost() { //Determine shipping cost
		if (findMatch()) {
			calcShipCost();
			return true;
		}
		return false;
	}

	private void calcShipCost() { //Calculate shipping cost
		cost = weight * price;
	}

	private void showLabel() { //Display shipping info to user
		System.out.println("\nShip to:");
		System.out.printf("%s\n%s\n%s, %s %s\n",addrDest[0],addrDest[1],addrDest[2],addrDest[3],zip);
		System.out.println("\nSender:");
		System.out.printf("%s\n%s\n%s, %s %s\n",addrRet[0],addrRet[1],addrRet[2],addrRet[3],addrRet[4]);
		System.out.printf("\n%-15s%7.2f%s\n%-15s$%6.2f\n\n","Parcel weight: ",weight,"lbs","Shipping fee: ",cost);
	}
}
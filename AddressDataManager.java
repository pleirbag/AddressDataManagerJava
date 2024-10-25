import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AddressDataManager {
	public static void main(String[] args) {
		int optionInt;
		DataBaseEditor dbEdit = new DataBaseEditor();
		while (true) {
			System.out.println("\nWelcome to our Phonebook manager\nPlease choose what would you like to access right now:\n" +
					"1. Search existing Contact\n" + "2. Add new Contact\n" + "3. Append existing contact" + "\n4. Exit the program");
			String optionConsole = System.console().readLine();
			try {
				optionInt = Integer.parseInt(optionConsole);
			} catch (NumberFormatException e) {
				optionInt = -1;
			}
			switch (optionInt) {
				case 1:
					dbEdit.searchContact();
					break;
				case 2:
					dbEdit.addNewContact();
					break;
				case 3:
				case 4:
					System.out.println("Thanks and hope to be useful again in the future");
					return;
				case -1:
					System.out.println("Error: not a valid option");

			}
		}
	}
}

class DataBaseEditor{

	public boolean appendNewAddress(PhoneBookInfo newEntry){
		try (FileWriter fileWriter = new FileWriter("Contacts.txt", true)){
			fileWriter.write("\nContact info{ \n");
			fileWriter.write(newEntry.name + "\n");
			fileWriter.write(newEntry.phoneNumber + "\n");
			fileWriter.write(newEntry.postalCode + "\n");
			fileWriter.write(newEntry.mailingAddress + "\n");
			fileWriter.write("}\n");
		}
		catch (IOException e) {
			System.out.println("Error opening the Phonebook file");
			return (false);
		}
		return (true);
	}

	public int verifyInfo(PhoneBookInfo newContactInfo){
		int optionInt;
		System.out.println("Please confirm the following information is correct:\nName: "
				+ newContactInfo.name + "\nPhone Number: " + newContactInfo.phoneNumber + "\nPostal Code: " + newContactInfo.postalCode
				+ "\nMailing Address: " + newContactInfo.mailingAddress);
		System.out.println("\nInsert\n1. to edit one of the fields\n2. to save information");
		String optionConsole = System.console().readLine();
		try {
			optionInt = Integer.parseInt(optionConsole);
		}
		catch (NumberFormatException e){
			optionInt = -1;
		}
		return(optionInt);
	}

	public int editField(PhoneBookInfo newContactInfo){
		int optionInt;
		System.out.println("\nPlease choose which field to edit\n1.Name\n2.Phone Number\n3. Postal Code\n4.Mailing Address\n5.Save changes");
		String optionConsole = System.console().readLine();
		try {
			optionInt = Integer.parseInt(optionConsole);
		}
		catch (NumberFormatException e){
			optionInt = -1;
		}
		switch (optionInt) {
			case 1:
				System.out.println("Insert new name:");
				newContactInfo.name = System.console().readLine();
				return (verifyInfo(newContactInfo));
			case 2:
				System.out.println("Insert new Phone Number:");
				newContactInfo.phoneNumber = System.console().readLine();
				return (verifyInfo(newContactInfo));
			case 3:
				System.out.println("Insert new Postal Code:");
				newContactInfo.postalCode = System.console().readLine();
				return (verifyInfo(newContactInfo));
			case 4:
				System.out.println("Insert new Mailing Address:");
				newContactInfo.mailingAddress = System.console().readLine();
				return (verifyInfo(newContactInfo));
			case 5:
				return (verifyInfo(newContactInfo));
		}
		return -1;
	}

	public void addNewContact(){
		int optionInt = -1;
		boolean isFinished = false;
		PhoneBookInfo newContactInfo = new PhoneBookInfo();
		System.out.println("Please insert the desired name for the new contact:\n");
		newContactInfo.name = System.console().readLine();
		System.out.println("Please insert the phone number for the new contact:\n");
		newContactInfo.phoneNumber = System.console().readLine();
		System.out.println("Please insert the postal code for the new contact:\n");
		newContactInfo.postalCode = System.console().readLine();
		System.out.println("Please insert the mailing address for the new contact:\n");
		newContactInfo.mailingAddress = System.console().readLine();

		while (optionInt == -1)
			optionInt = verifyInfo(newContactInfo);
		while (!isFinished ) {
			if (optionInt == 2)
				isFinished = appendNewAddress(newContactInfo);
			else if (optionInt == 1) {
				optionInt = editField(newContactInfo);
			} else
				System.out.println("Error not a valid option");
		}
		System.out.println(optionInt);
	}

	public PhoneBookInfo returnContact(int nbrInList, String currentLine, PhoneBookInfo contactFound){
		switch (nbrInList%7){
			case 3:
				contactFound.name = currentLine;
				break;
			case 4:
				contactFound.phoneNumber = currentLine;
				break;
			case 5:
				contactFound.postalCode = currentLine;
				break;
			case 6:
				contactFound.mailingAddress = currentLine;
				break;
		}
		return (contactFound);
	}

	public int contactLocation(String searchKey){
		String currentLine;
		PhoneBookInfo contact = new PhoneBookInfo();
		int i = 2;
		int nbrFound = 0;
		boolean printCurrent = false;
		try (RandomAccessFile contactList = new RandomAccessFile("Contacts.txt", "r")){
			currentLine = contactList.readLine();
			while (currentLine != null) {
				currentLine = contactList.readLine();
				if (i %7 > 2) {
					contact = returnContact(i, currentLine, contact);
					if (currentLine.contains(searchKey)) {
						printCurrent = true;
					}
				}
				if (printCurrent && i%7 == 0)
				{
					printContact(contact);
					printCurrent = false;
					nbrFound++;
				}
				i++;
			}
		}
		catch (IOException e)
		{
			System.out.println("Sorry, we encountered an error opening the file");
			return (-1);
		}
		return nbrFound;
	}

	public void printContact(PhoneBookInfo searchResult){
		System.out.println("\nContact information:\nName: "
				+ searchResult.name + "\nPhone Number: " + searchResult.phoneNumber + "\nPostal Code: " + searchResult.postalCode
				+ "\nMailing Address: " + searchResult.mailingAddress + "\n");
	}

	public void searchContact (){
		int searchResult;
		System.out.println("\nPlease insert the name, number, postal code, mailing address you would like to search\n" +
				"You may also insert the term only partially");
		String searchKey = System.console().readLine();
		searchResult = contactLocation(searchKey);
		if (searchResult == -1)
			System.out.println("\nSorry that doesent seem to be a valid contact\n");
		if (searchResult == 0)
			System.out.println("\nSorry couldnt find any contacts with that information\n");
	}
}

class PhoneBookInfo {
	String name;
	String phoneNumber;
	String postalCode;
	String mailingAddress;
}
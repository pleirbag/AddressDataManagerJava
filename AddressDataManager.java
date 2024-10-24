import java.io.FileWriter;
import java.io.IOException;

public class AddressDataManager {
	public static void main(String[] args) {
		int optionInt = -1;
		DataBaseEditor dbEdit = new DataBaseEditor();
		System.out.println("Welcome to our Phonebook manager\nPlease choose what would you like to access right now:\n" +
				"1. Search existing Contact\n" + "2. Add new Contact\n" + "3. Append existing contact" + "\n4. Exit the program");
		String optionConsole = System.console().readLine();
		try {
			optionInt = Integer.parseInt(optionConsole);
		}
		catch (NumberFormatException e){
			optionInt = -1;
		}
		switch (optionInt) {
			case 1:
				dbEdit.searchContact();
				break;
			case 2:
				dbEdit.addNewContact();
			case 3:
			case 4:
				System.out.println("Thanks and hope to be useful again in the future");
				return;
			case -1:
				System.out.println("Error: not a valid option");

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
		int optionInt = - 1;
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
		int optionInt = -1;
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

		optionInt = verifyInfo(newContactInfo);
		while (!isFinished ) {
			if (optionInt == 2)
				isFinished = appendNewAddress(newContactInfo);
			else if (optionInt == 1) {
				optionInt = editField(newContactInfo);
			} else
				System.out.println("Error not a valid option");
		}
	}
	public void searchContact (){}
}

class PhoneBookInfo {
	String name;
	String phoneNumber;
	String postalCode;
	String mailingAddress;
}
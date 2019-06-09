package Test;

import org.testng.annotations.Test;

import Helpers.Config;
import Helpers.TestBase;
import PageObject.ComputerDatabase;
import PageObject.EditComputer;
import PageObject.NewComputer;

public class TestEditComputer extends TestBase
{

	int row = 1;
	
	@Test(description = "TC_ID -> TC_6, TC_7.1, TC_7.6, TC_7.9, TC_7.12 and TC_10-> View added computer details, edit in the edit computer page and view edited computer in the home page", dataProvider = "GetTestConfig")
	public void testViewAddedComputerAndEdit(Config config) {
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");
		EditComputer editcomputer = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName);

		computerDatabase = (ComputerDatabase) editcomputer.editComputer(row);
		computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been updated");
		computerDatabase.enterSearchData(computerName).searchComputerName(computerName);

	}

	@Test(description = "TC_ID -> TC_7.2 And TC_14-> Verify edit computer is working when 'Computer name' field contains input data with leading and trailing spaces and confirmation message of successful update is shown", dataProvider = "GetTestConfig")
	public void testEditComputerWithSpacesInComputerName(Config config) {
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");
		EditComputer editcomputer = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName);

		row = 2;
		computerDatabase = (ComputerDatabase) editcomputer.editComputer(row);
		computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been updated");
	}

	@Test(description = "TC_ID -> TC_7.3 -> Display error message when special character is updated in 'Computer name' field in the edit computer page", dataProvider = "GetTestConfig")
	public void testErrorMessage_EditComputerWithSpecialCharatctersInComputerName(Config config) {

		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");
		EditComputer editcomputer = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName);

		row = 3;
		computerDatabase = (ComputerDatabase) editcomputer.editComputer(row);
		computerDatabase.compareUIMessage("Error Message");
	}

	@Test(description = "TC_ID -> TC_7.4 -> Display error message when existing data is entered in 'Computer name' while adding a new computer", dataProvider = "GetTestConfig")
	public void testErrorMessage_AddNewComputerWithExistingComputerName(Config config) {

		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");
		EditComputer editcomputer = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName);

		row = 4;
		computerDatabase = (ComputerDatabase) editcomputer.editComputer(row);
		computerDatabase.compareUIMessage("Error Message");

	}

	@Test(description = "TC_ID -> TC_7.5 -> Display error message when blank data is updated in 'Computer name' field in the edit computer page", dataProvider = "GetTestConfig")
	public void testErrorMessage_EditComputerWithBlankComputerName(Config config) {

		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase.clickAddNewComputer().
		addNewComputer(row);
		
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");
		EditComputer editcomputer = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName);

		row = 5;
		editcomputer.editComputer(row, true, false);
	}

	@Test(description = "TC_ID -> TC_7.7 And TC_7.10 -> Display error message when invalid data is updated in 'Introduced date' and 'Discontinued Date' field in the edit computer page", dataProvider = "GetTestConfig")
	public void testErrorMessage_EditComputerWithInvalidIntroducedDate(Config config) {

		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		NewComputer newComputer = computerDatabase.clickAddNewComputer();
		newComputer.addNewComputer(row);
		
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");
		EditComputer editcomputer = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName);

		//Introduced date with invalid date
		row = 6;
		editcomputer.editComputer(row, true, false);

		//Introduced date with leading and trailing spaces
		row = 7;
		editcomputer.editComputer(row, true, false);

		//Discontinued date with invalid date
		row = 9;
		editcomputer.editComputer(row, true, false);

		//Discontinued date with leading and trailing spaces
		row = 10;
		editcomputer.editComputer(row, true, false);

	}

	@Test(description = "TC_ID -> TC_7.8, TC_7.11 And TC_7.13 -> Verify user is able to add a computer when blank 'Introduced date', 'Discontinued Date' and 'Company' is entered", dataProvider = "GetTestConfig")
	public void testEditComputerWithBlankIntroducedAndDiscontinuedDateAndCompany(Config config) {

		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");
		EditComputer editcomputer = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName);

		row = 15;
		computerDatabase = (ComputerDatabase) editcomputer.editComputer(row);
		computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been updated");
	}

	@Test(description = "TC_ID -> TC_7.14 and TC_7.15 -> Display error message when blank data/only spaces is updated in all the fields while editing the existing computer", dataProvider = "GetTestConfig")
	public void testErrorMessage_EditComputerWithAllInvalidData(Config config) {

		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		NewComputer newComputer = computerDatabase.clickAddNewComputer();
		newComputer.addNewComputer(row);
		
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");

		EditComputer editcomputer = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName);
		//Blank date
		row = 13;
		editcomputer.editComputer(row, true, false);

		//Data with Spaces
		row = 14;
		editcomputer.editComputer(row, true, false);
	}
	
	@Test(description = "TC_ID -> TC_8 -> Cancel editing a new computer after/while entering valid data in the fields", dataProvider = "GetTestConfig")
	public void testCancelEditingComputer(Config config) {
		
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		NewComputer newComputer = computerDatabase.clickAddNewComputer();
		newComputer.addNewComputer(row);
		
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");

		EditComputer editcomputer = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName);
		editcomputer.editComputer(row, false, true);
	}
	
	@Test(description = "TC_ID -> TC_9 And TC_15 -> Delete an existing computer in the edit computer page and verify confirmation message of deletion", dataProvider = "GetTestConfig")
	public void testDeleteComputer(Config config) {
		
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		NewComputer newComputer = computerDatabase.clickAddNewComputer();
		newComputer.addNewComputer(row);
		
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");

		computerDatabase = computerDatabase.enterSearchData(computerName).searchComputerNameAndClick(config, computerName).deleteComputerName();
		computerDatabase.compareUIMessage("Done! Computer has been deleted");
		
	}

}

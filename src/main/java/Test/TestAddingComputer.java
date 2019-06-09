package Test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import Helpers.Config;
import Helpers.TestBase;
import PageObject.ComputerDatabase;
import PageObject.NewComputer;

public class TestAddingComputer extends TestBase
{
	WebDriver driver;
	
	@Test(description = "TC_ID -> TC_1 and TC_2.1 -> Display computer database home page and Add a new computer to the computer database", dataProvider = "GetTestConfig")
	public void testAddNewComputerWithValidInput(Config config) {
		
		int row = 1;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		computerDatabase.compareUIMessage("Done! Computer "+config.getRunTimeProperty("Computer_Name")+" has been created");
	}
	
	@Test(description = "TC_ID -> TC_2.2 -> Add a new computer to the computer database when 'Computer name' field contains input data with leading and trailing spaces", dataProvider = "GetTestConfig")
	public void testAddNewComputerWithSpaces(Config config) {
		int row = 2;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		computerDatabase.compareUIMessage("Done! Computer "+config.getRunTimeProperty("Computer_Name")+" has been created");
		
	}
	
	@Test(description = "TC_ID -> TC_2.4 -> Display error message when invalid data is entered in 'Computer name' while adding a new computer", dataProvider = "GetTestConfig")
	public void testErrorMessage_AddNewComputerWithInvalidDataInComputerName(Config config) {
		int row = 3;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		computerDatabase.compareUIMessage("Error Message");
		
	}
	
	@Test(description = "TC_ID -> TC_2.5 -> Display error message when existing data is entered in 'Computer name' while adding a new computer", dataProvider = "GetTestConfig")
	public void testErrorMessage_AddNewComputerWithExistingComputerName(Config config) {
		int row = 4;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		computerDatabase.compareUIMessage("Error Message");
		
	}
	
	@Test(description = "TC_ID -> TC_2.6 -> Display error message when blank data is entered in 'Computer name' while adding a new computer", dataProvider = "GetTestConfig")
	public void testErrorMessage_AddNewComputerWithBlankComputerName(Config config) {
		int row = 5;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase.clickAddNewComputer().
		addNewComputer(row, true, false);
	}
	
	@Test(description = "TC_ID -> TC_2.7 and TC_2.9 -> Display error message when invalid data/data with leading and trailing spaces is entered in 'Introduced date' while adding a new computer", dataProvider = "GetTestConfig")
	public void testErrorMessage_AddNewComputerWithInvalidIntroducedDate(Config config) {
		int row = 6;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		NewComputer newComputer = computerDatabase.clickAddNewComputer();
		newComputer.addNewComputer(row, true, false);

		//Introduced date with leading and trailing spaces
		row = 7;
		newComputer.addNewComputer(row, true, false);
	}
	
	@Test(description = "TC_ID -> TC_2.8 -> Verify user is able to add a computer when blank 'Introduced date' is entered", dataProvider = "GetTestConfig")
	public void testAddNewComputerWithBlankIntroducedDate(Config config) {
		int row = 8;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		computerDatabase.compareUIMessage("Done! Computer "+config.getRunTimeProperty("Computer_Name")+" has been created");
	}
	
	@Test(description = "TC_ID -> TC_2.10 and TC_2.12 -> Display error message when invalid data/data with leading and trailing spaces is entered in 'Discontinued date' while adding a new computer", dataProvider = "GetTestConfig")
	public void testErrorMessage_AddNewComputerWithInvalidDiscontinuedDate(Config config) {
		int row = 9;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		NewComputer newComputer = computerDatabase.clickAddNewComputer();
		newComputer.addNewComputer(row, true, false);

		//Introduced date with leading and trailing spaces
		row = 10;
		newComputer.addNewComputer(row, true, false);
	}
	
	@Test(description = "TC_ID -> TC_2.11 -> Verify user is able to add a computer when blank 'Discontinued date' is entered", dataProvider = "GetTestConfig")
	public void testAddNewComputerWithBlankDiscontinuedDate(Config config) {
		int row = 11;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		computerDatabase.compareUIMessage("Done! Computer "+config.getRunTimeProperty("Computer_Name")+" has been created");
	}
	
	@Test(description = "TC_ID -> TC_2.13 -> Verify is user is able to add new computer without entering company", dataProvider = "GetTestConfig")
	public void testAddNewComputerWithBlankCompany(Config config) {
		int row = 12;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		computerDatabase.compareUIMessage("Done! Computer "+config.getRunTimeProperty("Computer_Name")+" has been created");
	}
	
	@Test(description = "TC_ID -> TC_2.14 and TC_2.15 -> Display error message when blank data/only spaces is entered while adding a new computer", dataProvider = "GetTestConfig")
	public void testErrorMessage_AddNewComputerWithAllInvalidData(Config config) {
		int row = 13;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		NewComputer newComputer = computerDatabase.clickAddNewComputer();
		newComputer.addNewComputer(row, true, false);

		//Data with Spaces
		row = 14;
		newComputer.addNewComputer(row, true, false);
	}
	
	@Test(description = "TC_ID -> TC_3.1 and TC_3.2 -> Cancel adding a new computer to the computer database after/while entering valid data in the fields", dataProvider = "GetTestConfig")
	public void testCancelAddingNewComputer(Config config) {
		int row = 1;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		NewComputer newComputer = computerDatabase.clickAddNewComputer();
		newComputer.addNewComputer(row, false, true);
	}
	
	@Test(description = "TC_ID -> TC_4 -> User should be redirected to home page after clicking on 'Play sample application - Computer Database' link", dataProvider = "GetTestConfig")
	public void testUserRedirectionOnHomePage(Config config) {
		testBase = new TestBase();
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		NewComputer newComputer = computerDatabase.clickAddNewComputer();
		computerDatabase = newComputer.clickApplicationLink();
	}
}

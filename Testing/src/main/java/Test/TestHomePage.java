package Test;

import org.testng.annotations.Test;

import Helpers.Config;
import Helpers.TestBase;
import PageObject.ComputerDatabase;

public class TestHomePage extends TestBase
{

	@Test(description = "TC_ID -> TC_5.1 and TC_5.2 -> Verify user is able to filter added computer on home page", dataProvider = "GetTestConfig")
	public void testSearchNewComputerOnHomePage(Config config) {

		int row = 1;
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);

		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");
		computerDatabase.enterSearchData(computerName).searchComputerName(computerName);
	}

	@Test(description = "TC_ID -> TC_5.3 and TC_5.4 -> Verify no data found message when invalid/non-existing data is entered in filter field on home page", dataProvider = "GetTestConfig")
	public void testNoDataFoundInSearchOnHomePage(Config config) {

		String computerName = generateRandomAlphaNumericString(10);
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase.enterSearchData(computerName).searchInvalidComputerName();
	}

	@Test(description = "TC_ID -> TC_5.7 -> Verify sorted column is displayed with a sorting icon on home page", dataProvider = "GetTestConfig")
	public void testSortedDataInResultGridOnHomePage(Config config) {
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase.verifySortedComputerData();
	}

	@Test(description = "TC_ID -> TC_5.8 -> Verify duplicate records are not displayed in the result grid", dataProvider = "GetTestConfig")
	public void testNoDuplicateDataInResultGridOnHomePage(Config config) {
		int row = 4;
		//Add computer
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		String computerName = config.getRunTimeProperty("Computer_Name");
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");

		//Add same computer again
		computerDatabase = (ComputerDatabase) computerDatabase.clickAddNewComputer().
				addNewComputer(row);
		computerDatabase.compareUIMessage("Done! Computer "+computerName+" has been created");

		computerDatabase.searchComputerNameAndCheckDuplicateData(computerName, true);
	}
	
	@Test(description = "TC_ID -> TC_11, TC_12 and TC_13-> Verify pagination is working on 'Home' page and user is able to navigate to Next/Previous page", dataProvider = "GetTestConfig")
	public void testNextAndPreviousDataOnHomePage(Config config) {
		
		ComputerDatabase computerDatabase = new ComputerDatabase(config);
		computerDatabase.navigateToResultGrid("Next");
		computerDatabase.verifySortedComputerData();
		
		computerDatabase.navigateToResultGrid("Previous");
		computerDatabase.verifySortedComputerData();
	}


}

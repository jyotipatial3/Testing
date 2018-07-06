package PageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import Helpers.Config;
import Helpers.FileReader;

public class EditComputer {
	
private Config testConfig;
	
	public EditComputer(Config testConfig) {
		this.testConfig = testConfig;
		PageFactory.initElements(testConfig.driver, this);
		this.testConfig.waitForPageLoad(testConfig, deleteComputer);
	}

	@FindBy(id = "name")
	private WebElement computerName;

	@FindBy(id = "introduced")
	private WebElement introducedDate;

	@FindBy(id = "discontinued")
	private WebElement discontinuedDate;

	@FindBy(id = "company")
	private WebElement companyDropDown;

	@FindBy(xpath = "//input[@value='Save this computer']")
	private WebElement createComputer;

	@FindBy(linkText = "Cancel")
	private WebElement cancel;
	
	@FindBy(xpath = "//input[@value='Delete this computer']")
	private WebElement deleteComputer;
	
	/**
	 * Edit computer by entering value to all fields.
	 */
	public Object editComputer(int row) {
		Object returnPage = null;

		returnPage = editComputer(row, false, false);
		return returnPage;
	}

	public Object editComputer(int row, boolean negativeCase, boolean isCancel) {
		Object returnPage = null;

		FileReader computerDatabaseData = new FileReader(testConfig, "ComputerDatabase");
		String value;
		
		value = computerDatabaseData.GetData(row, "Computer Name", false);
		testConfig.putRunTimeProperty("Computer_Name", value.trim());
		computerName.clear();
		System.out.println("Enter "+value+" in Computer Name field");
		computerName.sendKeys(value);

		value = computerDatabaseData.GetData(row, "Introduced Date");
		introducedDate.clear();
		System.out.println("Enter "+value+" in Introduced Date field");
		introducedDate.sendKeys(value);

		value = computerDatabaseData.GetData(row, "Discontinued Date");
		discontinuedDate.clear();
		System.out.println("Enter "+value+" in Discontinued Date field");
		discontinuedDate.sendKeys(value);

		value = computerDatabaseData.GetData(row, "Company");
		selectCompany(value);

		if(isCancel){
			System.out.println("Click Cancel Button");
			cancel.click();
			returnPage = new ComputerDatabase();
		}
		else {
			System.out.println("Click Save this Computer button");
			createComputer.click();
			if(negativeCase)
				returnPage = this;
			else
				returnPage = new ComputerDatabase();
		}
		return returnPage;
	}

	public EditComputer selectCompany(String companyName) {
		Select sel = new Select(companyDropDown);

		if(companyName!=""){
			try
			{
				sel.selectByVisibleText(companyName);
				sel = new Select(companyDropDown);
				companyDropDown.click();
				System.out.println("Select "+companyName+" in Company DropDown field");
				sel.selectByVisibleText(companyName);
			}
			catch(Exception e){}
		}

		return this;
	}
	
	public ComputerDatabase deleteComputerName(){
		System.out.println("Delete Computer field");
		deleteComputer.click();
		
		return new ComputerDatabase();
	}

}

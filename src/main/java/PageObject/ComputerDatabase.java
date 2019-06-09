package PageObject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Helpers.Config;

public class ComputerDatabase {

	static WebDriver driver;
	static Config testConfig;
	
	@FindBy(linkText = "Play sample application — Computer database")
	private WebElement applicationLink;

	@FindBy(id = "searchbox")
	private WebElement searchBox;

	@FindBy(id = "searchsubmit")
	private WebElement searchSubmit;

	@FindBy(id = "add")
	private WebElement addComputerButton;

	@FindBy(css = "table.computers.zebra-striped>tbody")
	private WebElement wholeTable;

	@FindBy(css = "div.well>em")
	private WebElement noDataFoundMessage;

	@FindBy(css = "div.pagination>ul>li.next>a")
	private WebElement next;

	@FindBy(css = "div.pagination>ul>li.prev")
	private WebElement previous;

	@FindBy(css = "div.alert-message.warning")
	private WebElement uiMessage;
	
	public ComputerDatabase(Config testConfig) {
		this.testConfig = testConfig;
		driver = testConfig.openBrowser();
		PageFactory.initElements(driver, this);
		testConfig.waitForPageLoad(testConfig, searchBox);
	}

	public ComputerDatabase()
	{
		PageFactory.initElements(driver, this);
	}

	/**
	 * Click Add new computer
	 */
	public NewComputer clickAddNewComputer() {
		System.out.println("Click on Add a Computer Button");
		addComputerButton.click();
		return new NewComputer(testConfig);
	}

	public void compareUIMessage(String message){
		System.out.println("Compare Error/Confirmation Messages");
		Assert.assertEquals(uiMessage.getText(), message); //driver.findElement(By.cssSelector("div.alert-message.warning"))
	}

	/**
	 * @param computerName
	 * @return Computer Database Page with entered param
	 */
	public ComputerDatabase enterSearchData(String computerName){
		searchBox.sendKeys(computerName);
		return this;
	}

	/**
	 * @param computerName
	 * @return Computer Database Page with searched computer result grid
	 */
	public ComputerDatabase searchComputerName(String computerName){
		searchComputerNameAndCheckDuplicateData(computerName, false);
		return this;
	}
	
	/**
	 * @param computerName
	 * @param multipleData true if expecting multiple data else false
	 * @return Computer Database Page with searched computer result grid
	 */
	public ComputerDatabase searchComputerNameAndCheckDuplicateData(String computerName, boolean multipleData){
		searchSubmit.click();
		List<WebElement> columns;
		System.out.println("Compare "+computerName+" On home page");
		List<WebElement> rows = wholeTable.findElements(By.tagName("tr"));
		if(multipleData)
			System.out.println("Number of duplicate data:"+rows.size());
		else{
			columns = rows.get(0).findElements(By.tagName("td"));
			System.out.println("No duplicate column found");
			Assert.assertEquals(columns.get(0).getText(), computerName);
		}
		return this;
	}

	/**
	 * Search Invalid/Non Exsiting computer name
	 */
	public ComputerDatabase searchInvalidComputerName(){
		searchSubmit.click();
		System.out.println("Compare Nothing to display message on home page");
		Assert.assertEquals(noDataFoundMessage.getText(), "Nothing to display");
		return this;
	}

	
	/**
	 * @param direction Next to go to the next page, and Previous to go to the previous page
	 * @return
	 */
	public ComputerDatabase navigateToResultGrid(String direction){
		if(direction=="Previous")
			previous.click(); 
		if(direction=="Next")
			next.click();
		
		System.out.println("Navigate to "+direction+" on home page");
		return this;
	}
	
	public ComputerDatabase verifySortedComputerData(){
		List<WebElement> rows = wholeTable.findElements(By.tagName("tr"));
		ArrayList<String> rowData = new ArrayList<String>();
		for(int rownum=0; rownum<rows.size(); rownum++)
		{
			List<WebElement> columns = rows.get(rownum).findElements(By.tagName("td"));
			rowData.add(columns.get(0).getText());
		} 
		
		if(isSorted(rowData)){
			System.out.println("Row Data is sorted");
			Assert.assertTrue(true);
		}
		return this;
	}
	
	public static boolean isSorted(ArrayList<String> arrayList){
	    String previousComputerName = "";
	    for (String current: arrayList) {
	        if (current.compareTo(previousComputerName) < 0)
	            return false;
	        previousComputerName = current;
	    }
	    return true;
	}
	
	public EditComputer searchComputerNameAndClick(Config testConfig, String computerName){
		searchComputerNameAndCheckDuplicateData(computerName, false);
		driver.findElement(By.linkText(computerName)).click();
		
		return new EditComputer(testConfig);
	}
}

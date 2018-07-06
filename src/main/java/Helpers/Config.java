package Helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Config {

	Properties runtimeProperties;

	public Method testMethod;
	String testName;
	boolean endExecutionOnfailure;

	public WebDriver driver;

	public boolean testResult;
	public static String fileSeparator = File.separator;

	// package fields
	String testStartTime;

	public Config(Method method)
	{
		try
		{
			endExecutionOnfailure = true;
			this.testMethod = method;

			// Read the Config file
			Properties property = new Properties();

			String path = System.getProperty("user.dir") + fileSeparator + "Parameters" + fileSeparator + "TestData.properties";

			FileInputStream fn = new FileInputStream(path);
			property.load(fn);
			fn.close();

			this.runtimeProperties = new Properties();
			Enumeration<Object> em = property.keys();
			while (em.hasMoreElements())
			{
				String str = (String) em.nextElement();
				putRunTimeProperty(str, (String) property.get(str));
			}

			// Set the full path of test data sheet
			String testDataSheet = System.getProperty("user.dir") + getRunTimeProperty("TestDataSheet");

			putRunTimeProperty("TestDataSheet", testDataSheet);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}

	/**
	 * Add the given key value pair in the Run Time Properties
	 * 
	 * @param key
	 * @param value
	 */
	public void putRunTimeProperty(String key, String value)
	{
		String keyName = key.toLowerCase();
		runtimeProperties.put(keyName, value);
	}

	/**
	 * Get the Run Time Property value
	 * 
	 * @param key
	 *            key name whose value is needed
	 * @return value of the specified key
	 */
	public String getRunTimeProperty(String key)
	{
		String keyName = key.toLowerCase();
		String value = "";
		try
		{
			value = runtimeProperties.get(keyName).toString();
			value = replaceArgumentsWithRunTimeProperties(value);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
		return value;
	}

	public String getTestName()
	{
		return testName;
	}

	/**
	 * Replaces the arguments like {$someArg} present in input string with its
	 * value from RuntimeProperties
	 * 
	 * @param input
	 *            string in which some Argument is present
	 * @return replaced string
	 */
	public String replaceArgumentsWithRunTimeProperties(String input)
	{
		if (input.contains("{$"))
		{
			int index = input.indexOf("{$");
			input.length();
			input.indexOf("}", index + 2);
			String key = input.substring(index + 2, input.indexOf("}", index + 2));
			String value = getRunTimeProperty(key);
			input = input.replace("{$" + key + "}", value);
			return replaceArgumentsWithRunTimeProperties(input);
		}
		else
		{
			return input;
		}

	}

	/**
	 * Open browser
	 * @return webdriver
	 */
	public WebDriver openBrowser()
	{
		while (this.driver == null)
		{
			try
			{
				this.driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.get(getRunTimeProperty("ComputerDatabasePage"));
				System.out.println("Browser launched successfully for testcase:- "+getTestName());
			}
			catch (Exception e)
			{
				System.out.println("Unable to launch browser:-" + e.getLocalizedMessage());
			}
		}
		endExecutionOnfailure = false;
		return driver;
	}

	/**
	 * @param testConfig
	 * @param sheetName
	 * @return File
	 */
	public FileReader getFile(Config testConfig, String sheetName)
	{
		FileReader fileReader = new FileReader(testConfig, "TestDataSheet");
		return fileReader;
	}

	public void waitForPageLoad(Config testConfig, WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(testConfig.driver, 60);

		testConfig.driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try
		{
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch (StaleElementReferenceException e)
		{
			System.out.println("StaleElementReferenceException occured, wait upto additional " + 60 + " seconds.");
		}
	}

}

package Helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;


public class FileReader {

	String path;
	private Config testConfig;
	String filename;

	private FileInputStream fis = null;
	private String sheetName;

	private ArrayList<List<String>> testData;

	public FileReader(Config testConfig)
	{
		this.testConfig = testConfig;
	}

	/**
	 * @param testConfig
	 * @param sheetName
	 */
	public FileReader(Config testConfig, String sheetName)
	{
		String path = testConfig.getRunTimeProperty("TestDataSheet");
		readFile(testConfig, sheetName, path);
	}

	/**
	 * Returns the Excel sheet data value (returns {skip} if the excel value is
	 * blank, which means no operation)
	 * 
	 * @param row
	 *            Excel Row number to read
	 * @param column
	 *            Excel column name to read
	 * @return The value read
	 */
	public String GetData(int row, String column){
		return GetData(row, column, true);
	}

	public String GetData(int row, String column, boolean isTrim)
	{
		String data = "";
		List<String> headerRow = testData.get(0);
		List<String> dataRow = testData.get(row);

		for (int i = 0; i < headerRow.size(); i++)
		{
			if (headerRow.get(i).equalsIgnoreCase(column))
			{
				try
				{
					data = dataRow.get(i);
				}
				catch (IndexOutOfBoundsException e)
				{
					data = "";
				}
				break;
			}
		}

		if(isTrim)
			data = data.trim();

		if (data.contains("{empty}"))
			data = data.replace("{empty}", "");
		if (data.contains("{space}"))
			data = data.replace("{space}", " ");

		while (data.contains("{random"))
		{
			int start = data.indexOf("Num:") + 4;
			int end = data.indexOf("}");
			int length = Integer.parseInt(data.substring(start, end));

			if (data.contains("{randomAlphaNum:" + length + "}"))
				data = data.replace("{randomAlphaNum:" + length + "}", TestBase.generateRandomAlphaNumericString(length));
			if (data.contains("{randomAlphabetsNum:" + length + "}"))
				data = data.replace("{randomAlphabetsNum:" + length + "}", TestBase.generateRandomAlphabetsString(length));
			if (data.contains("{randomSpecialCharNum:" + length + "}"))
				data = data.replace("{randomSpecialCharNum:" + length + "}", TestBase.generateRandomSpecialCharacterString(length));
		}

		return data;
	}

	private String convertHSSFCellToString(HSSFCell cell, FormulaEvaluator evaluator)
	{
		String value = null;
		try
		{
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			{
				value = Double.toString(cell.getNumericCellValue());
			}
			else
				if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				{
					value = cell.getRichStringCellValue().toString();
				}
		}
		catch (NullPointerException ex)
		{
			value = "";
		}
		return value;
	}

	private void readFile(Config testConfig, String sheetName, String path)
	{
		this.testConfig = testConfig;
		int index = path.lastIndexOf("//");
		if (index != -1)
			System.out.println("Read:-'" + path.substring(path.lastIndexOf("//")) + "', Sheet:- '" + sheetName + "'");
		else
			System.out.println("Read:-'" + path + "', Sheet:- '" + sheetName + "'");

		filename = path;
		testData = new ArrayList<List<String>>();

		try
		{
			try
			{
				HSSFWorkbook workbook = null;
				HSSFSheet sheet = null;

				fis = new FileInputStream(filename);

				try
				{
					workbook = new HSSFWorkbook(fis);
				}
				catch(OutOfMemoryError e)
				{
					System.out.println(e);
					e.printStackTrace();
				}

				sheetName = sheetName.trim();
				sheet = workbook.getSheet(sheetName);
				FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

				Iterator<Row> rows = sheet.rowIterator();
				while (rows.hasNext())
				{
					HSSFRow row = (HSSFRow) rows.next();
					List<String> data = new ArrayList<String>();
					for (int z = 0; z < row.getLastCellNum(); z++)
					{
						String str = convertHSSFCellToString(row.getCell(z), evaluator);
						data.add(str);
					}
					testData.add(data);
				}
			}
			catch (IOException e) 
			{
					System.out.println(e);
			}
			this.sheetName = sheetName;
		}
		finally
		{
			if (fis != null)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					System.out.println(e);
				}
			}
		}
	}

}

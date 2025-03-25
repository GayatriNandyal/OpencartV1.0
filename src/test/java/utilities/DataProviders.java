package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders 
{
	// Data Provider 1:
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path = System.getProperty("user.dir")+"\\testData\\OpenCart_Login_TestData_Selenium.xlsx"; //Taking test data excel file from Test Data folder.
		
		ExcelUtilityFile xlutil = new ExcelUtilityFile(path); //Creating an object for ExcelUtilityFile
		
		// getting the total number of rows and columns same as that of excel inorder to create a 2D array
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);
		
		String logindata[][] = new String[totalrows][totalcols]; //Created a 2D array which can store the data from excel data file
		
		for(int i=1; i<=totalrows; i++) //i is for Rows; i is initialized to 1 as the 0 row is header row in excel
		{
			for(int j=0; j<totalcols; j++) //j is for columns; j is initialized to 0 as the 0 column is has data in excel
			{
				logindata[i-1][j] = xlutil.getCellData("Sheet1", i, j); //Read the data from excel and store in 2D array
			                                                            //i-1 as the array index starts from 0
			}
		} 
		return logindata;
	}
	
	// Data Provider 2:
	
	// Data Provider 3:


}

package com.spiv.fwa.suite.infmt;

import com.spiv.fwa.util.Utils;
import jdk.jfr.Timespan;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.spiv.fwa.suite.cmdr.CommanderSelectors.PROJECT_DIRECTORY_SELECT_FILTER_DROPDOWN;
import static com.spiv.fwa.suite.cmdr.CommanderSelectors.PROJECT_STATUS_CONFIRM_MODAL_FORM;
import static com.spiv.fwa.suite.infmt.InfmtSelectors.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public class InfmtScript {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public InfmtScript(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public InfmtScript enter2QueryEngine() {
    	Utils.delay(5000);
        this.getAnalysisButton().click();
        Utils.delay(3000);
        this.getQueryEngineButton().click();
        Utils.changeBrowserTab(driver, "/analysis/query-engine");
        Utils.delay(3000);
        return this;
    }

    public InfmtScript addNewQuery() {

        this.getNewQueryButton().click();
        Utils.delay(2000);
        Assert.assertNotNull("Data Source Not found", this.getDataSourceTab());
        this.getDataSourceDropdown().click();
        Utils.delay(2000);
        
        Select objSelectDS = new Select(driver.findElement(INFMNT_DATA_SOURCE_TAB_DATA_SOURCE_DROPDOWN));
        String cpProfessionalLines = "CP - Professional Lines";
        objSelectDS.selectByVisibleText(cpProfessionalLines);
        Utils.delay(1000);
        this.getAdditionalDataSourceDropdown().click();
        
        Select objSelectADS = new Select(driver.findElement(INFMNT_DATA_SOURCE_TAB_ADD_DATA_SOURCE_DROPDOWN));
        String adsStringElementValue = "D6";
        objSelectADS.selectByValue(adsStringElementValue);
        Utils.delay(2000);
        this.getDataSourceNextButton().click();
        Utils.delay(3000);
        
        Assert.assertNotNull("Search Criteria Tab Not found", this.getSearchCriteriaTab());
        this.getSearchCriteriaPeriodDropdown().click();
        Select objPeriod = new Select(driver.findElement(INFMNT_SEARCH_CRITERIA_PAYMENT_DATE_DROPDOWN));
        String queryCurrentMonth = "1 - Query Current Month";
        objPeriod.selectByVisibleText(queryCurrentMonth);
        Utils.delay(2000);
        this.getSearchCriteriaFieldDropdown().click();
        
        Utils.delay(2000);
        Select objField = new Select(driver.findElement(INFMNT_SEARCH_CRITERIA_FIELD_DROPDOWN));
        String allowsAmtString = "CP.Allowed Amt";
        objField.selectByVisibleText(allowsAmtString);
        Utils.delay(2000);
        this.getSearchCriteriaRelOperatorDropdown().click();
        
        Utils.delay(2000);
        Select objRelOperator = new Select(driver.findElement(INFMNT_SEARCH_CRITERIA_REL_OPERATOR_DROPDOWN));
        String relOperatorElementValue = "GREATER THAN";
        objRelOperator.selectByValue(relOperatorElementValue);
        Utils.delay(2000);
        
        this.getSearchCriteriaValueField().sendKeys("2000");
        Utils.delay(3000);

        this.getSecondFiledPlusButton().click();
        Utils.delay(2000);
        this.getSecondSearchCriteriaFieldDropdown().click();

        Utils.delay(2000);
        Select objSecondField = new Select(driver.findElement(INFMNT_SEARCH_SECOND_CRITERIA_FIELD_DROPDOWN));
        String patientNameString = "CP.Patient Name";
        objSecondField.selectByVisibleText(patientNameString);
        Utils.delay(2000);
        this.getSecondSearchRelOperatorDropdown().click();

        Utils.delay(2000);
        Select objReltOperator = new Select(driver.findElement(INFMNT_SEARCH_SECOND_REL_OPERATOR_DROPDOWN));
        String relOperateElementValue = "NOT EQUAL";
        objReltOperator.selectByValue(relOperateElementValue);
        Utils.delay(2000);

        this.getSecondCriteriaValueField().sendKeys("BLANKS");
        Utils.delay(2000);
        
        String recordCountValue = "Loading...";
        this.getSearchCriteriaRecordCount().click();
        
        long recordCountTimeoutMins = 5;
		//Wait until regex match any number on the value of this element. 
        //Timeout for recordCount set in var: recordCountTimeoutMins 
        WebDriverWait waitforRecordCount = new WebDriverWait(driver,Duration.ofMinutes(recordCountTimeoutMins));
        waitforRecordCount.until(ExpectedConditions.textMatches(INFMNT_SEARCH_CRITERIA_RECORD_COUNT_VALIDATION, Pattern.compile("\\d+"))); //\\d+ Any Number
        recordCountValue = this.getRecordCountValue().getText().replace(",", "");
        int recordCount = 0;
        if(!recordCountValue.isEmpty()) {
        	recordCount = Integer.parseInt(recordCountValue);
        }
        Assert.assertTrue("Record Count Expected to be greater than 0", recordCount > 0);
        Utils.delay(5000);
        
        this.getSearchCriteriaNextButton().click();
        Utils.delay(2000);
        Assert.assertNotNull("Customize Report Tab Not found", this.getCustomizeReportTab());
        this.getCustomizeReportFieldSelection().click();
        Utils.delay(2000);
        this.getCustomizeReportRightRow().click();
        Utils.delay(2000);
        this.getCustomizeReportViewReportButton().click();
        Utils.delay(8000);
        this.getViewReportConfirmationButton().click();
        Assert.assertNotNull("View Report Tab Not found", this.getViewReportPage());
        Utils.delay(5000);

        return this;

    }

    public InfmtScript ViewReportListsSection() {
        this.getPatientListReport().click();
        Utils.delay(5000);
        Assert.assertNotNull("Patient List Report Not found", this.getPatientsListPage());
        Utils.delay(5000);
        this.getPatientandProviderListBackToReport().click();
        Utils.delay(2000);
        this.getProviderListReport().click();
        Utils.delay(5000);
        Assert.assertNotNull("Patient or Provider List Report Not found", this.getPatientsListPage());
        Utils.delay(5000);
        this.getPatientandProviderListBackToReport().click();
        Utils.delay(2000);
        return this;
    }

    public InfmtScript ViewRowDetailsSection() {
        this.getSelectCheckBox().click();
        Utils.delay(2000);
        this.getPatientDetails().click();
        Utils.delay(2000);
        Utils.changeBrowserTab(driver, "/lookup");
        Utils.delay(3000);
        Assert.assertNotNull("Patient Lookup Report Not found",this.getPatientLookupValidation());
        Utils.delay(8000);
        Assert.assertFalse("No Records Found on Patient Lookup Report", this.validateInfmntDialogByMessage("No Records Found"));
        this.getPatientLookupSearch().click();
        Utils.delay(2000);
        this.getPatientLookupSearchSexField().sendKeys("F");
        Utils.delay(2000);
        this.getPatientLookupSearchListButton().click();
        Utils.delay(8000);
        this.getPatientDetailsSeachListCloseButton().click();
        Utils.delay(8000);
        return this;
    }

    public InfmtScript openNewBlankTab() {
        driver.switchTo().newWindow(WindowType.TAB);
        return this;
    }
    
	public InfmtScript openURL(String url) {
		driver.switchTo().newWindow(WindowType.TAB);
		Utils.delay(2000);
		driver.get(url);
		return this;
	}
    
    public InfmtScript waitForTimeout(long ms) {
        Utils.delay(ms);
        return this;
    }
    
    public InfmtScript checkForTimeoutDialog(String tab) {
        Utils.delay(2000);
        Utils.changeBrowserTab(driver, tab);
        Utils.delay(2000);
    	Assert.assertNotNull("Inactivity Logout Notice Confirmation Not found, "
    			+ "Check your timeout setting [infmt.session.timeout.delay.minutes] on properties. "
    			+ "Needs to be the same as Functional Admin timeout setting.", this.getInactivityLogoutNoticeDialog());
        return this;
    }
    
    public InfmtScript checkForSessionTimeoutScreen(String tab) {
        Utils.delay(2000);
        if(!Utils.browserTabExists(driver, tab)) {
        	Assert.assertNotNull("Session Timeout Screen Not found");
        }
        return this;
    }
    
	public InfmtScript sessionTimeoutScreenTest(WebDriver driver, String tab_url, String sessionTimeoutURL, 
			Long infmtSessionTimeoutMinutes) {
		
        try {
		//Wait for non-existan element to force the app wait the max duration in minutes
		//FluentWait causes the app to lose the session using RemoteDriver.
        	WebDriverWait dummyWait = new WebDriverWait(driver,Duration.ofMinutes(infmtSessionTimeoutMinutes));
        	dummyWait.until(presenceOfElementLocated(By.xpath("//div[dummy(@id, 'non-existant-element')]")));
        } catch (Exception e) {
            //Do nothing.
        }
		
		//Go around a little depending on the scope to validate by second chance, 
		//if we are in a different context go back to INFMNT queryAnalysisURL tab.
        Utils.delay(3000);
        String queryAnalysisURL = "/analysis/query-engine";
        if(tab_url != null) {
        	if(Utils.browserTabExists(driver, queryAnalysisURL)) {
        		Utils.changeBrowserTab(driver, queryAnalysisURL);
        	}
        }
        Utils.delay(2000);
        
        //Check if Inactivity Logout Notice dialog it's there for any reason.
        Assert.assertFalse("Inactivity Logout Notice still present after timeout of ["+infmtSessionTimeoutMinutes+"] minutes.", 
        		this.validateInfmntDialogByMessage("Inactivity Logout Notice"));
        
        //Click on the New Query Button
        WebElement newQueryButton = this.getNewQueryButton();
		Utils.delay(2000);
		if (newQueryButton != null && newQueryButton.isDisplayed()) {
			newQueryButton.click();
			Utils.delay(2000);
			Assert.fail("Session Timeout Screen Not found. Informant it's still navigable.");
		}
		Utils.delay(3000);
		
    	if(Utils.browserTabExists(driver, sessionTimeoutURL)) {
    		//If this step is reached, it's a PASS, Session timeout screen is present.
    		Utils.changeBrowserTab(driver, sessionTimeoutURL);
    	}
    	Utils.delay(5000);
		return this;
	}
    
    private WebElement getAnalysisButton() {
        return driver.findElement(INFMNT_MENU_ANALYSIS);

    }

    private WebElement getQueryEngineButton() {
        return driver.findElement(INFMNT_MENU_ANALYSIS_QUERYENGINE);

    }

    private WebElement getNewQueryButton() {
        try {
        	return driver.findElement(INFMNT_NEW_QUERY);
        } catch (Exception e) {
            return null;
        }
    }

    private WebElement getDataSourceDropdown() {
        return driver.findElement(INFMNT_DATA_SOURCE_TAB_DATA_SOURCE_DROPDOWN);
    }

    private WebElement getAdditionalDataSourceDropdown() {
        return driver.findElement(INFMNT_DATA_SOURCE_TAB_ADD_DATA_SOURCE_DROPDOWN);
    }

    private WebElement getDataSourceNextButton() {
        return driver.findElement(INFMNT_DATA_SOURCE_NEXT_BUTTON);
    }

    private WebElement getSearchCriteriaPeriodDropdown() {
        return driver.findElement(INFMNT_SEARCH_CRITERIA_PAYMENT_DATE_DROPDOWN);
    }

    private WebElement getSearchCriteriaFieldDropdown() {
        return driver.findElement(INFMNT_SEARCH_CRITERIA_FIELD_DROPDOWN);
    }

    private WebElement getSearchCriteriaRelOperatorDropdown() {
        return driver.findElement(INFMNT_SEARCH_CRITERIA_REL_OPERATOR_DROPDOWN);
    }

    private WebElement getSearchCriteriaValueField() {
        return driver.findElement(INFMNT_SEARCH_CRITERIA_VALUE_FIELD);
    }

    private WebElement getSecondFiledPlusButton(){
        return driver.findElement(INFMNT_SEARCH_SECOND_FIELD_PLUS_BUTTON);
    }

    private WebElement getSecondSearchCriteriaFieldDropdown(){
        return driver.findElement(INFMNT_SEARCH_SECOND_CRITERIA_FIELD_DROPDOWN);
    }

    private WebElement getSecondSearchRelOperatorDropdown(){
        return driver.findElement(INFMNT_SEARCH_SECOND_REL_OPERATOR_DROPDOWN);
    }

    private WebElement getSecondCriteriaValueField(){
        return driver.findElement(INFMNT_SEARCH_SECOND_CRITERIA_VALUE_FIELD);
    }

    private WebElement getSearchCriteriaRecordCount() {
        return driver.findElement(INFMNT_SEARCH_CRITERIA_RECORD_COUNT);
    }

    private WebElement getSearchCriteriaNextButton() {
        return driver.findElement(INFMNT_SEARCH_CRITERIA_NEXT_BUTTON);
    }

    private WebElement getCustomizeReportFieldSelection() {
        return driver.findElement(INFMNT_CUSTOMIZE_REPORT_FIELD_SELECTION);
    }

    private WebElement getCustomizeReportRightRow() {
        return driver.findElement(INFMNT_CUSTOMIZE_REPORT_RIGHT_ARROW);
    }

    private WebElement getCustomizeReportViewReportButton() {
        return driver.findElement(INFMNT_CUSTOMIZE_REPORT_VIEW_REPORT_BUTTON);
    }

    private WebElement getViewReportConfirmationButton() {
        return driver.findElement(INFMNT_VIEW_REPORT_CONFIRMATION_BUTTON);
    }

    private WebElement getPatientListReport() {
        return driver.findElement(INFMNT_VIEW_REPORT_PATIENT_LIST);
    }

    private WebElement getPatientandProviderListBackToReport() {
        return driver.findElement(INFMNT_VIEW_REPORT_PATIENTANDPROVIDER_BACKTOREPORT);
    }

    private WebElement getProviderListReport() {
        return driver.findElement(INFMNT_VIEW_REPORT_PROVIDER_LIST);
    }
    private WebElement getSelectCheckBox() {
        try {
            return wait.until(presenceOfElementLocated(INFMNT_VIEW_REPORT_SELECT_CHECKBOX));
        } catch (Exception e) {
            return null;
        }
    }
    private WebElement getPatientDetails() {
        return driver.findElement(INFMNT_VIEW_REPORT_PATIENT_DETAILS);
    }

    private WebElement getPatientLookupSearch() {
        return driver.findElement(INFMNT_VIEW_REPORT_PATIENT_DETAILS_SEARCH);
    }

    private WebElement getPatientLookupSearchSexField() {
        return driver.findElement(INFMNT_VIEW_REPORT_PATIENT_DETAILS_SEARCH_SEX_FIELD);
    }

    private WebElement getPatientLookupSearchListButton() {
        return driver.findElement(INFMNT_VIEW_REPORT_PATIENT_DETAILS_SEARCH_LIST_BUTTON);
    }

    private WebElement getPatientDetailsSeachListCloseButton() {
        return driver.findElement(INFMNT_VIEW_REPORT_PATIENT_DETAILS_SEARCH_LIST_CLOSE_BUTTON);
    }





    private WebElement getDataSourceTab() {
        try {
            return wait.until(presenceOfElementLocated(INFMNT_DATA_SOURCE_TAB_VALIDATION));
        } catch (Exception e) {
            return null;
        }

    }

    private WebElement getSearchCriteriaTab() {
        try {
            return wait.until(presenceOfElementLocated(INFMNT_SEARCH_CRITERIA_TAB_VALIDATION));
        } catch (Exception e) {
            return null;
        }

    }

    private WebElement getRecordCountValue() {
        try {
            return wait.until(presenceOfElementLocated(INFMNT_SEARCH_CRITERIA_RECORD_COUNT_VALIDATION));
        } catch (Exception e) {
            return null;
        }
    }

    private WebElement getCustomizeReportTab() {
        try {
            return wait.until(presenceOfElementLocated(INFMNT_CUSTOMIZE_REPORT_TAB_VALIDATION));
        } catch (Exception e) {
            return null;
        }

    }

    private WebElement getViewReportPage() {
        try {
            return wait.until(presenceOfElementLocated(INFMNT_VIEW_REPORT_PAGE));
        } catch (Exception e) {
            return null;
        }
    }

  /* private WebElement getReportTab() {
        try {
            return wait.until(presenceOfElementLocated(INFMNT_VIEW_REPORT_PAGE));
        } catch (Exception e) {
            return null;
        }

} */

    private WebElement getPatientsListPage() {
        try {
            return wait.until(presenceOfElementLocated(INFMNT_VIEW_REPORT_PATIENTANDPROVIDER_LIST_VALIDATION));
        } catch (Exception e) {
            return null;
        }
    }
    private WebElement getPatientLookupValidation() {
        try {
            return wait.until(presenceOfElementLocated(INFMNT_VIEW_REPORT_PATIENT_LOOKUP_VALIDATION));
        } catch (Exception e) {
            return null;
        }

    }
    
    private WebElement getInactivityLogoutNoticeDialog() {
        try {
            return driver.findElement(INFMNT_SESSION_EXPIRE_NOTICE_DIALOG);
        } catch (Exception e) {
            return null;
        }
    }
    
    //Search for a dialog on screen with an specific text on it. If it's present it will return true.
    private boolean validateInfmntDialogByMessage(String dialogMessage) {
        try {
            WebElement we = driver.findElement(INFMNT_DIALOG);
            if(we != null) {
	            List<WebElement> c = we.findElements(By.xpath("./child::*"));
	            //c.stream().forEach(p -> System.out.println(p.getText()));
				if(c.stream().anyMatch(message -> message.getText().contains(dialogMessage))) {
					return true;
				}
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}



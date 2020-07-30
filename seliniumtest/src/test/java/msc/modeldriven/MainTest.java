package msc.modeldriven;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class MainTest {

    WebDriver webDriver;

    @Before
    public void startBrowser(){
        System.setProperty("webdriver.chrome.driver","E:\\Repos\\msc\\chromedriver_win32\\chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.get("http://localhost:8080/");
    }

    @After
    public void stopBrowser(){
        webDriver.quit();
    }

    //TestCase1
    @Test
    public void Home_FindOwners_Veterinarians(){
        //navigate to find owner page
        navigateToFindOwners(webDriver);
        Assert.assertTrue(isCurrentPageFindOwners(webDriver));
        //navigate to Veterinarians page
        navigateToVeterinarians(webDriver);
    }

    //TestCase2
    @Test
    public void Home_FindOwners_EnterInvalidLastName_EnterUniqueUserLastName(){
        //navigate to find owner page
        navigateToFindOwners(webDriver);
        Assert.assertTrue(isCurrentPageFindOwners(webDriver));
        //try searching with invalid name
        enterLastNameAndClickFindOwnerButton("XXXX",webDriver);
        //should end up in the same page
        Assert.assertTrue(isCurrentPageFindOwners(webDriver));
        //try searching with unique name.
        enterLastNameAndClickFindOwnerButton("Black",webDriver);
        //should end up owner information page
        Assert.assertTrue(isCurrentPageOwnerInformation(webDriver));
    }

    //TestCase3
    @Test
    public void Home_FindOwners_EnterCommonLastName_ClickOnFirstListItem(){
        //navigate to find owner page
        navigateToFindOwners(webDriver);
        Assert.assertTrue(isCurrentPageFindOwners(webDriver));
        //try searching with common name
        enterLastNameAndClickFindOwnerButton("Davis",webDriver);
        //should end up in the owners list page
        Assert.assertTrue(isCurrentPageOwnersList(webDriver));
        //Click on the first owner of the list
        WebElement firstOwnerOfTheList = webDriver.findElement(By.xpath("/html/body/div[1]/div/table/tbody/tr[1]/td[1]/a"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(firstOwnerOfTheList).click().build().perform();
        //should end up in the owner information page
        Assert.assertTrue(isCurrentPageOwnersInformation(webDriver));
    }

    //TestCase4
    @Test
    public void Home_FindOwners_AddNewOwner_EnterIncorrectPhoneNumber_EnterCorrectPhoneNumber(){
        //navigate to find owner page
        navigateToFindOwners(webDriver);
        Assert.assertTrue(isCurrentPageFindOwners(webDriver));
        //click on add owner
        WebElement addOwnerButtonElement = webDriver.findElement(By.xpath("/html/body/div[1]/div/a"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(addOwnerButtonElement).click().build().perform();
        //should go to owner page
        Assert.assertTrue(isCurrentPageAddOwner(webDriver));
        //fill data and click on add owner button
        enterFormDataAndClickAddOwnerButton(webDriver,"Saman","Lal","Gamagedara","Colombo","ed2455fdsxf");
        //should go to owner page
        Assert.assertTrue(isCurrentPageAddOwner(webDriver));
        enterFormDataAndClickAddOwnerButton(webDriver,"Saman","Lal","Gamagedara","Colombo","07712345678");
        //should go to the newly created owner information page
        Assert.assertTrue(isCurrentPageOwnerInformation(webDriver));
    }

    //TestCase5
    @Test
    public void OwnerInformation_ClickEditOwner(){
        webDriver.get("http://localhost:8080/owners/1");
        //should go to the owner information page
        Assert.assertTrue(isCurrentPageOwnerInformation(webDriver));
        //click on edit owner button
        Actions actions = new Actions(webDriver);
        //get add owner button
        WebElement editOwnerButton = webDriver.findElement(By.xpath("/html/body/div[1]/div/a[1]"));
        //click on edit owner button
        actions.moveToElement(editOwnerButton).click().build().perform();
        //should go to the owner page with update button

        WebElement updateOwnerButton = webDriver.findElement(By.xpath( "/html/body/div[1]/div/form/div[2]/div/button"));
        Assert.assertTrue(updateOwnerButton.isDisplayed() );
        Assert.assertEquals("Update Owner", updateOwnerButton.getText());

    }
    //TestCase6
    @Test
    public void OwnerInformation_AddNewPetWrongDate_AddNewPetCorrectDate(){
        webDriver.get("http://localhost:8080/owners/1");
        //should go to the owner information page
        Assert.assertTrue(isCurrentPageOwnerInformation(webDriver));
        //click on edit owner button
        Actions actions = new Actions(webDriver);
        //get add new pet button
        WebElement addNewPetButton = webDriver.findElement(By.xpath("/html/body/div[1]/div/a[2]"));
        //click on add new pet button
        actions.moveToElement(addNewPetButton).click().build().perform();
        //should go to new pet page
        Assert.assertTrue(isCurrentPageNewPet(webDriver));
        //enter incorrect (future) date and click add pet
        enterFormDataAndClickAddPet(webDriver,"Lulu","2050-10-10");
        //should nd up in the same page
        Assert.assertTrue(isCurrentPageNewPet(webDriver));
        //enter correct date and click add pet
        enterFormDataAndClickAddPet(webDriver,"Lulu","2010-10-10");
        //should go back to owner information page
        Assert.assertTrue(isCurrentPageOwnerInformation(webDriver));

    }

    //TestCase7
    @Test
    public void OwnerInformation_AddVisitWrongPastDate_AddVisitCorrectData(){
        webDriver.get("http://localhost:8080/owners/1");
        //should go to the owner information page
        Assert.assertTrue(isCurrentPageOwnerInformation(webDriver));
        //click on add visit button
        Actions actions = new Actions(webDriver);
        //get add visit button for first pet
        WebElement addVisitButton = webDriver.findElement(By.xpath("/html/body/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr/td[2]/a"));
        //click on add visit button
        actions.moveToElement(addVisitButton).click().build().perform();
        //should end up in the New Visit page
        Assert.assertTrue(isCurrentPageNewVisit(webDriver));
        //fill data wrong date
        enterFormDataAndClickAddVisit(webDriver,"2001-10-10","Headache");
        //should end up at same page
        Assert.assertTrue(isCurrentPageNewVisit(webDriver));
        //fill correct date
        enterFormDataAndClickAddVisit(webDriver,"2020-10-10","Headache");
        //should end up at owner information page
        Assert.assertTrue(isCurrentPageOwnerInformation(webDriver));
    }

    //TestCase8
    @Test
    public void OwnerInformation_ClickOnEditPet(){
        webDriver.get("http://localhost:8080/owners/1");
        //should go to the owner information page
        Assert.assertTrue(isCurrentPageOwnerInformation(webDriver));
        //click on edit pet button
        Actions actions = new Actions(webDriver);
        //get edit pet for first pet
        WebElement editPetButton = webDriver.findElement(By.xpath("/html/body/div[1]/div/table[2]/tbody/tr/td[2]/table/tbody/tr/td[1]/a"));
        //click on edit pet button
        actions.moveToElement(editPetButton).click().build().perform();
        //should go to pet page
        Assert.assertTrue(isCurrentPagePet(webDriver));

        WebElement nameElement = webDriver.findElement(By.xpath("//*[@id=\"name\"]"));
        WebElement dateElement = webDriver.findElement(By.xpath("//*[@id=\"birthDate\"]"));

        //asset data
        Assert.assertTrue(nameElement.isDisplayed());
        Assert.assertEquals("Leo",nameElement.getAttribute("value"));

        Assert.assertTrue(dateElement.isDisplayed());
        Assert.assertEquals("2010-09-07",dateElement.getAttribute("value"));

    }




    private void enterFormDataAndClickAddPet(WebDriver webDriver,String name, String date) {
        WebElement nameElement = webDriver.findElement(By.xpath("//*[@id=\"name\"]"));
        WebElement dateElement = webDriver.findElement(By.xpath("//*[@id=\"birthDate\"]"));

        //clear then add new data
        nameElement.clear();
        nameElement.sendKeys(name);
        dateElement.clear();
        dateElement.sendKeys(date);

        Actions actions = new Actions(webDriver);
        //get add pet button
        WebElement addOwnerButton = webDriver.findElement(By.xpath("/html/body/div[1]/div/form/div[2]/div/button"));
        //click on add pet button
        actions.moveToElement(addOwnerButton).click().build().perform();

    }


    private void enterFormDataAndClickAddOwnerButton(WebDriver webDriver,String fName, String lName, String addr, String city,String tel) {
        WebElement firstNameElement = webDriver.findElement(By.xpath("//*[@id=\"firstName\"]"));
        WebElement lastNameElement = webDriver.findElement(By.xpath("//*[@id=\"lastName\"]"));
        WebElement addressElement = webDriver.findElement(By.xpath("//*[@id=\"address\"]"));
        WebElement cityElement = webDriver.findElement(By.xpath("//*[@id=\"city\"]"));
        WebElement telephoneElement = webDriver.findElement(By.xpath("//*[@id=\"telephone\"]"));

        //clear then add new data
        firstNameElement.clear();
        firstNameElement.sendKeys(fName);
        lastNameElement.clear();
        lastNameElement.sendKeys(lName);
        addressElement.clear();
        addressElement.sendKeys(addr);
        cityElement.clear();
        cityElement.sendKeys(city);
        telephoneElement.clear();
        telephoneElement.sendKeys(tel);


        Actions actions = new Actions(webDriver);
        //get add owner button
        WebElement addOwnerButton = webDriver.findElement(By.xpath("/html/body/div[1]/div/form/div[2]/div/button"));
        //click on add owner button
        actions.moveToElement(addOwnerButton).click().build().perform();
    }

    private void enterFormDataAndClickAddVisit(WebDriver webDriver,String date,String description) {
        WebElement dateElement = webDriver.findElement(By.xpath("//*[@id=\"date\"]"));
        WebElement descriptionElement = webDriver.findElement(By.xpath("//*[@id=\"description\"]"));

        //clear then add new data
        dateElement.clear();
        dateElement.sendKeys(date);
        descriptionElement.clear();
        descriptionElement.sendKeys(description);

        Actions actions = new Actions(webDriver);
        //get add visit button
        WebElement addVisitButton = webDriver.findElement(By.xpath("/html/body/div[1]/div/form/div[2]/div/button"));
        //click on add visit button
        actions.moveToElement(addVisitButton).click().build().perform();

    }
    private boolean isCurrentPagePet(WebDriver webDriver) {
        WebElement findOwnersInformationTitle = webDriver.findElement(By.xpath("/html/body/div[1]/div/h2"));
        return findOwnersInformationTitle.isDisplayed() && "Pet".equals(findOwnersInformationTitle.getText());
    }
    private boolean isCurrentPageNewVisit(WebDriver webDriver) {
        WebElement findOwnersInformationTitle = webDriver.findElement(By.xpath("/html/body/div[1]/div/h2"));
        return findOwnersInformationTitle.isDisplayed() && "New Visit".equals(findOwnersInformationTitle.getText());
    }

    private boolean isCurrentPageNewPet(WebDriver webDriver) {
        WebElement findOwnersInformationTitle = webDriver.findElement(By.xpath("/html/body/div[1]/div/h2"));
        return findOwnersInformationTitle.isDisplayed() && "New Pet".equals(findOwnersInformationTitle.getText());
    }


    private boolean isCurrentPageAddOwner(WebDriver webDriver) {
        WebElement findOwnersInformationTitle = webDriver.findElement(By.xpath("/html/body/div[1]/div/h2"));
        return findOwnersInformationTitle.isDisplayed() && "Owner".equals(findOwnersInformationTitle.getText());
    }

    private boolean isCurrentPageOwnersInformation(WebDriver webDriver) {
        WebElement findOwnersInformationTitle = webDriver.findElement(By.xpath("/html/body/div[1]/div/h2[1]"));
        return findOwnersInformationTitle.isDisplayed() && "Owner Information".equals(findOwnersInformationTitle.getText());
    }

    private boolean isCurrentPageOwnersList(WebDriver webDriver) {
        WebElement findOwnersInformationTitle = webDriver.findElement(By.xpath("/html/body/div[1]/div/h2"));
        return findOwnersInformationTitle.isDisplayed() && "Owners".equals(findOwnersInformationTitle.getText());
    }

    private boolean isCurrentPageOwnerInformation(WebDriver webDriver) {
        WebElement findOwnersInformationTitle = webDriver.findElement(By.xpath("/html/body/div[1]/div/h2[1]"));
        return findOwnersInformationTitle.isDisplayed() && "Owner Information".equals(findOwnersInformationTitle.getText());
    }

    private boolean isCurrentPageFindOwners(WebDriver webDriver) {
        WebElement findOwnersTitle = webDriver.findElement(By.xpath("/html/body/div[1]/div/h2"));
        return findOwnersTitle.isDisplayed() && "Find Owners".equals(findOwnersTitle.getText());
    }

    private void enterLastNameAndClickFindOwnerButton(String lastName, WebDriver webDriver){
        //get last name text box
        WebElement lastNameElement = webDriver.findElement(By.xpath("//*[@id=\"lastName\"]"));
        //clear text in text box
        lastNameElement.clear();
        //add new text
        lastNameElement.sendKeys(lastName);
        Actions actions = new Actions(webDriver);
        //get find owner button
        WebElement findOwnerButton = webDriver.findElement(By.xpath("/html/body/div[1]/div/form/div[2]/div/button"));
        //click on find owner button
        actions.moveToElement(findOwnerButton).click().build().perform();
    }

    private void navigateToFindOwners(WebDriver webDriver) {
        WebElement findOwners = webDriver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[2]/a/span[2]"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(findOwners).click().build().perform();
    }

    private void navigateToVeterinarians(WebDriver webDriver) {
        WebElement findOwners = webDriver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[3]/a"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(findOwners).click().build().perform();

        WebElement titleVeterinarians = webDriver.findElement(By.xpath("/html/body/div[1]/div/h2"));
        Assert.assertTrue(titleVeterinarians.isDisplayed());
    }

}
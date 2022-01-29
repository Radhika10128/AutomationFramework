package Automation;

import automationResources.CommonBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ExcelDataDriven;

import java.io.IOException;
import java.util.ArrayList;

public class FileUpload extends CommonBase {

    private WebDriver driver;
    @BeforeClass
    public void setUp() {
        driver = getDriver();
    }

    @Test(groups = { "Sanity", "author-Radhika" }, description="Upload File")
    public void uploadFile() throws InterruptedException {
        driver.get(prop.getProperty("fileUpload"));
        String fileLocation = System.getProperty("user.dir") + "\\FileUpload.JPG";

        WebElement uploadElement = driver.findElement(By.id("uploadfile_0"));
        uploadElement.sendKeys(fileLocation);

        driver.findElement(By.id("terms")).click();
        driver.findElement(By.name("send")).click();
        System.out.println("File is Uploaded Successfully");
    }

    @Test(groups = { "Sanity", "author-Radhika" }, description="Extract Data From Excel")
    public void accessExcelData() throws IOException {
        ExcelDataDriven data = new ExcelDataDriven(driver);
        ArrayList<String> a = data.getData("Purchase");

        System.out.println(a.get(0));
        System.out.println(a.get(1));
        System.out.println(a.get(2));
        System.out.println(a.get(3));
    }

}

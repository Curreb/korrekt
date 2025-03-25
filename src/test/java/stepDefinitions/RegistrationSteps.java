package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationSteps {
    WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the user is on the registration page")
    public void openRegistrationPage() {
        //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("they enter valid registration details")
    public void enterValidDetails() {
        driver.findElement(By.id("member_firstname")).sendKeys("John");
        driver.findElement(By.id("member_lastname")).sendKeys("Doe");
        driver.findElement(By.id("member_emailaddress")).sendKeys("abcdefgqhid@example.com");
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys("abcdefgqhid@example.com");
        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Password123");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Password123");
        driver.findElement(By.id("dp")).sendKeys("02/07/2000");
    }

    @When("they accept the terms and conditions and they press i am over 18")
    public void acceptTerms() throws InterruptedException {
        driver.findElement(By.cssSelector("label[for='sign_up_25']")).click();
        Thread.sleep(100);
        driver.findElement(By.cssSelector("label[for='sign_up_26']")).click();
        Thread.sleep(500);
    }

    @And("they accept code of ethics")
    public void theyAcceptCodeOfEthics() {
        driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[11]/div/div[7]/label")).click();
    }

    @When("they submit the form")
    public void submitForm() {
        driver.findElement(By.name("join")).click();
    }

    @Then("the account should be created successfully")
    public void verifySuccessfulRegistration() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("SignUpConfirmation"));

        WebElement confirmationMassage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/h2")));

        String currentUrl = driver.getCurrentUrl();
        System.out.println("URL after valid registration: " + currentUrl);


        if (!currentUrl.contains("Confirmation") || !currentUrl.contains("MembershipNumber")) {
            throw new AssertionError("Account not created. Current URL: " + currentUrl);
        }
    }

    @When("they enter valid details except last name")
    public void enterDetailsWithoutLastName() throws InterruptedException {
        driver.findElement(By.id("member_firstname")).sendKeys("John");
        driver.findElement(By.id("member_emailaddress")).sendKeys("cdpeg@example.com");
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys("cdpeg@example.com");
        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Pasword123");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Pasword123");
        driver.findElement(By.id("dp")).sendKeys("02/06/2000");

        driver.findElement(By.cssSelector("label[for='sign_up_25']")).click();
        Thread.sleep(100);
        driver.findElement(By.cssSelector("label[for='sign_up_26']")).click();
        Thread.sleep(500);
    }

    @Then("an error message should be displayed for missing last name")
    public void verifyMissingLastNameError() {
        WebElement error = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[5]/div[2]/div/span"));
        assertTrue(error.isDisplayed());
    }

    @When("they enter valid details but mismatched passwords")
    public void enterDetailsWithMismatchedPasswords() throws InterruptedException {
        driver.findElement(By.id("member_firstname")).sendKeys("John");
        driver.findElement(By.id("member_lastname")).sendKeys("Doe");
        driver.findElement(By.id("member_emailaddress")).sendKeys("cdeg@example.com");
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys("cdeg@example.com");
        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Password123");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("ijsbdffvijbsd");
        driver.findElement(By.id("dp")).sendKeys("02/07/2000");

        driver.findElement(By.cssSelector("label[for='sign_up_25']")).click();
        Thread.sleep(100);
        driver.findElement(By.cssSelector("label[for='sign_up_26']")).click();
        Thread.sleep(500);
    }

    @Then("an error message should be displayed for password mismatch")
    public void verifyPasswordMismatchError() {
        WebElement error = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[8]/div/div[2]/div[2]/div/span"));
        assertTrue(error.isDisplayed());
    }

    @When("they do not accept the terms and conditions")
    public void doNotAcceptTerms() throws InterruptedException {

        driver.findElement(By.cssSelector("label[for='sign_up_25']"));
        Thread.sleep(100);
    }

    @Then("an error message should be displayed for unaccepted terms")
    public void verifyTermsNotAcceptedError() {
        WebElement error = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[11]/div/div[2]/div[1]/span/span"));
        assertTrue(error.isDisplayed());
    }
}
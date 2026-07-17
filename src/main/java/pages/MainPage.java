package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;

    private final By orderButtonTop = By.cssSelector(".Button_Button__ra12g:not(.Button_Inverted__3IF-i)");
    private final By orderButtonBottom = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");
    private final By cookieButton = By.id("rcc-confirm-button");
    private final By faqQuestions = By.cssSelector(".accordion__button");
    private final By faqAnswers = By.cssSelector(".accordion__panel");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    public void acceptCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            cookieBtn.click();
        } catch (Exception e) {
            // куки не появились
        }
    }

    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }

    public void clickOrderButtonBottom() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(orderButtonBottom));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        try {
            button.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    public void clickQuestion(int index) {
        List<WebElement> questions = driver.findElements(faqQuestions);
        if (index >= 0 && index < questions.size()) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", questions.get(index));
            questions.get(index).click();
        }
    }

    public String getAnswerText(int index) {
        List<WebElement> answers = driver.findElements(faqAnswers);
        if (index >= 0 && index < answers.size()) {
            return answers.get(index).getText();
        }
        return "";
    }

}
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class OrderPage {
    private final WebDriver driver;

    private final By nameInput = By.cssSelector("input[placeholder='* Имя']");
    private final By surnameInput = By.cssSelector("input[placeholder='* Фамилия']");
    private final By addressInput = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationInput = By.cssSelector("input[placeholder='* Станция метро']");
    private final By metroStationDropdown = By.cssSelector(".select-search__row");
    private final By phoneInput = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    private final By dateInput = By.cssSelector("input[placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.cssSelector(".Dropdown-placeholder");
    private final By rentalPeriodOptions = By.cssSelector(".Dropdown-option");
    private final By blackPearlCheckbox = By.id("black");
    private final By greyHopelessnessCheckbox = By.id("grey");
    private final By commentInput = By.cssSelector("input[placeholder='Комментарий для курьера']");
    private final By orderButton = By.cssSelector(".Order_Buttons__1xGrp .Button_Button__ra12g:not(.Button_Inverted__3IF-i)");
    private final By confirmButton = By.xpath("//button[text()='Да']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    public void fillSurname(String surname) {
        driver.findElement(surnameInput).sendKeys(surname);
    }

    public void fillAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    public void selectMetroStation(String station) {
        driver.findElement(metroStationInput).click();
        driver.findElement(metroStationInput).sendKeys(station);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> stations = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(metroStationDropdown));
        if (!stations.isEmpty()) {
            stations.get(0).click();
        }
    }

    public void fillPhone(String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void fillDate(String date) {
        WebElement dateField = driver.findElement(dateInput);
        dateField.clear();
        dateField.sendKeys(date);
        driver.findElement(By.cssSelector(".Order_Header__BZXOb")).click();
    }

    public void selectRentalPeriod(String period) {
        driver.findElement(rentalPeriodDropdown).click();
        List<WebElement> options = driver.findElements(rentalPeriodOptions);
        for (WebElement option : options) {
            if (option.getText().contains(period)) {
                option.click();
                break;
            }
        }
    }

    public void selectColor(String color) {
        if (color.equalsIgnoreCase("черный")) {
            driver.findElement(blackPearlCheckbox).click();
        } else if (color.equalsIgnoreCase("серый")) {
            driver.findElement(greyHopelessnessCheckbox).click();
        }
    }

    public void fillComment(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        confirmBtn.click();
    }

    public String getSuccessModalText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()='Посмотреть статус']")));
        WebElement modal = driver.findElement(By.xpath("//div[contains(@class,'Order_Modal')]"));
        return modal.getText();
    }
}
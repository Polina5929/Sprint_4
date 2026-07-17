package tests;

import driver.DriverFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.OrderPage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTests {

    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    private final String orderButton;
    private final String name;
    private final String surname;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String color;
    private final String comment;

    public OrderTests(String orderButton, String name, String surname, String address,
                      String metroStation, String phone, String date, String rentalPeriod,
                      String color, String comment) {
        this.orderButton = orderButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"top", "Александр", "Психушкин", "ул. Тверская, д. 1",
                        "Тверская", "89994897622", "16.07.2026", "сутки",
                        "черный", "Позвонить за час"},
                {"bottom", "Павлина", "Мандарина", "ул. Арбат, д. 20",
                        "Арбатская", "89083743379", "25.08.2025", "трое суток",
                        "серый", "Оставить у двери"}
        });
    }

    @Test
    public void testOrderFlow() {
        WebDriver driver = driverFactory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();

        if (orderButton.equals("top")) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillName(name);
        orderPage.fillSurname(surname);
        orderPage.fillAddress(address);
        orderPage.selectMetroStation(metroStation);
        orderPage.fillPhone(phone);
        orderPage.clickNextButton();

        orderPage.fillDate(date);
        orderPage.selectRentalPeriod(rentalPeriod);
        orderPage.selectColor(color);
        orderPage.fillComment(comment);
        orderPage.clickOrderButton();

        orderPage.confirmOrder();

        String successText = orderPage.getSuccessModalText();
        assertTrue(successText.contains("Заказ оформлен"));
    }
}
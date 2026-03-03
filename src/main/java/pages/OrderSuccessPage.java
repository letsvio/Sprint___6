package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderSuccessPage {

    private final WebDriver driver;
    //Модальное окно успешного оформления заказа
    private final By successModal = By.xpath("//div[contains(text(), 'Заказ оформлен')]");

    public OrderSuccessPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isSuccessModalVisible() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(successModal))
                .isDisplayed();
    }

}
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class FirstOrderPage {

    private WebDriver driver;

    public FirstOrderPage (WebDriver driver){
        this.driver = driver;
    }
    // инпут имени пользователя
    private final By nameInput = By.xpath("//input[@placeholder='* Имя']");
    // инпут фамилии пользователя
    private final By surnameNameInput = By.xpath("//input[@placeholder='* Фамилия']");
    // инпут станций метро
    private final By metroInput =  By.cssSelector("input.select-search__input");
    // енам станций метро
    private final By metroEnum = By.cssSelector("div.select-search__select");
    // инпут адреса пользователя
    private final By clientAddressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    // инпут мобильного телефона пользователя
    private final By clientMobileNumberInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    // кнопка продолжения заполнения данных
    private final By continueOrderInput = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    // метод для выбора станции метро
    private By metroOption(String station) {
        return By.xpath("//div[@class='select-search__select']//div[normalize-space()='" + station + "']");
    }


    public void pressContinueButton(){
        driver.findElement(continueOrderInput).click();
    }

    public void setNameInput(String name){
        driver.findElement(nameInput).sendKeys(name);
    }

    public void setClientAddressInput(String clientAddress){
        driver.findElement(clientAddressInput).sendKeys(clientAddress);
    }

    public void setSurnameNameInput(String surnameName){
        driver.findElement(surnameNameInput).sendKeys(surnameName);
    }


    public void setMetroInput (String station){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(metroInput).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(metroEnum));
        wait.until(ExpectedConditions.elementToBeClickable(metroOption(station))).click();
    }

    public void setClientMobileNumberInput(String clientNumber){
        driver.findElement(clientMobileNumberInput).sendKeys(clientNumber);
    }

}

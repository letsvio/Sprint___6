package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SecondOrderPage {

    private WebDriver driver;

    public SecondOrderPage (WebDriver driver){
        this.driver = driver;
    }
    //Инпут даты
    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    //кнопка срока аренды
    private final By rentEnumDrop = By.cssSelector(".Dropdown-control");

    //енам срока аренды
    private final By rentOption(String text) {
        return By.xpath(
                "//div[contains(@class,'Dropdown-menu')]//div[contains(@class,'Dropdown-option') and normalize-space()='" + text + "']"
        );
    }

    //чекбокс выбора цвета
    private final By colorScooter = By.cssSelector(".Checkbox_Input__14A2w");

    //инпут комментария
    private final By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");

    //кнопка создания заказа
    private final By createOrderButton = By.xpath("//div[contains(@class,'Order_Buttons')]//button[normalize-space()='Заказать']");

    //кнопка подтверждения создания заказа
    private final By confirmOrder = By.xpath("//div[contains(@class,'Order_Buttons')]//button[normalize-space()='Да']");

    //кнопка просмотра статуса заказа
    private final By pressCheckStatus = By.xpath("//div[contains(@class,'Order_NextButton')]//button[normalize-space()='Посмотреть статус']");

    //кнопка для клика, чтобы закрыть лишних окон
    private final By body = By.tagName("body");


    public void setDateInput (String date){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(dateInput));
        driver.findElement(dateInput).sendKeys(date);
    }

    public void setCommentInput (String comment){
        driver.findElement(commentInput).sendKeys(comment);
    }

    public void setRent(String period){

        driver.findElement(body).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(rentEnumDrop)).click();

        wait.until(ExpectedConditions.elementToBeClickable(rentOption(period))).click();
    }

    public void setColorScooter (){
        driver.findElement(colorScooter).click();
    }



    public void clickconfirmOrder (){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrder)).click();
    }

    public void clickCreateOrder (){
        driver.findElement(createOrderButton).click();
    }

//    public void checkStatusButton(){
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.elementToBeClickable(pressCheckStatus)).getText();
//    }




}

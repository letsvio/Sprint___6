package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {

    public static final String URL = "https://qa-scooter.praktikum-services.ru/";

    public MainPage (WebDriver driver){
        this.driver = driver;
    }

    private WebDriver driver;

    //Кнопка заказать в хедере
    private By topOrderButton = By.xpath("(//button[normalize-space()='Заказать'])[1]");

    //Кнопка заказать после скролла
    private By afterScrollOrderButton  = By.xpath("(//button[normalize-space()='Заказать'])[2]");

    //метод элемента для поиска текста вопроса
    public WebElement getQuestionByText(String text) {
        return driver.findElement(
                By.xpath(".//div[@class='accordion__button' and text()='" + text + "']")
        );
    }
    //метод элемента для поиска текста ответа
    public WebElement getAnswerByQuestionText(String questionText) {
        return driver.findElement(
                By.xpath(
                        "//div[@data-accordion-component='AccordionItemPanel' and normalize-space()='" + questionText + "']"
                )
        );
    }

    private By buttonYandex = By.cssSelector(".Header_LogoYandex__3TSOI");


     public void pressButtonYandex(){
        driver.findElement(buttonYandex).click();
    }





    public void clickCookieBannerClose(){
        By cookieButton = By.cssSelector(".App_CookieConsent__1yUIN button");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(
                ExpectedConditions.presenceOfElementLocated(cookieButton)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);
    }


    public void openBaseURL(){
        driver.get(URL);
    }

    public void clickTopOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(topOrderButton))
                .click();
    }

    public void clickAfterScrollOrderButton() {
        WebElement element = driver.findElement(afterScrollOrderButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(afterScrollOrderButton))
                .click();
    }

}

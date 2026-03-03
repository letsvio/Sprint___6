import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FirstOrderPage;
import pages.MainPage;
import pages.OrderSuccessPage;
import pages.SecondOrderPage;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsChrome {

    private WebDriver driver;


    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }


    static Stream<Arguments> questionsAndAnswers() {
        return Stream.of(
                Arguments.of(
                        "Сколько это стоит? И как оплатить?",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."
                ),
                Arguments.of(
                        "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."
                ),
                Arguments.of(
                        "Как рассчитывается время аренды?",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."
                ),
                Arguments.of(
                        "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."
                ),
                Arguments.of(
                        "Можно ли продлить заказ или вернуть самокат раньше?",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."
                ),
                Arguments.of(
                        "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."
                ),
                Arguments.of(
                        "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."
                ),
                Arguments.of(
                        "Я жизу за МКАДом, привезёте?",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."
                )
        );
    }



    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(
                        "Дмитрий",
                        "Сергеев",
                        "Парк культуры",
                        "г. Москва, ул. Остоженка, д. 25, кв. 78",
                        "79035553322",
                        "24.12.1121",
                        "Не очень важный коммент",
                        "четверо суток"
                ),
                Arguments.of(
                        "Анна",
                        "Васильева",
                        "Комсомольская",
                        "г. Москва, ул. Каланчевская, д. 21, стр. 1",
                        "79154446688",
                        "22.11.2321",
                        "Очень важный коммент",
                        "сутки"

                )

        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void successCreateOrderScooterFromTopButton(String name, String surnameName, String station, String clientAddress, String clientNumber, String date,
                                                String comment, String period) {
        MainPage mainPage = new MainPage(driver);
        mainPage.openBaseURL();
        mainPage.clickTopOrderButton();
        mainPage.clickCookieBannerClose();


        FirstOrderPage firstOrderPage = new FirstOrderPage(driver);
        firstOrderPage.setNameInput(name);
        firstOrderPage.setSurnameNameInput(surnameName);
        firstOrderPage.setMetroInput(station);
        firstOrderPage.setClientAddressInput(clientAddress);
        firstOrderPage.setClientMobileNumberInput(clientNumber);
        firstOrderPage.pressContinueButton();

        SecondOrderPage secondOrderPage = new SecondOrderPage(driver);
        secondOrderPage.setDateInput(date);
        secondOrderPage.setCommentInput(comment);
        secondOrderPage.setRent(period);
        secondOrderPage.setColorScooter();
        secondOrderPage.clickCreateOrder();
        secondOrderPage.clickconfirmOrder();

        OrderSuccessPage successPage = new OrderSuccessPage(driver);

        assertTrue(successPage.isSuccessModalVisible(),
                "Модальное окно успешного заказа не появилось");

    }


    @ParameterizedTest
    @MethodSource("testData")
    void successCreateOrderAfterScrollOrderButton(String name, String surnameName, String station, String clientAddress, String clientNumber, String date,
                                                  String comment, String period) {
        MainPage mainPage = new MainPage(driver);
        mainPage.openBaseURL();
        mainPage.clickAfterScrollOrderButton();
        mainPage.clickCookieBannerClose();


        FirstOrderPage firstOrderPage = new FirstOrderPage(driver);
        firstOrderPage.setNameInput(name);
        firstOrderPage.setSurnameNameInput(surnameName);
        firstOrderPage.setMetroInput(station);
        firstOrderPage.setClientAddressInput(clientAddress);
        firstOrderPage.setClientMobileNumberInput(clientNumber);
        firstOrderPage.pressContinueButton();


        SecondOrderPage secondOrderPage = new SecondOrderPage(driver);
        secondOrderPage.setDateInput(date);
        secondOrderPage.setCommentInput(comment);
        secondOrderPage.setRent(period);
        secondOrderPage.setColorScooter();
        secondOrderPage.clickCreateOrder();
        secondOrderPage.clickconfirmOrder();

        OrderSuccessPage successPage = new OrderSuccessPage(driver);

        assertTrue(successPage.isSuccessModalVisible(),
                "Модальное окно успешного заказа не появилось");
    }


    @ParameterizedTest()
    @MethodSource("questionsAndAnswers")
    public void checkAccordionQuestionAndAnswer(String expectedQuestion,
                                                String expectedAnswer) {

        MainPage mainPage = new MainPage(driver);
        mainPage.openBaseURL();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        mainPage.clickCookieBannerClose();

        WebElement question = mainPage.getQuestionByText(expectedQuestion);
        WebElement answer = mainPage.getAnswerByQuestionText(expectedAnswer);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", question);

        wait.until(ExpectedConditions.elementToBeClickable(question)).click();

        wait.until(ExpectedConditions.visibilityOf(answer));

        assertEquals(expectedQuestion, question.getText().trim(),
                "Текст вопроса не совпадает");

        assertEquals(expectedAnswer, answer.getText().trim(),
                "Текст ответа не совпадает");
    }




    @AfterEach
        public void teardown () {
            driver.quit();
        }

}

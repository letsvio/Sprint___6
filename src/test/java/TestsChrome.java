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

    // Массивы вопросов и ответов
    private static final String[] QUESTIONS = {
            "Сколько это стоит? И как оплатить?",
            "Хочу сразу несколько самокатов! Так можно?",
            "Как рассчитывается время аренды?",
            "Можно ли заказать самокат прямо на сегодня?",
            "Можно ли продлить заказ или вернуть самокат раньше?",
            "Вы привозите зарядку вместе с самокатом?",
            "Можно ли отменить заказ?",
            "Я жизу за МКАДом, привезёте?"
    };

    private static final String[] ANSWERS = {
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    };


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

    @Test
    public void checkAccordionQuestionsAndAnswers() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openBaseURL();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        mainPage.clickCookieBannerClose();

        List<WebElement> questionElements = mainPage.getListQuestionElements();
        List<WebElement> answerElements = mainPage.getListAnswersElements();

        assertEquals(QUESTIONS.length, questionElements.size(), "Количество вопросов не совпадает с ожидаемым!");
        assertEquals(ANSWERS.length, answerElements.size(), "Количество ответов не совпадает с ожидаемым!");

        for (int i = 0; i < questionElements.size(); i++) {
            WebElement question = questionElements.get(i);
            WebElement answer = answerElements.get(i);

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);

            wait.until(ExpectedConditions.elementToBeClickable(question));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);

            wait.until(ExpectedConditions.visibilityOf(answer));

            String actualQuestion = question.getText().trim();
            String expectedQuestion = QUESTIONS[i];
            assertEquals(expectedQuestion, actualQuestion, "Вопрос не соответствует ожидаемому!");

            String actualAnswer = answer.getText().trim();
            String expectedAnswer = ANSWERS[i];
            assertEquals(expectedAnswer, actualAnswer, "Ответ не соответствует ожидаемому для вопроса: " + actualQuestion);

            System.out.println("Вопрос: " + actualQuestion);
            System.out.println("Ответ: " + actualAnswer);
        }
    }



    @AfterEach
        public void teardown () {
            driver.quit();
        }

}

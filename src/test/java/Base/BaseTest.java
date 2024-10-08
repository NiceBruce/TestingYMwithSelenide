package Base;

import allure.selenide.CustomAllureSelenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.BeforeAll;

/**
 *   Данный класс описывает базовую ОБЩУЮ структуру для тестов и ОБЩИЕ методы для работы с ними.
 *   @author Борис Демин
 */
public class BaseTest {

    /**
     * Данный метод необходим для управления отчетами в Allure Framework,
     * с определенными настройками для создания скриншотов и сохранения исходного кода страницы.
     * ... тест будет долгим(
     */
    @BeforeAll
    public static void setup(){
        SelenideLogger.addListener("AllureSelenide",new CustomAllureSelenide().screenshots(true).savePageSource(false));
    }
}

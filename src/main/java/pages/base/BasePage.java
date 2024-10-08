package pages.base;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;
import utils.Assertions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;
import static utils.Properties.driversProperties;

/**
 *   Данный абстрактный класс описывает базовую общую структуру веб-страницы маркетплейса и общие метод(ы) для работы с ней.
 *   @author Борис Демин
 */
public abstract class BasePage {

    /**
     * Текстовая переменная, представляющая XPATH-локатор, представляющий поле ввода для "ПОИСКА" товаров
     */
    protected String inputSearchLocator = "//input[@id='header-search']";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для описания кнопки "Каталог" на главной странице Яндекс-маркета
     */
    protected String catalogButtonLocator = "//div[@data-zone-name='catalog']/button";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, цель которого идентифицировать,
     * что в данный момент происходит загрузка/обновление товаров в поисковой выдаче
     * на главной странице Яндекс-маркета
     */
    protected String preloaderResults =  "//div[@data-grabber='SearchSerp']//div[@data-auto='preloader']";

    /**
     * Класс, который используется для выполнения действий с элементами веб-страницы, требующие интерактивности,
     * таких как клики, наведение курсора, перетаскивание, ввод текста и другие
     */
    protected Actions actions = Selenide.actions();


    /**
     * Метод, цель которого заключается в утверждении,
     * что страница загружена посредством определения виден ли элемент "catalogButton"
     * Так же данный метод разворачивает браузер на все окно. Метод запускается следующим после метода open()
     * @param somePage - тип страницы, необходимой для дальнейшего перехода и работы с ней, вызова ее методов
     * @return возвращаемое значение любой класс-наследник BasePage
     */
    @Step("Шаг 1. Проверка, что страница успешно загружена: {somePage}")
    public <T extends BasePage> T checkIsPageLoaded(Class<T> somePage) {
        WebDriverRunner.getWebDriver().manage().window().maximize();

        $x(catalogButtonLocator).shouldBe(Condition.visible,
                Duration.ofMillis(driversProperties.driverExplicitlyWait()));

        Assertions.assertTrue($x(catalogButtonLocator).exists(), "Элемент \"catalogButton\" отсутствует на странице!");

        return somePage.cast(page(somePage));
    }
}

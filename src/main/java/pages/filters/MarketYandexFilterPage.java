package pages.filters;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.base.BasePage;
import pages.results.MarketYandexPageResult;
import utils.Assertions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;
import static utils.Properties.driversProperties;


/**
 *   Данный класс описывает базовую общую структуру "фильтров поиска товаров" веб-страницы маркетплейса
 *   и общие методы для работы по поиску и их заполнению с последующей проверкой "введены ли данные"
 *   @author Борис Демин
 */
public class MarketYandexFilterPage extends BasePage {

    /**
     * Текстовая переменная, представляющая XPATH-локатор - описания секции "Производитель" на главной странице Яндекс-маркета,
     * с возможностью подстановки в конце необходимого типа производителя.
     */
    private String manufacturerSelector = "//div[contains(@data-zone-data, 'Производитель')]//div[contains(@data-zone-data, '%s')]";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для описания поля ввода для фильтра по цене
     * - "Цены ОТ" на главной странице Яндекс-маркета. (Параметр на перспективу)
     */
    private String inputPriceFrom = "//div[contains(@data-auto, 'filter-range-glprice')]//span[contains(@data-auto, 'min')]//input";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для описания поля ввода для фильтра по цене - "Цены ДО"
     * на главной странице Яндекс-маркета (Параметр на перспективу)
     */
    private String inputPriceTo = "//div[contains(@data-auto, 'filter-range-glprice')]//span[contains(@data-auto, 'max')]//input";



    /**
     * Универсальный метод, цель которого установить фильтры по производителю товаров
     * @param manufacturer - текстовое значение "Необходимого производителя товаров", в дальнейшем подставляется в
     * локатор manufacturerSelector.
     * Метод ожидает видимость элемента manufacturerCheckBox для установки по фильтру производитель,
     * после чего производится клик.
     * Так же после клика ожидает, что результаты по примененному фильтру "прогрузились"
     * посредством ожидания "исчезновения" веб-элемента идентификатора загрузки/обновления результатов выдачи
     * @return - возвращаем текущий объект, для последующего вызова необходимых методов
     */

    @Step("Шаг 6. Задаем параметр фильтра \"Производитель\" следующим значением: {manufacturer}")
    public MarketYandexFilterPage setFilterByManufacturer(String manufacturer) {
        SelenideElement manufacturerCheckBox = $x(String.format(manufacturerSelector, manufacturer));
        manufacturerCheckBox.shouldBe(Condition.visible, Duration.ofMillis(driversProperties.driverExplicitlyWait()));
        manufacturerCheckBox.click();
        $x(preloaderResults).shouldNot(Condition.visible);

        return this;
    }


    /**
     * Метод, цель которого проверить установлены ли фильтры по "Производителю товара"
     * <br/> Принимает параметр:
     * @param manufacturer текстовое значение "Необходимого производителя товаров", в дальнейшем подставляется в
     * локатор manufacturerSelector.
     * Метод находит элемент чекбокс и проверяет свойство "opacity" = 1, показывающее, что данный фильтр применен
     * @return возвращаемое значение класс MarketYandexPageResult, предназначенный для поиска/обработки/навигации/проверки
     * результатов выдачи поиска товаров по выбранным фильтрам.
     */
    @Step("Шаг 7. Проверяем, что параметр фильтра \"Производитель\" : {manufacturer} установлен")
    public MarketYandexPageResult checkManufacturerFiltersIsInstalled(String manufacturer) {
        SelenideElement manufacturerCheckBox = $x(String.format(manufacturerSelector, manufacturer));

        Assertions.assertTrue(manufacturerCheckBox.$x(".//span").getCssValue("opacity").equals("1"),
                String.format("Параметр фильтра \"Производитель\" : %s не установлен!", manufacturer));

        return page(MarketYandexPageResult.class);
    }
}

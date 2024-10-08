package ru.yandex.market;

import Base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.base.MarketYandexBasePage;

import static com.codeborne.selenide.Selenide.*;

/**
 *   Данный класс описывает набор тестов для страницы "market.yandex.ru"
 *   @author Борис Демин
 */
public class MarketYandexTest extends BaseTest {
    /**
     * Данный параметризированный тест содержит необходимые шаги для проверки функционала "market.yandex.ru"
     * Тест написан в стиле фреймворка Selenide.
     * В отчете Allure можно увидеть повторяющиеся шаги 8 и 9, предназначенные для прохождения по страницам и накоплению результатов поиска.
     * Так сделано намеренно, чтобы узнать на каком этапе (странице) упал тест.
     * Так же в заключительном шаге 10 - проверке результатов поиска - мы увидим проверку всех элементов всех страниц.
     */
    @Epic("Тест-кейс 2.1 'Selenide'")
    @Feature("Проверка результатов выдачи Яндекс Маркета по предустановленным фильтрам")
    @DisplayName("Проверка результатов выдачи товаров Яндекс Маркета")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("utils.DataProvider#providerProductSelection")
    public void openYaMarket(String url, String productCategory, String subCategory, String filterManufacturer){
        open(url, MarketYandexBasePage.class)
                .checkIsPageLoaded(MarketYandexBasePage.class)
                .goToCatalog(productCategory)
                .selectMainCatalogSection(productCategory)
                .moveToSubCategory(subCategory)
                .checkIsSectionDisplayed(subCategory)
                .setFilterByManufacturer(filterManufacturer)
                .checkManufacturerFiltersIsInstalled(filterManufacturer)
                .moveInDirection(true)
                .checkResultFromAllPages();
    }
}

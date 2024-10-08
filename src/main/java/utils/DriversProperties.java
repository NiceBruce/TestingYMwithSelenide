package utils;

import org.aeonbits.owner.Config;

/**
 *   Данный интерфейс необходим для работы с конфигурационным файлом свойств драйвера
 *   @author Борис Демин
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "file:src/test/resources/drivers.properties"
})
public interface DriversProperties extends Config {

    /**
     *   Параметр "тип" драйвера, Хром или ФайрФокс!
     */
    @Key("driver.type")
    String driverType();

    /**
     *   Параметр "неявное ожидание" для драйвера
     */
    @Key("driver.implicitly_wait")
    int driverImplivitlyWait();

    /**
     *   Параметр "явное ожидание" для драйвера
     */
    @Key("driver.explicitly_wait")
    int driverExplicitlyWait();

    /**
     *   Параметр "очистка куки" для драйвера
     */
    @Key("driver.clear_cookie")
    Boolean driverClerCookie();

    /**
     *   Параметр "держать ли браузер открытым после тестов" для драйвера
     */
    @Key("driver.hold_open_browser")
    Boolean driverHoldOpenBrowser();

    /**
     *   Параметр "URL" для драйвера
     */
    @Key("market.yandex.url")
    String marketYandexUrl();

    /**
     *   Параметр "Proxy" для драйвера
     *   заполнять в формате 94.137.90.61:50100
     */
    @Key("driver.proxy")
    String driverProxy();

    /**
     *   Параметр "ProxyAuth" для драйвера
     */
    @Key("driver.user_agent")
    String driverUserAgent();

    /**
     *   Параметр "UserAgent" для драйвера
     *   заполнять в формате login:password
     */
    @Key("driver.auth")
    String driverProxyAuth();
}

package utils;

import org.aeonbits.owner.ConfigFactory;

/**
 *   Данный класс Properties предназначен для хранения и предоставления доступа к конфигурационным данным
 *   для классов DriversProperties и FilterProperties.
 *   @author Борис Демин
 */
public class Properties {

    /**
     * Статическое поле, представляющее объект типа DriversProperties, созданное с помощью фабрики конфигурации.
     * Представляет собой доступ к конфигурации веб-драйвера
     */
    public static DriversProperties driversProperties = ConfigFactory.create(DriversProperties.class);

    /**
     * Статическое поле, представляющее объект типа FilterProperties, созданное с помощью фабрики конфигурации.
     * Представляет собой доступ к конфигурации для фильтров поиска.
     */
    public static FilterProperties filterProperties = ConfigFactory.create(FilterProperties.class);
}

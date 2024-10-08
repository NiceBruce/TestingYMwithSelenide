package utils;

import org.aeonbits.owner.Config;

/**
 *   Данный интерфейс необходим для работы с конфигурационным файлом свойств фильтров
 *   для отбора по необходимым параметрам
 *   @author Борис Демин
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:src/test/resources/filter.properties"
})
public interface FilterProperties extends Config {

    /**
     * Целочисленное значение фильтра "цена ОТ"
     */
    @Key("filter.PriceFrom")
    Integer filterPriceFrom();

    /**
     * Целочисленное значение фильтра "цена ДО"
     */
    @Key("filter.PriceTo")
    Integer filterPriceTo();

    /**
     * Текстовое значение фильтра "производителя"
     */
    @Key("filter.manufacturer")
    String filterManufacturer();

    /**
     * Текстовое значение фильтра по "модели" производителя
     */
    @Key("filter.modelName")
    String filterModelName();



}

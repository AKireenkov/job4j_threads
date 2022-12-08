package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Реализация неблокирующего кеша.
 * Часть данных хранится в кеше, в данном случае, это информация о модели данных Base.
 *
 * @author Andrey Kireenkov
 * @version 1.0
 * @since 08.12.2022
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * Метод добавления элемента в карту memory.
     * За счет добавления, через метод putIfAbsent(), метод становится потокобезопасным.
     * Метод putIfAbsent() является атомарным.
     *
     * @param model модель данных для добавления в карту.
     * @return возвращает true, если объект был добавлен впервые в карту по данному ключу.
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * Метод обновления объекта в карте.
     * Является потокобезопасным за счет атомарности computeIfPresent().
     * В методе computeIfPresent() используется проверка версии объекта.
     * Нить, достает объект из карты по id, сравнивает версии полученного объекта,
     * и объкта, повторно запрошенного по этому id.
     * Если версии расходятся - выбрасывается исключение. Это значит, что, другая нить, изменила версию этого объекта.
     *
     * @param model объект, которым необходимо заменить другой объект.
     * @return возвращает true, если произошло обновление объекта.
     */
    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (k, v) -> {
            Base stored = memory.get(v.getId());
            if (stored.getVersion() != v.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base updateBase = new Base(v.getId(), v.getVersion() + 1);
            updateBase.setName(model.getName());
            return updateBase;
        }) != null;
    }

    /**
     * Метод удаления объекта из карты по id.
     *
     * @param model объект, который необходимо удалить.
     */
    public void delete(Base model) {
        memory.remove(model.getId());
    }

    /**
     * Метод для получения объекта из карты memory.
     *
     * @param id id, по которому происходит запрос объекта.
     * @return найденный объект.
     */
    public Base get(int id) {
        return memory.get(id);
    }
}

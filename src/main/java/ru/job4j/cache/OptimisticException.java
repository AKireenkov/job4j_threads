package ru.job4j.cache;

/**
 * Реализация исключения, выброшенного в случае несовпадения версий одного объекта.
 *
 * @author Andrey Kireenkov
 * @version 1.0
 * @since 08.12.2022
 */
public class OptimisticException extends RuntimeException {

    /**
     * Выбрасываемое исключение.
     *
     * @param message сообщение, передаваемое в момент выбрасывания исключения.
     */
    public OptimisticException(String message) {
        super(message);
    }
}

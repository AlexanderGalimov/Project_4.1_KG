package ru.cs.vsu.galimov.draw;

/**
 * Интерфейс декларирует метод, который будет отвечать, подходит ли заданный экземпляр класса какому-либо условию
 */
public interface IFilter<T> {
    boolean permit(T value);
}

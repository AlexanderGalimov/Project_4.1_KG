package ru.cs.vsu.galimov.screen;

/**
 * Класс, описывающий координаты экранной точки.
 */
public class ScreenPoint {
    int i, j;

    public ScreenPoint(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
    
}

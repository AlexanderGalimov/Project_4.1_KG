package ru.cs.vsu.galimov.screen;

import java.util.Collection;

/**
 * Хранит составляющие экранных координат раздельно.
 */
public class ScreenCoordinates {
    private int[] xx, yy;
    /**
     * Создаёт экзмпляр на основе коллекции экранных точек
     * @param points точки
     */
    public ScreenCoordinates(Collection<ScreenPoint> points) {
        xx = new int[points.size()];
        yy = new int[points.size()];
        int i = 0;
        for (ScreenPoint p : points) {
            xx[i] = p.getI();
            yy[i] = p.getJ();
            i++;
        }
    }

    public int[] getXx() {
        return xx;
    }

    public int[] getYy() {
        return yy;
    }
    
    /**
     * Количество точек
     * @return количество точек
     */
    public int size() {
        return xx.length;
    }
}

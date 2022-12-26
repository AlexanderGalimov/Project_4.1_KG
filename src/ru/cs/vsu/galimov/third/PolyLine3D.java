package ru.cs.vsu.galimov.third;


import ru.cs.vsu.galimov.math.Vector3;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Полилиния в трёхмерном пространстве.
 * Описывает ломанную в трёхмерном пространстве по опорным точкам
 */
public class PolyLine3D {
    private List<Vector3> points;
    private boolean closed;
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }



    public Vector3 getNormal() {
        float x1 = points.get(0).getX();
        float y1 = points.get(0).getY();
        float z1 = points.get(0).getZ();
        float x2 = points.get(1).getX();
        float y2 = points.get(1).getY();
        float z2 = points.get(1).getZ();
        float x3 = points.get(2).getX();
        float y3 = points.get(2).getY();
        float z3 = points.get(2).getZ();
        float v1 = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
        float v2 = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
        float v3 = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        return (new Vector3(v1,v2, v3));
    }

    public double getCos(Vector3 z, Vector3 normal) {
        double cos = (z.getX()*normal.getX() + z.getY()*normal.getY() + z.getZ()*normal.getZ())/(sqrt(pow(z.getX(), 2) + pow(z.getY(), 2) + pow(z.getZ(), 2))*sqrt(pow(normal.getX(), 2) + pow(normal.getY(), 2) + pow(normal.getZ(), 2)));
        if (cos < 0) {
            return -cos;
        }
        return cos;
    }


    /**
     * Создаёт новую полилинию на основе трёхмерных точек.
     * @param points список точек-вершин ломанной
     * @param closed признак замкнутостит линии
     */
    public PolyLine3D(Collection<Vector3> points, boolean closed) {
        this.points = new LinkedList<Vector3>(points);
        this.closed = closed;
    }

    public PolyLine3D(List<Vector3> v, boolean closed) {
        this.points = v;
        this.closed = closed;
        this.color = Color.BLACK;
    }

    public PolyLine3D(Collection<Vector3> points, boolean closed, Color color){
        this.points = new LinkedList<>(points);
        this.closed = closed;
        this.color = color;
    }

    /**
     * Признак закрытости
     * @return возвращает истину, если линия замкнута, иначе - ложь.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Список вершин линии
     * @return возвращает список точек.
     */
    public List<Vector3> getPoints() {
        return points;
    }
    
    /**
     * Вычисляет среднее арифметическое по оси Z.
     * @return среднее по Z для полилинии.
     */
    public float avgZ() {
        if (points == null || points.size() == 0)
            return 0;
        float sum = 0;
        for (Vector3 v : points)
            sum += v.getZ();
        return sum / points.size();
    }
    
}

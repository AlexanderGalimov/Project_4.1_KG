package ru.cs.vsu.galimov.draw;

import ru.cs.vsu.galimov.math.Vector3;
import ru.cs.vsu.galimov.screen.ScreenConverter;
import ru.cs.vsu.galimov.screen.ScreenCoordinates;
import ru.cs.vsu.galimov.screen.ScreenPoint;
import ru.cs.vsu.galimov.third.PolyLine3D;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Реализация рисователя полигонов с помощью рёбер.
 */
public class SimpleEdgeDrawer extends ScreenGraphicsDrawer {

    public SimpleEdgeDrawer(ScreenConverter sc, Graphics2D g) {
        super(sc, g);
    }
    
    /**
     * Рисует одну полилинию на графиксе.
     * @param polyline полилиния
     */
    @Override
    protected void oneDraw(PolyLine3D polyline) {
        LinkedList<ScreenPoint> points = new LinkedList<>();
        /*переводим все точки в экранные*/
        for (Vector3 v : polyline.getPoints())
            points.add(getScreenConverter().r2s(v));
        getGraphics().setColor(Color.BLACK);
        /*если точек меньше двух, то рисуем отдельными алгоритмами*/
        if (points.size() < 2) {
            if (points.size() > 0)
                getGraphics().fillRect(points.get(0).getI(), points.get(0).getJ(), 1, 1);
            return;
        }
        /*создаём хранилище этих точек в виде двух массивов*/
        ScreenCoordinates crds = new ScreenCoordinates(points);
        /*если линия замкнута - рисуем полигон, иначе - полилинию*/
        /*if (polyline.isClosed())
            getGraphics().fillPolygon(crds.getXx(), crds.getYy(), crds.size());
        else
            getGraphics().drawPolyline(crds.getXx(), crds.getYy(), crds.size());*/
        Random random = new Random();
        //getGraphics().setColor(new Color(random.nextInt(255),  random.nextInt(255), random.nextInt(255)));
        getGraphics().setColor(Color.BLACK);
        if (polyline.isClosed()) {
            getGraphics().drawPolygon(crds.getXx(), crds.getYy(), crds.size());
            double cos = polyline.getCos(new Vector3(0, 0, 1), polyline.getNormal());
            System.out.println(cos);
            getGraphics().setColor(new Color((int) (130*(cos)), (int) (130*(cos)), (int) (130*(cos))));
            getGraphics().fillPolygon(crds.getXx(), crds.getYy(), crds.size());
            //getGraphics().drawPolygon(crds.getXx(), crds.getYy(), crds.size());//todo fill polygon
        }else
            getGraphics().drawPolyline(crds.getXx(), crds.getYy(), crds.size());//todo поменять приближение
    }

    /**
     * В данной реализации возвращаем фильтр, который одобряет все полилинии.
     * @return фильтр полилиний
     */
    @Override
    protected IFilter<PolyLine3D> getFilter() {
        return new IFilter<PolyLine3D>() {
            @Override
            public boolean permit(PolyLine3D line) {
                return true;
            }
        };
    }

    /**
     * Сравниваем полилинии по-среднему Z.
     * @return компаратор
     */
    @Override
    protected Comparator<PolyLine3D> getComparator() {
        return new Comparator<PolyLine3D>() {
            private static final float EPSILON = 1e-10f;
            @Override
            public int compare(PolyLine3D o1, PolyLine3D o2) {
                float d = o1.avgZ() - o2.avgZ();
                if (-EPSILON < d && d < EPSILON)
                    return 0;
                return d < 0 ? -1 : 1;
            }
        };
    }
    
}

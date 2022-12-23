package ru.cs.vsu.galimov.models;

import ru.cs.vsu.galimov.math.Vector3;
import ru.cs.vsu.galimov.third.IModel;
import ru.cs.vsu.galimov.third.PolyLine3D;

import java.util.*;

public class Bolt implements IModel {
    private final Vector3 center;
    private final double heightOfHat;
    private final double radiusOfHat;
    private final double radiusOfBase;
    private final double length;

    public Bolt(Vector3 center, double heightOfHat, double radius, double radiusOfBase, double length) {
        this.center = center;
        this.heightOfHat = heightOfHat;
        this.radiusOfHat = radius;
        this.radiusOfBase = radiusOfBase;
        this.length = length;
    }

    @Override
    public List<PolyLine3D> getLines() {
        // hat
        List<PolyLine3D> lines = new ArrayList<>();
        List<Vector3> upPointsHex = new ArrayList<>();
        List<Vector3> downPointsHex = new ArrayList<>();
        /*List<Vector3> down = new ArrayList<>();
        List<Vector3> up = new ArrayList<>();*/
        double currX;
        double currY;
        /*double prevX = 0;
        double prevY = 0;
        int koef = 10;*/

        for (int i = 0; i <= 6; i++) {
            double angle = Math.PI * i / 3;
            currX = Math.cos(angle) * radiusOfHat;
            currY = Math.sin(angle) * radiusOfHat;
            /*if(i == 6){
                currX = Math.cos(0) * radiusOfHat;
                currY = Math.sin(0) * radiusOfHat;
            }
            else{
                currX = Math.cos(angle) * radiusOfHat;
                currY = Math.sin(angle) * radiusOfHat;
            }*/
            /*if(i != 0){
                Vector3 currVectorUp1;
                Vector3 currVectorDown1;
                for (int j = koef; j > 0; j--) {
                    double v2x = (prevX + 1.0 / j * currX) / (1 + 1.0 / j);
                    double v2y = (prevY + 1.0 / j * currY) / (1 + 1.0 / j);
                    currVectorUp1 = new Vector3((float) v2x,(float)(v2y), center.getZ());
                    currVectorDown1 = new Vector3((float) v2x,(float)(v2y), center.getZ() - (float)(heightOfHat));
                    upPointsHex.add(currVectorUp1);
                    downPointsHex.add(currVectorDown1);
                }

                Vector3 currVectorUp;
                Vector3 currVectorDown;
                for (int j = 1; j <= koef; j+=1) {
                    double v1x = (prevX + j * currX) / (1 + j);
                    double v1y = (prevY + j * currY) / (1 + j);
                    currVectorUp = new Vector3((float) v1x,(float)(v1y), center.getZ());
                    currVectorDown = new Vector3((float) v1x,(float)(v1y), center.getZ() - (float)(heightOfHat));
                    upPointsHex.add(currVectorUp);
                    downPointsHex.add(currVectorDown);
                }
            }*/

            /*if(i != 0){
                Vector3 currVectorUp1;
                Vector3 currVectorDown1;
                double v2x = (prevX + (double) (koef) * currX) / (1 + (double) (koef));
                double v2y = (prevY + (double) (koef) * currY) / (1 + (double) (koef));
                double smX = currX - v2x;
                double smY = currY - v2y;
                double v2xPrv = prevX;
                double v2xprv = prevY;
                for (int j = 0; j <= koef; j++) {
                    currVectorUp1 = new Vector3((float) (v2xPrv),(float)(v2xprv), center.getZ());
                    currVectorDown1 = new Vector3((float) (v2xPrv),(float)(v2xprv), center.getZ() - (float)(heightOfHat));
                    v2xPrv += smX;
                    v2xprv += smY;
                    upPointsHex.add(currVectorUp1);
                    downPointsHex.add(currVectorDown1);
                }
            }*/

            upPointsHex.add(new Vector3(center.getX() + (float)(currX),(float)(currY),center.getZ() - (float)(heightOfHat)));
            downPointsHex.add(new Vector3(center.getX() + (float)(currX),center.getY() + (float)(currY), center.getZ()));

            /*up.add(new Vector3(center.getX() + (float)(currX),(float)(currY),center.getZ() - (float)(heightOfHat)));
            down.add(new Vector3(center.getX() + (float)(currX),center.getY() + (float)(currY), center.getZ()));*/

            /*prevX = currX;
            prevY = currY*/
        }

        makeRibsCircle(lines, upPointsHex, downPointsHex, false);

        lines.add(new PolyLine3D(upPointsHex,true));
        lines.add(new PolyLine3D(downPointsHex, true));

        Vector3 centerHigh = new Vector3(center.getX(), center.getY(), center.getZ() - (float)(heightOfHat));
        for (int i = 0; i < upPointsHex.size(); i++) {
            lines.add(new PolyLine3D(Arrays.asList(centerHigh,upPointsHex.get(i), new Vector3(0,0,0)),true));
            lines.add(new PolyLine3D(Arrays.asList(center,downPointsHex.get(i), new Vector3(0,0,0)),true));
        }

//        lines.add(new PolyLine3D(up,true));
//        lines.add(new PolyLine3D(down,true));

        //todo
        // base
        double x;
        double y;

        List<Vector3> currCircle;
        List<Vector3> prevCircle = new ArrayList<>();
        List<List<Vector3>> circles = new ArrayList<>();

        int k = 8;
        for (int i = 0; i <= k; i++) {
            currCircle = new ArrayList<>();
            for (double v = 0; v < 360; v+=10){
                x = radiusOfBase * Math.cos((v * Math.PI) / 180);
                y = radiusOfBase * Math.sin((v * Math.PI) / 180);
                currCircle.add(new Vector3(center.getX() + (float) (x),center.getY() + (float) (y), (float)(i * length / k)));
            }
            if(prevCircle.size() != 0){
                makeRibsCircle(lines, prevCircle, currCircle, false);
            }
            prevCircle = currCircle;
            circles.add(currCircle);
        }

        //
        /*List<Vector3> highLine = spiral(0.3f, 0.01f, 1f, 100 * (float) Math.PI, 0);
        List<Vector3> downLine = spiral(0.3f, 0.01f, 1f, 100 * (float) Math.PI, -0.1f);
        List<Vector3> middleLine = spiral(0.35f, 0.01f, 1f, 100 * (float) Math.PI, -0.05f);*/

        List<Vector3> highLine = spiral(0.3f, 0.01f, 1f, 90 * (float) Math.PI, 0.3f);
        List<Vector3> downLine = spiral(0.3f, 0.01f, 1f, 90 * (float) Math.PI, 0.2f);
        List<Vector3> middleLine = spiral(0.35f, 0.01f, 1f, 90 * (float) Math.PI, 0.25f);
        /*lines.add(new PolyLine3D(downLine,true));
        *//*lines.add(new PolyLine3D(middleLine,true));*//*
        lines.add(new PolyLine3D(highLine,true));*/

        makeRibsCircle(lines, middleLine, highLine, true);
        makeRibsCircle(lines, middleLine, downLine, true);
        makeRibsCircle(lines, highLine, downLine, true);

        solder(lines,circles.get(circles.size() - 1));
        return lines;
    }

    public List<Vector3> spiral(float a, float b, float from, float to, float centerZ) {
        List<Vector3> points = new ArrayList<>();
        for (float t = from; t <= to;t += 0.2){
            double x = a * Math.cos(t);
            double y = a * Math.sin(t);
            double z = b * t;
            points.add(new Vector3((float) x, (float) y, (float) z + centerZ));
        }
        return points;
    }

    private void solder(List<PolyLine3D> lines, List<Vector3> circle){
        Vector3 v = new Vector3(0,0, (float)(center.getZ() + length));
        for (int i = 0; i < circle.size(); i++) {
            if(i != circle.size() - 1){
                lines.add(new PolyLine3D(Arrays.asList(circle.get(i),circle.get(i+1),v),true));
            }
            else{
                lines.add(new PolyLine3D(Arrays.asList(circle.get(i),circle.get(0),v),true));
            }
        }
    }

    private void makeRibsCircle(List<PolyLine3D> lines, List<Vector3> upPointsHex, List<Vector3> downPointsHex, boolean flag) {
        List<Vector3> currRib1;
        List<Vector3> currRib2;
        for (int i = 0; i < upPointsHex.size(); i++) {
            currRib1 = new ArrayList<>();
            currRib2 = new ArrayList<>();
            if(flag && i == upPointsHex.size() - 1){
                break;
            }
            if(i != upPointsHex.size() - 1){
                currRib1.add(upPointsHex.get(i));
                currRib1.add(upPointsHex.get(i+1));
                currRib1.add(downPointsHex.get(i));

                currRib2.add(downPointsHex.get(i));
                currRib2.add(downPointsHex.get(i+1));
                currRib2.add(upPointsHex.get(i+1));
            }
            else {
                currRib1.add(upPointsHex.get(i));
                currRib1.add(upPointsHex.get(0));
                currRib1.add(downPointsHex.get(i));

                currRib2.add(downPointsHex.get(i));
                currRib2.add(downPointsHex.get(0));
                currRib2.add(upPointsHex.get(0));
            }

            lines.add(new PolyLine3D(currRib1,true));
            lines.add(new PolyLine3D(currRib2,true));
        }
    }
}

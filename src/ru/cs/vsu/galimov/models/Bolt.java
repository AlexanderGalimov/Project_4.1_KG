package ru.cs.vsu.galimov.models;

import ru.cs.vsu.galimov.math.Vector3;
import ru.cs.vsu.galimov.third.IModel;
import ru.cs.vsu.galimov.third.PolyLine3D;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Bolt implements IModel {
    private final Vector3 center;
    private double heightOfHat;
    private double radiusOfHat;
    private double radiusOfBase;
    private double length;
    private List<PolyLine3D> lines;

    public double getHeightOfHat() {
        return heightOfHat;
    }

    public double getRadiusOfHat() {
        return radiusOfHat;
    }

    public void setHeightOfHat(double heightOfHat) {
        this.heightOfHat = heightOfHat;
    }

    public void setRadiusOfHat(double radiusOfHat) {
        this.radiusOfHat = radiusOfHat;
    }

    public void setRadiusOfBase(double radiusOfBase) {
        this.radiusOfBase = radiusOfBase;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getRadiusOfBase() {
        return radiusOfBase;
    }

    public double getLength() {
        return length;
    }

    public Bolt(Vector3 center, double heightOfHat, double radius, double radiusOfBase, double length) {
        this.center = center;
        this.heightOfHat = heightOfHat;
        this.radiusOfHat = radius;
        this.radiusOfBase = radiusOfBase;
        this.length = length;
        logic();
    }

    public void logic(){
        // hat
        lines = new ArrayList<>();
        List<Vector3> upPointsHex = new ArrayList<>();
        List<Vector3> downPointsHex = new ArrayList<>();
        double currX;
        double currY;

        for (int i = 0; i <= 6; i++) {
            double angle = Math.PI * i / 3;
            currX = Math.cos(angle) * radiusOfHat;
            currY = Math.sin(angle) * radiusOfHat;

            upPointsHex.add(new Vector3(center.getX() + (float)(currX),(float)(currY),center.getZ() - (float)(heightOfHat)));
            downPointsHex.add(new Vector3(center.getX() + (float)(currX),center.getY() + (float)(currY), center.getZ()));
        }

        makeRibsCircle(lines, upPointsHex, downPointsHex, false);
        lines.add(new PolyLine3D(downPointsHex,true,randomColor()));
        lines.add(new PolyLine3D(upPointsHex,true,randomColor()));

        Vector3 centerHigh = new Vector3(center.getX(), center.getY(), center.getZ() - (float)(heightOfHat));
        for (int i = 0; i < upPointsHex.size(); i++) {
            lines.add(new PolyLine3D(Arrays.asList(centerHigh,upPointsHex.get(i), centerHigh),true,randomColor()));
            lines.add(new PolyLine3D(Arrays.asList(center,downPointsHex.get(i), center),true,randomColor()));
        }

        //todo
        // base
        double x;
        double y;

        List<Vector3> currCircle;
        List<Vector3> prevCircle = new ArrayList<>();
        List<List<Vector3>> circles = new ArrayList<>();

        int k = 100;
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

        /*List<Vector3> highLine = spiral(0.3f, 0.01f, 1f, 90 * (float) Math.PI, 0.3f);
        List<Vector3> downLine = spiral(0.3f, 0.01f, 1f, 90 * (float) Math.PI, 0.2f);
        List<Vector3> middleLine = spiral(0.35f, 0.01f, 1f, 90 * (float) Math.PI, 0.25f);*/

        List<Vector3> highLine = spiral((float)(radiusOfBase), 0.02f, 1f, 45 * (float) length, 0.3f);
        List<Vector3> downLine = spiral((float)(radiusOfBase), 0.02f, 1f, 45 * (float) length, 0.2f);
        List<Vector3> middleLine = spiral((float)(radiusOfBase) + 0.05f, 0.02f, 1f, 45 * (float) length, 0.25f);

        makeRibsCircle(lines, middleLine, highLine, true);
        makeRibsCircle(lines, middleLine, downLine, true);
        makeRibsCircle(lines, highLine, downLine, true);

        solder(lines,circles.get(circles.size() - 1));
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }

    public Color randomColor(){
        Random random = new Random();
        return new Color(random.nextInt(255),  random.nextInt(255), random.nextInt(255));
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
                lines.add(new PolyLine3D(Arrays.asList(circle.get(i),circle.get(i+1),v),true,randomColor()));
            }
            else{
                lines.add(new PolyLine3D(Arrays.asList(circle.get(i),circle.get(0),v),true,randomColor()));
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
            //if(i != upPointsHex.size() - 1){
                currRib1.add(upPointsHex.get(i));
                currRib1.add(upPointsHex.get((i+1)% upPointsHex.size()));
                currRib1.add(downPointsHex.get(i));

                currRib2.add(upPointsHex.get((i+1)%upPointsHex.size()));
                currRib2.add(downPointsHex.get((i+1)%downPointsHex.size()));
                currRib2.add(downPointsHex.get(i));
            /*}
            else {
                currRib1.add(upPointsHex.get(i));
                currRib1.add(upPointsHex.get(0));
                currRib1.add(downPointsHex.get(i));

                currRib2.add(downPointsHex.get(i));
                currRib2.add(downPointsHex.get(0));
                currRib2.add(upPointsHex.get(0));
            }*/

            lines.add(new PolyLine3D(currRib1,true,randomColor()));
            lines.add(new PolyLine3D(currRib2,true,randomColor()));
        }
    }
}

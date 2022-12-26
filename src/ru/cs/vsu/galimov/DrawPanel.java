package ru.cs.vsu.galimov;


import ru.cs.vsu.galimov.draw.IDrawer;
import ru.cs.vsu.galimov.draw.SimpleEdgeDrawer;
import ru.cs.vsu.galimov.math.Vector3;
import ru.cs.vsu.galimov.models.Bolt;
import ru.cs.vsu.galimov.screen.ScreenConverter;
import ru.cs.vsu.galimov.third.Camera;
import ru.cs.vsu.galimov.third.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;
    private Bolt bolt;

    public DrawPanel() {
        super();
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();

        /*scene.getModelsList().add(new Parallelepiped(
                new Vector3(-0.4f, -0.4f, -0.4f),
                new Vector3(0.4f, 0.4f, 0.4f)
        ));*/
        bolt = new Bolt(new Vector3(0,0,0), 0.5,1, 0.3, Math.PI);
        //bolt = new Bolt(new Vector3(0,0,0), 0.5,0.5, 0.1, Math.PI);
        scene.getModelsList().add(bolt);
        /*scene.getModelsList().add(
                new HelixLine(0.3f, 0.01f, 0, 100 * (float) Math.PI, 0));
        scene.getModelsList().add(
                new HelixLine(0.3f, 0.01f, 1f, 100 * (float) Math.PI, -0.1f));
        scene.getModelsList().add(
                new HelixLine(0.35f, 0.01f, 1f, 100 * (float) Math.PI, -0.05f));*/

        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
    }

    public void changeParamsOfBolt(float f1,float f2, float f3, float f4){
        bolt.setHeightOfHat(f1);
        bolt.setRadiusOfHat(f2);
        bolt.setRadiusOfBase(f3);
        bolt.setLength(f4);
        bolt.logic();
        repaint();
    }



    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}

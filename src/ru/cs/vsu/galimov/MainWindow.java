package ru.cs.vsu.galimov;

import ru.cs.vsu.galimov.math.Vector3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
    private DrawPanel panelMain = new DrawPanel();
    private JButton button;
    private JTextField  centerField;
    private JTextField heightOfHatField;
    private JTextField radiusOfHatField;
    private JTextField radiusOfBaseField;
    private JTextField lengthField;
    private JLabel heightOfHatLabel;
    private JLabel radiusOfHatLabel;
    private JLabel radiusOfBaseLabel;
    private JLabel lengthLabel;

    public MainWindow(){
        this.setTitle("Pult");

        Container formElementsContainer = getContentPane();
        formElementsContainer.setLayout(null);

        button = new JButton();
        button.setText("Решение системы с помощью обратной матрицы");
        button.setSize(100, 50);
        button.setLocation(0, 300);
        button.addActionListener(this);
        add(button);

        heightOfHatLabel = new JLabel("heightOfHat: ");
        heightOfHatLabel.setLabelFor(heightOfHatField);
        heightOfHatLabel.setSize(80, 11);
        heightOfHatLabel.setLocation(0, 80);
        add(heightOfHatLabel);

        heightOfHatField = new JTextField(1);
        heightOfHatField.setSize(50, 50);
        heightOfHatField.setLocation(80, 60);
        formElementsContainer.add(heightOfHatField);

        radiusOfHatLabel = new JLabel("hat radius: ");
        radiusOfHatLabel.setLabelFor(radiusOfHatField);
        radiusOfHatLabel.setSize(80, 11);
        radiusOfHatLabel.setLocation(0, 140);
        add(radiusOfHatLabel);

        radiusOfHatField = new JTextField(1);
        radiusOfHatField.setSize(50, 50);
        radiusOfHatField.setLocation(80, 120);
        formElementsContainer.add(radiusOfHatField);

        radiusOfBaseLabel = new JLabel("base radius: ");
        radiusOfBaseLabel.setLabelFor(radiusOfBaseField);
        radiusOfBaseLabel.setSize(80, 11);
        radiusOfBaseLabel.setLocation(0, 190);
        add(radiusOfBaseLabel);

        radiusOfBaseField = new JTextField(1);
        radiusOfBaseField.setSize(50, 50);
        radiusOfBaseField.setLocation(80, 180);
        formElementsContainer.add(radiusOfBaseField);

        lengthLabel = new JLabel("length: ");
        lengthLabel.setLabelFor(lengthField);
        lengthLabel.setSize(80, 11);
        lengthLabel.setLocation(0, 250);
        add(lengthLabel);

        lengthField = new JTextField(1);
        lengthField.setSize(50, 50);
        lengthField.setLocation(80, 240);
        formElementsContainer.add(lengthField);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    public MainWindow(boolean flag){

        this.setTitle("MainDemo");
        this.add(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }

    public void setNewParams(){
        float heightOfHat = (float) Double.parseDouble(heightOfHatField.getText());
        float radiusOfHat = (float) Double.parseDouble(radiusOfHatField.getText());
        float radiusOfBase = (float) Double.parseDouble(radiusOfBaseField.getText());
        float length = (float) Double.parseDouble(lengthField.getText());
        panelMain.changeParamsOfBolt(heightOfHat, radiusOfHat, radiusOfBase, length);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            setNewParams();
            this.repaint();
        }
    }
}

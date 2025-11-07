package Engine.Window;

import Engine.Camera;
import Engine.Inputs.Inputs;
import Engine.Math.ComplexCalculator;
import Engine.Object3D.Object3D;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final int width, height;
    private final Panel panel;

    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        panel = new Panel(width, height);
        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle(title);
    }

    public void setInputListener(Inputs inputs){
        this.addKeyListener(inputs);
        this.addMouseListener(inputs);
        this.addMouseMotionListener(inputs);
        this.addMouseWheelListener(inputs);
    }

    public void setVisible(boolean bool){
        super.setVisible(bool);
    }

    public void drawObject3D(Object3D object3D){
        int[][][] triangles2D = object3D.getTrianglesScreenPositions();

        //add the color of the Object
        Color[] colors = new Color[triangles2D.length];
        for (int i = 0; i < triangles2D.length; i++) {
            colors[i] = object3D.getColor();
        }

        drawTriangles(triangles2D, object3D.getCulledTrianglesRelativToCamera(), colors);
    }

    public void repaint(){
        panel.repaint();
    }

    public void drawTriangles(int[][][] triangles2D,float[][][] triangles3D, Color[] colors){
        panel.addTriangles(triangles2D, triangles3D, colors);
    }

    public void clear(){
        panel.clear();
    }

    public void setBackgroundColor(Color color){
        panel.setBackground(color);
    }

}

package Engine.Window;

import Engine.Math.ComplexCalculator;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private final int width;
    private final int height;

    private int[][][] triangles = new int[0][][];
    private float[][][] triangles3D = new float[0][][];
    private Color[] triangleColors = new Color[0];

    public Panel(int width, int height){
        this.width = width;
        this.height = height;

        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(width, height));
        this.setVisible(true);
        this.setLayout(null);
    }


    //triangles[index][0 = x values, 1 = y values][value index]
    public void addTriangles(int[][][] triangles2D, float[][][] triangles3D, Color[] colors){
        //transform new triangles
        for(int[][] triangle : triangles2D){
            for(int i = 0; i < 3 ; i++){
                triangle[0][i] = triangle[0][i] + width/2; //0,0 is at the center of the screen
                triangle[1][i] = height - triangle[1][i] - height/2; //makes y go up instead of down
            }
        }

        float[][][] newTriangles3D = new float[this.triangles3D.length + triangles3D.length][][];
        System.arraycopy(this.triangles3D, 0, newTriangles3D, 0, this.triangles3D.length);
        System.arraycopy(triangles3D, 0, newTriangles3D, this.triangles3D.length, triangles3D.length);

        //append triangles to triangle array
        int[][][] newTriangles = new int[this.triangles.length + triangles2D.length][][];
        System.arraycopy(this.triangles, 0, newTriangles, 0, this.triangles.length);
        System.arraycopy(triangles2D, 0, newTriangles, this.triangles.length, triangles2D.length);

        //append colors to color-array
        Color[] newColors = new Color[this.triangleColors.length + colors.length];
        System.arraycopy(triangleColors, 0, newColors, 0, this.triangleColors.length);
        System.arraycopy(colors, 0, newColors, this.triangleColors.length, colors.length);

        //set new array to the old one to save it
        this.triangles = newTriangles;
        this.triangles3D = newTriangles3D;
        this.triangleColors = newColors;

        sortTrianglesZ();
    }

    private void sortTrianglesZ(){
        if(triangles3D.length != triangles.length)
            throw new RuntimeException("3D Dreiecke sind nicht so viele wie 2D Dreiecke. 3D: " + triangles3D.length + " 2D: " + triangles.length);

        float[] averagePositions = new float[triangles3D.length];
        float[][][] newTriangles3D = triangles3D.clone();
        int[][][] newTriangles2D = triangles.clone();
        Color[] newColors = triangleColors.clone();


        for (int i = 0; i < averagePositions.length; i++) {
            //float[] averageTrianglePosition = ComplexCalculator.calculateAveragePositionOfTriangle(newTriangles3D[i]);
            averagePositions[i] = ComplexCalculator.getMax(
                    new float[]{
                            ComplexCalculator.calculateDistance3D(new float[]{triangles3D[i][0][0], triangles3D[i][1][0], triangles3D[i][2][0]}, new float[]{0,0,0}),
                            ComplexCalculator.calculateDistance3D(new float[]{triangles3D[i][0][1], triangles3D[i][1][1], triangles3D[i][2][1]}, new float[]{0,0,0}),
                            ComplexCalculator.calculateDistance3D(new float[]{triangles3D[i][0][2], triangles3D[i][1][2], triangles3D[i][2][2]}, new float[]{0,0,0})
            });
        }


        //insert sort
        for(int i = 1; i < averagePositions.length; i++){
            if(averagePositions[i] < averagePositions[i-1]){
                for(int j = i; j > 0; j--){
                    if(averagePositions[j] < averagePositions[j-1]){
                        //switch j and j-1

                        //averagePositions switch
                        float temp = averagePositions[j];
                        averagePositions[j] = averagePositions[j-1];
                        averagePositions[j-1] = temp;

                        //triangles3D
                        float[][] tempTriangle3D = newTriangles3D[j];
                        newTriangles3D[j] = newTriangles3D[j-1];
                        newTriangles3D[j-1] = tempTriangle3D;

                        //triangles2D
                        int[][] tempTriangle2D = newTriangles2D[j];
                        newTriangles2D[j] = newTriangles2D[j-1];
                        newTriangles2D[j-1] = tempTriangle2D;

                        //colors
                        Color tempColor = newColors[j];
                        newColors[j] = newColors[j-1];
                        newColors[j-1] = tempColor;
                    }
                }
            }
        }

       // ComplexCalculator.printVector(averagePositions);


        this.triangles3D = newTriangles3D.clone();
        this.triangles = newTriangles2D.clone();
        this.triangleColors = newColors.clone();
    }


    public void clear(){
        triangles = new int[0][][];
        triangleColors = new Color[0];
        triangles3D = new float[0][][];
    }



    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        g2d.setColor(this.getBackground());
        g2d.fillRect(0, 0, width, height);

        //clone triangles and colors before starting, because clearing the array is possible during the loop
        int[][][] triangles = this.triangles.clone();
        Color[] triangleColors = this.triangleColors.clone();

        //drawing the triangles
        for (int i = triangles.length-1; i >= 0; i--) {

            int[] xPos = triangles[i][0];
            int[] yPos = triangles[i][1];



            g2d.setColor(triangleColors[i]);
            g2d.fillPolygon(xPos, yPos, 3);
            g2d.setColor(Color.BLACK);
            g2d.drawPolygon(xPos, yPos, 3);
        }
    }
}

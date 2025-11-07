package Engine.Object3D;

import Engine.Camera;
import Engine.Math.ComplexCalculator;
import Engine.Math.Transformer;

import java.awt.*;

public abstract class Object3D {
    /*updating order
    * 1. vertices relativ to object
    * 1.1 set edges
    * 2. vertices relativ to origin
    * 3. vertices relativ to camera
    * 4. triangles relativ to camera
    * 5. cut edges with bfc
    * 6. vertices relativ to screen position
    * 7. triangles relativ to screen position
    */


    protected float[] position;

    //vertices[index][x,y,z]
    protected float[][] verticesRelativToObject;
    protected float[][] verticesRelativToOrigin;
    protected float[][] verticesRelativToCamera;
    protected float[][] verticesScreenPositions;

    //triangles[index][x,y,z][vertexNumber]
    protected float[][][] trianglesRelativToCamera; //without cut faces
    protected float[][][] culledTrianglesRelativToCamera;
    protected int[][][] trianglesScreenPositions;

    //edges[index][vertex1, vertex2, vertex3]
    protected int[][] edges;
    protected int[][] cutEdges;

    protected Transformer transformer = new Transformer();
    protected Color color = Color.white;
    protected Camera camera;

    public Object3D(){

    }

    public void setPosition(float[] position){
        this.position = position;
        setVerticesRelativToOrigin();
    }


    public void move(float[] delta){
        verticesRelativToOrigin = transformer.translateVertices(verticesRelativToOrigin, delta);
        setVerticesRelativToCamera();
    }

    public void rotateX(float deltaDegrees){
        verticesRelativToObject = transformer.rotateVerticesX(verticesRelativToObject, deltaDegrees);
        setVerticesRelativToOrigin();
    }

    public void rotateY(float deltaDegrees){
        verticesRelativToObject = transformer.rotateVerticesY(verticesRelativToObject, deltaDegrees);
        setVerticesRelativToOrigin();
    }

    public void rotateZ(float deltaDegrees){
        verticesRelativToObject = transformer.rotateVerticesZ(verticesRelativToObject, deltaDegrees);
        setVerticesRelativToOrigin();
    }

    public float[][] getVerticesRelativToCamera() {
        return verticesRelativToCamera;
    }

    public float[][] getVerticesRelativToObject() {
        return verticesRelativToObject;
    }

    public float[][] getVerticesRelativToOrigin() {
        return verticesRelativToOrigin;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public float[][][] getTrianglesRelativToCamera() {
        return trianglesRelativToCamera;
    }

    public float[][] getVerticesScreenPositions() {
        //update function just in case
        setVerticesRelativToCamera();
        return verticesScreenPositions;
    }

    public int[][] getEdges() {
        return edges;
    }

    protected void scaleVerticesRelativToObject(float scale){
        verticesRelativToObject = transformer.scaleVertices(verticesRelativToObject, scale);
    }

    //this function updates the vertices relativ to the origin
    //if you change the vertices relativ to the object you have to call this function
    protected void setVerticesRelativToOrigin(){
        verticesRelativToOrigin = transformer.translateVertices(verticesRelativToObject, position);
        setVerticesRelativToCamera();
    }

    protected void setVerticesRelativToCamera(){
        verticesRelativToCamera = transformer.translateVerticesToCamera(verticesRelativToOrigin, camera);
        verticesRelativToCamera = transformer.rotateVerticesToCamera(verticesRelativToCamera, camera);
        setTrianglesRelativToCamera();
    }

    protected void setTrianglesRelativToCamera(){
        //triangles[index][x,y,z][vertexNumber]
        float[][][] triangles = new float[edges.length][3][3];
        for (int i = 0; i < edges.length; i++) {
            triangles[i][0] = new float[]{verticesRelativToCamera[edges[i][0]][0], verticesRelativToCamera[edges[i][1]][0], verticesRelativToCamera[edges[i][2]][0]};
            triangles[i][1] = new float[]{verticesRelativToCamera[edges[i][0]][1], verticesRelativToCamera[edges[i][1]][1], verticesRelativToCamera[edges[i][2]][1]};
            triangles[i][2] = new float[]{verticesRelativToCamera[edges[i][0]][2], verticesRelativToCamera[edges[i][1]][2], verticesRelativToCamera[edges[i][2]][2]};
        }
        trianglesRelativToCamera = triangles;
        setCutEdges();
    }

    protected void setVerticesScreenPositions(){
        verticesScreenPositions = transformer.transformToScreenCoordinates(verticesRelativToCamera, camera);
    }

    protected void setCutEdges(){
        int[] cutFaces = transformer.cutFaces(trianglesRelativToCamera);
        int[][] newEdges = new int[edges.length - cutFaces.length][];

        int index = 0;
        for (int i = 0; i < edges.length; i++) {
            if(!ComplexCalculator.contains(cutFaces, i)){
                newEdges[index] = edges[i];
                index++;
            }
        }

        cutEdges = newEdges;
        setVerticesScreenPositions();
    }

    protected void setTrianglesScreenPositions(){
        setVerticesRelativToCamera();

        //store the triangles
        int[][][] triangles = new int[cutEdges.length][2][3];

        //store the triangles
        for (int i = 0; i < cutEdges.length; i++) {
            //x positions
            triangles[i][0] = new int[]{
                    (int) verticesScreenPositions[cutEdges[i][0]][0],
                    (int) verticesScreenPositions[cutEdges[i][1]][0],
                    (int) verticesScreenPositions[cutEdges[i][2]][0],
            };

            //y positions
            triangles[i][1] = new int[]{
                    (int) verticesScreenPositions[cutEdges[i][0]][1],
                    (int) verticesScreenPositions[cutEdges[i][1]][1],
                    (int) verticesScreenPositions[cutEdges[i][2]][1],
            };
        }
        trianglesScreenPositions = triangles;
    }

    public float[][][] getCulledTrianglesRelativToCamera() {
        culledTrianglesRelativToCamera = new float[cutEdges.length][3][2];

        for (int i = 0; i < culledTrianglesRelativToCamera.length; i++) {
            //x positions
            culledTrianglesRelativToCamera[i][0] = new float[]{
                    verticesRelativToCamera[cutEdges[i][0]][0],
                    verticesRelativToCamera[cutEdges[i][1]][0],
                    verticesRelativToCamera[cutEdges[i][2]][0],
            };

            //y positions
            culledTrianglesRelativToCamera[i][1] = new float[]{
                    verticesRelativToCamera[cutEdges[i][0]][1],
                    verticesRelativToCamera[cutEdges[i][1]][1],
                    verticesRelativToCamera[cutEdges[i][2]][1],
            };

            //z positions
            culledTrianglesRelativToCamera[i][2] = new float[]{
                    verticesRelativToCamera[cutEdges[i][0]][2],
                    verticesRelativToCamera[cutEdges[i][1]][2],
                    verticesRelativToCamera[cutEdges[i][2]][2],
            };
        }

        return culledTrianglesRelativToCamera;
    }

    public int[][][] getTrianglesScreenPositions() {
        setTrianglesScreenPositions();
        return trianglesScreenPositions;
    }

    public int[][] getCutEdges() {
        return cutEdges;
    }
}

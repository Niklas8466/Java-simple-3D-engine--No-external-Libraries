package Engine.Object3D;

import Engine.Camera;

public class Sphere extends Object3D{
    private int horizontalRings;
    private int verticalRings;

    public Sphere(float[] pos, int horizontalRings, int verticalRings, float size, Camera camera){
        this.position = pos;
        this.horizontalRings = horizontalRings;
        this.verticalRings = verticalRings;
        this.camera = camera;

        //Create object relativ to itself
        setVerticesRelativToObject();
        setEdges();
        scaleVerticesRelativToObject(size);

        //start updating chain to fill out other positions
        setVerticesRelativToOrigin();
    }

    //SRT
    //SCALE => ROTATE => TRANSPOSE
    private void setVerticesRelativToObject(){
        // +2 für Nord- und Südpol
        int vertexCount = (horizontalRings - 2) * verticalRings + 2;
        verticesRelativToObject = new float[vertexCount][3];

        // Pole
        verticesRelativToObject[0] = new float[]{0f, 0.5f, 0f};               // Nordpol
        verticesRelativToObject[vertexCount - 1] = new float[]{0f, -0.5f, 0f}; // Südpol

        int index = 1;
        for (int i = 1; i < horizontalRings - 1; i++) {
            float theta = (float) (Math.PI * i / (horizontalRings - 1)); // von 0..π
            float y = (float) (0.5f * Math.cos(theta));            // Höhe
            float r = (float) (0.5f * Math.sin(theta));            // Radius des Rings

            for (int j = 0; j < verticalRings; j++) {
                float phi = (float) (2 * Math.PI * j / verticalRings);
                float x = (float) (r * Math.cos(phi));
                float z = (float) (r * Math.sin(phi));
                verticesRelativToObject[index++] = new float[]{x, y, z};
            }
        }
    }

    private void setEdges() {
        int triangleCount = verticalRings                    // obere Dreiecke
                + (horizontalRings - 3) * verticalRings * 2  // mittlere Bänder
                + verticalRings;                    // untere Dreiecke
        edges = new int[triangleCount][3];

        int index = 0;

        // Obere Dreiecke
        for (int j = 0; j < verticalRings; j++) {
            int next = (j + 1) % verticalRings;
            edges[index++] = new int[]{1 + next, 0, 1+ j};
        }

        // Mittlere Bänder
        for (int i = 0; i < horizontalRings - 3; i++) {
            int startCurrent = 1 + i * verticalRings;
            int startNext = startCurrent + verticalRings;

            for (int j = 0; j < verticalRings; j++) {
                int next = (j + 1) % verticalRings;
                int a = startCurrent + j;
                int b = startCurrent + next;
                int c = startNext + j;
                int d = startNext + next;

                edges[index++] = new int[]{c, b, a};
                edges[index++] = new int[]{c, d, b};
            }
        }

        // Untere Dreiecke
        int southStart = 1 + (horizontalRings - 3) * verticalRings;
        int southPole = verticesRelativToObject.length - 1;
        for (int j = 0; j < verticalRings; j++) {
            int next = (j + 1) % verticalRings;
            edges[index++] = new int[]{southStart + j, southPole, southStart + next};
        }
    }
}

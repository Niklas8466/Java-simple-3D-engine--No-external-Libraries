package Engine;

import Engine.Math.ComplexCalculator;
import Engine.Math.Transformer;

public class Camera {
    private float[] position;
    private float[] rotation;
    private float FOV;
    private float focalLength;

    public Camera(float[] pos, float FOV, int screenHeight){
        this.position = pos;
        this.FOV = FOV;
        this.focalLength = (float) (screenHeight /(2f * Math.tan(Math.toRadians(FOV)/2)));
        this.rotation = new float[3];
    }

    public void moveRelativToCamera(float angleXZPlane, float speed){
        //start with a direction of 1 for each direction (unit circle) later scale                                        //ignore z rotation
        float[] rotationInRadians = new float[]{(float) Math.toRadians(rotation[0]), (float) Math.toRadians(rotation[1]), (float) Math.toRadians(rotation[2])};

        //y stays 1 to make it independent of cameras position and x and y never lead to going up
        float[] unitVectorForwards = getForwardVector();
        float[] scaledVectorForwards = ComplexCalculator.scalarMultiplication(unitVectorForwards, speed);
        float[] deltaRelativToOrigin = ComplexCalculator.rotatePointY(scaledVectorForwards, angleXZPlane);
        move(deltaRelativToOrigin);
    }

    public void move(float[] delta){
        position = ComplexCalculator.vectorAddition(position, delta);
    }

    public void rotateX(float angleDegree){
        rotation[0] += angleDegree;
        rotation[0] = ComplexCalculator.clampToRange(rotation[0], -90, 90);
    }

    public void rotateY(float angleDegree){
        rotation[1] += angleDegree;

    }

    public float[] getPosition(){
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public float[] getRotation() {
        return rotation;
    }

    public void setRotation(float[] rotation) {
        this.rotation = rotation;
    }

    public float getFocalLength(){
        return focalLength;
    }

    public float[] getNormalVector(){
        float[] forwardVector = new float[]{0f, 0f, 1f};
        float[] normalVector = ComplexCalculator.rotatePointX(forwardVector, rotation[0]);
        normalVector = ComplexCalculator.rotatePointY(normalVector, rotation[1]);
        normalVector = ComplexCalculator.rotatePointZ(normalVector, rotation[2]);
        return normalVector;
    }

    private float[] getForwardVector(){
        float[] rotationInRadians = new float[]{(float) Math.toRadians(rotation[0]), (float) Math.toRadians(rotation[1]), (float) Math.toRadians(rotation[2])};

        //y stays 1 to make it independent of cameras position and x and y never lead to going up
        float[] unitVectorForwards = new float[]{(float) Math.sin(rotationInRadians[1]), 0f, (float) Math.cos(rotationInRadians[1])};
        return unitVectorForwards;
    }


}
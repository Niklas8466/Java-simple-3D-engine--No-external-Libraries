package Engine;

import Engine.Inputs.Inputs;
import Engine.Math.ComplexCalculator;
import Engine.Math.Transformer;
import Engine.Object3D.Cube;
import Engine.Object3D.Model3D;
import Engine.Object3D.Pyramide;
import Engine.Object3D.Sphere;
import Engine.Window.Window;

import java.awt.*;

public class Engine {
    private final Camera camera;
    private final Transformer transformer;
    private final Window window;
    private final Inputs inputs;

    private int refreshRatePerSecond = 40; //1000 is max
    private int currentFPS = 0;
    private int screenHeight = 1000;
    private int screenWidth = 1900;


    //testing
    Cube[][] chessboard;
    Cube cube;
    Sphere sphere;
    Pyramide pyramide;

    Model3D rook;
    Model3D queen;
    Model3D duck;
    Model3D pikachu;
    Model3D dinosaur;
    Model3D bones;


    public Engine(){
        this.camera = new Camera(new float[]{0f, 100f, 0f}, 45, screenHeight);
        this.transformer = new Transformer();
        this.window = new Window(screenWidth, screenHeight, "3D Engine");
        this.inputs = new Inputs();
        window.setInputListener(inputs);
    }

    public void run(){
        innit(); //execute innit function once

        //run next frame if enough time has passed (fps cap)
        long lastTime = System.currentTimeMillis();
        while (true){
            long newTime = System.currentTimeMillis();
            if(newTime-lastTime >= 1000/refreshRatePerSecond){
                currentFPS = (int) (1000/(newTime-lastTime));
                lastTime = System.currentTimeMillis();
                update();
            }

        }
    }

    public void innit(){
        window.setVisible(true);
        cube = new Cube(new float[]{0, 0f, 200f}, 100, camera);
        sphere = new Sphere(new float[]{-150, 0f, 200f},20, 20, 100, camera);
        pyramide = new Pyramide(new float[]{150, 0f, 200f}, 100, camera);


        rook = new Model3D("3DModels/chess pieces finished.obj", "rook", new float[]{100, 0f, 300f}, 100, camera);
        rook.setColor(Color.lightGray);

        queen = new Model3D("3DModels/chess pieces finished.obj", "queen", new float[]{-50, 0f, 300f}, 100, camera);
        queen.setColor(new Color(74, 31, 0));

        duck = new Model3D("3DModels/RubberDuck.obj", "duck", new float[]{125, 70f, 200f}, 10, camera);
        duck.setColor(Color.YELLOW);

        pikachu = new Model3D("3DModels/pikachu.obj", "pikachu", new float[]{0, 70f, 0}, 100, camera);
        pikachu.setColor(Color.YELLOW);

        dinosaur =  new Model3D("3DModels/dinosaurs_02.obj", "Dinosaur", new float[]{0, 70f, 0}, 1, camera);
        dinosaur.setColor(new Color(19, 87, 1));

        bones =  new Model3D("3DModels/PileBones.obj", "Pile_of_Bones", new float[]{0, 70f, 0}, 10, camera);
        bones.setColor(Color.lightGray);

        sphere.setColor(Color.RED);
        pyramide.setColor(Color.WHITE);
        cube.setColor(Color.YELLOW);

        window.setBackgroundColor(new Color(50, 50, 50));
    }

    public void update(){
        window.clear();
        System.out.println("FPS: " + getCurrentFPS());

        //Scene
        testInputs();
        window.drawObject3D(rook);
        window.drawObject3D(queen);
        window.drawObject3D(cube);
        window.drawObject3D(pyramide);
        window.drawObject3D(sphere);
        window.repaint();
    }

    private void testInputs(){
        float speed = 1.5f;
        float rotationSpeed = 0.8f;
        if(inputs.isPressed('w'))
            camera.moveRelativToCamera(0, speed);
        if(inputs.isPressed('s'))
            camera.moveRelativToCamera(180, speed);
        if(inputs.isPressed('d'))
            camera.moveRelativToCamera(90, speed);
        if(inputs.isPressed('a'))
            camera.moveRelativToCamera(-90, speed);
        if(inputs.isPressed('q'))
            camera.rotateY(-rotationSpeed);
        if(inputs.isPressed('e'))
            camera.rotateY(rotationSpeed);
        if(inputs.isPressed('v'))
            camera.rotateX(-rotationSpeed);
        if(inputs.isPressed('b'))
            camera.rotateX(rotationSpeed);
        if(inputs.isPressed(' '))
            camera.move(new float[]{0f, speed, 0f});
        if(inputs.isPressed('c'))
            camera.move(new float[]{0f, -speed, 0f});
    }

    public int getCurrentFPS(){
        return currentFPS;
    }

    public void setRefreshRatePerSecond(int refreshRatePerSecond){
        this.refreshRatePerSecond = refreshRatePerSecond;
    }
}

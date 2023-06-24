import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class DummyTest {
    private Window window = new Window(800, 800, "Dummy Main");
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());

    ArrayList<Object> importX = new ArrayList<Object>();
    List<ShaderProgram.ShaderModuleData> shaderModuleDataList = Arrays.asList(
            new ShaderProgram.ShaderModuleData(
                    "D:\\Chrome Download\\Grafkom Julix\\Grafkom Julix\\Project Grafkom-22\\Main\\resources\\shaders\\scene.vert", GL_VERTEX_SHADER),
            new ShaderProgram.ShaderModuleData(
                    "D:\\Chrome Download\\Grafkom Julix\\Grafkom Julix\\Project Grafkom-22\\Main\\resources\\shaders\\scene.frag", GL_FRAGMENT_SHADER)
    );
    Model m = null;
    Model m1 = null;
    Model m2 = null;
    public void run() {
        init();
        loop();
        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
        glDepthRange(0.0f, 1.0f);

        camera.setPosition(-0.12f, 4.5799975f, 22.460037f);
        camera.setRotation((float) Math.toRadians(0.0f), (float) Math.toRadians(0.0f));

        try{
            m = ObjLoader.loadModel(new File("D:\\Chrome Download\\Grafkom Julix\\Grafkom Julix\\Project Grafkom-22\\Main\\src\\blenderAssets\\FinalBaseMesh.obj"));
            m1 = ObjLoader.loadModel(new File("D:\\Chrome Download\\Grafkom Julix\\Grafkom Julix\\Project Grafkom-22\\Main\\src\\blenderAssets\\street.obj"));
            m2 = ObjLoader.loadModel(new File("D:\\Chrome Download\\Grafkom Julix\\Grafkom Julix\\Project Grafkom-22\\Main\\src\\blenderAssets\\GolfBall.obj"));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        importX.add(new Sphere(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new Vector4f(1.0f, 0.0f, 1.0f, 1.0f),
                0.0,
                new ArrayList<>(List.of(0f, 0f, 0f)),
                4.0f,
                0.1f,
                8.0f,
                15,
                30,
                m));
        importX.get(0).scaleObject(0.5f, 0.5f, 0.5f);

        importX.add(new Sphere(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                0.0,
                new ArrayList<>(List.of(0f, 0f, 0f)),
                4.0f,
                0.1f,
                8.0f,
                15,
                30,
                m1));

        importX.add(new Sphere(
                shaderModuleDataList,
                new ArrayList<>(
                ),
                new Vector4f(1.0f, 1.0f, 0.0f, 1.0f),
                0.0,
                new ArrayList<>(List.of(0f, 0f, 0f)),
                4.0f,
                0.1f,
                8.0f,
                15,
                30,
                m2));
        importX.get(2).translateObject(0.0f, 5f, 1.0f);
    }

    public void input() {

        if (window.isKeyPressed(GLFW_KEY_LEFT_ALT)) {
            camera.moveForward(0.12f);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveBackwards(0.12f);
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveDown(0.12f);
        }

        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveUp(0.12f);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.moveLeft(0.12f);
        }

        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.moveRight(0.12f);
        }

        if (window.getMouseInput().isLeftButtonPressed()){
            Vector2f displayVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float)Math.toRadians(displayVec.x * 0.1f), (float) Math.toRadians(displayVec.y * 0.1f));
        }

        if (window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()-(window.getMouseInput().getScroll().y*0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }

        if(window.isKeyPressed(GLFW_KEY_Z)){
            camera.rotateObjectCamera(0f,(float)(Math.toRadians(1)));
        }
        if(window.isKeyPressed(GLFW_KEY_X)){
            camera.rotateObjectCamera(0f,(float)(Math.toRadians(-1)));
        }
        if(window.isKeyPressed(GLFW_KEY_C)){
            camera.rotateObjectCamera((float)(Math.toRadians(1)),0);
        }
        if(window.isKeyPressed(GLFW_KEY_V)){
            camera.rotateObjectCamera((float)(Math.toRadians(-1)),0);
        }
        if(window.isKeyPressed(GLFW_KEY_COMMA))
        {
            camera.addRotation((float) Math.toRadians(0f), (float) Math.toRadians(-1f));
        }
        if(window.isKeyPressed(GLFW_KEY_SLASH))
        {
            camera.addRotation((float) Math.toRadians(0f), (float) Math.toRadians(1f));
        }
        if(window.isKeyPressed(GLFW_KEY_CAPS_LOCK))
        {
            camera.addRotation((float) Math.toRadians(1f), (float) Math.toRadians(0f));
        }
        if(window.isKeyPressed(GLFW_KEY_TAB))
        {
            camera.addRotation((float) Math.toRadians(-1f), (float) Math.toRadians(0f));
        }
        if(window.isKeyPressed(GLFW_KEY_6)){
            importX.get(0).translateObject(0.0f, 0.0f, 5f);
        }
    }



    public void loop() {

        while (window.isOpen()) {
            window.update();
            glClearColor(1f,1f,1f, 1.0f);
            GL.createCapabilities();
            glClearDepth(1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            input();


            for (Object obj3D : importX) {
                obj3D.draw(camera, projection);
            }

//            System.out.println("X"+camera.getPosition().get(0));
//            System.out.println("Y"+camera.getPosition().get(1));
//            System.out.println("Z"+camera.getPosition().get(2));


            //Restore State
            glDisableVertexAttribArray(0);
            // Pull for window events
            // The key callback above will only be
            // invoked during this call
            glfwPollEvents();
        }
    }


    public static void main (String[]args){
        new DummyTest().run();
    }
}


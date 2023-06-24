package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Sphere extends Circle3D{
    float rZ;
    int stackCount;
    int sectorCount;
    List<Vector3f> normal;
    int nbo;
    Model m = new Model();
    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                  double r, ArrayList<Float> centerPoint, float rX, float rY, float rZ, int stackCount, int sectorCount, Model m)
    {
        super(shaderModuleDataList, vertices, color, centerPoint, rX, rY);
        this.rZ = rZ;
        this.stackCount = stackCount;
        this.sectorCount = sectorCount;
        this.m = m;
        createNew();
        setupVAOVBO();
    }
    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, double r, ArrayList<Float> centerPoint, float rX, float rY, float rZ, int stackCount, int sectorCount, int option) {
        super(shaderModuleDataList, vertices, color, centerPoint, rX, rY);
        this.rZ = rZ;
        this.stackCount = stackCount;
        this.sectorCount = sectorCount;

        if(option == 0){
            createBox();
        }else if(option == 1){
            createSphereElipsoid();

        }
        else if(option == 2){
            createTabung();

        }
        else if(option == 3){
            createSphere();

        }
        else if(option == 4){
            createHyperboloid1();

        }
        else if(option == 5){
            createHyperboloid2();

        }
        else if(option == 6){
            createEllipticCone();

        }
        else if(option == 7){
            createEllipticParaboloid();

        }
        else if(option == 8){
            createHyperboloidParaboloid();
        }
        setupVAOVBO();
    }
    @Override
    public void setupVAOVBO(){
        //// VAOVBONBO UNTUK SHADING

        //Setup VAOVBO, panggil fungsi parent
        super.setupVAOVBO();
        //Setup NBO
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        //Mengirim vertices ke shader
        glBufferData(GL_ARRAY_BUFFER,Utils.listoFloat(normal),GL_STATIC_DRAW);
//        Ver New Directional Light
//        uniformsMap.createUniform("dirLight");
//        uniformsMap.createUniform("viewPos");

//        Untuk Shading Ver Old
//        uniformsMap.createUniform("lightColor");
//        uniformsMap.createUniform("lightPos");




    }
    @Override
    public void drawSetup(Camera camera, Projection projection) {
        //drawSetup untuk Shading
        super.drawSetup(camera,projection);
        //Bind NBO
        //Untuk Simpan VBO/NBO pada index ke-n
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1,3, GL_FLOAT, false, 0,0);
//        uniformsMap.setUniform("lightColor", new Vector3f(1.0f,0.5f,.8f));
//        uniformsMap.setUniform("lightPos", new Vector3f(0.0f,3.0f,2.4f));
        uniformsMap.setUniform("dirLight.direction", new Vector3f(-0.2f,-1.0f, -0.3f));
        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f,0.05f, 0.05f));
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.4f,0.4f, 0.4f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.5f,0.5f, 0.5f));

//        posisi PointLight
        Vector3f[] _pointLightPositions ={
//                new Vector3f(0.7f,0.2f,2.0f),
//                new Vector3f(2.3f,-3.3f,-4.0f),
//                new Vector3f(4.0f,2.0f,-12.0f),
//                new Vector3f(-5f,4.5f,-6.0f),
                new Vector3f(-5f,5.0f,-6.0f),
                new Vector3f(-5f,2f,-6.0f),
//                new Vector3f(2.0f,0.0f,-6.0f),
//                new Vector3f(5.0f, 0.0f, 5.0f)
        };
        for(int i = 0; i < _pointLightPositions.length;i++){
            uniformsMap.setUniform("pointLights["+ i +"].position", _pointLightPositions[i]);

            uniformsMap.setUniform("pointLights["+ i +"].ambient", new Vector3f(0.05f,0.05f,0.05f));

            uniformsMap.setUniform("pointLights["+ i +"].diffuse", new Vector3f(0.8f,0.8f,0.8f));

            uniformsMap.setUniform("pointLights["+ i +"].specular", new Vector3f(1f,1f,1f));

            uniformsMap.setUniform("pointLights["+ i +"].constant", (1f));

            uniformsMap.setUniform("pointLights["+ i +"].linear", (0.09f));

            uniformsMap.setUniform("pointLights["+ i +"].quadratic", (0.032f));
        }

//        SpotLight
        uniformsMap.setUniform("spotLight.position", camera.getPosition());
        uniformsMap.setUniform("spotLight.direction", camera.getDirection());
        uniformsMap.setUniform("spotLight.ambient", new Vector3f(0.0f,0.0f, 0.0f));
        uniformsMap.setUniform("spotLight.diffuse", new Vector3f(1.0f,1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.specular", new Vector3f(1.0f,1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.constant", (1f));
        uniformsMap.setUniform("spotLight.linear", (0.09f));
        uniformsMap.setUniform("spotLight.quadratic", (0.032f));
        uniformsMap.setUniform("spotLight.cutOff", (float)(Math.cos(Math.toRadians(12.5f))));
        uniformsMap.setUniform("spotLight.outerCutOff", (float)(Math.cos(Math.toRadians(12.5f))));

        uniformsMap.setUniform("viewPos", camera.getPosition());
    }

    public void draw(Camera camera, Projection projection){
        drawSetup(camera,projection);
        // Draw vertices
        glLineWidth(1);
        glPointSize(1);
        glDrawArrays(GL_POLYGON, 0, vertices.size());
        for(Object child : getChildObject()){
            child.draw(camera,projection);
        }
    }
    public void createNew(){
        normal = new ArrayList<>();
        for(Face face : m.faces){
            Vector3f n1 = m.normals.get((int) face.normal.x-1);
            normal.add(n1);
            Vector3f v1 = m.vertices.get((int) face.vertex.x-1);
            vertices.add(v1);

            Vector3f n2 = m.normals.get((int) face.normal.y-1);
            normal.add(n2);
            Vector3f v2 = m.vertices.get((int) face.vertex.y-1);
            vertices.add(v2);

            Vector3f n3 = m.normals.get((int) face.normal.z-1);
            normal.add(n3);
            Vector3f v3 = m.vertices.get((int) face.vertex.z-1);
            vertices.add(v3);
        }
    }

    public void createBox()
    {
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        // x jika minus di kiri, x jika plus di kanan
        // y di minus di bawah, y jika plus di atas
        // z di minus belakang, z di plus depan

        //titik 0 kiri atas belakang
        temp.x = (float)centerPoint.get(0) - rX/2;
        temp.y = (float)centerPoint.get(1) + rY/2;
        temp.z = (float)centerPoint.get(2) - rZ/2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 1 kiri bawah belakang
        temp.x = (float)centerPoint.get(0) - rX/2;
        temp.y = (float)centerPoint.get(1) - rY/2;
        temp.z = (float)centerPoint.get(2) - rZ/2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 2 kanan bawah belakang
        temp.x = (float)centerPoint.get(0) + rX/2;
        temp.y = (float)centerPoint.get(1) - rY/2;
        temp.z = (float)centerPoint.get(2) - rZ/2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 3 kanan atas belakang
        temp.x = (float)centerPoint.get(0) + rX/2;
        temp.y = (float)centerPoint.get(1) + rY/2;
        temp.z = (float)centerPoint.get(2) - rZ/2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 4 kiri atas depan
        temp.x = (float)centerPoint.get(0) - rX/2;
        temp.y = (float)centerPoint.get(1) + rY/2;
        temp.z = (float)centerPoint.get(2) + rZ/2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 5 kiri bawah depan
        temp.x = (float)centerPoint.get(0) - rX/2;
        temp.y = (float)centerPoint.get(1) - rY/2;
        temp.z = (float)centerPoint.get(2) + rZ/2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 6 kanan bawah depan
        temp.x = (float)centerPoint.get(0) + rX/2;
        temp.y = (float)centerPoint.get(1) - rY/2;
        temp.z = (float)centerPoint.get(2) + rZ/2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 7 kanan atas depan
        temp.x = (float)centerPoint.get(0) + rX/2;
        temp.y = (float)centerPoint.get(1) + rY/2;
        temp.z = (float)centerPoint.get(2) + rZ/2;
        tempVertices.add(temp);

        vertices.clear();
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));

        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));

        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));

        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));
        normal = new ArrayList<>
                (Arrays.asList(
                   //Belakang
                    new Vector3f(0.0f, 0.0f,-1.0f),
                    new Vector3f(0.0f, 0.0f,-1.0f),
                    new Vector3f(0.0f, 0.0f,-1.0f),
                    new Vector3f(0.0f, 0.0f,-1.0f),
                    new Vector3f(0.0f, 0.0f,-1.0f),
                    new Vector3f(0.0f, 0.0f,-1.0f),
                    //Depan
                    new Vector3f(0.0f, 0.0f,1.0f),
                    new Vector3f(0.0f, 0.0f,1.0f),
                    new Vector3f(0.0f, 0.0f,1.0f),
                    new Vector3f(0.0f, 0.0f,1.0f),
                    new Vector3f(0.0f, 0.0f,1.0f),
                    new Vector3f(0.0f, 0.0f,1.0f),
                    //Kanan
                    new Vector3f(1.0f, 0.0f,0.0f),
                    new Vector3f(1.0f, 0.0f,0.0f),
                    new Vector3f(1.0f, 0.0f,0.0f),
                    new Vector3f(1.0f, 0.0f,0.0f),
                    new Vector3f(1.0f, 0.0f,0.0f),
                    new Vector3f(1.0f, 0.0f,0.0f),
                    //Kiri
                    new Vector3f(-1.0f, 0.0f,0.0f),
                    new Vector3f(-1.0f, 0.0f,0.0f),
                    new Vector3f(-1.0f, 0.0f,0.0f),
                    new Vector3f(-1.0f, 0.0f,0.0f),
                    new Vector3f(-1.0f, 0.0f,0.0f),
                    new Vector3f(-1.0f, 0.0f,0.0f),
                    //Atas
                    new Vector3f(0.0f, 1.0f,0.0f),
                    new Vector3f(0.0f, 1.0f,0.0f),
                    new Vector3f(0.0f, 1.0f,0.0f),
                    new Vector3f(0.0f, 1.0f,0.0f),
                    new Vector3f(0.0f, 1.0f,0.0f),
                    new Vector3f(0.0f, 1.0f,0.0f),
                        //Atas
                    new Vector3f(0.0f, -1.0f,0.0f),
                    new Vector3f(0.0f, -1.0f,0.0f),
                    new Vector3f(0.0f, -1.0f,0.0f),
                    new Vector3f(0.0f, -1.0f,0.0f),
                    new Vector3f(0.0f, -1.0f,0.0f),
                    new Vector3f(0.0f, -1.0f,0.0f)
                ));
    }

    public void createSphereElipsoid(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/180){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/180){
                float x = rX* (float)(Math.cos(v) * Math.cos(u));
                float y = rY * (float)(Math.cos(v) * Math.sin(u));
                float z = rZ * (float)(Math.sin(v));
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }
    public void createTabung(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        for(double i = 0 ; i <= 360 ; i += 0.1f){
            float x = centerPoint.get(0) + rX * (float)Math.cos(Math.toRadians(i));
            float y = centerPoint.get(1) + rY * (float)Math.sin(Math.toRadians(i));

            temp.add(new Vector3f(x,y,0f));
            temp.add(new Vector3f(x,y,-rZ));
        }
        vertices = temp;
    }

    public void createSphere(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/9){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/9){
                float x = this.rX * (float)(Math.cos(v) * Math.cos(u));
                float y = this.rY * (float)(Math.cos(v) * Math.sin(u));
                float z = this.rZ * (float)(Math.sin(v));
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }

    public void createHyperboloid1() {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = rX * (float)((1/Math.cos(v)) * Math.cos(u));
                float y = rY * (float)((1/Math.cos(v)) * Math.sin(u));
                float z = rZ * (float)(Math.tan(v));
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices = temp;
    }

    public void createHyperboloid2() {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI/2; u<= Math.PI/2; u+=Math.PI/60){
                float x = 0.5f * (float)((1/Math.cos(v)) * Math.cos(u));
                float y = 0.5f * (float)((1/Math.cos(v)) * Math.sin(u));
                float z = 0.5f * (float)(Math.tan(v));
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices = temp;

    }
    public void createEllipticCone() {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        for(double v = 0; v<= 100; v+=0.05){
            for(double u = -Math.PI; u<= Math.PI; u+=1){
                float x = 0.5f * (float)u * (float)(Math.cos(v));
                float y = 0.5f * (float)u * (float)((Math.sin(v)));
                float z = 0.5f * (float)u;
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices = temp;
    }

    public void createEllipticParaboloid() {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        for(double v = 0; v<= 100; v+=0.05){
            for(double u = -Math.PI; u<= Math.PI; u+=0.01){
                float x = 0.5f * (float)v * (float)(Math.cos(u));
                float y = 0.5f * (float)v * (float)((Math.sin(u)));
                float z = 0.5f * (float)Math.pow(v,2);
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices = temp;
    }
    public void createHyperboloidParaboloid() {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        for(double v = 0; v<= 100; v+=0.01){
            for(double u = -Math.PI; u<= Math.PI; u+=0.666){
                float x = 0.125f * (float)v * (float)(Math.tan(u));
                float y = 0.25f * (float)v * (float)((1/Math.cos(u)));
                float z = 0.5f * (float)Math.pow(v,2);
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices = temp;
    }


    public float getrZ() {
        return rZ;
    }

    public void setrZ(float rZ) {
        this.rZ = rZ;
    }
}

package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;


public class Circle3D extends Object {
    List<Float> centerPoint;
    Float rX;
    Float rY;
    public Circle3D(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, List<Float> centerPoint, Float radiusX,Float radiusY) {
        super(shaderModuleDataList, vertices, color);
        this.centerPoint = centerPoint;
        this.rX = radiusX;
        this.rY = radiusY;
        //setupVAOVBO();
    }

    public double degToRad(float degree){
        return (degree * Math.PI / (float) 180);
    }
    public void createCircle(){
        vertices.clear();
        for(float i = 0;i<360;i+=0.1){
            double rad = degToRad(i);
            Float x = (float) (centerPoint.get(0)+Math.cos(rad)*rX);
            Float y = (float) (centerPoint.get(1)+Math.sin(rad)*rY);
            Float z = 0.0f;
            vertices.add(new Vector3f(x,y,z));
        }
    }
    public void createRectangle(){
        vertices.clear();
        int degree = 45;
        for(float i = 0;i<4;i++){
            double rad = degToRad(degree);
            Float x = (float) (centerPoint.get(0)+Math.cos(rad)*rX);
            Float y = (float) (centerPoint.get(1)+Math.sin(rad)*rY);
            Float z = 0.0f;
            vertices.add(new Vector3f(x,y,z));
            degree+=90;
        }
    }
    public void createTriangle(){
        vertices.clear();
        int degree = 90;
        for(float i = 0;i<3;i++){
            double rad = degToRad(degree);
            Float x = (float) (centerPoint.get(0)+Math.cos(rad)*rX);
            Float y = (float) (centerPoint.get(1)+Math.sin(rad)*rY);
            Float z = 0.0f;
            vertices.add(new Vector3f(x,y,z));
            if(degree == 90){
                degree += 135;
            }
            else{
                degree += 90;
            }
        }
    }


    public List<Float> getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(List<Float> centerPoint) {
        this.centerPoint = centerPoint;
    }

    public Float getrX() {
        return rX;
    }

    public void setrX(Float rX) {
        this.rX = rX;
    }

    public Float getrY() {
        return rY;
    }

    public void setrY(Float rY) {
        this.rY = rY;
    }
}
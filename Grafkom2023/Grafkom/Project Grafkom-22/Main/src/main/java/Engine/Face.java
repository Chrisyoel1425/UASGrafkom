package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Face {
    public Vector3f vertex = new Vector3f(); // three Indices
    public Vector3f normal = new Vector3f();
    public Face(Vector3f vertex, Vector3f normal){
        this.vertex = vertex;
        this.normal = normal;
    }



//    public Vector4f vertex = new Vector4f(); // Four Indices
//    public Vector4f normal = new Vector4f();
//    public Face(Vector4f vertex, Vector4f normal){
//        this.vertex = vertex;
//        this.normal = normal;
//
//    }


}

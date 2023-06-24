package Engine;

import org.joml.Vector3f;

import java.io.*;

public class ObjLoader {
    public static  Model loadModel(File f) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        Model m = new Model();
        String line;

        while((line = reader.readLine()) != null)
        {
            if(line.startsWith("v "))
            {
                float x = Float.parseFloat(line.split("\\s+")[1]);
                float y = Float.parseFloat(line.split("\\s+")[2]);
                float z = Float.parseFloat(line.split("\\s+")[3]);
                m.vertices.add(new Vector3f(x,y,z));
            }
            else if(line.startsWith("vn "))
            {
                float x = Float.parseFloat(line.split("\\s+")[1]);
                float y = Float.parseFloat(line.split("\\s+")[2]);
                float z = Float.parseFloat(line.split("\\s+")[3]);
                m.normals.add(new Vector3f(x,y,z));
            }
            else if(line.startsWith("f "))
            {

                Vector3f vertexIndices = new Vector3f
                        (
                                Float.parseFloat(line.split("\\s+")[1].split("/")[0]), // X
                                Float.parseFloat(line.split("\\s+")[2].split("/")[0]), // Y
                                Float.parseFloat(line.split("\\s+")[3].split("/")[0])  // Z
                        );
                Vector3f normalIndices = new Vector3f
                        (
                                Float.parseFloat(line.split("\\s+")[1].split("/")[2]), // X
                                Float.parseFloat(line.split("\\s+")[2].split("/")[2]), // Y
                                Float.parseFloat(line.split("\\s+")[3].split("/")[2])  // Z
                        );
                m.faces.add(new Face(vertexIndices, normalIndices));
            }
        }
        reader.close();
        return m;
    }
}

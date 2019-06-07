package renderEngine;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import models.RawModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OBJLoader {

    public static RawModel loadObjModel(String fileName, VAOsLoader loader) throws IOException {

        InputStream inputStream =
                new FileInputStream("objModels/"+ fileName +".obj");
        Obj obj = ObjUtils.convertToRenderable(
                ObjReader.read(inputStream));

        int[] indices = ObjData.getFaceVertexIndicesArray(obj);
        float[] vertices = ObjData.getVerticesArray(obj);
        float[] texCoords = ObjData.getTexCoordsArray(obj, 2);
        float[] normals = ObjData.getNormalsArray(obj);
        return loader.loadToVAO(vertices, indices, texCoords,normals );
    }
}

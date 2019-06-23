package GilterSimulator;

import Engine.renderEngine.MultipleRenderer;
import Engine.terrains.Terrain;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TerrainManager {
    private static TerrainManager terrainManager;
    @Getter
    private List<Terrain> terrains = new ArrayList<>();

    public static TerrainManager getInstance() {
        if(terrainManager == null) terrainManager = new TerrainManager();
        return terrainManager;
    }

    public void addTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    public void processTerrains(MultipleRenderer renderer){
        for (Terrain t : terrains){
            renderer.processTerrain(t);
        }
    }

}

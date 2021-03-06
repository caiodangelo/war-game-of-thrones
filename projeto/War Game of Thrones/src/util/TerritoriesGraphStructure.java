package util;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MainScene;
import models.Region;
import models.BackEndTerritory;
import models.TerritoryID;
import prefuse.Constants;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.data.util.BreadthFirstIterator;

public class TerritoriesGraphStructure {
    
    private static TerritoriesGraphStructure instance;
    private Graph g;
    
    private TerritoriesGraphStructure() {
        GraphMLReader gr = new GraphMLReader();
        try {
            g = gr.readGraph("resources/xml/map_structure.xml");
        } catch (DataIOException ex) {
            Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void test(){
        for (int i = 0; i < g.getNodeCount(); i++) {
            System.out.println("TERRITÓRIO: "+g.getNode(i).get("name"));
            System.out.println("VIZINHOS:");
            Iterator it = g.getNode(i).neighbors();
            while (it.hasNext()) {
                System.out.println(((Node) it.next()).get("name"));
            }
        }
    }
    
    public int getNodeCount(){
        return g.getNodeCount();
    }
    
    public Node getNode(int index){
        return g.getNode(index);
    }
    
    public static TerritoriesGraphStructure getInstance() {
        if (instance == null)
            instance = new TerritoriesGraphStructure();
        return instance;
    }
    
    public Iterator getNeighborsIterator(int node) {
        return g.getNode(node).neighbors();
    }

    //algorithms here

    public BackEndTerritory[] fillTerritories(Region[] regions) {
        BackEndTerritory [] resp = new BackEndTerritory[39];
        for(int i = 0; i < getNodeCount(); i++){
            Node n = getNode(i);
            String terrName = n.getString("name");
            String regName = n.getString("region");
            int regIndex = TerritoryID.getRegionID(regName);
            Region parent = regions[regIndex];
            BackEndTerritory newTerritory = new BackEndTerritory(terrName, parent);
            parent.addTerritory(newTerritory);
            resp[i] = newTerritory;
        }
        return resp;
    }
    
}

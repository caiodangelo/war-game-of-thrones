package util;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MainScene;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;

public class TerritoriesGraphStructure {
    
    private static TerritoriesGraphStructure instance;
    private Graph g;
    
    public TerritoriesGraphStructure() {
        GraphMLReader gr = new GraphMLReader();
        try {
            g = gr.readGraph("resources/xml/map_structure.xml");
            for (int i = 0; i < g.getNodeCount(); i++) {
                System.out.println("TERRITÓRIO: "+g.getNode(i).get("name"));
                System.out.println("VIZINHOS:");
                Iterator it = g.getNode(i).neighbors();
                while (it.hasNext()) {
                    System.out.println(((Node) it.next()).get("name"));
                }
            }
        } catch (DataIOException ex) {
            Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
}

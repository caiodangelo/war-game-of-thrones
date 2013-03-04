package main;

import org.newdawn.slick.geom.Vector2f;

public class VectorOps {

    /**
     * Faz v1 + v2 e retorna novo vetor
     */
    public static Vector2f add(Vector2f v1, Vector2f v2){
        v1 = v1.copy();
        v1.add(v2);
        return v1;
    }
    
    /**
     * Faz v1 - v2 e retorna novo vetor
     */
    public static Vector2f sub(Vector2f v1, Vector2f v2){
        v1 = v1.copy();
        v2 = v2.negate();
        v1.add(v2);
        return v1;
    }
}

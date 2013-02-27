/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package models;

/**
 *
 * @author anderson
 */
public class CardTerritory {

    public static final int SQUARE = 1;
    public static final int CIRCLE = 2;
    public static final int TRIANGLE = 3;
    public static final int JOKER = 4;

    private int type;
    private BackEndTerritory territory;

    public CardTerritory(int geometricFigure, BackEndTerritory territory) {
        this.type = geometricFigure;
        this.territory = territory;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BackEndTerritory getTerritory() {
        return territory;
    }

    public void setTerritory(BackEndTerritory territory) {
        this.territory = territory;
    }
    
    public boolean isJoker() {
        return type == 4;
    }

}
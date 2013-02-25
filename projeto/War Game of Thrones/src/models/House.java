package models;

import java.io.Serializable;
import de.lessvoid.nifty.tools.Color;

/**
 *
 * @author rodrigo
 */
public class House implements Serializable {

    // Exemplo:                                 AARRGGBB
    //TODO: trocar cores
    
    private String name;

    private Color color;
    private String symbol;
    private Player player;
    private String imgPath;
    

    public House() {
    }

    public House(String name, Color color, String symbol, String imgPath) {
        this.name = name;
        this.color = color;
        this.symbol = symbol;
        this.imgPath = imgPath;
    }

    public House(String name, Color color, String symbol, String imgPath, Player player) {
        this(name, color, symbol, imgPath);
        this.player = player;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getImgPath() {
        return imgPath;
    }
}
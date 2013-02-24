package models;
import de.lessvoid.nifty.tools.Color;
/**
 *
 * @author rodrigo
 */
public class House {

    // Exemplo:                                 AARRGGBB
    //TODO: trocar cores
    
    private String name;

    private Color color;
    private String symbol;
    private Player player;

    public House() {
    }

    public House(String name, Color color, String symbol) {
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    public House(String name, Color color, String symbol, Player player) {
        this(name, color, symbol);
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
}
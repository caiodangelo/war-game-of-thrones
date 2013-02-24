package models;

import java.io.Serializable;

/**
 *
 * @author rodrigo
 */
public class House implements Serializable {

    // Exemplo:                                 AARRGGBB
    //TODO: trocar cores
    public static final int COLOR_LANNISTER = 0x00F21F0C;
    public static final int COLOR_STARK = 0x00000001;
    public static final int COLOR_GREYJOY = 0x00000002;
    public static final int COLOR_FREE_FOLK = 0x00000003;
    public static final int COLOR_BARATHEON = 0x00000004;
    public static final int COLOR_TARGARYEN = 0x00000005;
    private String name;
    /*
     * A cor vai ficar em formato ARGB hexadecimal, e terá algumas cores padrões./
     *
     * TODO: Definir isso melhor depois.
     */
    private int color;
    private String symbol;
    private Player player;

    public House() {
    }

    public House(String name, int color, String symbol) {
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    public House(String name, int color, String symbol, Player player) {
        this(name, color, symbol);
        this.player = player;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
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
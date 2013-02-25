package models;

import java.io.Serializable;

public class TerritoryTransaction implements Serializable {

    public Territory attacker;
    public Territory defender;
    public int numberOfAttackers;

    public TerritoryTransaction() {
    }

    public TerritoryTransaction(Territory attacker, Territory defender, int numberOfAttackers) {
        this.attacker = attacker;
        this.defender = defender;
        this.numberOfAttackers = numberOfAttackers;
    }
}

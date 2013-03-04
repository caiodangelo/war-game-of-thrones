package models;

import java.io.Serializable;

public class TerritoryTransaction implements Serializable {

    public BackEndTerritory attacker;
    public BackEndTerritory defender;
    public int numberOfAttackers;

    public TerritoryTransaction() {
    }

    public TerritoryTransaction(BackEndTerritory attacker, BackEndTerritory defender, int numberOfAttackers) {
        this.attacker = attacker;
        this.defender = defender;
        this.numberOfAttackers = numberOfAttackers;
    }

    public boolean isValid() {
        return attacker.getSurplusArmies() > 0 && attacker.getSurplusArmies() >= numberOfAttackers && defender.getNumArmies() > 0;
    }
}

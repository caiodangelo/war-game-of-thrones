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
        return attacker != null && defender != null && numberOfAttackers > 0 &&
                attacker.getSurplusArmies() > 0 && attacker.getSurplusArmies() >= numberOfAttackers &&
                defender.getNumArmies() > 0 && attacker.getOwner() != defender.getOwner();
    }
    
    @Override
    public String toString(){
        return "Territory Transaction from " + this.attacker + " OF " + attacker.getOwner().getName() + " to " + this.defender + " OF " + defender.getOwner().getName() + " with count " + this.numberOfAttackers;
    }
}

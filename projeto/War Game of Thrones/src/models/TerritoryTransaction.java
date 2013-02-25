package models;

import java.io.Serializable;

public class TerritoryTransaction implements Serializable {
        Territory attacker;
        Territory defender;
        int numberOfAttackers;

        public TerritoryTransaction() {
        }
        
        public TerritoryTransaction(Territory attacker, Territory defender, int numberOfAttackers) {
            this.attacker = attacker;
            this.defender = defender;
            this.numberOfAttackers = numberOfAttackers;
        }
    }

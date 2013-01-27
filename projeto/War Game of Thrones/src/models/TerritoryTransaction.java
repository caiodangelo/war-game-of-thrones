package models;

public class TerritoryTransaction {
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

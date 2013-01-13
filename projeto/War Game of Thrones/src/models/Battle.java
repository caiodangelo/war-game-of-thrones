package models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author rodrigo
 */
public class Battle {

    protected Territory attacker;
    protected Territory defender;
    protected int numberAttackers;
    protected int numberDefenders;
    protected Integer[] attackersDices;
    protected Integer[] defendersDices;
    protected int attackerDeaths;
    protected int defenderDeaths;
    protected boolean valid;
    protected boolean concluded;
    protected boolean conquested;

    public Battle(Territory attacker, Territory defender, int numberAttackers, int numberDefenders) {
        this.attacker = attacker;
        this.defender = defender;
        this.numberAttackers = numberAttackers;
        this.numberDefenders = numberDefenders;
        valid = attacker.isNeighbour(defender) && attacker.getOwner() != defender.getOwner();
        valid = valid && isArmiesCountValid() && attacker.getNumArmies() > numberAttackers && defender.getNumArmies() >= numberDefenders;
        concluded = false;
        conquested = false;
    }

    public void attack() {
        attackersDices = rollDices(numberAttackers);
        defendersDices = rollDices(numberDefenders);
        Arrays.sort(attackersDices, Collections.reverseOrder());
        Arrays.sort(defendersDices, Collections.reverseOrder());
        int smallerPart = Math.min(numberAttackers, numberDefenders);
        for (int i = 0; i < smallerPart; i++) {
            if (compareDices(attackersDices[i], defendersDices[i])) {
                defenderDeaths++; // Ataque ganha
            }
            else {
                attackerDeaths++; // Defesa ganha
            }
        }
}

    public void concludeAttack() {
        if (attackerDeaths > 0 || defenderDeaths > 0) {
            conquested = defenderDeaths >= defender.getNumArmies();
            attacker.decreaseArmies(attackerDeaths);
            defender.decreaseArmies(attackerDeaths);
            if (conquested)
                defender.setOwner(attacker.getOwner());
            concluded = true;
        }
    }

    public void moveArmiesAfterConquest(int numberArmies) {
        int remainingArmies = numberAttackers - attackerDeaths;
        if (concluded && conquested && numberArmies >= 1 && numberArmies <= remainingArmies) {
            attacker.transferArmies(defender, numberArmies);
        }
    }

    public Territory getAttacker() {
        return attacker;
    }

    public int getAttackerDeaths() {
        return attackerDeaths;
    }

    public boolean isConcluded() {
        return concluded;
    }

    public boolean isConquested() {
        return conquested;
    }

    public Territory getDefender() {
        return defender;
    }

    public int getDefendersDeaths() {
        return defenderDeaths;
    }

    public int getNumberAttackers() {
        return numberAttackers;
    }

    public int getNumberDefenders() {
        return numberDefenders;
    }

    public boolean isValid() {
        return valid;
    }

    public Integer[] getAttackersDices() {
        return attackersDices;
    }

    public Integer[] getDefendersDices() {
        return defendersDices;
    }

    protected final Integer[] rollDices(int number) {
        Integer[] dices = new Integer[number];
        for (int i = 0; i < dices.length; i++) {
            dices[i] = new Random().nextInt(5) + 1;
        }
        return dices;
    }

    protected boolean compareDices(int attacker, int defender) {
        return attacker > defender;
    }

    protected final boolean isArmiesCountValid() {
        return numberAttackers >= 1 && numberAttackers <= 3 && numberDefenders >= 1 && numberDefenders <= 3;
    }
}

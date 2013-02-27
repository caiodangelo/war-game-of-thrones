package models;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author rodrigo
 */
public class Battle {

    protected BackEndTerritory attacker;
    protected BackEndTerritory defender;
    protected int numberAttackers;
    protected int numberDefenders;
    protected Integer[] attackersDices;
    protected Integer[] defendersDices;
    protected int attackerDeaths;
    protected int defenderDeaths;
    protected boolean valid;
    protected boolean concluded;
    protected boolean conquested;

    public Battle(BackEndTerritory attacker, BackEndTerritory defender, int numberAttackers, int numberDefenders) {
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
        //Estatisticas
        defender.increaseNumAttacks();
        //Board.getInstance().getStatistic().setTerritoryMoreAttacked(null);
        Player attackerPlayer = this.getAttackerPlayerFromTerritory();
        Player defenderPlayer = this.getDefenderPlayerFromTerritory();
        attackerPlayer.getStatisticPlayerManager().increaseNumberOfAttacks();
        defenderPlayer.getStatisticPlayerManager().increaseNumberOfDefences();
        attackerPlayer.getStatisticPlayerManager().updateAttackTable(defenderPlayer);
        attackerPlayer.getStatisticPlayerManager().setYouAttackedMore();
        defenderPlayer.getStatisticPlayerManager().updateDefenceTable(attackerPlayer);
        defenderPlayer.getStatisticPlayerManager().setMoreEnemy();
        //Fim Estatisticas
        attackersDices = rollDices(numberAttackers);
        defendersDices = rollDices(numberDefenders);
        Integer[] tempAttackersDices = Arrays.copyOf(attackersDices, attackersDices.length);
        Integer[] tempDefendersDices = Arrays.copyOf(defendersDices, defendersDices.length);
        //Estatisticas        
        attackerPlayer.getStatisticPlayerManager().averageAttackDices(attackersDices);
        defenderPlayer.getStatisticPlayerManager().averageDefenceDices(defendersDices);
        //Fim Estatisticas
        
        Arrays.sort(tempAttackersDices, Collections.reverseOrder());
        Arrays.sort(tempDefendersDices, Collections.reverseOrder());
        int smallerPart = Math.min(numberAttackers, numberDefenders);
        for (int i = 0; i < smallerPart; i++) {
            if (compareDices(tempAttackersDices[i], tempDefendersDices[i])) {
                defenderDeaths++; // Ataque ganha
                //Estatisticas
                attackerPlayer.getStatisticPlayerManager().increaseNumberOfAttackWins();
                attackerPlayer.getStatisticPlayerManager().setSuccessfulAttackPercentage();
                attackerPlayer.getStatisticPlayerManager().increaseLostArmies();
                //Board.getInstance().getStatistic().setMostWinnerAttacks();
                //Fim estatisticas
            }
            else {
                attackerDeaths++; // Defesa ganha
                //Estatisticas
                defenderPlayer.getStatisticPlayerManager().increaseNumberOfDefenceWins();
                defenderPlayer.getStatisticPlayerManager().setSuccessfulDefencePercentage();
                defenderPlayer.getStatisticPlayerManager().increaseLostArmies();
                //Board.getInstance().getStatistic().setMostWinnerDefences();
                //Fim estatisticas
            }
            
//            Board.getInstance().getStatistic().setMoreAttacker();
//            Board.getInstance().getStatistic().setMoreDefender();
        }
    }

    public void concludeAttack() {
        if (attackerDeaths > 0 || defenderDeaths > 0) {
            conquested = defenderDeaths >= defender.getNumArmies();
            attacker.decreaseArmies(attackerDeaths);
            defender.decreaseArmies(defenderDeaths);
        }
        concluded = true;
        if (conquested)
            defender.setOwner(attacker.getOwner());
    }
    
    public void moveVictoriousArmies(int armiesMoved) {
        attacker.decreaseArmies(armiesMoved);
        defender.increaseArmies(armiesMoved);
        attacker.getOwner().setMaySwapCards(true);
        //Estatistica
        defender.increaseNumConquests();
        //Board.getInstance().getStatistic().setTerritoryMoreConquested(null);
        //Fim estatistica
    }

    public void moveArmiesAfterConquest(int numberArmies) {
        int remainingArmies = numberAttackers - attackerDeaths;
        if (concluded && conquested && numberArmies >= 1 && numberArmies <= remainingArmies) {
            attacker.transferArmies(defender, numberArmies);
        }
    }

    public BackEndTerritory getAttacker() {
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

    public BackEndTerritory getDefender() {
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
    
    public Player getAttackerPlayerFromTerritory() {
        return attacker.getOwner();
    }
    
    public Player getDefenderPlayerFromTerritory() {
        return defender.getOwner();
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

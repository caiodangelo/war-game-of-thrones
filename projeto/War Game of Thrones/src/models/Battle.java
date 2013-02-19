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
        //Estatisticas
        defender.increaseNumAttacks();
        Board.getInstance().getStatistic().setTerritoryMoreAttacked(null);       
        
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
        
        //Estatisticas        
        attackerPlayer.getStatisticPlayerManager().averageAttackDices(attackersDices);
        defenderPlayer.getStatisticPlayerManager().averageDefenceDices(defendersDices);
        //Fim Estatisticas
        
        Arrays.sort(attackersDices, Collections.reverseOrder());
        Arrays.sort(defendersDices, Collections.reverseOrder());
        int smallerPart = Math.min(numberAttackers, numberDefenders);
        for (int i = 0; i < smallerPart; i++) {
            if (compareDices(attackersDices[i], defendersDices[i])) {
                defenderDeaths++; // Ataque ganha
                //Estatisticas
                attackerPlayer.getStatisticPlayerManager().increaseNumberOfAttackWins();
                attackerPlayer.getStatisticPlayerManager().setSuccessfulAttackPercentage();
                attackerPlayer.getStatisticPlayerManager().increaseLostArmies();
                Board.getInstance().getStatistic().setMostWinnerAttacks();
                //Fim estatisticas
            }
            else {
                attackerDeaths++; // Defesa ganha
                //Estatisticas
                defenderPlayer.getStatisticPlayerManager().increaseNumberOfDefenceWins();
                defenderPlayer.getStatisticPlayerManager().setSuccessfulDefencePercentage();
                defenderPlayer.getStatisticPlayerManager().increaseLostArmies();
                Board.getInstance().getStatistic().setMostWinnerDefences();
                //Fim estatisticas
            }
            
            Board.getInstance().getStatistic().setMoreAttacker();
            Board.getInstance().getStatistic().setMoreDefender();
        }
}

    public void concludeAttack() {
        if (attackerDeaths > 0 || defenderDeaths > 0) {
            conquested = defenderDeaths >= defender.getNumArmies();
            attacker.decreaseArmies(attackerDeaths);
            defender.decreaseArmies(attackerDeaths);
            if (conquested) {
                defender.setOwner(attacker.getOwner());
                //Estatistica
                defender.increaseNumConquests();
                Board.getInstance().getStatistic().setTerritoryMoreConquested(null);
                //Fim estatistica
            }
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

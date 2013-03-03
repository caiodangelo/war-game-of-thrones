/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Anderson
 */
public class StatisticPlayerManager implements Serializable{

    private int numberOfAttacks;//DONE
    private float successfulAttackPercentage;//DONE
    private int numberOfAttackWins;//Numero de vitorias no ataque
    private int numberOfDefences;//DONE
    private float successfulDefencePercentage;//DONE
    private int numberOfDefenceWins;//Numero de vitorias na defesa
    private float dicesOfAttackAverage;//DONE
    private int numberOfAttackDicesPlayed; // Numero de dados jogados pelo jogador de ataque
    private float dicesOfDefenceAverage;//DONE
    private int numberOfDefenceDicesPlayed; // Numero de dados jogados pelo jogador de defesa
    private int numberOfCardsSwapped; //DONE
    private int receivedArmies;//DONE
    private int lostArmies;//DONE
    private int[] attackTable; //vetor que indica o num de ataques feitos a cada jogador; Player descoberto pelo indice do vetor
    private Player youAttackMore;//DONE
    private int[] defenceTable; //tabela que indica o num de defesas feitos diante do ataque adversario; Player descoberto pelo indice do vetor
    private Player moreEnemy;//DONE

    public StatisticPlayerManager() {
        this.numberOfAttacks = 0;
        this.successfulAttackPercentage = 0;
        this.numberOfAttackWins = 0;
        this.numberOfDefences = 0;
        this.successfulDefencePercentage = 0;
        this.numberOfDefenceWins = 0;
        this.dicesOfAttackAverage = 0;
        this.numberOfAttackDicesPlayed = 0;
        this.dicesOfDefenceAverage = 0;
        this.numberOfDefenceDicesPlayed = 0;
        this.numberOfCardsSwapped = 0;
        this.receivedArmies = 0;
        this.lostArmies = 0;
        this.attackTable = new int[6];
            for (int i = 0; i < this.attackTable.length; i++) {
                this.attackTable[i] = 0;
            }
        this.youAttackMore = null;
        this.defenceTable = new int[6];
            for (int i = 0; i < this.defenceTable.length; i++) {
                this.defenceTable[i] = 0;
            }
        this.moreEnemy = null;
    }

    public int getNumberOfAttacks() {
        return numberOfAttacks;
    }

    public float getSuccessfulAttackPercentage() {
        return successfulAttackPercentage;
    }

    public int getNumberOfDefences() {
        return numberOfDefences;
    }

    public float getSuccessfulDefencePercentage() {
        return successfulDefencePercentage;
    }

    public float getDicesOfAttackAverage() {
        return dicesOfAttackAverage;
    }

    public float getDicesOfDefenceAverage() {
        return dicesOfDefenceAverage;
    }

    public int getReceivedArmies() {
        return receivedArmies;
    }

    public int getLostArmies() {
        return lostArmies;
    }

    public Player getYouAttackMore() {
        return youAttackMore;
    }

    public Player getMoreEnemy() {
        return moreEnemy;
    }

    public int[] getAttackTable() {
        return attackTable;
    }

    public int[] getDefenceTable() {
        return defenceTable;
    }

    public int getNumberOfAttackWins() {
        return numberOfAttackWins;
    }

    public int getNumberOfDefenceWins() {
        return numberOfDefenceWins;
    }

    public int getNumberOfCardsSwapped() {
        return numberOfCardsSwapped;
    }

    public void increaseNumberOfAttacks() {
        this.numberOfAttacks++;
    }

    public void increaseNumberOfDefences() {
        this.numberOfDefences++;
    }

    public void increaseNumberOfAttackWins() {
        this.numberOfAttackWins++;
    }

    public void increaseNumberOfDefenceWins() {
        this.numberOfDefenceWins++;
    }

    public void averageAttackDices(Integer[] dices) {
        float allDicesAdded = this.dicesOfAttackAverage * this.numberOfAttackDicesPlayed;
        int sum = 0; 
        for (int i = 0; i < dices.length; i++) {
            sum += dices[i];
            this.numberOfAttackDicesPlayed++;
        }
        float result = (float)(allDicesAdded + sum) / this.numberOfAttackDicesPlayed;
        this.dicesOfAttackAverage = round(result, 2);
        
    }
    
    public void averageDefenceDices(Integer[] dices) {
        float allDicesAdded = this.dicesOfDefenceAverage * this.numberOfDefenceDicesPlayed;
        int sum = 0;
        for (int i = 0; i < dices.length; i++) {
            this.numberOfDefenceDicesPlayed++;
            sum += dices[i];
        }
        float result = (float) (allDicesAdded + sum) / this.numberOfDefenceDicesPlayed;
        this.dicesOfDefenceAverage = round(result, 2);
    }

    public void setSuccessfulAttackPercentage() {
        float result = (float) this.numberOfAttackWins / (float) this.numberOfAttacks * 100;
        this.successfulAttackPercentage = round(result, 2);
    }

    public void setSuccessfulDefencePercentage() {
        float result = (float)this.numberOfDefenceWins / (float)this.numberOfDefences * 100;
        this.successfulDefencePercentage = round(result, 2);
    }
    
    public void increaseNumberOfCardsSwapped () {
        this.numberOfCardsSwapped++;
    }

    public void increaseReceivedArmies(int pendingArmies) {
        this.receivedArmies = this.receivedArmies + pendingArmies;
    }

    public void increaseLostArmies() {
        this.lostArmies++;
    }

    public void updateAttackTable(Player player) {
        Board board = Board.getInstance();
        int index = board.getPlayers().indexOf(player);
        this.attackTable[index]++;
    }

    public void updateDefenceTable(Player player) {
        Board board = Board.getInstance();
        int index = board.getPlayers().indexOf(player);
        this.defenceTable[index]++;
    }

    public void setYouAttackedMore() {
        Board board = Board.getInstance();
        LinkedList<Player> players = board.getPlayers();
        int indexOfMostAttackedPlayer = 0;
        int mostAttacks = this.attackTable[indexOfMostAttackedPlayer];
        for (int i = 1; i < players.size(); i++) {
            if (this.attackTable[i] > mostAttacks) {
                mostAttacks = this.attackTable[i];
                indexOfMostAttackedPlayer = i;
            }
        }
        this.youAttackMore = board.getPlayer(indexOfMostAttackedPlayer);
    }

    public void setMoreEnemy() {
        Board board = Board.getInstance();
        LinkedList<Player> players = board.getPlayers();
        int indexOfMostAttackedPlayer = 0;
        int mostAttacks = this.defenceTable[indexOfMostAttackedPlayer];
        for (int i = 1; i < players.size(); i++) {
            if (this.defenceTable[i] > mostAttacks) {
                mostAttacks = this.defenceTable[i];
                indexOfMostAttackedPlayer = i;
            }
        }
        this.moreEnemy = board.getPlayer(indexOfMostAttackedPlayer);
    }

    public int getNumberOfAttackDicesPlayed() {
        return numberOfAttackDicesPlayed;
    }

    public int getNumberOfDefenceDicesPlayed() {
        return numberOfDefenceDicesPlayed;
    }
    
    public float round(double n, int casas) {
       int mult = (int) Math.pow(10, casas);
       float temp = (float) (n * mult);
       temp = Math.round(temp);
       return temp / mult;
   }

    public void setNumberOfAttacks(int num) {
        this.numberOfAttacks = num;
    }

    public void setNumberOfAttackWins(int num) {
        this.numberOfAttackWins = num;
    }

    void setNumberOfDefences(int i) {
        this.numberOfDefences = i;
    }

    void setNumberOfDefenceWins(int i) {
        this.numberOfDefenceWins = i;
    }
    
    
}

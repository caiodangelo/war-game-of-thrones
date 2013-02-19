/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Anderson
 */
public class StatisticPlayerManager {

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
    private int numberOfCardsSwapped; //TODO:Falta colocar no metodo de troca de cartas
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
        int allDicesAdded = (int) this.dicesOfAttackAverage * this.numberOfAttackDicesPlayed;
        for (int i = 0; i < dices.length; i++) {
            this.numberOfAttackDicesPlayed++;
            this.dicesOfAttackAverage = (allDicesAdded + dices[i]) / this.numberOfAttackDicesPlayed;
        }
    }

    public void averageDefenceDices(Integer[] dices) {
        int allDicesAdded = (int) this.dicesOfDefenceAverage * this.numberOfDefenceDicesPlayed;
        for (int i = 0; i < dices.length; i++) {
            this.numberOfDefenceDicesPlayed++;
            this.dicesOfDefenceAverage = (allDicesAdded + dices[i]) / this.numberOfDefenceDicesPlayed;

        }
    }

    public void setSuccessfulAttackPercentage() {
        this.successfulAttackPercentage = (this.numberOfAttackWins / this.numberOfAttacks) * 100;
    }

    public void setSuccessfulDefencePercentage() {
        this.successfulDefencePercentage = (this.numberOfDefenceWins / this.numberOfDefences) * 100;
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
        int index = this.attackTable[0];
        for (int i = 0; i < board.getPlayers().size() - 1; i++) {
            if (this.attackTable[i + 1] > this.attackTable[i]) {
                index = this.attackTable[i + 1];
            }
        }
        this.youAttackMore = board.getPlayer(index);
    }

    public void setMoreEnemy() {
        Board board = Board.getInstance();
        int index = this.defenceTable[0];
        for (int i = 0; i < board.getPlayers().size() - 1; i++) {
            if (this.defenceTable[i + 1] > this.defenceTable[i]) {
                index = this.defenceTable[i + 1];
            }
        }
        this.moreEnemy = board.getPlayer(index);
    }
}

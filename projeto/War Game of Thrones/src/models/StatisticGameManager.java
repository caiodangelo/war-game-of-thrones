/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Anderson
 */
public class StatisticGameManager implements Serializable {
    
    private Date playTime;
    private ArrayList<Player> moreAttacker;//DONE
    private ArrayList<Player> moreDefender;//DONE
    private ArrayList<Player> mostWinnerAttacks;//DONE
    private ArrayList<Player> mostWinnerDefences;//DONE
    private ArrayList<Territory> territoryMoreAttacked;//TODO: Atrelar a chamada do metodo com a lista de todos os territorios
    private ArrayList<Territory> territoryMoreConquered;//TODO: Atrelar a chamada do metodo com a lista de todos os territorios
    
    public StatisticGameManager() {
        this.playTime = null;
        this.moreAttacker = new ArrayList<Player>();
        this.moreDefender = new ArrayList<Player>();
        this.mostWinnerAttacks = new ArrayList<Player>();
        this.mostWinnerDefences = new ArrayList<Player>();
    }
    
    public Date getPlayTime() {
        return playTime;
    }

    public ArrayList<Player> getMoreAttacker() {
        return moreAttacker;
    }

    public ArrayList<Player> getMoreDefender() {
        return moreDefender;
    }

    public ArrayList<Player> getMostWinnerAttacks() {
        return mostWinnerAttacks;
    }

    public ArrayList<Player> getMostWinnerDefences() {
        return mostWinnerDefences;
    }

    public ArrayList<Territory> getTerritoryMoreAttacked() {
        return territoryMoreAttacked;
    }

    public ArrayList<Territory> getTerritoryMoreConquered() {
        return territoryMoreConquered;
    }
    
    public void setMoreAttacker() {
        Board board = Board.getInstance();
        int attacksNumber = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (attacksNumber < board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttacks()) {
                attacksNumber = board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttacks();
                this.moreAttacker.clear();
                this.moreAttacker.add(board.getPlayer(i));
            } else {
                if (attacksNumber == board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttacks()) {
                    this.moreAttacker.add(board.getPlayer(i));
                }
            }
        }
    }
    
    public void setMoreDefender() {
        Board board = Board.getInstance();
        int defencesNumber = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (defencesNumber < board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefences()) {
                defencesNumber = board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefences();
                this.moreDefender.clear();
                this.moreDefender.add(board.getPlayer(i));
            } else {
                if (defencesNumber == board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefences()) {
                    this.moreDefender.add(board.getPlayer(i));
                }
            }
        }
    }
    
    public void setMostWinnerAttacks() {
        Board board = Board.getInstance();
        int numberOfAttackVictories = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (numberOfAttackVictories < board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttackWins()) {
                numberOfAttackVictories = board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttackWins();
                this.mostWinnerAttacks.clear();
                this.mostWinnerAttacks.add(board.getPlayer(i));
            } else {
                if (numberOfAttackVictories == board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttackWins()) {
                    this.mostWinnerAttacks.add(board.getPlayer(i));
                }
            }
        }
    }
    public void setMostWinnerDefences() {
        Board board = Board.getInstance();
        int numberOfDefencesVictories = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (numberOfDefencesVictories < board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefenceWins()) {
                numberOfDefencesVictories = board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefenceWins();
                this.mostWinnerDefences.clear();
                this.mostWinnerDefences.add(board.getPlayer(i));
            } else {
                if (numberOfDefencesVictories == board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefenceWins()) {
                    this.mostWinnerDefences.add(board.getPlayer(i));
                }
            }
        }
    }
    
    public void setTerritoryMoreAttacked(ArrayList<Territory> allTerritories) {
        int attacksNumber = 0;
        for (int i = 0; i < allTerritories.size(); i++) {
            if(attacksNumber < allTerritories.get(i).getNumAttacks()) {
                attacksNumber = allTerritories.get(i).getNumAttacks();
                this.territoryMoreAttacked.clear();
                this.territoryMoreAttacked.add(allTerritories.get(i));
            } else {
                if(attacksNumber == allTerritories.get(i).getNumAttacks()) {
                    this.territoryMoreAttacked.add(allTerritories.get(i));
                }
            }
        }
    }
    
    public void setTerritoryMoreConquested(ArrayList<Territory> allTerritories) {
        int conquestsNumber = 0;
        for (int i = 0; i < allTerritories.size(); i++) {
            if(conquestsNumber < allTerritories.get(i).getNumConquests()) {
                conquestsNumber = allTerritories.get(i).getNumConquests();
                this.territoryMoreConquered.clear();
                this.territoryMoreConquered.add(allTerritories.get(i));
            } else {
                if(conquestsNumber == allTerritories.get(i).getNumConquests()) {
                    this.territoryMoreConquered.add(allTerritories.get(i));
                }
            }
        }
    }
}

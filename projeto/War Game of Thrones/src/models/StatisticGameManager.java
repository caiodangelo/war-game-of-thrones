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
    
    private static StatisticGameManager instance;
    private Date initTime;
    private Date endTime;
    private String playTime;
    //private ArrayList<Player> moreAttacker;//DONE
    //private ArrayList<Player> moreDefender;//DONE
    //private ArrayList<Player> mostWinnerAttacks;//DONE
    //private ArrayList<Player> mostWinnerDefences;//DONE
    //private ArrayList<Territory> territoryMoreAttacked;//TODO: Atrelar a chamada do metodo com a lista de todos os territorios
    //private ArrayList<Territory> territoryMoreConquered;//TODO: Atrelar a chamada do metodo com a lista de todos os territorios
    
    public StatisticGameManager() {
        //this.moreAttacker = new ArrayList<Player>();
        //this.moreDefender = new ArrayList<Player>();
        //this.mostWinnerAttacks = new ArrayList<Player>();
        //this.mostWinnerDefences = new ArrayList<Player>();
        //this.territoryMoreAttacked = null;
        //this.territoryMoreConquered = null;
    }
    
    public static StatisticGameManager getInstance() {
        if (instance == null)
            instance = new StatisticGameManager();
        return instance;
    }

    public String getPlayTime() {
        return playTime;
    }

    public ArrayList<Player> getMoreAttacker() {
        Board board = Board.getInstance();
        ArrayList<Player> moreAttacker = new ArrayList<Player>();
        int attacksNumber = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (attacksNumber < board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttacks()) {
                attacksNumber = board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttacks();
                moreAttacker.clear();
                moreAttacker.add(board.getPlayer(i));
            } else {
                if (attacksNumber == board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttacks())
                    moreAttacker.add(board.getPlayer(i));
            }
        }
        return moreAttacker;
    }

    public ArrayList<Player> getMoreDefender() {
        Board board = Board.getInstance();
        ArrayList<Player> moreDefender = new ArrayList<Player>();
        int defencesNumber = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (defencesNumber < board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefences()) {
                defencesNumber = board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefences();
                moreDefender.clear();
                moreDefender.add(board.getPlayer(i));
            } else {
                if (defencesNumber == board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefences()) {
                    moreDefender.add(board.getPlayer(i));
                }
            }
        }
        return moreDefender;
    }

    public ArrayList<Player> getMostWinnerAttacks() {
        Board board = Board.getInstance();
        ArrayList<Player> mostWinnerAttacks = new ArrayList<Player>();
        int numberOfAttackVictories = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (numberOfAttackVictories < board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttackWins()) {
                numberOfAttackVictories = board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttackWins();
                mostWinnerAttacks.clear();
                mostWinnerAttacks.add(board.getPlayer(i));
            } else {
                if (numberOfAttackVictories == board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttackWins()) {
                    mostWinnerAttacks.add(board.getPlayer(i));
                }
            }
        }
        return mostWinnerAttacks;
    }

    public ArrayList<Player> getMostWinnerDefences() {
        Board board = Board.getInstance();
        ArrayList<Player> mostWinnerDefences = new ArrayList<Player>();
        int numberOfDefencesVictories = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (numberOfDefencesVictories < board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefenceWins()) {
                numberOfDefencesVictories = board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefenceWins();
                mostWinnerDefences.clear();
                mostWinnerDefences.add(board.getPlayer(i));
            } else {
                if (numberOfDefencesVictories == board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefenceWins()) {
                    mostWinnerDefences.add(board.getPlayer(i));
                }
            }
        }
        return mostWinnerDefences;
    }

    public ArrayList<Territory> getTerritoryMoreAttacked(ArrayList<Territory> allTerritories) {
        ArrayList<Territory> territoryMoreAttacked = new ArrayList<Territory>();
        int attacksNumber = 0;
        for (int i = 0; i < allTerritories.size(); i++) {
            if(attacksNumber < allTerritories.get(i).getNumAttacks()) {
                attacksNumber = allTerritories.get(i).getNumAttacks();
                territoryMoreAttacked.clear();
                territoryMoreAttacked.add(allTerritories.get(i));
            } else {
                if(attacksNumber == allTerritories.get(i).getNumAttacks()) {
                    territoryMoreAttacked.add(allTerritories.get(i));
                }
            }
        }
        return territoryMoreAttacked;
    }

    public ArrayList<Territory> getTerritoryMoreConquered(ArrayList<Territory> allTerritories) {
        ArrayList<Territory> territoryMoreConquered = new ArrayList<Territory>();
        int conquestsNumber = 0;
        for (int i = 0; i < allTerritories.size(); i++) {
            if(conquestsNumber < allTerritories.get(i).getNumConquests()) {
                conquestsNumber = allTerritories.get(i).getNumConquests();
                territoryMoreConquered.clear();
                territoryMoreConquered.add(allTerritories.get(i));
            } else {
                if(conquestsNumber == allTerritories.get(i).getNumConquests()) {
                    territoryMoreConquered.add(allTerritories.get(i));
                }
            }
        }
        return territoryMoreConquered;
    }
    
    public void setInitTime(Date d) {
        initTime = d;
    }
    
    public void setPlayTime() {
        endTime = new Date();
        long ms = endTime.getTime() - initTime.getTime();
        int s = (int) ((ms / 1000) % 60);
        int m = (int) (((ms / 1000) / 60) % 60);
        int h = (int) ((ms / 1000) / 3600);
        playTime = h+"h"+m+"min"+s+"s";
    }
    
//    public void setMoreAttacker() {
//        Board board = Board.getInstance();
//        int attacksNumber = 0;
//        for (int i = 0; i < board.getPlayers().size(); i++) {
//            if (attacksNumber < board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttacks()) {
//                attacksNumber = board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttacks();
//                this.moreAttacker.clear();
//                this.moreAttacker.add(board.getPlayer(i));
//            } else {
//                if (attacksNumber == board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttacks()) {
//                    this.moreAttacker.add(board.getPlayer(i));
//                }
//            }
//        }
//    }
    
//    public void setMoreDefender() {
//        Board board = Board.getInstance();
//        int defencesNumber = 0;
//        for (int i = 0; i < board.getPlayers().size(); i++) {
//            if (defencesNumber < board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefences()) {
//                defencesNumber = board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefences();
//                this.moreDefender.clear();
//                this.moreDefender.add(board.getPlayer(i));
//            } else {
//                if (defencesNumber == board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefences()) {
//                    this.moreDefender.add(board.getPlayer(i));
//                }
//            }
//        }
//    }
    
//    public void setMostWinnerAttacks() {
//        Board board = Board.getInstance();
//        int numberOfAttackVictories = 0;
//        for (int i = 0; i < board.getPlayers().size(); i++) {
//            if (numberOfAttackVictories < board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttackWins()) {
//                numberOfAttackVictories = board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttackWins();
//                this.mostWinnerAttacks.clear();
//                this.mostWinnerAttacks.add(board.getPlayer(i));
//            } else {
//                if (numberOfAttackVictories == board.getPlayer(i).getStatisticPlayerManager().getNumberOfAttackWins()) {
//                    this.mostWinnerAttacks.add(board.getPlayer(i));
//                }
//            }
//        }
//    }
    
//    public void setMostWinnerDefences() {
//        Board board = Board.getInstance();
//        int numberOfDefencesVictories = 0;
//        for (int i = 0; i < board.getPlayers().size(); i++) {
//            if (numberOfDefencesVictories < board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefenceWins()) {
//                numberOfDefencesVictories = board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefenceWins();
//                this.mostWinnerDefences.clear();
//                this.mostWinnerDefences.add(board.getPlayer(i));
//            } else {
//                if (numberOfDefencesVictories == board.getPlayer(i).getStatisticPlayerManager().getNumberOfDefenceWins()) {
//                    this.mostWinnerDefences.add(board.getPlayer(i));
//                }
//            }
//        }
//    }
    
//    public void setTerritoryMoreAttacked(ArrayList<Territory> allTerritories) {
//        int attacksNumber = 0;
//        for (int i = 0; i < allTerritories.size(); i++) {
//            if(attacksNumber < allTerritories.get(i).getNumAttacks()) {
//                attacksNumber = allTerritories.get(i).getNumAttacks();
//                this.territoryMoreAttacked.clear();
//                this.territoryMoreAttacked.add(allTerritories.get(i));
//            } else {
//                if(attacksNumber == allTerritories.get(i).getNumAttacks()) {
//                    this.territoryMoreAttacked.add(allTerritories.get(i));
//                }
//            }
//        }
//    }
    
//    public void setTerritoryMoreConquested(ArrayList<Territory> allTerritories) {
//        int conquestsNumber = 0;
//        for (int i = 0; i < allTerritories.size(); i++) {
//            if(conquestsNumber < allTerritories.get(i).getNumConquests()) {
//                conquestsNumber = allTerritories.get(i).getNumConquests();
//                this.territoryMoreConquered.clear();
//                this.territoryMoreConquered.add(allTerritories.get(i));
//            } else {
//                if(conquestsNumber == allTerritories.get(i).getNumConquests()) {
//                    this.territoryMoreConquered.add(allTerritories.get(i));
//                }
//            }
//        }
//    }
}

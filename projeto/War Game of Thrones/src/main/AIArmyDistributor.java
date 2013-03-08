package main;

import gui.InGameGUIController;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import models.AIPlayer;
import models.BackEndTerritory;
import models.Board;
import models.CardTerritory;
import models.Difficulty;
import models.Region;
import models.RepositoryCardsTerritory;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import util.Entity;

public class AIArmyDistributor extends Entity implements MovementCompleteListener {

    private AIPlayer player;
    private IAHelper helper;
    private Difficulty d;
    private Map m;
    private MapMover mover;
    private LinkedList<Territory> territoriesToZoom;
    private HashMap<BackEndTerritory, Integer> armiesPlaced;
    private int pendingArmies;

    public AIArmyDistributor(GameScene scene, AIPlayer p, IAHelper helper, Map m, MapMover mover) {
        this.player = p;
        this.helper = helper;
        this.mover = mover;
        this.m = m;
        d = player.getDifficulty();
    }

    public void start() {
        System.out.println("IA ARMY DISTRIBUTOR START");
        InGameGUIController.getInstance().startPlayerInitialDistribution();
        List<CardTerritory> trade = player.getDifficulty().tradeCards();
        if (trade != null && RepositoryCardsTerritory.checkCardsTradeable(trade)) {
            System.out.println("IA TRADE CARDS");
            int oldArmies = player.getTotalPendingArmies();
            RepositoryCardsTerritory.getInstance().swapCards(trade, player);
            int newArmies = player.getTotalPendingArmies() - oldArmies;
            InGameGUIController.getInstance().updatePlayersData();
            InGameGUIController.getInstance().setRavenMessage(player.getName() + " trocou suas cartas e conseguiu " + newArmies + " exércitos");
        }
        pendingArmies = player.getTotalPendingArmies();
        armiesPlaced = d.distributeArmies();
        Territory[] frontTerr = m.getTerritories();
        territoriesToZoom = new LinkedList<Territory>();
        for (Territory t : frontTerr) {
            BackEndTerritory tBack = t.getBackEndTerritory();
            if (tBack.getOwner().equals(player) && gotArmies(tBack, armiesPlaced)) {
                System.out.println(t + " got armies, sending to queue");
                territoriesToZoom.offer(t);
            }
        }
        territoriesToZoom = sortList(territoriesToZoom);
        processNextZoom();
    }

    private void processNextZoom() {
        Territory next = territoriesToZoom.poll();
        if (next != null) {
            int placedCount = armiesPlaced.get(next.getBackEndTerritory());
            pendingArmies -= placedCount;
//            pendingArmies -= next.getBackEndTerritory().getNumArmies() - 1;
            InGameGUIController.getInstance().showRemainingPendingArmies(player, pendingArmies);
            mover.activate(next.getArmyRelativePos(), this);
        } else if (Board.getInstance().isOnInitialSetup()) {
            TurnHelper.getInstance().changeTurn();
        } else {
            GameScene.getInstance().startAIAttackerAnim(player);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, float delta) {
        super.update(gc, sb, delta);
    }

    @Override
    public void onMovementComplete() {
        processNextZoom();
    }

    private boolean gotArmies(BackEndTerritory tBack, HashMap<BackEndTerritory, Integer> armiesPlaced) {
        return armiesPlaced.containsKey(tBack);
    }

    private static LinkedList<Territory> sortList(LinkedList<Territory> territoriesToZoom) {
        LinkedList<Territory> resp = new LinkedList<Territory>();
        Territory t;
        while (!territoriesToZoom.isEmpty()) {
            //get a random territory and at to the queue
            Territory first = territoriesToZoom.remove((int) (Math.random() * territoriesToZoom.size()));
            resp.offer(first);
            territoriesToZoom.remove(first);
            BackEndTerritory firstBack = first.getBackEndTerritory();
            BackEndTerritory currBack;
            //get adjacent territories
            for (int i = 0; i < territoriesToZoom.size(); i++) {
                t = territoriesToZoom.get(i);
                currBack = t.getBackEndTerritory();
                if (currBack.isNeighbour(firstBack) && sameRegion(currBack, firstBack)) {
                    territoriesToZoom.remove(t);
                    resp.offer(t);
                }
            }

            Region firstRegion = firstBack.getRegion();
            //get non-adjacent territories in the same region
            for (int i = 0; i < territoriesToZoom.size(); i++) {
                t = territoriesToZoom.get(i);
                currBack = t.getBackEndTerritory();
                if (currBack.getRegion().equals(firstRegion)) {
                    territoriesToZoom.remove(t);
                    resp.offer(t);
                }
            }
        }

        return resp;
    }

    private static boolean sameRegion(BackEndTerritory t1, BackEndTerritory t2) {
        Region r1 = t1.getRegion();
        Region r2 = t2.getRegion();
        return r1.equals(r2);
    }
}

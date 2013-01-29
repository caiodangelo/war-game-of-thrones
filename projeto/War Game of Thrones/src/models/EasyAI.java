package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyAI extends Difficulty {

    public AIPlayer player;

    /**
     * Faz a IA distribuir os exércitos pendentes para os territórios que ela
     * possui.
     */
    @Override
    public void distributeArmies() {
        // Distribui os exércitos de forma totalmente aleatória
        while (player.getPendingArmies() > 0) {
            int numTerritories = player.getTerritories().size();
            int territoryToDistribute = new Random().nextInt(numTerritories);
            player.distributeArmies(player.getTerritories().get(territoryToDistribute), 1);
        }
    }

    /**
     * Pergunta à IA se ela deseja continuar atacando, ou se deseja terminar a
     * rodada de ataque e passar para a de movimentação.
     */
    @Override
    protected boolean keepAttacking() {
        int numSurplusArmies = 0;
        for (Territory territory : player.getTerritories()) {
            numSurplusArmies += territory.getSurplusArmies();
        }
        // Entre 0 e 19 exércitos de sobra, ele terá X * 5% de chance a mais de atacar. Ou seja, quanto mais exército
        // de sobra o jogador tiver, maior a chance dele querer continuar atacando, até um máximo de 90% de chance.
        int chance = numSurplusArmies * 5;
        chance = (chance > 90) ? 90 : chance;
        chance = new Random().nextInt(chance);
        return chance < 90; // 90% máximo de chance de continuar atacando.
    }

    /**
     * Pergunta à IA qual o próximo ataque dela. A classe AttackData contém o
     * território de origem e destino do ataque, e o número de exércitos que
     * será usado para atacar.
     */
    @Override
    public TerritoryTransaction nextAttack() {
        Territory origin = null;
        Territory destiny = null;
        if (keepAttacking()) {
            for (int control = 0; control < 100; control++) { // Control serve só como safeguard, caso dê merda e entre em loop infinito.
                switch (player.getMission().getType()) {
                    case Mission.TYPE_HOUSE: // Pega o primeiro território do adversário que eu posso atacar
                        for (Territory territory : player.getMission().getHouse().getPlayer().getTerritories()) {
                            List<Territory> origins = new ArrayList<Territory>();
                                for (Territory neighbour : territory.getNeighbours()) {
                                    if (neighbour.getOwner() == player && origin.getSurplusArmies() >= 1)
                                        origins.add(neighbour);
                                }
                            if (origins.size() > 0) {
                                    // Escolhe o território de origem com o maior número de exércitos
                                    int maxArmies = 0;
                                    for (Territory possibleOrigin: origins) {
                                        if (possibleOrigin.getSurplusArmies() > maxArmies) {
                                            maxArmies = possibleOrigin.getSurplusArmies();
                                            origin = possibleOrigin;
                                        }
                                    }
                                    destiny = territory;
                                    int numArmies = origin.getSurplusArmies() > 3 ? 3 : origin.getSurplusArmies();
                                    return new TerritoryTransaction(origin, destiny, numArmies);
                                }
                        }
                        break;
                    case Mission.TYPE_REGION: // Pega o primeiro território do continente objetivo que eu posso atacar
                        for (Region region : player.getMission().getRegions()) {
                            for (Territory territory : region.getTerritories()) {
                                List<Territory> origins = new ArrayList<Territory>();
                                for (Territory neighbour : territory.getNeighbours()) {
                                    if (neighbour.getOwner() == player && origin.getSurplusArmies() >= 1)
                                        origins.add(neighbour);
                                }
                                if (origins.size() > 0) {
                                    // Escolhe o território de origem com o maior número de exércitos
                                    int maxArmies = 0;
                                    for (Territory possibleOrigin: origins) {
                                        if (possibleOrigin.getSurplusArmies() > maxArmies) {
                                            maxArmies = possibleOrigin.getSurplusArmies();
                                            origin = possibleOrigin;
                                        }
                                    }
                                    destiny = territory;
                                    int numArmies = origin.getSurplusArmies() > 3 ? 3 : origin.getSurplusArmies();
                                    return new TerritoryTransaction(origin, destiny, numArmies);
                                }
                            }
                        }
                        break;
                    case Mission.TYPE_TERRITORY: // Pega o primeiro território que eu posso atacar
                        // Não faz nada, pois pegar o primeiro terrítório atacável já é feito abaixo
                        break;
                }
                // Se não tiver nenhum território que eu possa atacar que satisfaça diretamente meu objetivo, a IA vai
                // atacar qualquer merda e ver oq vai acontecer
                destiny = getRandomAttackableTerritory();
                for (Territory neighbour : destiny.getNeighbours()) {
                    if (neighbour.getOwner() == player)
                        origin = neighbour;
                }
                int numArmies = origin.getSurplusArmies() > 3 ? 3 : origin.getSurplusArmies();
                return new TerritoryTransaction(origin, destiny, numArmies);
            }
        }
        return null;
    }

    /**
     * Pergunta à IA quantos exércitos que atacaram ela deseja mover para o
     * território que foi conquistado (apenas se foi conquistado).
     *
     * @param qtdPodeMover Um número entre 1 e 3
     */
    @Override
    public int moveAfterConquest(Territory origin, Territory conquered, int numberCanMove) {
        return numberCanMove; // A IA fácil acha que é sempre bom mover a porra toda que puder
    }

    /**
     * Pergunta à IA se ela deseja movimentar mais algum exército para outro
     * território, ou se deseja terminar a movimentação e passar a vez.
     *
     * @return
     */
    @Override
    protected boolean keepMoving() {
        int chance = new Random().nextInt(100);
        return chance < 80; // 80% de chance de continuar movendo.
    }

    @Override
    public TerritoryTransaction nextMove() {
        if (keepMoving()) {
            Territory origin = null;
            Territory destiny = null;
            for (Territory territory : player.getTerritories()) {
                if (keepMoving() && territory.getNumArmiesCanMoveThisRound() >= 1) {
                    for (Territory neighbour : territory.getNeighbours()) {
                        if (neighbour.getOwner() == player && neighbour.getNumArmies() <= territory.getNumArmies()) {
                            // AI fácil acha que só é bom mover para equilibrar o número de exércitos
                            origin = territory;
                            destiny = neighbour;
                            int numArmies = new Random().nextInt(origin.getNumArmiesCanMoveThisRound());
                            return new TerritoryTransaction(origin, destiny, numArmies);
                        }
                    }
                }
            }
        }
        return null; // Se de algum jeito a AI não escolher nada, retorna null e diz que não quer mover mais.
    }

    private Territory getRandomAttackableTerritory() {
        List<Territory> defenders = new ArrayList<Territory>();
        for (Territory territory : player.getTerritories()) {
            if (territory.getSurplusArmies() > 0) {
                for (Territory neighbour : territory.getNeighbours()) {
                    if (neighbour.getOwner() != player) {
                        defenders.add(neighbour);
                    }
                }
            }
        }
        int index = new Random().nextInt(defenders.size());
        return defenders.get(index);
    }
}

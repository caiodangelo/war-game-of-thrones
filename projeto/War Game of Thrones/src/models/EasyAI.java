package models;

import java.util.Random;

public class EasyAI extends Difficulty {

    /**
     * Faz a IA distribuir os exércitos pendentes para os territórios que ela
     * possui.
     */
    @Override
    public void distributeArmies() {
    }

    /**
     * Pergunta à IA se ela deseja continuar atacando, ou se deseja terminar a
     * rodada de ataque e passar para a de movimentação.
     */
    @Override
    protected boolean keepAttacking() {
        return true;
    }

    /**
     * Pergunta à IA qual o próximo ataque dela. A classe AttackData contém o
     * território de origem e destino do ataque, e o número de exércitos que
     * será usado para atacar.
     */
    @Override
    public TerritoryTransaction nextAttack() {
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
        if (keepAttacking()) {
            Territory origin = null;
            Territory destiny = null;
            int amount = new Random().nextInt(origin.getNumArmiesCanMoveThisRound());
            TerritoryTransaction move = new TerritoryTransaction(origin, destiny, amount);
            return move;
        }
        return null;
    }
}
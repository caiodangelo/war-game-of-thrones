package models;

abstract class Difficulty {

    /**
     * Faz a IA distribuir os exércitos pendentes para os territórios que ela
     * possui.
     */
    abstract public void distributeArmies();

    /**
     * Pergunta à IA se ela deseja continuar atacando, ou se deseja terminar a
     * rodada de ataque e passar para a de movimentação.
     */
    abstract protected boolean keepAttacking();

    /**
     * Pergunta à IA qual o próximo ataque dela. A classe AttackData contém o
     * território de origem e destino do ataque, e o número de exércitos que
     * será usado para atacar.
     */
    abstract public TerritoryTransaction nextAttack();

    /**
     * Pergunta à IA quantos exércitos que atacaram ela deseja mover para o
     * território que foi conquistado (apenas se foi conquistado).
     *
     * @param qtdPodeMover Um número entre 1 e 3
     */
    abstract public int moveAfterConquest(Territory origin, Territory conquered, int numberCanMove);

    /**
     * Pergunta à IA se ela deseja movimentar mais algum exército para outro
     * território, ou se deseja terminar a movimentação e passar a vez.
     *
     * @return
     */
    abstract protected boolean keepMoving();

    /**
     * Retorna um objeto informando qual vai ser a próxima movimentação da IA ou
     * nulo se a IA não quiser mais movimentar exércitos.
     *
     * @return
     */
    abstract public TerritoryTransaction nextMove();
}
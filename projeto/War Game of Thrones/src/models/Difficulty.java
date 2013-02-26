package models;

import java.util.List;

abstract class Difficulty {

    public AIPlayer player;

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
     * Pergunta à IA se ela quer trocar suas cartas. Se ela quiser, retornará um array de 3 cartas,
     * senão, retornará nulo.
     */
    abstract public List<CardTerritory> tradeCards();

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
    abstract public int moveAfterConquest(BackEndTerritory origin, BackEndTerritory conquered, int numberCanMove);

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

    /**
     * Retorna o tipo de carta selecionada para a troca.
     * Retorna 0 se não for possível uma troca válida, de 1 a 4 para os tipos padrões de cartas, e 5 para trocar uma
     * carta de cada tipo.
     */
    protected int getCardSelectedType(int triangleCards, int circleCards, int squareCards, int jokerCards) {
        int selectedType = 0;
        if ((triangleCards + jokerCards) >= 3) {
            selectedType = CardTerritory.TRIANGLE;
        } else if ((circleCards + jokerCards) >= 3) {
            selectedType = CardTerritory.CIRCLE;
        } else if ((squareCards + jokerCards) >= 3) {
            selectedType = CardTerritory.SQUARE;
        } else if (jokerCards >= 3) {
            selectedType = CardTerritory.JOKER;
        } else {
            triangleCards = triangleCards >= 1 ? 1 : 0;
            circleCards = circleCards >= 1 ? 1 : 0;
            squareCards = squareCards >= 1 ? 1 : 0;
            jokerCards = jokerCards >= 1 ? 1 : 0;
            if ((triangleCards + circleCards + squareCards + jokerCards) >= 3) {
                selectedType = 5; // Indica que vou trocar uma de cada tipo
            }
        }
        return selectedType;
    }
}
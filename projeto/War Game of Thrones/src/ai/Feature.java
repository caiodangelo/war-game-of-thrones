package ai;

import models.Board;
import models.Player;

/**
 * Feature é um aspecto de avaliação para gerar uma "nota" para um estado de jogo.
 * Toda feature avalia um estado de jogo e um jogador, retornando um número real que é
 * a nota para aquele estado de jogo, visto de um certo ponto de vista. Cada ponto de vista é uma
 * feature, e cada feature tem uma importância e uma escala diferente.
 *
 * A importância serve para indicar o quanto dada feature vai afetar a nota final de um estado de jogo,
 * e a escala serve para nivelar o resultado de todas as features para um valor entre 0 e 1.
 *
 * Um exemplo de feature seria a DistanceToFrontierFeature, que avalia numa escala de 0 a 1 a distribuição
 * dos exércitos do jogador entre territórios de fronteira e territórios internos (que só tem fronteira
 * com territórios do próprio jogador), usando uma fórmula que atribui valores mais baixos a exércitos
 * mais longe da fronteira, e valores mais altos a exércitos perto da fronteira:
 *
 *                                         Nº exércitos total do jogador
 * Feature Result =  ----------------------------------------------------------------------
 *                    ∑(t ∈ territorios_jogador) num_exercitos(t) * distância_fronteira(t)
 *
 * @author rodrigo
 */
public abstract class Feature {

    // A importância dessa feature. Basicamente é pra fazer uma média ponderada com as outras features
    protected double importance;
    // O fator de escala serve para igualar essa feature com as outras, numa escala igual (por exemplo, um valor de 0 a 1)
    protected double scaleFactor;

    public abstract double calculate(Board gameState, Player player);

    /**
     * Retorna a nota desta feature multiplicada por sua escala, de forma a dar uma nota que varia sempre de (0 a 1],
     * e depois multiplica por sua importância, para gerar a nota final desta feature.
     */
    public double calculateScaledGrade(Board gameState, Player player) {
        return calculate(gameState, player) * scaleFactor * importance;
    }

    public double getImportance() {
        return importance;
    }
}

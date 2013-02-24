package ai;

import models.Board;
import models.Player;

/**
 *
 * @author rodrigo
 *
 * Feature é um aspecto de avaliação para determinar o próximo movimento da IA.
 * Toda feature avalia um estado de jogo e um jogador, retornando um número real que é
 * como se fosse uma nota para aquele estado de jogo, visto de um certo ponto de vista específico.
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
 */
public abstract class Feature {

    // A importância dessa feature. Basicamente é pra fazer uma média ponderada com as outras features
    protected double importance;

    // O fator de escala serve para igualar essa feature com as outras, numa escala igual (por exemplo, um valor de 0 a 1)
    protected double scaleFactor;

    public abstract double calculate(Board gameState, Player player);

}
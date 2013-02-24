package ai;

import java.util.List;
import models.Board;
import models.Player;

/**
 *
 * @author rodrigo
 */
public abstract class Evaluator {

    protected Board currentGameState;
    protected List<Feature> features;
    protected Player player;

    public Evaluator(Board currentGameState, Player player) {
        this.currentGameState = currentGameState;
        this.player = player;
    }

    /**
     * Cada evaluator diferente implementará essa função de jeitos diferentes, a fim de copiar
     * o estado atual do tabuleiro e executar uma possível ação dentre as disponíveis para o jogador.
     * Esse tabuleiro simulado será avaliado para se definir se essa ação trouxe mais vantagens do
     * que outra ação disponível.
     */
    public abstract Board simulateActionExecution();

    /**
     * Aplica todos as features desse evaluator no tabuleiro gerado no jogador selecionado,
     * para gerar uma "nota" para esse tabuleiro gerado. Essa nota será utilizada depois
     * para decidir qual ação será tomada pela IA.
     */
    public abstract double evaluate(Board gameState);

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public void addFeature(Feature feature) {
        this.features.add(feature);
    }

    public Board getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(Board currentGameState) {
        this.currentGameState = currentGameState;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
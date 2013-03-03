package ai;

import java.util.ArrayList;
import java.util.List;
import models.Board;
import models.Player;

/**
 *
 * @author rodrigo
 */
public abstract class Evaluator {

    protected Board currentGameState;
    protected Board simulatedGameState;
    protected List<Feature> features;
    protected Player player;
    protected double simulatedRating = Double.NEGATIVE_INFINITY;
    protected double currentRating = Double.NEGATIVE_INFINITY;

    public Evaluator(Board currentGameState, Player player) {
        this.currentGameState = currentGameState;
        this.player = player;
        this.features = new ArrayList<Feature>();
    }

    /**
     * Cada evaluator diferente implementará essa função de jeitos diferentes, a fim de copiar
     * o estado atual do tabuleiro e executar uma possível ação dentre as disponíveis para o jogador.
     * Esse tabuleiro simulado será avaliado para se definir se essa ação trouxe mais vantagens do
     * que outra ação disponível.
     */
    protected abstract Board simulateActionExecution();

    public Board getSimulatedGameState() {
        if (simulatedGameState == null) {
            simulatedGameState = simulateActionExecution();
        }
        return simulatedGameState;
    }

    public Player getSimulatedPlayer() {
        Player clonedPlayer = null;
        for (Player newPlayer : getSimulatedGameState().getPlayers()) {
            if (player.getName().equals(player.getName())) {
                clonedPlayer = newPlayer;
            }
        }
        return clonedPlayer;
    }

    /**
     * Aplica todos as features desse evaluator no tabuleiro gerado no jogador selecionado,
     * para gerar uma "nota" para esse tabuleiro gerado. Essa nota será utilizada depois
     * para decidir qual ação será tomada pela IA.
     */
    public double evaluate() {
        double grade = 0;
        double importanceSum = 0;
        for (Feature feature : features) {
            grade += feature.calculateScaledGrade(getSimulatedGameState(), getSimulatedPlayer());
            importanceSum += feature.getImportance();
        }
        simulatedRating = grade / importanceSum;
        return simulatedRating;
    }

    public double evaluateCurrentGameState() {
        double grade = 0;
        double importanceSum = 0;
        for (Feature feature : features) {
            grade += feature.calculateScaledGrade(currentGameState, player);
            importanceSum += feature.getImportance();
        }
        currentRating = grade / importanceSum;
        return currentRating;
    }

    /**
     * Compara a nota do GameState gerado pela ação aplicada neste evaluator com a nota
     * do GameState original, e retorna true se a nota do novo GameState for maior ou 
     * igual do que a nota do GameState original, senão retorna false.
     */
    public boolean isGoodAction() {
        if (simulatedRating > -1000.0 && currentRating > -1000.0) {
            return simulatedRating >= currentRating;
        }
        return false;
    }

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
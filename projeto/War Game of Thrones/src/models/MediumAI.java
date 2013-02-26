package models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rodcastro
 *
 */
public class MediumAI extends Difficulty {

    /*Missão Região: A IA deve colocar seus exercitos em territorios que são necessarios para completar a
     * sua missão. Caso já tenha mais de 3 exercitos em todos esses territorios, ela 
     * deve colocar em territorios vizinhos aos territorios necessarios para completar a missão.
     * Caso ainda sobre exercitos, ele coloca nos demais.
     * Missão Território: Tenta equilibrar os exercitos entre os territorios.
     * Missão Casa: colocar mais exercitos onde há vizinhos cujos donos pertencem a Casa a ser derrotada.
     */
    @Override
    public void distributeArmies() {
        int numberOfSearches = 0;
        while (player.getPendingArmies() > 0) {
            switch (player.getMission().getType()) {
                case Mission.TYPE_REGION:
                    distributeAccordingRegionMission(numberOfSearches);
                    break;
                case Mission.TYPE_TERRITORY:
                    distributeAccordingTerritoryMission();
                    break;
                case Mission.TYPE_HOUSE:
                    distributeAccordingHouseMission();
                    break;
            }  
            numberOfSearches++;
        }
    }
   
    public void distributeAccordingHouseMission() {
        boolean successful = false;
        int numArmies;
        House house  = player.getHouse();
        for (BackEndTerritory territory : player.getTerritories()) {
            List<BackEndTerritory> neighbours = territory.getNeighbours();
            for (BackEndTerritory neighbour : neighbours) {
                if (house.equals(neighbour.getOwner().getHouse())) {
                    while (!successful) {
                        if (territory.getNumArmies() > 3) {
                            numArmies = 3;
                            successful = player.distributeArmies(territory, numArmies);
                            numArmies--;
                        }
                        else {
                            numArmies = 6;
                            successful = player.distributeArmies(territory, numArmies);
                            numArmies--;                            
                        }
                    }
                }
                else {
                    player.distributeArmies(territory, 1);
                }
            }
        }
    }
    
    public void distributeAccordingTerritoryMission() {
        boolean successful;
        for (int i = 0; i < player.getTerritories().size(); i++) {
            BackEndTerritory territory = player.getTerritories().get(i);
            successful = distribute(territory, 0);
            if (! successful) break;
        }
    }
    
    public void distributeAccordingRegionMission(int numberOfSearches) {
        ArrayList<BackEndTerritory> aux = new ArrayList<BackEndTerritory>();        
        for (BackEndTerritory territory : player.getTerritories()) {
            for (Region region : player.getMission().getRegions()) {
                if (territory.belongToRegion(region)) //Se o territorio for crucial para completar a missao
                    distribute(territory, numberOfSearches);
                else
                    aux.add(territory); //Adiciona numa lista auxiliar                     
            }
        }
        for (BackEndTerritory territory : aux) { //Verifica se tem vizinhos que são cruciais para completar a missão
            List<BackEndTerritory> neighbours = territory.getNeighbours();
            for (BackEndTerritory neighbour : neighbours) {
                for (Region region : player.getMission().getRegions()) {
                    if (neighbour.belongToRegion(region))
                        distribute(territory, numberOfSearches);                        
                }
            }
        }        
    }

    /*
     * Metodo auxiliar que distribui de fato os exercitos. Parametro "num" indica quantas vezes a IA 
     * já acessou os seus territorios para a distruibuição
     */
    public boolean distribute(BackEndTerritory territory, int numberOfSearches) {
        int numArmies = territory.getNumArmies();
        if (numberOfSearches == 0) {
            if ((numArmies == 1) && (player.getPendingArmies() >= 2)) {
                return player.distributeArmies(territory, 2);
            }
            if ((numArmies == 2) && (player.getPendingArmies() >= 1)) {                
                return player.distributeArmies(territory, 1);
            }
        } 
        else {
            if (player.getPendingArmies() > 0) {                
                return player.distributeArmies(territory, 1);
            }
        }
        return false;
    }

    @Override
    public List<CardTerritory> tradeCards() {
        if (player.numCards() >= 3) {
            List<CardTerritory> cards = new ArrayList<CardTerritory>();
            int triangleCards = 0, circleCards = 0, squareCards = 0, jokerCards = 0;
            for (CardTerritory card : player.getCards()) {
                switch (card.getType()) {
                    case CardTerritory.CIRCLE:
                        circleCards++;
                        break;
                    case CardTerritory.TRIANGLE:
                        triangleCards++;
                        break;
                    case CardTerritory.SQUARE:
                        squareCards++;
                        break;
                    case CardTerritory.JOKER:
                        jokerCards++;
                        break;
                }
            }
            int selectedType = getCardSelectedType(triangleCards, circleCards, squareCards, jokerCards);
            switch (selectedType) {
                case 1:
                case 2:
                case 3:
                case 4:
                    for (CardTerritory card : player.getCards()) {
                        if (card.getType() == selectedType) {
                            cards.add(card);
                        }
                    }
                    if (cards.size() >= 3) {
                        return cards;
                    }
                    break;
                case 5:
                    boolean[] chosenTypes = new boolean[4];
                    for (int i = 0; i < chosenTypes.length; i++) {
                        chosenTypes[i] = false;
                    }
                    for (CardTerritory card : player.getCards()) {
                        if (!chosenTypes[card.getType() - 1]) {
                            cards.add(card);
                            chosenTypes[card.getType() - 1] = true;
                        }
                    }
                    if (cards.size() >= 3) {
                        return cards;
                    }
                    break;
            }
        }
        return null; // Não conseguiu arranjar nenhum esquema de troca
    }
    
    /*
     * A IA deve continuar atacando se ele tiver num aceitavel de exercitos de sobra.
     * E deve parar se seus territorios estiverem com num baixos.
     */
    @Override
    protected boolean keepAttacking() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * A IA deve escolher para atacar algum territorio que seja necessario para completar a missão
     * e que ela seja facil de ser conquistada, ou seja, q tenha poucos exercitos de defesa. 
     * Apos isso ela deve procurar apenas territorios q tenham no max o msm num de exercitos q o seu 
     * territorio atacante.
     */
    @Override
    public TerritoryTransaction nextAttack() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int moveAfterConquest(BackEndTerritory origin, BackEndTerritory conquered, int numberCanMove) {
        if (origin.getNumArmies() >= 6) {
            return numberCanMove;
        }
        if (origin.getNumArmies() <= 3) {
            return 1;
        }
        if ((origin.getNumArmies() == 4) || (origin.getNumArmies() == 5)) {
            if (2 > numberCanMove) {
                return numberCanMove;
            }
        }
        return 2;
    }

    @Override
    protected boolean keepMoving() {
        /*O jogador continuará a movimentação se tiver um territorio com 1 exercito e esse territorio
         * tiver algum vizinho cujo proprietario seja o proprio jogador, desde que esse territorio vizinho
         * tenha exercitos para movimentar, para que a movimentação seja util
         */
        for (BackEndTerritory myTerritory : player.getTerritories()) {
            if (myTerritory.getNumArmies() == 1) {
                List<BackEndTerritory> neighbours = myTerritory.getNeighbours();
                for (BackEndTerritory territory : neighbours) {
                    if ((territory.getOwner().equals(player)) && (territory.getNumArmiesCanMoveThisRound() >= 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TerritoryTransaction nextMove() {
        int numberFriendNeighbours = 0;
        int dangerousEnemyArmies = 0;
        int numArmies;
        if (keepMoving()) {
            BackEndTerritory origin;
            BackEndTerritory destiny;
            for (BackEndTerritory territory : player.getTerritories()) {
                if (keepMoving() && territory.getNumArmiesCanMoveThisRound() >= 1) {
                    for (BackEndTerritory neighbour : territory.getNeighbours()) {
                        if (neighbour.getOwner().equals(player)) {
                            numberFriendNeighbours++;
                        } else {
                            if (dangerousEnemyArmies < neighbour.getNumArmies()) {
                                dangerousEnemyArmies = neighbour.getNumArmies();
                            }
                        }
                    }
                    //Se houver algum inimigo com um num de exercitos muito grande (razao > 1,5), AI player não fará a movimentação
                    if ((territory.getNumArmies() / dangerousEnemyArmies) <= 1.5) {
                        for (BackEndTerritory neighbour : territory.getNeighbours()) {
                            if (neighbour.getOwner().equals(player) && neighbour.getNumArmies() <= territory.getNumArmies()) {
                                origin = territory;
                                destiny = neighbour;

                                if (numberFriendNeighbours == territory.getNeighbours().size()) {
                                    //Se o territorio estiver cercado de vizinhos amigos, ele distribui exercitos igualmente entre eles
                                    //dexando-o com o min possivel de exercitos
                                    numArmies = (int) Math.ceil(origin.getNumArmiesCanMoveThisRound() / numberFriendNeighbours);
                                    return new TerritoryTransaction(origin, destiny, numArmies);
                                } else {
                                    //Caso contrario, ele tenta dividir os exercitos em igualdade entre eles
                                    numArmies = (int) origin.getNumArmies() / numberFriendNeighbours + 1;
                                    if (numArmies > origin.getNumArmiesCanMoveThisRound()) {
                                        numArmies = origin.getNumArmiesCanMoveThisRound();
                                    }
                                    return new TerritoryTransaction(origin, destiny, numArmies);
                                }

                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}

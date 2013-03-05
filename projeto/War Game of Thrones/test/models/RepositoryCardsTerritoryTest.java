/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author anderson
 */
public class RepositoryCardsTerritoryTest {

    RepositoryCardsTerritory repositoryCards;
    CardTerritory card, joker1, joker2;

    @BeforeClass
    public void setUp() {
        repositoryCards = RepositoryCardsTerritory.getInstance();
    }

    @Test
    public void construtorDeveInicializarVariaveis() {
        repositoryCards = new RepositoryCardsTerritory();
        assertNotNull(repositoryCards.deck);
        assertNotNull(repositoryCards.repository);
    }

    @Test
    public void removeJokerDeveRemoverCoringas() {
        repositoryCards.removeJokers();
        assertEquals(repositoryCards.getDeck().size(), 39);
        assertEquals(repositoryCards.getRepository().size(), 2);
    }

    @Test
    public void sorteioInicialCom6JogadoresDeveSortearTodasAsCartas() {
        Board board = new Board();
        for (int i = 0; i < 6; i++) {
            HumanPlayer p = new HumanPlayer(null);
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }

        repositoryCards.initialRaffle();

        int contador = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            contador += board.getPlayer(i).getCards().size();
        }
        assertEquals(repositoryCards.getDeck().size(), 41);
        assertEquals(contador, 39);
    }

    @Test
    public void sorteioInicialCom2JogadoresDeveSortearTodasAsCartas() {
        Board board = new Board();
        for (int i = 0; i < 2; i++) {
            HumanPlayer p = new HumanPlayer(null);
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }

        repositoryCards.initialRaffle();

        int contador = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            contador += board.getPlayer(i).getCards().size();
        }
        assertEquals(repositoryCards.getDeck().size(), 41);
        assertEquals(contador, 39);
    }

    @Test
    public void sorteioInicialCom5JogadoresDeveSortearTodasAsCartas() {
        Board board = new Board();
        for (int i = 0; i < 5; i++) {
            HumanPlayer p = new HumanPlayer(null) {
            };
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }

        repositoryCards.initialRaffle();

        int contador = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            contador += board.getPlayer(i).getCards().size();
        }
        assertEquals(repositoryCards.getDeck().size(), 41);
        assertEquals(contador, 39);
    }

    @Test
    public void RepositorioDeveEstarVazioAoFimDoSorteio() {
        Board board = new Board();
        for (int i = 0; i < 5; i++) {
            HumanPlayer p = new HumanPlayer(null) {
            };
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }

        repositoryCards.initialRaffle();

        assertEquals(repositoryCards.getRepository().size(), 0);
    }

    @Test
    public void DequeDeveEstarCheioAoFimDoSorteio() {
        Board board = new Board();
        for (int i = 0; i < 6; i++) {
            HumanPlayer p = new HumanPlayer(null) {
            };
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }
        repositoryCards.initialRaffle();

        assertEquals(repositoryCards.getDeck().size(), 41);
    }


    @Test
    public void isDifferentCardsDeveSerTrueQuandoTodasOsTiposDasCartasForemDiferentes(){
        CardTerritory card1 = repositoryCards.deck.get(0);
        CardTerritory card2 = repositoryCards.deck.get(13);
        CardTerritory card3 = repositoryCards.deck.get(35);
        
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertTrue(RepositoryCardsTerritory.isDifferentCards(cards));
    }
    
    @Test
    public void isDifferentCardsDeveSerFalseQuandoTiverCartasDeTiposIguais(){
        CardTerritory card1 = repositoryCards.deck.get(0);
        CardTerritory card2 = repositoryCards.deck.get(2);
        CardTerritory card3 = repositoryCards.deck.get(3);
        
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertFalse(RepositoryCardsTerritory.isDifferentCards(cards));
    }
    
    @Test
    public void isDifferentCardsDeveSerTrueQuandoTiver2CartasCuringasIguais(){
        CardTerritory card1 = repositoryCards.deck.get(0);
        CardTerritory card2 = repositoryCards.deck.get(39);
        CardTerritory card3 = repositoryCards.deck.get(40);
    
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertTrue(RepositoryCardsTerritory.isDifferentCards(cards));
    }
    
    @Test
    public void isSameCardsDeveSerTrueQuandoTodasAsCartasForemIguais(){
        CardTerritory card1 = repositoryCards.deck.get(15);
        CardTerritory card2 = repositoryCards.deck.get(16);
        CardTerritory card3 = repositoryCards.deck.get(17);
        
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertTrue(RepositoryCardsTerritory.isSameCards(cards));
    }
    
    @Test
    public void isSameCardsDeveSerFalseQuandoAlgumaCartaForDiferente(){
        CardTerritory card1 = repositoryCards.deck.get(2);
        CardTerritory card2 = repositoryCards.deck.get(16);
        CardTerritory card3 = repositoryCards.deck.get(17);
        
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertFalse(RepositoryCardsTerritory.isSameCards(cards));
    }
    
    @Test
    public void isSameCardsDeveSerTrueQuandoTiverCartaCuringa(){
        CardTerritory card1 = repositoryCards.deck.get(16);
        CardTerritory card2 = repositoryCards.deck.get(17);
        CardTerritory card3 = repositoryCards.deck.get(40);
        
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertTrue(RepositoryCardsTerritory.isSameCards(cards));
    }
    
    public void swapCardsDeveSerTrueQuandoATrocaForBemSucedida(){
        Player p = new HumanPlayer(null);
        CardTerritory card1 = repositoryCards.deck.get(16);
        CardTerritory card2 = repositoryCards.deck.get(17);
        CardTerritory card3 = repositoryCards.deck.get(18);
        
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);        
        
        p.addCard(card1);
        p.addCard(card2);
        p.addCard(card3);
        
        assertTrue(repositoryCards.swapCards(cards, p));
    }
    
    public void dequeDeveTer3CartasAMaisAposATrocaDeCartas() {     
        CardTerritory card1 = repositoryCards.deck.get(0);
        CardTerritory card2 = repositoryCards.deck.get(1);
        CardTerritory card3 = repositoryCards.deck.get(2);
        repositoryCards.deck.remove(card1);
        repositoryCards.deck.remove(card2);
        repositoryCards.deck.remove(card3);
        int deckSize = repositoryCards.deck.size();
        
        Player p = new HumanPlayer(null);
        p.addCard(card1);
        p.addCard(card2);
        p.addCard(card3);
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);        
        
        repositoryCards.swapCards(cards, p);
        assertEquals(repositoryCards.getDeck().size(), deckSize + 3);
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.LinkedList;
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
        for (int i = 0; i < 42; i++) {
            card = mock(CardTerritory.class);
            repositoryCards.addCardToDeck(card);
        }
        joker1 = mock(CardTerritory.class);
        when(joker1.getType()).thenReturn(4);
        repositoryCards.addCardToDeck(joker1);
        joker2 = mock(CardTerritory.class);
        when(joker2.getType()).thenReturn(4);
        repositoryCards.addCardToDeck(joker2);

        repositoryCards.removeJokers();
        assertEquals(repositoryCards.getDeck().size(), 42);
        assertEquals(repositoryCards.getRepository().size(), 2);

        for (CardTerritory c : repositoryCards.getDeck()) {
            assertNotEquals(c, joker2);
            assertNotEquals(c, joker1);
        }
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
            contador = contador + board.getPlayer(i).getCards().size();
        }
        assertEquals(repositoryCards.getDeck().size(), 44);
        assertEquals(contador, 42);
    }

    @Test
    public void sorteioInicialCom2JogadoresDeveSortearTodasAsCartas() {
        Board board = new Board();
        for (int i = 0; i < 2; i++) {
            HumanPlayer p = new HumanPlayer(null) {
            };
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }

        repositoryCards.initialRaffle();

        int contador = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            contador = contador + board.getPlayer(i).getCards().size();
        }
        assertEquals(repositoryCards.getDeck().size(), 44);
        assertEquals(contador, 42);
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
            contador = contador + board.getPlayer(i).getCards().size();
        }
        assertEquals(repositoryCards.getDeck().size(), 44);
        assertEquals(contador, 42);
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
        for (int i = 0; i < 42; i++) {
            card = mock(CardTerritory.class);
            repositoryCards.addCardToDeck(card);
        }
        joker1 = mock(CardTerritory.class);
        when(joker1.getType()).thenReturn(4);
        repositoryCards.addCardToDeck(joker1);
        joker2 = mock(CardTerritory.class);
        when(joker2.getType()).thenReturn(4);
        repositoryCards.addCardToDeck(joker2);

        repositoryCards.initialRaffle();

        assertEquals(repositoryCards.getDeck().size(), 44);
    }

    @Test
    public void consultSwapTableDeveRetornarOsValoresCertosDeExercitosRecebidosAposATrocaDeCartas() {
        int number;
        number = repositoryCards.consultSwapTable(1);
        assertEquals(number, 4);
        number = repositoryCards.consultSwapTable(2);
        assertEquals(number, 6);
        number = repositoryCards.consultSwapTable(3);
        assertEquals(number, 8);
        number = repositoryCards.consultSwapTable(5);
        assertEquals(number, 12);
        number = repositoryCards.consultSwapTable(6);
        assertEquals(number, 15);
        number = repositoryCards.consultSwapTable(7);
        assertEquals(number, 20);
        number = repositoryCards.consultSwapTable(10);
        assertEquals(number, 35);
    }
    
    @Test
    public void isDifferentCardsDeveSerTrueQuandoTodasOsTiposDasCartasForemDiferentes(){
        CardTerritory card1 = mock(CardTerritory.class);
        CardTerritory card2 = mock(CardTerritory.class);
        CardTerritory card3 = mock(CardTerritory.class);
        
        when(card1.getType()).thenReturn(CardTerritory.JOKER);
        when(card2.getType()).thenReturn(CardTerritory.SQUARE);
        when(card3.getType()).thenReturn(CardTerritory.TRIANGLE);
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertTrue(repositoryCards.isDifferentCards(cards));
    }
    
    @Test
    public void isDifferentCardsDeveSerFalseQuandoTiverCartasDeTiposIguais(){
        CardTerritory card1 = mock(CardTerritory.class);
        CardTerritory card2 = mock(CardTerritory.class);
        CardTerritory card3 = mock(CardTerritory.class);
        
        when(card1.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card2.getType()).thenReturn(CardTerritory.SQUARE);
        when(card3.getType()).thenReturn(CardTerritory.SQUARE);
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertFalse(repositoryCards.isDifferentCards(cards));
    }
    
    @Test
    public void isDifferentCardsDeveSerTrueQuandoTiver2CartasCuringasIguais(){
        CardTerritory card1 = mock(CardTerritory.class);
        CardTerritory card2 = mock(CardTerritory.class);
        CardTerritory card3 = mock(CardTerritory.class);
        
        when(card1.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card2.getType()).thenReturn(CardTerritory.JOKER);
        when(card3.getType()).thenReturn(CardTerritory.JOKER);
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertTrue(repositoryCards.isDifferentCards(cards));
    }
    
    @Test
    public void isSameCardsDeveSerTrueQuandoTodasAsCartasForemIguais(){
        CardTerritory card1 = mock(CardTerritory.class);
        CardTerritory card2 = mock(CardTerritory.class);
        CardTerritory card3 = mock(CardTerritory.class);
        
        when(card1.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card2.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card3.getType()).thenReturn(CardTerritory.CIRCLE);
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertTrue(repositoryCards.isSameCards(cards));
    }
    
    @Test
    public void isSameCardsDeveSerFalseQuandoAlgumaCartaForDiferente(){
        CardTerritory card1 = mock(CardTerritory.class);
        CardTerritory card2 = mock(CardTerritory.class);
        CardTerritory card3 = mock(CardTerritory.class);
        
        when(card1.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card2.getType()).thenReturn(CardTerritory.TRIANGLE);
        when(card3.getType()).thenReturn(CardTerritory.CIRCLE);
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertFalse(repositoryCards.isSameCards(cards));
    }
    
    @Test
    public void isSameCardsDeveSerTrueQuandoTiverCartaCuringa(){
        CardTerritory card1 = mock(CardTerritory.class);
        CardTerritory card2 = mock(CardTerritory.class);
        CardTerritory card3 = mock(CardTerritory.class);
        
        when(card1.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card2.getType()).thenReturn(CardTerritory.JOKER);
        when(card3.getType()).thenReturn(CardTerritory.CIRCLE);
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        assertTrue(repositoryCards.isSameCards(cards));
    }
    
    public void swapCardsDeveSerTrueQuandoATrocaForBemSucedida(){
        Player p = mock(Player.class);
        CardTerritory card1 = mock(CardTerritory.class);
        CardTerritory card2 = mock(CardTerritory.class);
        CardTerritory card3 = mock(CardTerritory.class);
        
        when(card1.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card2.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card3.getType()).thenReturn(CardTerritory.CIRCLE);
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
        for (int i = 0; i < 10; i++) {
            CardTerritory c = mock(CardTerritory.class);
        }
        
        Player p = mock(Player.class);
        CardTerritory card1 = mock(CardTerritory.class);
        CardTerritory card2 = mock(CardTerritory.class);
        CardTerritory card3 = mock(CardTerritory.class);
        
        when(card1.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card2.getType()).thenReturn(CardTerritory.CIRCLE);
        when(card3.getType()).thenReturn(CardTerritory.CIRCLE);
        List<CardTerritory> cards = new ArrayList<CardTerritory>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);        
        
        p.addCard(card1);
        p.addCard(card2);
        p.addCard(card3);
        
        repositoryCards.swapCards(cards, p);
        assertEquals(repositoryCards.getDeck().size(), 13);
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

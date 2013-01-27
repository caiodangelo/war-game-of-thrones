/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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
            HumanPlayer p = new HumanPlayer(null) {};
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }
        
        repositoryCards.initialRaffle(board);
        
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
            HumanPlayer p = new HumanPlayer(null) {};
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }
        
        repositoryCards.initialRaffle(board);

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
            HumanPlayer p = new HumanPlayer(null) {};
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }
        
        repositoryCards.initialRaffle(board);
        
        int contador = 0;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            contador = contador + board.getPlayer(i).getCards().size();
        }
        assertEquals(repositoryCards.getDeck().size(), 44);
        assertEquals(contador, 42);
   }
    
    @Test
    public void RepositorioDeveEstarVazioAoFimDoSorteio(){
        Board board = new Board();
        for (int i = 0; i < 5; i++) {
            HumanPlayer p = new HumanPlayer(null) {};
            board.addPlayer(p, i, Board.HUMAN_PLAYER);
        }
        
        repositoryCards.initialRaffle(board);
        
        assertEquals(repositoryCards.getRepository().size(), 0);
    }
    
    @Test
    public void DequeDeveEstarCheioAoFimDoSorteio(){
        Board board = new Board();
        for (int i = 0; i < 6; i++) {
            HumanPlayer p = new HumanPlayer(null) {};
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
        
        repositoryCards.initialRaffle(board);
        
        assertEquals(repositoryCards.getDeck().size(), 44);
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

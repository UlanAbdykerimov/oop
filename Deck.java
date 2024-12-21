import java.util.*;

public class Deck
{
    private static final int HEARTS = 0;
    private static final int DIAMONDS = 1;
    private static final int SPADES = 2;
    private static final int CLUBS = 3;

    private static final int JACK = 11;
    private static final int QUEEN = 12;
    private static final int KING = 13;
    private static final int ACE = 14;
    
    // Instance variables
    
    // This stores the deck which is a list of the Card objects.
    private ArrayList<Card> deck;
    
    /**
     * This creates a Deck. A Deck starts as a list of 52 cards.
     * We loop through each suit and rank and construct a card
     * and add it to the deck.
     */
    public Deck()
    {
        deck = new ArrayList<Card>();
        
        for(int rank = 2; rank <= ACE; rank++)
        {
            for(int suit = HEARTS; suit <= CLUBS; suit++)
            {
                Card card = new Card(rank, suit);
                deck.add(card);
            }
        }
    }
    
    // Getter method
    
    /**
     * This getter method returns the ArrayList of cards.
     * @return ArrayList<Card> of the Cards.
     */
    public ArrayList<Card> getCards()
    {
        return deck;
    }
    
    /**
     * This deals the first Card from the deck by removing it.
     * @return The first Card in the deck.
     */
    public Card deal()
    {
        return deck.remove(0);
    }
    
    /**
     * This prints out the current state of the deck.
     */
    public void print()
    {
        for(Card card: deck)
        {
            System.out.println(card);
        }
    }
    
    /**
     * This shuffles the deck by making 52 swaps of
     * card positions.
     */
    public void shuffle() {
        if (deck == null || deck.isEmpty()) {
            throw new IllegalStateException("Cannot shuffle an empty deck.");
        }
        Collections.shuffle(deck);
    }


    private List<Card> removedHighCards = new ArrayList<>();

    public void removeHighCards() {
        removedHighCards.clear();
        Iterator<Card> iterator = deck.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getValue() >= 9 && card.getValue() <= 13) {
                removedHighCards.add(card);
                iterator.remove();
            }
        }
    }

    public void restoreHighCards() {
        deck.addAll(removedHighCards);
        removedHighCards.clear(); // Clear the list after restoration
    }

}
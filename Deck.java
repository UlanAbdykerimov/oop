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
    public void shuffle()
    {
        for(int i = 0; i < deck.size(); i++)
        {
            int randomIndex = Randomizer.nextInt(52);
            Card x = deck.get(i);
            Card y = deck.get(randomIndex);
            
            deck.set(i, y);
            deck.set(randomIndex, x);
        }
        if (deck == null || deck.isEmpty()) {
            throw new IllegalStateException("Cannot shuffle an empty deck.");
        }
        Collections.shuffle(deck); // Shuffle the cards randomly
    }

    public void removeHighCards() {
        deck.removeIf(card -> card.getValue() >= 9 && card.getValue() <= 13);
    }
    public void restoreHighCards() {
        // Restore cards from 9 to King into the deck if they are not already present
        for (int rank = 9; rank <= KING; rank++) {
            for (int suit = HEARTS; suit <= CLUBS; suit++) {
                Card card = new Card(rank, suit);
                if (!deck.contains(card)) {
                    deck.add(card); // Add the card only if it is missing
                }
            }
        }
    }




}
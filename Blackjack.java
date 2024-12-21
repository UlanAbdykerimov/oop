/**
 * This plays the Blackjack card game that we wrote throughout 
 * the videos in this lesson.
 * 
 * Try to play the game and test it out. As you play it, can you think
 * of ways to improve the game? Can you think of ways to improve the code
 * or organize the code?
 * 
 * @author Ulan
 *
 */
public class Blackjack extends ConsoleProgram
{

    private static final int HEARTS = 0;
    private static final int DIAMONDS = 1;
    private static final int SPADES = 2;
    private static final int CLUBS = 3;
    
    private static final int JACK = 11;
    private static final int QUEEN = 12;
    private static final int KING = 13;
    private static final int ACE = 14;
    
    // The starting bankroll for the player.
    private static final int STARTING_BANKROLL = 100;
    
    /**
     * Ask the player for a move, hit or stand.
     * 
     * @return A lowercase string of "hit" or "stand"
     * to indicate the player's move.
     */

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.run();
    }

    private String getPlayerMove()
    {
        while(true)
        {
            String move = readLine("Enter move (hit/stand): ");
            move = move.toLowerCase();
            
            if(move.equals("hit") || move.equals("stand"))
            {
                return move;
            }
            System.out.println("Please try again."); 
        }
    }
    
    /**
     * Play the dealer's turn.
     * 
     * The dealer must hit if the value of the hand is less
     * than 17. 
     * 
     * @param dealer The hand for the dealer.
     * @param deck The deck.
     */
    private void dealerTurn(Hand dealer, Deck deck)
    {
        while(true)
        {
            System.out.println("Dealer's hand");
            System.out.println(dealer);
            
            int value = dealer.getValue();
            System.out.println("Dealer's hand has value " + value);
            
            readLine("Enter to continue...");
            
            if(value < 17)
            {
                System.out.println("Dealer hits");
                Card c = deck.deal();
                dealer.addCard(c);
                
                System.out.println("Dealer card was " + c);
                
                if(dealer.busted())
                {
                    System.out.println("Dealer busted!");
                    break;
                }
            }
            else
            {
                System.out.println("Dealer stands.");
                break;
            }
        }
    }
    
    /**
     * Play a player turn by asking the player to hit
     * or stand.
     * 
     * Return whether or not the player busted.
     */
    private boolean playerTurn(Hand player, Deck deck, boolean[] cardsRemoved, int fieldIndex) {
        while (true) {
            int value = player.getValue();
            if (value >= 17 && !cardsRemoved[fieldIndex]) {
                System.out.println("Your hand has value " + value);
                String buyAdvantage = readLine("Would you like to remove all cards from 9 to King from the deck for this field? It costs 50% of potential winnings. (Y/N): ");
                if (buyAdvantage.equalsIgnoreCase("Y")) {
                    deck.removeHighCards();
                    cardsRemoved[fieldIndex] = true;
                    System.out.println("All cards from 9 to King have been removed from the deck for this field.");
                }
            }

            String move = getPlayerMove();

            if (move.equals("hit")) {
                Card c = deck.deal();
                System.out.println("Your card was: " + c);
                player.addCard(c);
                System.out.println("Player's hand");
                System.out.println(player);

                if (player.busted()) {
                    return true;
                }
            } else {
                // If we didn't hit, the player chose to
                // stand, which means the turn is over.
                return false;
            }
        }
    }



    private boolean playerWins(Hand player, Hand dealer)
    {
        if(player.busted())
        {
            return false;
        }
        
        if(dealer.busted())
        {
            return true;
        }
        
        return player.getValue() > dealer.getValue();
    }

    private boolean push(Hand player, Hand dealer)
    {
        return player.getValue() == dealer.getValue();
    }

    private double findWinner(Hand dealer, Hand player, int bet, boolean cardsRemoved) {
        if (playerWins(player, dealer)) {
            System.out.println("Player wins!");

            if (player.hasBlackjack()) {
                return 1.5 * bet;
            }
            if (cardsRemoved) {
                return 0.5 * bet; // Reward adjusted for removed cards
            }

            return bet;
        } else if (push(player, dealer)) {
            System.out.println("You push");
            return 0;
        } else {
            System.out.println("Dealer wins");
            return -bet;
        }
    }


    private double playRound(double bankroll) {
        boolean[] cardsRemoved = new boolean[5]; // Track removed cards for each field
        int roundNums = readInt("How many playing fields you want to play on? (max 5): ");
        if (roundNums < 1) roundNums = 1;
        else if (roundNums > 5) roundNums = 5;

        int[] bet = new int[6];
        for (int i = 1; i <= roundNums; i++) {
            bet[i] = readInt("What is your bet for " + i + " field: ");
        }

        Deck deck = new Deck();
        deck.shuffle();

        Hand[] players = new Hand[roundNums];
        for (int i = 0; i < roundNums; i++) {
            players[i] = new Hand();
            players[i].addCard(deck.deal());
            players[i].addCard(deck.deal());
        }

        Hand dealer = new Hand();
        dealer.addCard(deck.deal());
        dealer.addCard(deck.deal());

        System.out.println("Dealer's hand");
        dealer.printDealerHand();

        for (int i = 0; i < roundNums; i++) {
            System.out.println("Player's field " + (i + 1) + ": ");
            System.out.println(players[i]);

            // Play turn for the current field
            boolean playerBusted = playerTurn(players[i], deck, cardsRemoved, i);
            if (playerBusted) {
                System.out.println("You busted on field " + (i + 1) + " :(");
            }

            // Restore cards if removed for the next field
            if (cardsRemoved[i]) {
                deck.restoreHighCards();
                System.out.println("High cards (9 to King) restored for the next field.");
            }
        }

        readLine("Enter for dealer turn...");
        dealerTurn(dealer, deck);

        for (int i = 0; i < roundNums; i++) {
            System.out.print("Hand on the field " + (i + 1) + " (" + bet[i + 1] + "): " + cardsRemoved[i]);
            double bankrollChange = findWinner(dealer, players[i], bet[i + 1], cardsRemoved[i]);
            bankroll += bankrollChange;
        }

        System.out.println("New bankroll: " + bankroll);
        return bankroll;
    }



    /**
     * Play the blackjack game. Initialize the bankroll and keep
     * playing roudns as long as the user wants to.
     */
    public void run()
    {
        double bankroll = STARTING_BANKROLL;
        System.out.println("Starting bankroll: " + bankroll);
   
        while(true)
        {
            bankroll = playRound(bankroll);
            
            String playAgain = readLine("Would you like to play again? (Y/N)");
            if(playAgain.equalsIgnoreCase("N"))
            {
                break;
            }
        }
        
        System.out.println("Thanks for playing!");
    }
	
}
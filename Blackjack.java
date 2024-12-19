/**
 * This plays the Blackjack card game that we wrote throughout 
 * the videos in this lesson.
 * 
 * Try to play the game and test it out. As you play it, can you think
 * of ways to improve the game? Can you think of ways to improve the code
 * or organize the code?
 * 
 * @author jkeesh
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
    private boolean playerTurn(Hand player, Deck deck)
    {
        while(true)
        {
            String move = getPlayerMove();
            
            if(move.equals("hit"))
            {
                Card c = deck.deal();
                System.out.println("Your card was: " + c);
                player.addCard(c);
                System.out.println("Player's hand");
                System.out.println(player);
                
                if(player.busted())
                {
                    return true;
                }
            }
            else
            {
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

    private double findWinner(Hand dealer, Hand player, int bet)
    {
        if(playerWins(player, dealer))
        {
            System.out.println("Player wins!");
            
            if(player.hasBlackjack())
            {
                return 1.5 * bet;
            }
            
            return bet;
        }
        else if(push(player, dealer))
        {
            System.out.println("You push");
            return 0;
        }
        else
        {
            System.out.println("Dealer wins");
            return -bet;
        }
    }

    private double playRound(double bankroll)
    {
        int roundNums = readInt("How many playing fields you want to play on? (max 5): ");
        if (roundNums<1) roundNums=1;
        else if (roundNums>5) roundNums=5;
        int[] bet = new int[6];
        for (int i=1; i<=roundNums; i++) {
            bet[i] = readInt("What is your bet for " + i + " field: ");
        }

        Deck deck = new Deck();
        deck.shuffle();
        
        Hand player1 = new Hand();
        Hand player2 = new Hand();
        Hand player3 = new Hand();
        Hand player4 = new Hand();
        Hand player5 = new Hand();
        Hand dealer = new Hand();
        
        player1.addCard(deck.deal());
        dealer.addCard(deck.deal());
        player1.addCard(deck.deal());
        dealer.addCard(deck.deal());
        player2.addCard(deck.deal());
        player2.addCard(deck.deal());
        player3.addCard(deck.deal());
        player3.addCard(deck.deal());
        player4.addCard(deck.deal());
        player4.addCard(deck.deal());
        player5.addCard(deck.deal());
        player5.addCard(deck.deal());

        System.out.println("Dealer's hand");
        //System.out.println(dealer);
        dealer.printDealerHand();
        
        System.out.println("Player's first field: ");
        System.out.println(player1);
        boolean playerBusted = playerTurn(player1, deck);
        if(playerBusted) {
            System.out.println("You busted :(");
        }

        if(roundNums>=2) {
            System.out.println("Player's second field: ");
            System.out.println(player2);
            playerBusted = playerTurn(player2, deck);
            if(playerBusted)
            {
                System.out.println("You busted :(");
            }
        }

        if(roundNums>=3) {
            System.out.println("Player's third field: ");
            System.out.println(player3);
            playerBusted = playerTurn(player3, deck);
            if(playerBusted)
            {
                System.out.println("You busted :(");
            }
        }
        if(roundNums>=4) {
            System.out.println("Player's fourth field: ");
            System.out.println(player4);
            playerBusted = playerTurn(player4, deck);
            if(playerBusted)
            {
                System.out.println("You busted :(");
            }
        }
        if(roundNums>=5) {
            System.out.println("Player's fifth field: ");
            System.out.println(player5);
            playerBusted = playerTurn(player5, deck);
            if(playerBusted)
            {
                System.out.println("You busted :(");
            }
        }

        readLine("Enter for dealer turn...");
        dealerTurn(dealer, deck);

        System.out.print("Hand on the first field (" + bet[1] + "): ");
        double bankrollChange = findWinner(dealer, player1, bet[1]);
        bankroll += bankrollChange;

        if(roundNums>=2) {
            System.out.print("Hand on the second field (" + bet[2] + "): ");
            bankrollChange = findWinner(dealer, player2, bet[2]);
            bankroll += bankrollChange;
        }

        if(roundNums>=3) {
            System.out.print("Hand on the third field (" + bet[3] + "): ");
            bankrollChange = findWinner(dealer, player3, bet[3]);
            bankroll += bankrollChange;
        }
        if(roundNums>=4) {
            System.out.print("Hand on the fourth field (" + bet[4] + "): ");
            bankrollChange = findWinner(dealer, player4, bet[4]);
            bankroll += bankrollChange;
        }
        if(roundNums>=5) {
            System.out.print("Hand on the fifth field (" + bet[5] + "): ");
            bankrollChange = findWinner(dealer, player5, bet[5]);
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
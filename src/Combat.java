import java.util.ArrayList;
import java.util.Scanner;

public class Combat {

	static ArrayList<Player> players = new ArrayList<Player>( );

	public static void main( String[] args ) {
		createPlayers( );
		while ( true ) {
			// First player's turn
			System.out.println( "Player 1's turn" );
			printStats( );
			turn( 0, 1 );
			// Check win condition
			if ( players.get( 1 ).getHealth( ) <= 0 ) {
				System.out.println( "Player 1 wins!" );
				break;
			}
			// Second player's turn
			System.out.println( "Player 2's turn" );
			printStats( );
			turn( 1, 0 );
			// Check win condition
			if ( players.get( 0 ).getHealth( ) <= 0 ) {
				System.out.println( "Player 2 wins!" );
				break;
			}
		}
	}

	public static void createPlayers( ) {
		System.out.print( "Please choose the 2 classes\n" + "Operative: 0\nJuggernaut: 1\nSavant: 2\nMason: 3\n" );
		Scanner scanner = new Scanner( System.in );
		int class1 = scanner.nextInt( );
		int class2 = scanner.nextInt( );

		if ( class1 == 0 )
			players.add( new Operative( "Player 1" ) );
		if ( class1 == 1 )
			players.add( new Juggernaut( "Player 1" ) );
		if ( class1 == 2 )
			players.add( new Savant( "Player 1" ) );
		if ( class1 == 3 )
			players.add( new Mason( "Player 1" ) );

		if ( class2 == 0 )
			players.add( new Operative( "Player 2" ) );
		if ( class2 == 1 )
			players.add( new Juggernaut( "Player 2" ) );
		if ( class2 == 2 )
			players.add( new Savant( "Player 2" ) );
		if ( class2 == 3 )
			players.add( new Mason( "Player 2" ) );

		scanner.close( );
	}

	public static void turn( int player, int opponent ) {
		Player p = players.get( player );
		Player o = players.get( opponent );

		listActions( p );
		Scanner actionScanner = new Scanner(System.in);
		Action performed = p.getActions().get(actionScanner.nextInt( ));
	}

	private static void listActions( Player p ) {
		ArrayList<Action> actions = p.getActions();
		System.out.println( "Please choose an action: " );
		for(int i = 0; i < actions.size( ); i++){
			
			System.out.println( i + ". " + actions.get( i ).toString( ));
		}
	}

	private static void printStats( ) {
		System.out.println( "Player 1:" );
		Player p1 = players.get( 0 );
		System.out.printf( "Health: %f/%f\tAP: %d\n\n", p1.getHealth( ), p1.getMaxHealth( ), p1.getMaxAP( ) );
		System.out.println( "Player 2:" );
		Player p2 = players.get( 1 );
		System.out.printf( "Health: %f/%f\tAP: %d\n\n", p2.getHealth( ), p2.getMaxHealth( ), p2.getMaxAP( ) );

	}
}

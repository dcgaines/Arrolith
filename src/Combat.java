import java.util.ArrayList;
import java.util.Scanner;

public class Combat {

	static ArrayList<Player> players = new ArrayList<Player>( );
	static Scanner scanner;

	public static void main( String[] args ) {
		scanner = new Scanner(System.in);
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
		scanner.close( );
	}

	public static void createPlayers( ) {
		System.out.print( "Please choose the 2 classes\n" + "Operative: 0\nJuggernaut: 1\nSavant: 2\nMason: 3\n" );
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
	}

	public static void turn( int player, int opponent ) {
		Player p = players.get( player );
		Player o = players.get( opponent );
		p.resetAP( );
		int act = 0;
		Action performed = null;
		listActions( p );
		do {
			act = scanner.nextInt( );
			if(act == 6)
				break;
			performed = p.getActions( ).get( act );
			if ( performed.getCost( ) > p.getAP( ) ) {
				System.out.println( "Insufficient action points, select a different action." );
			} else {
				perform(performed, p, o);
			}
		} while ( p.getAP( ) > 0 );
	}

	private static void listActions( Player p ) {
		ArrayList<Action> actions = p.getActions( );
		System.out.println( "Please choose an action: " );
		for ( int i = 0; i < actions.size( ); i++ ) {

			System.out.println( i + ". " + actions.get( i ).toString( ) );
		}
		System.out.println( "6. End Turn" );
	}

	private static void printStats( ) {
		System.out.println( "Player 1:" );
		Player p1 = players.get( 0 );
		System.out.printf( "Health: %.2f/%.2f\tAP: %d\n\n", p1.getHealth( ), p1.getMaxHealth( ), p1.getMaxAP( ) );
		System.out.println( "Player 2:" );
		Player p2 = players.get( 1 );
		System.out.printf( "Health: %.2f/%.2f\tAP: %d\n\n", p2.getHealth( ), p2.getMaxHealth( ), p2.getMaxAP( ) );

	}
	
	private static void perform(Action a, Player p, Player o){
		switch(a.getActionType( )){
		case Action.ATTACK:
			switch(a.getWeaponType( )){
			case Action.MELEE:
				if(a.getAttackType() == Action.WEAK)
					o.subtractHealth( p.meleeDmg );
				else
					o.subtractHealth( p.meleeDmg * 1.5 );
				break;
			case Action.RANGED:
				if(a.getAttackType( ) == Action.WEAK)
					o.subtractHealth( p.rangeDmg );
				else
					o.subtractHealth( p.rangeDmg * 1.5 );
				break;
			case Action.MAGIC:
				if(a.getAttackType( ) == Action.WEAK)
					o.subtractHealth( p.magicDmg );
				else
					o.subtractHealth( p.magicDmg * 1.5 );
				break;
			}
			break;
		case Action.HEAL:
			p.addHealth(5);
			break;
		case Action.DEFEND:
			p.defend();
			break;
		}
		p.actionPoints -= a.getCost( );
	}
}

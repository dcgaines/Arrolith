import java.util.ArrayList;
import java.util.Scanner;

public class Combat {

	static Player player;
	static Monster monster = new Monster(0, 0);
	
	static Scanner scanner;

	public static void main( String[] args ) {
		scanner = new Scanner(System.in);
		createPlayers( );
		while ( true ) {
			// First player's turn
			System.out.println( "Player's turn" );
			printStats( );
			playerTurn();
			// Check win condition
			if ( monster.getHealth( ) <= 0 ) {
				System.out.println( "Player wins!" );
				break;
			}
			// Second player's turn
			System.out.println( "Monster's turn" );
			printStats( );
			monsterTurn( );
			// Check win condition
			if ( player.getHealth( ) <= 0 ) {
				System.out.println( "Player loses!" );
				break;
			}
		}
		scanner.close( );
	}

	public static void createPlayers( ) {
		System.out.print( "Please choose the player class\n" + "Operative: 0\nJuggernaut: 1\nSavant: 2\nMason: 3\n" );
		int class1 = scanner.nextInt( );

		if ( class1 == 0 )
			player = new Operative( "Player" );
		if ( class1 == 1 )
			player = new Juggernaut( "Player" );
		if ( class1 == 2 )
			player = new Savant( "Player" );
		if ( class1 == 3 )
			player = new Mason( "Player" );

		monster = new Monster(0,0);
	}

	private static void playerTurn( ) {
		player.resetAP( );
		int act = 0;
		Action performed = null;
		listActions( player );
		do {
			act = scanner.nextInt( );
			if(act == 6)
				break;
			performed = player.getActions( ).get( act );
			if ( performed.getCost( ) > player.getAP( ) ) {
				System.out.println( "Insufficient action points, select a different action." );
			} else {
				perform(performed, player, monster);
			}
		} while ( player.getAP( ) > 0 );
	}
	
	private static void monsterTurn(){
		int numActions;
		if(monster.getHealth( ) < monster.getMaxHealth( ))
			numActions = 4;
		else
			numActions = 3;
		
		int act = (int)(Math.random( ) * numActions);
		Action performed = monster.getActions( ).get( act );
		perform(performed, monster, player);
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
		System.out.println( "Player:" );
		System.out.printf( "Health: %d/%d\tAP: %d\n\n", player.getHealth( ), player.getMaxHealth( ), player.getMaxAP( ) );
		
		System.out.println( "Monster:" );
		System.out.printf( "Health: %d/%d\n\n", monster.getHealth( ), monster.getMaxHealth( ));

	}
	
	private static void perform(Action a, Being p, Being o){
		switch(a.getActionType( )){
		case Action.ATTACK:
			switch(a.getWeaponType( )){
			case Action.MELEE:
				if(a.getAttackType() == Action.WEAK)
					o.subtractHealth( p.meleeDmg );
				else
					o.subtractHealth( p.meleeDmg * 2 );
				break;
			case Action.RANGED:
				if(a.getAttackType( ) == Action.WEAK)
					o.subtractHealth( p.rangeDmg );
				else
					o.subtractHealth( p.rangeDmg * 2 );
				break;
			case Action.MAGIC:
				if(a.getAttackType( ) == Action.WEAK)
					o.subtractHealth( p.magicDmg );
				else
					o.subtractHealth( p.magicDmg * 2 );
				break;
			}
			break;
		case Action.HEAL:
			p.addHealth(100 + 10 * p.intellect);
			break;
		case Action.DEFEND:
			p.defend();
			break;
		}
		if(p instanceof Player)
			((Player)p).actionPoints -= a.getCost( );
	}
}

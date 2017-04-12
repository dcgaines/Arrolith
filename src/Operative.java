
public class Operative extends Player {

	public Operative( String name ) {
		super( name );
		playerClass = Player.OPERATIVE;
		perseverance = 1;
		observation = 6;
		intellect = 3;
		negotiation = 3;
		tact = 6;
		strength = 1;
		
		updateStats( );
		
		health = maxHealth;

		actions.add( new Action( "Talon's Fury",	 	2, Action.ATTACK, Action.MELEE, Action.WEAK ) );
		actions.add( new Action( "Mortal Puncture", 	3, Action.ATTACK, Action.MELEE, Action.STRONG ) );
		actions.add( new Action( "Precise Fire", 		2, Action.ATTACK, Action.RANGED, Action.WEAK ) );
		actions.add( new Action( "Targeted Volley", 	4, Action.ATTACK, Action.RANGED, Action.STRONG ) );
		actions.add( new Action( "Heal with Potion", 	2, Action.HEAL ) );
		actions.add( new Action( "Defend", 				2, Action.DEFEND ) );
	}

}

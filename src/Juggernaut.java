
public class Juggernaut extends Player {

	public Juggernaut( String name ) {
		super( name );
		playerClass = Player.JUGGERNAUT;
		perseverance = 6;
		observation = 3;
		intellect = 1;
		negotiation = 1;
		tact = 3;
		strength = 6;
		
		updateStats();
		health = maxHealth;
		
		actions.add( new Action( "Quelling Slash",	 	2, Action.ATTACK, Action.MELEE, Action.WEAK ) );
		actions.add( new Action( "Hornan Incision", 	3, Action.ATTACK, Action.MELEE, Action.STRONG ) );
		actions.add( new Action( "Parry",		 		2, Action.ATTACK, Action.MELEE, Action.WEAK ) );
		actions.add( new Action( "Barbed Pummel",	 	3, Action.ATTACK, Action.MELEE, Action.STRONG ) );
		actions.add( new Action( "Heal with Potion", 	2, Action.HEAL ) );
		actions.add( new Action( "Defend", 				2, Action.DEFEND ) );
	}

}

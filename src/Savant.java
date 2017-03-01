
public class Savant extends Player {

	public Savant( String name ) {
		super( name );
		playerClass = Player.SAVANT;
		perseverance = 3;
		observation = 3;
		intellect = 6;
		negotiation = 1;
		tact = 6;
		strength = 1;
		
		updateStats();
		
		actions.add( new Action( "Serpentine Stun",	 	2, Action.ATTACK, Action.MAGIC, Action.WEAK ) );
		actions.add( new Action( "Thrashing Thunder", 	4, Action.ATTACK, Action.MAGIC, Action.STRONG ) );
		actions.add( new Action( "Bloody Bludgeon", 	2, Action.ATTACK, Action.MELEE, Action.WEAK ) );
		actions.add( new Action( "Pulverizing Pulse", 	4, Action.ATTACK, Action.MELEE, Action.STRONG ) );
		actions.add( new Action( "Heal with Potion", 	2, Action.HEAL ) );
		actions.add( new Action( "Defend", 				2, Action.DEFEND ) );
	}

}

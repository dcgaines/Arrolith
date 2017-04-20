
public class Mason extends Player {

	public Mason( String name ) {
		super( name );
		playerClass = Player.MASON;
		perseverance = 1;
		observation = 1;
		intellect = 3;
		negotiation = 6;
		tact = 6;
		strength = 3;
		
		updateStats();
		health = maxHealth;
		resetAP();
		
		actions.add( new Action( "Righteous Lash",	 	2, Action.ATTACK, Action.MELEE, Action.WEAK ) );
		actions.add( new Action( "Hearth-Forged Bane", 	3, Action.ATTACK, Action.MELEE, Action.STRONG ) );
		actions.add( new Action( "Sacred Burst", 		2, Action.ATTACK, Action.MAGIC, Action.WEAK ) );
		actions.add( new Action( "Fragmented Barrage", 	4, Action.ATTACK, Action.MAGIC, Action.STRONG ) );
		actions.add( new Action( "Heal with Potion", 	1, Action.HEAL ) );
		actions.add( new Action( "Defend", 				1, Action.DEFEND ) );
	}

}

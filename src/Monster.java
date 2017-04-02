
public class Monster extends Being {

	protected Player owner;

	public Monster( Player p ) {
		owner = p;
		updateStats( );
		act( );
	}

	public Monster( int x, int y ) {
		owner = null;
		xCoord = x;
		yCoord = y;

		perseverance = 2;
		observation = 2;
		intellect = 2;
		negotiation = 2;
		tact = 2;
		strength = 2;

		maxHealth = 2000;

		health = maxHealth;

		setDamages( );

		act( );
	}

	public void act( ) {
		actions.add( new Action( "Weak Attack", 1, Action.ATTACK, Action.MELEE, Action.WEAK ) );
		actions.add( new Action( "Strong Attack", 1, Action.ATTACK, Action.MELEE, Action.STRONG ) );
		actions.add( new Action( "Defend", 1, Action.DEFEND ) );
		actions.add( new Action( "Heal", 1, Action.HEAL ) );
	}

	// Only call when first instantiated
	public void updateStats( ) {
		perseverance = owner.perseverance;
		observation = owner.observation;
		intellect = owner.intellect;
		negotiation = owner.negotiation;
		tact = owner.tact;
		strength = owner.strength;

		xCoord = owner.xCoord;
		yCoord = owner.yCoord;

		maxHealth = 20 + ( perseverance * 2 );

		// Make monsters weaker than players
		maxHealth /= 2;

		health = maxHealth;

		setDamages( );
	}

}

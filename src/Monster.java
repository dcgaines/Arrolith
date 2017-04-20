
public class Monster extends Being {

	protected Player owner;
	protected int level;

	public Monster( Player p, int lvl ) {
		owner = p;
		level = lvl;
		updateStats( );
		act( );
	}

	public Monster( int x, int y, int lvl ) {
		owner = null;
		xCoord = x;
		yCoord = y;
		level = lvl;

		perseverance = 2 * level;
		observation = 2 * level;
		intellect = 2 * level;
		negotiation = 2 * level;
		tact = 2 * level;
		strength = 2 * level;

		maxHealth = 1500 + 500 * level;

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

		maxHealth = 2400 + ( perseverance * 200 );

		health = maxHealth;

		setDamages( );
	}
	
	public Player getOwner(){
		return owner;
	}
	
	public int getLevel(){
		return level;
	}

}

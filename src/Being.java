import java.util.ArrayList;

public abstract class Being {

	// Base damages, subject to balancing
	static final double baseMeleeDmg = 200;
	static final double baseRangeDmg = 140;
	static final double baseMagicDmg = 170;

	protected int perseverance;
	protected int observation;
	protected int intellect;
	protected int negotiation;
	protected int tact;
	protected int strength;

	protected int xCoord;
	protected int yCoord;

	protected int maxHealth;
	protected int health;
	protected int defense = 0;

	protected int meleeDmg;
	protected int rangeDmg;
	protected int magicDmg;

	protected ArrayList<Action> actions = new ArrayList<Action>( );

	public int getX( ) {
		return xCoord;
	}

	public int getY( ) {
		return yCoord;
	}

	public int getMaxHealth( ) {
		return maxHealth;
	}

	public int getHealth( ) {
		return health;
	}

	public void addHealth( int hp ) {
		health += hp;
		if ( health > maxHealth )
			health = maxHealth;
	}

	public int subtractHealth( int hp ) {
		if ( defense > 0 )
			if ( hp > defense ) {
				hp -= defense;
				defense = 0;
			} else {
				defense -= hp;
				hp = 0;
			}
		health -= hp;
		if ( health < 0 )
			health = 0;
		return hp;
	}

	public ArrayList<Action> getActions( ) {
		return actions;
	}

	public void defend( ) {
		defense += 150 + tact * 50;
	}

	public void setDamages( ) {
		meleeDmg = (int) ( Being.baseMeleeDmg * ( 1 + ( strength + observation - 1 ) / 10. ) );
		rangeDmg = (int) ( Being.baseRangeDmg * ( 1 + ( observation + intellect - 1 ) / 10. ) );
		magicDmg = (int) ( Being.baseMagicDmg * ( 1 + ( intellect + strength - 1 ) / 10. ) );
	}
}

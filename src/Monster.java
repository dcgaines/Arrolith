import java.util.ArrayList;

public class Monster {

	// Base damages, subject to balancing
	static final double baseMeleeDmg = 1.0;
	static final double baseRangeDmg = .7;
	static final double baseMagicDmg = .8;

	// Health
	protected double maxHealth;
	protected double health;
	protected int actionPoints;
	
	protected Player owner;

	// POINTS
	protected int perseverance;
	protected int observation;
	protected int intellect;
	protected int negotiation;
	protected int tact;
	protected int strength;

	protected double meleeDmg;
	protected double rangeDmg;
	protected double magicDmg;

	// Location on the map
	protected int xCoord;
	protected int yCoord;
	
	// ArrayList of actions
	protected ArrayList<Action> actions;

	public Monster( Player p ) {
		owner = p;
		updateStats( );
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
		
		actions = owner.actions;
		
		actionPoints = 2 + ( tact / 3 );
		maxHealth = 20 + ( perseverance * 2 );
		
		// Make monsters weaker than players
		maxHealth /= 2;
		
		health = maxHealth;

		meleeDmg = Player.baseMeleeDmg * ( 1 + ( strength - 1 ) / 10. );
		rangeDmg = Player.baseRangeDmg * ( 1 + ( observation - 1 ) / 10. );
		magicDmg = Player.baseMagicDmg * ( 1 + ( intellect - 1 ) / 10. );

	}
}

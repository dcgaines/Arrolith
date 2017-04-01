import java.util.ArrayList;

public class Monster {

	// Base damages, subject to balancing
	static final double baseMeleeDmg = 1.0;
	static final double baseRangeDmg = .7;
	static final double baseMagicDmg = .8;

	// Health
	protected double maxHealth;
	protected double health;
	protected double defense = 0;
	
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
		act();
	}
	
	public Monster(int x, int y){
		owner = null;
		xCoord = x;
		yCoord = y;
		
		perseverance = 2;
		observation = 2;
		intellect = 2;
		negotiation = 2;
		tact = 2;
		strength = 2;
		
		maxHealth = 10;
		
		health = maxHealth;

		meleeDmg = Player.baseMeleeDmg * ( 1 + ( strength + observation - 1 ) / 10. );
		rangeDmg = Player.baseRangeDmg * ( 1 + ( observation + intellect - 1 ) / 10. );
		magicDmg = Player.baseMagicDmg * ( 1 + ( intellect + strength - 1 ) / 10. );
		
		act();
	}

	public void act(){
		actions.add( new Action("Weak Attack", 1, Action.ATTACK, Action.MELEE, Action.WEAK) );
		actions.add( new Action("Strong Attack", 1, Action.ATTACK, Action.MELEE, Action.STRONG) );
		actions.add( new Action("Defend", 1, Action.DEFEND) );
		actions.add( new Action("Heal", 1, Action.HEAL) );
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

		meleeDmg = Player.baseMeleeDmg * ( 1 + ( strength + observation - 1 ) / 10. );
		rangeDmg = Player.baseRangeDmg * ( 1 + ( observation + intellect - 1 ) / 10. );
		magicDmg = Player.baseMagicDmg * ( 1 + ( intellect + strength - 1 ) / 10. );
	}
	
	public int getX(){
		return xCoord;
	}
	
	public int getY(){
		return yCoord;
	}
	
	public double getMaxHealth( ) {
		return maxHealth;
	}

	public double getHealth( ) {
		return health;
	}

	public void addHealth( double hp ) {
		health += hp;
		if(health > maxHealth)
			health = maxHealth;
	}
	
	public void subtractHealth( double hp ){
		if(defense > 0)
			if(hp > defense){
				hp -= defense;
				defense = 0;
			} else {
				defense -= hp;
				hp = 0;
			}
		health -= hp;
		if(health < 0)
			health = 0;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void defend(){
		defense = 1 + tact;
	}
}

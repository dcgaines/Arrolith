import java.util.ArrayList;

public abstract class Being {
	
	// Base damages, subject to balancing
	static final double baseMeleeDmg = 4.0;
	static final double baseRangeDmg = 3.2;
	static final double baseMagicDmg = 3.6;
	
	protected int perseverance;
	protected int observation;
	protected int intellect;
	protected int negotiation;
	protected int tact;
	protected int strength;
	
	protected int xCoord;
	protected int yCoord;
	
	protected double maxHealth;
	protected double health;
	protected double defense = 0;
	
	protected double meleeDmg;
	protected double rangeDmg;
	protected double magicDmg;
	
	protected ArrayList<Action> actions = new ArrayList<Action>();
	
	
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

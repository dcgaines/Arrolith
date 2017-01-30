
public class Player {
	// Player class identifiers
	static int SAVANT = 0;
	static int OPERATIVE = 1;
	static int JUGGERNAUT = 2;
	static int MASON = 3;
	
	// Base damages, subject to balancing
	static double baseMeleeDmg = 1.0;
	static double baseRangeDmg = .7;
	static double baseMagicDmg = .8;
	
	
	private String name;
	private double maxHealth;
	private double health;
	
	private int actionPoints;
	private int perseverance;
	private int observation;
	private int intellect;
	private int negotiation;
	private int tact;
	private int strength;
	
	private int xCoord;
	private int yCoord;
	
	
}

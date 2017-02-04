
public class Player {
	// Player class identifiers
	static final int SAVANT = 0;
	static final int OPERATIVE = 1;
	static final int JUGGERNAUT = 2;
	static final int MASON = 3;

	// Base damages, subject to balancing
	static final double baseMeleeDmg = 1.0;
	static final double baseRangeDmg = .7;
	static final double baseMagicDmg = .8;

	// Character name
	private String name;
	private int playerClass;

	// Health
	private double maxHealth;
	private double health;

	private int actionPoints;

	// POINTS
	private int perseverance;
	private int observation;
	private int intellect;
	private int negotiation;
	private int tact;
	private int strength;

	private double meleeDmg;
	private double rangeDmg;
	private double magicDmg;

	// Location on the map
	private int xCoord;
	private int yCoord;

	public Player( int pClass, String name ) {
		playerClass = pClass;
		this.name = name;

		// Initializes points based on class
		switch ( playerClass ) {
		case Player.SAVANT:
			perseverance = 3;
			observation = 3;
			intellect = 6;
			negotiation = 1;
			tact = 6;
			strength = 1;
			break;
		case Player.OPERATIVE:
			perseverance = 1;
			observation = 6;
			intellect = 3;
			negotiation = 3;
			tact = 6;
			strength = 1;
			break;
		case Player.JUGGERNAUT:
			perseverance = 6;
			observation = 3;
			intellect = 1;
			negotiation = 1;
			tact = 3;
			strength = 6;
			break;
		case Player.MASON:
			perseverance = 1;
			observation = 1;
			intellect = 3;
			negotiation = 6;
			tact = 6;
			strength = 3;
			break;
		}

		actionPoints = 2 + ( tact / 3 );
		maxHealth = 20 + ( perseverance * 2 );
		health = maxHealth;

		meleeDmg = Player.baseMeleeDmg * ( 1 + ( strength - 1 ) / 10. );
		rangeDmg = Player.baseRangeDmg * ( 1 + ( observation - 1 ) / 10. );
		magicDmg = Player.baseMagicDmg * ( 1 + ( intellect - 1 ) / 10. );

	}

	public int getX( ) {
		return xCoord;
	}

	public int getY( ) {
		return yCoord;
	}

	public boolean walkUp( Map map ) {
		Tile upTile;
		try{
			upTile = map.getTile( yCoord - 1, xCoord );
		} catch(NullPointerException e){
			return false;
		}
		if(upTile.getWalk()){
			yCoord--;
			return true;
		} else{
			return false;
		}
	}
	
	public boolean walkRight( Map map ){
		Tile rightTile;
		try{
			rightTile = map.getTile( yCoord, xCoord + 1 );
		} catch(NullPointerException e){
			return false;
		}
		if(rightTile.getWalk()){
			xCoord++;
			return true;
		} else{
			return false;
		}
	}

	public boolean walkDown( Map map ){
		Tile downTile;
		try{
			downTile = map.getTile( yCoord + 1, xCoord );
		} catch(NullPointerException e){
			return false;
		}
		if(downTile.getWalk()){
			yCoord++;
			return true;
		} else{
			return false;
		}
	}
	
	public boolean walkLeft( Map map ){
		Tile leftTile;
		try{
			leftTile = map.getTile( yCoord, xCoord - 1 );
		} catch(NullPointerException e){
			return false;
		}
		if(leftTile.getWalk()){
			xCoord--;
			return true;
		} else{
			return false;
		}
	}
}

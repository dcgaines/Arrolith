import java.util.ArrayList;

public abstract class Player {
	// Player class identifiers
	static final int SAVANT = 1;
	static final int OPERATIVE = 3;
	static final int JUGGERNAUT = 0;
	static final int MASON = 2;

	// Base damages, subject to balancing
	static final double baseMeleeDmg = 1.0;
	static final double baseRangeDmg = .7;
	static final double baseMagicDmg = .8;

	// Character name
	protected String name;
	protected int playerClass;

	// Health
	protected double maxHealth;
	protected double health;

	protected int actionPoints;
	protected int maxAP;

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
	protected ArrayList<Action> actions = new ArrayList<Action>( );

	public Player( String name ) {
		this.name = name;
	}

	public void updateStats( ) {
		actionPoints = 2 + ( tact / 3 );
		maxAP = actionPoints;
		maxHealth = 20 + ( perseverance * 2 );
		health = maxHealth;

		meleeDmg = Player.baseMeleeDmg * ( 1 + ( strength - 1 ) / 10. );
		rangeDmg = Player.baseRangeDmg * ( 1 + ( observation - 1 ) / 10. );
		magicDmg = Player.baseMagicDmg * ( 1 + ( intellect - 1 ) / 10. );

	}

	public int getType( ) {
		return playerClass;
	}

	public int getX( ) {
		return xCoord;
	}

	public int getY( ) {
		return yCoord;
	}

	public void setInitPos( byte playerNum, Map map ) {
		switch ( playerNum ) {
		case 0:
			xCoord = 0;
			yCoord = 0;
			break;
		case 1:
			xCoord = map.getWidth( ) - 1;
			yCoord = map.getHeight( ) - 1;
			break;
		case 2:
			xCoord = 0;
			yCoord = map.getHeight( ) - 1;
			break;
		case 3:
			xCoord = map.getWidth( ) - 1;
			yCoord = 0;
			break;
		}
	}

	public boolean walkUp( Map map ) {
		Tile upTile;
		try {
			upTile = map.getTile( yCoord - 1, xCoord );
		} catch ( NullPointerException e ) {
			return false;
		} catch ( ArrayIndexOutOfBoundsException a ) {
			return false;
		}
		if ( upTile.getWalk( ) ) {
			yCoord--;
			return true;
		} else if ( upTile.getAction( ) ) {
			upTile.action( );
			return true;
		} else {
			return false;
		}
	}

	public boolean walkRight( Map map ) {
		Tile rightTile;
		try {
			rightTile = map.getTile( yCoord, xCoord + 1 );
		} catch ( NullPointerException e ) {
			return false;
		} catch ( ArrayIndexOutOfBoundsException a ) {
			return false;
		}
		if ( rightTile.getWalk( ) ) {
			xCoord++;
			return true;
		} else if ( rightTile.getAction( ) ) {
			rightTile.action( );
			return true;
		} else {
			return false;
		}
	}

	public boolean walkDown( Map map ) {
		Tile downTile;
		try {
			downTile = map.getTile( yCoord + 1, xCoord );
		} catch ( NullPointerException e ) {
			return false;
		} catch ( ArrayIndexOutOfBoundsException a ) {
			return false;
		}
		if ( downTile.getWalk( ) ) {
			yCoord++;
			return true;
		} else if ( downTile.getAction( ) ) {
			downTile.action( );
			return true;
		} else {
			return false;
		}
	}

	public boolean walkLeft( Map map ) {
		Tile leftTile;
		try {
			leftTile = map.getTile( yCoord, xCoord - 1 );
		} catch ( NullPointerException e ) {
			return false;
		} catch ( ArrayIndexOutOfBoundsException a ) {
			return false;
		}
		if ( leftTile.getWalk( ) ) {
			xCoord--;
			return true;
		} else if ( leftTile.getAction( ) ) {
			leftTile.action( );
			return true;
		} else {
			return false;
		}
	}

	public double getMaxHealth( ) {
		return maxHealth;
	}

	public double getHealth( ) {
		return health;
	}

	public void setHealth( double hp ) {
		health = hp;
	}

	public int getAP( ) {
		return actionPoints;
	}

	public int getMaxAP( ) {
		return maxAP;
	}
}


public abstract class Player extends Being{
	// Player class identifiers
	static final int SAVANT = 1;
	static final int OPERATIVE = 3;
	static final int JUGGERNAUT = 0;
	static final int MASON = 2;

	// Character name
	protected String name;
	protected int playerClass;

	protected int actionPoints;
	protected int maxAP;

	public Player( String name ) {
		this.name = name;
	}

	public void updateStats( ) {
		actionPoints = 2 + ( tact / 3 );
		maxAP = actionPoints;
		maxHealth = 20 + ( perseverance * 2 );
		health = maxHealth;

		meleeDmg = Being.baseMeleeDmg * ( 1 + ( strength + observation - 1 ) / 10. );
		rangeDmg = Being.baseRangeDmg * ( 1 + ( observation + intellect - 1 ) / 10. );
		magicDmg = Being.baseMagicDmg * ( 1 + ( intellect + strength - 1 ) / 10. );

	}

	public int getType( ) {
		return playerClass;
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

	public int getAP( ) {
		return actionPoints;
	}

	public int getMaxAP( ) {
		return maxAP;
	}

	public void resetAP(){
		actionPoints = maxAP;
	}
	
}

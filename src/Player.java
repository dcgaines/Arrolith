
public abstract class Player extends Being {
	// Player class identifiers
	static final int SAVANT = 1;
	static final int OPERATIVE = 3;
	
	static final int MASON = 2;
	static final int JUGGERNAUT = 0;
	
	// Character name
	protected String name;
	protected int playerClass;

	protected int actionPoints;
	protected int maxAP;
	
	protected int insignias;
	protected int coins;
	private int lastLeveled = 1;

	public Player( String name ) {
		this.name = name;
	}

	public void updateStats( ) {
		maxAP = 2 + ( tact / 3 );
		level = coins / 40 + 1;
		
		if(level - lastLeveled >= 2){
			perseverance++;
			observation++;
			intellect++;
			strength++;
			lastLeveled += 2;
		}
		if(level < lastLeveled){
			perseverance--;
			observation--;
			intellect--;
			strength--;
			lastLeveled -= 2;
		}

		maxHealth = 2000 + ( perseverance * 200 );
		setDamages( );
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
		map.getTile( yCoord, xCoord ).setOccupied( true );
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
			map.getTile( yCoord, xCoord ).setOccupied( false );
			yCoord--;
			map.getTile( yCoord, xCoord ).setOccupied( true );
			return true;
		} else if ( upTile.getAction( ) ) {
			if(actionPoints == 0)
				return false;
			else {
				actionPoints--;
				upTile.action( this );
				return true;
			}
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
			map.getTile( yCoord, xCoord ).setOccupied( false );
			xCoord++;
			map.getTile( yCoord, xCoord ).setOccupied( true );
			return true;
		} else if ( rightTile.getAction( ) ) {
			if(actionPoints == 0)
				return false;
			else {
				actionPoints--;
				rightTile.action( this );
				return true;
			}
			
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
			map.getTile( yCoord, xCoord ).setOccupied( false );
			yCoord++;
			map.getTile( yCoord, xCoord ).setOccupied( true );
			return true;
		} else if ( downTile.getAction( ) ) {
			if(actionPoints == 0)
				return false;
			else {
				downTile.action( this );
				actionPoints--;
				return true;
			}
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
			map.getTile( yCoord, xCoord ).setOccupied( false );
			xCoord--;
			map.getTile( yCoord, xCoord ).setOccupied( true );
			return true;
		} else if ( leftTile.getAction( ) ) {
			if(actionPoints == 0)
				return false;
			else {
				leftTile.action( this );
				actionPoints--;
				return true;
			}
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

	public void resetAP( ) {
		actionPoints = maxAP;
	}
	
	public int getInsignias(){
		return insignias;
	}

	public void addInsignias(int i){
		insignias += i;
	}
	
	public int getCoins(){
		return coins;
	}
	
	public void addCoins(int c){
		coins += c;
		if(coins < 0)
			coins = 0;
	}
	
	public int getLevel(){
		return level;
	}	
}

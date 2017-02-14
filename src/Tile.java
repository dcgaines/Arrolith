
public class Tile {
	private int type;
	private boolean walkable;
	private double itemChance;
	private double goldChance;
	private boolean actionable;

	public Tile( int type ) {
		this.type = type;

		switch ( type ) {
		case TileType.GRASS:
			walkable = true;
			itemChance = .2;
			goldChance = .1;
			actionable = false;
			break;
		case TileType.DIRT:
			walkable = true;
			itemChance = .1;
			goldChance = .05;
			actionable = false;
			break;
		case TileType.GRASS_FLOWER:
		case TileType.GRASS_FLOWER1:
		case TileType.GRASS_FLOWER2:
			walkable = true;
			itemChance = .3;
			goldChance = .15;
			actionable = false;
			break;
		case TileType.TREE:
		case TileType.TREE_BARE:
		case TileType.ROCK:
		case TileType.WALL:
		case TileType.INTERIOR_WALL:
		case TileType.RIGHT_WALL:
		case TileType.LEFT_WALL:
		case TileType.RIGHT_COLUMN:
		case TileType.LEFT_COLUMN:
		case TileType.LEFT_CONNECT:
		case TileType.RIGHT_CONNECT:
		case TileType.STATUE:
			walkable = false;
			itemChance = 0;
			goldChance = 0;
			actionable = false;
			break;
		case TileType.DOOR_CLOSED:
		case TileType.DOOR_CLOSED_L:
		case TileType.DOOR_CLOSED_R:
			walkable = false;
			itemChance = 0;
			goldChance = 0;
			actionable = true;
			break;
		case TileType.INTERIOR:
		case TileType.INTERIOR1:
		case TileType.INTERIOR2:
		case TileType.ROAD:
		case TileType.ROAD1:
		case TileType.DOOR_OPEN:
		case TileType.DOOR_OPEN_L:
		case TileType.DOOR_OPEN_R:
			walkable = true;
			itemChance = 0;
			goldChance = 0;
			actionable = false;
			// TODO: continue for as many tile types as we have
		}
	}

	public boolean getWalk( ) {
		return walkable;
	}

	public double getItem( ) {
		return itemChance;
	}

	public double getGold( ) {
		return goldChance;
	}

	public boolean getAction( ) {
		return actionable;
	}

	public int getType( ) {
		return type;
	}
	
	public void action(){
		if(actionable){
			walkable = true;
			actionable = false;
			// Opens door
			switch(type){
			case TileType.DOOR_CLOSED:
				type = TileType.DOOR_OPEN;
				break;
			case TileType.DOOR_CLOSED_L:
				type = TileType.DOOR_OPEN_L;
				break;
			case TileType.DOOR_CLOSED_R:
				type = TileType.DOOR_OPEN_R;
				break;
			}
		}
	}
}

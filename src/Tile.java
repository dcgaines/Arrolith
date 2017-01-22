@SuppressWarnings("unused")
public class Tile {
	private int type;
	private boolean walkable;
	private double itemChance;
	private double goldChance;
	private boolean actionable;
	
	public Tile(int type){
		this.type = type;
		
		switch(type){
		case TileType.GRASS:
			walkable = true;
			itemChance = .2;
			goldChance = .1;
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
		case TileType.ROCK:
		case TileType.WALL:
		case TileType.WALL_COLUMN:
		case TileType.WALL_STATUE:
			walkable = false;
			itemChance = 0;
			goldChance = 0;
			actionable = false;
		case TileType.DOOR:
			walkable = false;
			itemChance = 0;
			goldChance = 0;
			actionable = true;
		case TileType.INTERIOR:
			walkable = true;
			itemChance = 0;
			goldChance = 0;
			actionable = false;
		//TODO: continue for as many tile types as we have
		}
	}
}

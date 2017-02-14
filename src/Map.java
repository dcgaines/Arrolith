import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Map {
	private int height; // Number of tiles vertically in the game window
	private int width; // Number of tiles horizontally in the game window
	private Tile[][] map;

	public Map( int players ) throws IOException {
		if ( players < 2 || players > 4 ) {
			throw new RuntimeException( ); // TODO: Ideally don't throw an
											// exception
											// I would like this to be not
											// possible to enter this statement
											// For example, only present 3
											// buttons for player count that
											// return 2, 3, or 4
		}
		switch ( players ) {
		case 2:
			int type = TileType.MAP_ERR;
			int pick;
			String baseType;
			Scanner scanner = new Scanner( new File( "map2P.txt" ) );
			scanner.useDelimiter( "\n|\t" );
			height = Integer.parseInt( scanner.next( ).trim( ) );
			width = Integer.parseInt( scanner.next( ).trim( ) );
			map = new Tile[height][width];

			for ( int i = 0; i < height; i++ ) {
				for ( int j = 0; j < width; j++ ) {
					baseType = scanner.next( ).trim( ); // Gets next tile type
														// and trims white space

					// Sets the tile type based on the text document
					switch ( baseType ) {
					case "GRASS":
						type = TileType.GRASS;
						break;
					case "DIRT":
						type = TileType.DIRT;
						break;
					case "FLOWER":
						pick = (int) ( Math.random( ) * 3 );
						switch ( pick ) {
						case 0:
							type = TileType.GRASS_FLOWER;
							break;
						case 1:
							type = TileType.GRASS_FLOWER1;
							break;
						case 2:
							type = TileType.GRASS_FLOWER2;
							break;
						}
						break;
					case "TREE":
						pick = (int) ( Math.random( ) * 2 );
						if ( pick == 0 ) {
							type = TileType.TREE;
						} else {
							type = TileType.TREE_BARE;
						}
						break;
					case "STATUE":
						type = TileType.STATUE;
						break;
					case "WALL":
						type = TileType.WALL;
						break;
					case "INTWALL":
						type = TileType.INTERIOR_WALL;
						break;
					case "LWALL":
						type = TileType.LEFT_WALL;
						break;
					case "RWALL":
						type = TileType.RIGHT_WALL;
						break;
					case "LCOL":
						type = TileType.LEFT_COLUMN;
						break;
					case "RCOL":
						type = TileType.RIGHT_COLUMN;
						break;
					case "LCON":
						type = TileType.LEFT_CONNECT;
						break;
					case "RCON":
						type = TileType.RIGHT_CONNECT;
						break;
					case "DOOR":
						type = TileType.DOOR_CLOSED;
						break;
					case "LDOOR":
						type = TileType.DOOR_CLOSED_L;
						break;
					case "RDOOR":
						type = TileType.DOOR_CLOSED_R;
						break;
					case "ROCK":
						pick = (int) ( Math.random( ) * 2 );
						if ( pick == 0 ) {
							type = TileType.ROCK;
						} else {
							type = TileType.ROCK1;
						}
						break;
					case "ROAD":
						pick = (int) ( Math.random( ) * 2 );
						if ( pick == 0 ) {
							type = TileType.ROAD;
						} else {
							type = TileType.ROAD1;
						}
						break;
					case "INTERIOR":
						pick = (int) ( Math.random( ) * 3 );
						switch(pick){
						case 0:
							type = TileType.INTERIOR;
							break;
						case 1:
							type = TileType.INTERIOR1;
							break;
						case 2:
							type = TileType.INTERIOR2;
							break;
						}
						break;
					}

					map[i][j] = new Tile( type );
				}
			}
			break;
		case 3:
			break;
		case 4:
			break;
		default:
			break;
		}
	}

	public Tile getTile( int i, int j ) {
		return map[i][j];
	}

	public int getHeight( ) {
		return height;
	}

	public int getWidth( ) {
		return width;
	}
}

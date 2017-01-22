
public class Map {
	int height = 70; // Number of tiles vertically in the game window
	int width = 120; // Number of tiles horizontally in the game window
	
	public Map( int players ) {
		if (players < 2 || players > 4){
			throw new RuntimeException(); 	// TODO: Ideally don't throw an exception
											// I would like this to be not possible to enter this statement
											// For example, only present 3 buttons for player count that return 2, 3, or 4
		}
		switch(players){
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		default:
			break;			
		}
	}
}

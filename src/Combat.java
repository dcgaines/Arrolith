import java.util.ArrayList;
import java.util.Scanner;

public class Combat {

	static ArrayList<Player> players = new ArrayList<Player>();
	
	public static void main(String[] args){
		createPlayers();
	}
	
	public static void createPlayers(){
		System.out.print("Please choose the 2 classes\n" + 
				"Operative: 0\nJuggernaut: 1\nSavant: 2\nMason: 3\n");
				Scanner scanner = new Scanner(System.in);
				int class1 = scanner.nextInt( );
				int class2 = scanner.nextInt( );
				
				if(class1 == 0)
					players.add( new Operative("Player 1") );
				if(class1 == 1)
					players.add( new Juggernaut("Player 1") );
				if(class1 == 2)
					players.add( new Savant("Player 1") );
				if(class1 == 3)
					players.add( new Mason("Player 1") );
				
				if(class2 == 0)
					players.add( new Operative("Player 2") );
				if(class2 == 1)
					players.add( new Juggernaut("Player 2") );
				if(class2 == 2)
					players.add( new Savant("Player 2") );
				if(class2 == 3)
					players.add( new Mason("Player 2") );
				
				scanner.close( );
	}
}

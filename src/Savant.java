
public class Savant extends Player {

	public Savant( String name ) {
		super( name );
		playerClass = Player.SAVANT;
		perseverance = 3;
		observation = 3;
		intellect = 6;
		negotiation = 1;
		tact = 6;
		strength = 1;
		updateStats();
	}

}
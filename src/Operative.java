
public class Operative extends Player {

	public Operative( String name ) {
		super( name );
		playerClass = Player.OPERATIVE;
		perseverance = 1;
		observation = 6;
		intellect = 3;
		negotiation = 3;
		tact = 6;
		strength = 1;
		updateStats();
	}

}

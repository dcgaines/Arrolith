
public class Mason extends Player {

	public Mason( String name ) {
		super( name );
		playerClass = Player.MASON;
		perseverance = 1;
		observation = 1;
		intellect = 3;
		negotiation = 6;
		tact = 6;
		strength = 3;
		updateStats();
	}

}

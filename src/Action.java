
public class Action {
	// Action type constants
	public static final int ATTACK = 0;
	public static final int HEAL = 1;
	public static final int DEFEND = 2;

	// Weapon type constants
	public static final int RANGED = 0;
	public static final int MELEE = 1;
	public static final int MAGIC = 2;

	// Attack type constants
	public static final int WEAK = 0;
	public static final int STRONG = 1;

	private int actionType = -1;
	private int weaponType = -1;
	private int attackType = -1;

	private String name;

	private int cost;

	public Action( String str, int c, int t ) {
		name = str;
		cost = c;
		actionType = t;
	}

	public Action( String str, int c, int t, int w, int a ) {
		this( str, c, t );
		weaponType = w;
		attackType = a;
	}

	public int getActionType( ) {
		return actionType;
	}

	public int getWeaponType( ) {
		return weaponType;
	}

	public int getAttackType( ) {
		return attackType;
	}

	public String getName( ) {
		return name;
	}

	public int getCost( ) {
		return cost;
	}

	@Override
	public String toString( ) {
		String str = name + " | ";
		if ( actionType == ATTACK ) {
			if ( attackType == STRONG )
				str += "Strong ";
			if ( attackType == WEAK )
				str += "Weak ";
			if ( weaponType == RANGED )
				str += "ranged ";
			if ( weaponType == MELEE )
				str += "melee ";
			if ( weaponType == MAGIC )
				str += "magic ";
			str += "attack | ";
		}
		str += "AP: " + cost;
		return str;
	}
}

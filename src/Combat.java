import java.awt.Graphics2D;
import java.util.ArrayList;

public class Combat {

	private final byte FIGHT = -1;
	private final byte ATTACK1 = 0;
	private final byte ATTACK2 = 1;
	private final byte ATTACK3 = 2;
	private final byte ATTACK4 = 3;
	private final byte DEFEND = 4;
	private final byte HEAL = 5;
	private final byte ENDTURN = 6;
	private byte selChoice;

	private ArrayList<Action> actionQueue;
	private byte actionIndex;
	private Action monsterAction;

	Player player;
	Monster monster;
	boolean playerTurn;
	boolean betweenTurn;
	boolean transition;
	int tFrame;
	int amount;
	boolean drawAction;
	ArrayList<Action> actions;

	public Combat(Player p, Monster m) {
		player = p;
		actions = p.getActions();

		actionQueue = new ArrayList<Action>(2);
		if (m == null)
			monster = new Monster(0, 0, 1);
		else
			monster = m;
		selChoice = FIGHT;
		playerTurn = true;
		betweenTurn = false;
		transition = false;
		tFrame = 0;
	}

	private void checkKeys(Keys keys, byte leftRight, byte lrPress, byte upDown, byte udPress) {
		if (keys.isKeyPressed(leftRight))
			selChoice = lrPress;
		else if (keys.isKeyPressed(upDown))
			selChoice = udPress;
	}

	public int calculate(Keys keys) {
		if (transition) {
			if (playerTurn) {
				if (actionIndex == actionQueue.size()) {
					actionQueue.clear();
					selChoice = -2;
					playerTurn = false;
					transition = false;
					betweenTurn = true;
					actionIndex = 0;
					if (monster.getHealth() <= 0) {
						player.resetAP();
						return monster.getLevel();
					}
				}
				else {
				if (tFrame < 1) {
					perform(actionQueue.get(actionIndex), player, monster);
					drawAction = true;
				}
				if (tFrame > 60) {
					tFrame = -1;
					actionIndex++;
					drawAction = false;
				}
				}
				tFrame++;
			}
			else { //monster's turn 
				if (tFrame < 1) {
				makeMonsterAction();
				perform(monsterAction, monster, player);
				drawAction = true;
				}
				if (tFrame > 60) {
					tFrame = -1;
					player.resetAP();
					selChoice = FIGHT;
					transition = false;
					drawAction = false;
					playerTurn = true;
					if (player.getHealth() <= 0) {
						player.resetAP();
						double mod = 1 - (player.negotiation / 10.);
						return (int)(-monster.getLevel( ) * 20 * mod);
					}
				}
				tFrame++;
			}
		}
		 else {
			if (playerTurn) {
				switch (selChoice) {
				case FIGHT:
					if (keys.isKeyPressed(keys.A)) selChoice = ATTACK1;
					else checkKeys(keys, keys.RIGHT, DEFEND, keys.DOWN, HEAL);
					break;
				case DEFEND:
					if (keys.isKeyPressed(keys.A)) playerTurn(DEFEND);
					else checkKeys(keys, keys.LEFT, FIGHT, keys.DOWN, ENDTURN);
					break;
				case HEAL:
					if (keys.isKeyPressed(keys.A)) playerTurn(HEAL);
					else checkKeys(keys, keys.RIGHT, ENDTURN, keys.UP, FIGHT);
					break;
				case ENDTURN:
					if (keys.isKeyPressed(keys.A)) playerTurn(ENDTURN);
					else checkKeys(keys, keys.LEFT, HEAL, keys.UP, DEFEND);
					break;
				case ATTACK1:
					if (keys.isKeyPressed(keys.A)) playerTurn(ATTACK1);
					else if (keys.isKeyPressed(keys.B)) selChoice = FIGHT;
					else
						checkKeys(keys, keys.RIGHT, ATTACK2, keys.DOWN, ATTACK3);
					break;
				case ATTACK2:
					if (keys.isKeyPressed(keys.A))
						playerTurn(ATTACK2);
					else if (keys.isKeyPressed(keys.B))
						selChoice = FIGHT;
					else
						checkKeys(keys, keys.LEFT, ATTACK1, keys.DOWN, ATTACK4);
					break;
				case ATTACK3:
					if (keys.isKeyPressed(keys.A))
						playerTurn(ATTACK3);
					else if (keys.isKeyPressed(keys.B))
						selChoice = FIGHT;
					else
						checkKeys(keys, keys.RIGHT, ATTACK4, keys.UP, ATTACK1);
					break;
				case ATTACK4:
					if (keys.isKeyPressed(keys.A))
						playerTurn(ATTACK4);
					else if (keys.isKeyPressed(keys.B))
						selChoice = FIGHT;
					else
					 	checkKeys(keys, keys.LEFT, ATTACK3, keys.UP, ATTACK2);
					break;
				}
			} else if (betweenTurn) {
				tFrame++;
				if (tFrame >= 60) {
					tFrame = 0;
					betweenTurn = false;
				}
			} else {
				transition = true;
				selChoice = -2;
			}
			if (!playerTurn && monster.getHealth( ) <= 0 ) {
				player.resetAP();
				return monster.getLevel( );
			} 
		}
		
		return 0;
	}

	public void draw(Graphics2D g, ArroGraphics graphics) {
		graphics.drawCombatOptions(g, selChoice, player);
		graphics.drawBattleHUD(g, player);
		graphics.drawCombatMonster(g, monster, betweenTurn, tFrame);
		if (drawAction)
				graphics.drawSelAction(g, playerTurn? actionQueue.get(actionIndex) : monsterAction,
						playerTurn, player, amount);
		// draw defend
		// draw heal
		// draw endturn
	}

	private void playerTurn(int act) {

		if (act == 6) {
			transition = true;
			selChoice = -2;
			actionIndex = 0;
			System.out.println("Ended turn. " + player.getHealth());
			return;
		}
		Action performed = player.getActions().get(act);
		if (performed.getCost() > player.getAP()) {
			return;
		} else {
			
			actionQueue.add(performed);
			player.actionPoints -= performed.getCost();
			if (player.getAP() <= 0) {
				transition = true;
				selChoice = -2;
			}
		}
	}

	private void makeMonsterAction() {
		int numActions;
		if (monster.getHealth() < monster.getMaxHealth())
			numActions = 4;
		else
			numActions = 3;

		int act = (int) (Math.random() * numActions);
		monsterAction = monster.getActions().get(act);
	}

	private void perform(Action a, Being p, Being o) {
		// leave alone
		switch (a.getActionType()) {
		case Action.ATTACK:
			switch (a.getWeaponType()) {
			case Action.MELEE:
				if (a.getAttackType() == Action.WEAK)
					amount = randDmg(p.meleeDmg, p.tact);
				else
					amount = randDmg(2 * p.meleeDmg, p.tact);
				break;
			case Action.RANGED:
				if (a.getAttackType() == Action.WEAK)
					amount = randDmg(p.rangeDmg, p.tact);
				else
					amount = randDmg(p.rangeDmg * 2, p.tact);
				break;
			case Action.MAGIC:
				if (a.getAttackType() == Action.WEAK)
					amount = randDmg(p.magicDmg, p.tact);
				else
					amount = randDmg(p.magicDmg * 2, p.tact);
				break;
			}
			amount = o.subtractHealth(amount);
			break;
		case Action.HEAL:
			amount = 400 + 20 * p.intellect;
			if(p instanceof Player)
				amount /= 3;
			p.addHealth(amount);
			break;
		case Action.DEFEND:
			p.defend();
			break;
		}
	}
	
	private int randDmg(int damage, int tact){
		double min = tact / 10. + .4;
		double dmg = damage * ( (Math.random( ) * (1 - min) + min));
		
		double crit = Math.random( );
		if (crit < tact / 30.){
			dmg *= 1.5;
		}
			
		
		return (int) dmg;
	}
}

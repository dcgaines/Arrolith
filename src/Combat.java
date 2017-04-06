import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	Player player;
	Monster monster;
	boolean playerTurn;
	boolean betweenTurn;
	int tFrame;
	ArrayList<Action> actions;
	
	public Combat(Player p, Monster m) {
		player = p;
		actions = p.getActions();
		if (m == null) 
			monster = new Monster(0, 0);
		else
			monster = m;
		selChoice = FIGHT;
		playerTurn = true;
		betweenTurn = false;
		tFrame = 0;
	}

	private void checkKeys(Keys keys,byte leftRight, byte lrPress, byte upDown, byte udPress) {
		if (keys.isKeyPressed(leftRight)) 
			selChoice = lrPress;
		else if (keys.isKeyPressed(upDown))
			selChoice = udPress;
	}
	public int calculate(Keys keys) {		
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
				if (keys.isKeyPressed(keys.A))	playerTurn(ENDTURN);
				else checkKeys(keys, keys.LEFT, HEAL, keys.UP, DEFEND);
				break;
			case ATTACK1:
				if (keys.isKeyPressed(keys.A)) playerTurn(ATTACK1);
				else if (keys.isKeyPressed(keys.B)) selChoice = FIGHT;
				else checkKeys(keys, keys.RIGHT, ATTACK2, keys.DOWN, ATTACK3);
				break;
			case ATTACK2:
				if (keys.isKeyPressed(keys.A)) playerTurn(ATTACK2);
				else if (keys.isKeyPressed(keys.B)) selChoice = FIGHT;
				else checkKeys(keys, keys.LEFT, ATTACK1, keys.DOWN, ATTACK4);
				break;
			case ATTACK3:
				if (keys.isKeyPressed(keys.A)) playerTurn(ATTACK3);
				else if (keys.isKeyPressed(keys.B)) selChoice = FIGHT;
				else checkKeys(keys, keys.RIGHT, ATTACK4, keys.UP, ATTACK1);
				break;
			case ATTACK4:
				if (keys.isKeyPressed(keys.A)) playerTurn(ATTACK4);
				else if (keys.isKeyPressed(keys.B)) selChoice = FIGHT;
				else checkKeys(keys, keys.LEFT, ATTACK3, keys.UP, ATTACK2);
				break;
			}
			if (!playerTurn && monster.getHealth( ) <= 0 ) {
				player.resetAP();
				return 1;
			} 
		}
		else if (betweenTurn) {
			tFrame++;
			if (tFrame >= 60) {
				tFrame = 0;
				betweenTurn = false;
			}
		}
		else {
			monsterTurn();
			playerTurn = true;
			if ( player.getHealth( ) <= 0 ) {
				player.resetAP();
				return -1;
			}	
		}
		return 0;
	}
	public void draw(Graphics2D g, ArroGraphics graphics) {
		graphics.drawCombatOptions(g, selChoice, player);
		graphics.drawBattleHUD(g, player);
		graphics.drawCombatMonster(g, monster, betweenTurn, tFrame);
			
		//draw defend
		//draw heal
		//draw endturn
	}

	private void playerTurn(int act) {
		if(act == 6) {
			playerTurn = false;
			betweenTurn = true;
			selChoice = -2;
			System.out.println("Ended turn. " + player.getHealth());
			return;
		}
		Action performed = player.getActions( ).get( act );
			if ( performed.getCost( ) > player.getAP( ) ) {
				return;
			} else {
				System.out.println(player.getActions().get(act).toString());
				perform(performed, player, monster);
				if (player.getAP() <= 0){
					selChoice = -2;
					playerTurn = false;
					betweenTurn = true;
				}
			}
	}
	
	private void monsterTurn(){
		int numActions;
		if(monster.getHealth( ) < monster.getMaxHealth( ))
			numActions = 4;
		else
			numActions = 3;
		
		int act = (int)(Math.random( ) * numActions);
		Action performed = monster.getActions( ).get( act );
		perform(performed, monster, player);
		player.resetAP();
		selChoice = FIGHT;
	}

	private void listActions(Graphics2D g) {
		//TODO: change to drawing actions to screen
		
		for ( int i = 0; i < actions.size( ); i++ ) {

			System.out.println( i + ". " + actions.get( i ).toString( ) );
		}
		System.out.println( "6. End Turn" );
	}

	private void printStats( ) {
		//TODO: change to drawing hud
		System.out.println( "Player:" );
		System.out.printf( "Health: %d/%d\tAP: %d\n\n", player.getHealth( ), player.getMaxHealth( ), player.getMaxAP( ) );
		
		System.out.println( "Monster:" );
		System.out.printf( "Health: %d/%d\n\n", monster.getHealth( ), monster.getMaxHealth( ));

	}
	
	private void perform(Action a, Being p, Being o){
		//leave alone
		switch(a.getActionType( )){
		case Action.ATTACK:
			switch(a.getWeaponType( )){
			case Action.MELEE:
				if(a.getAttackType() == Action.WEAK)
					o.subtractHealth( p.meleeDmg );
				else
					o.subtractHealth( p.meleeDmg * 2 );
				break;
			case Action.RANGED:
				if(a.getAttackType( ) == Action.WEAK)
					o.subtractHealth( p.rangeDmg );
				else
					o.subtractHealth( p.rangeDmg * 2 );
				break;
			case Action.MAGIC:
				if(a.getAttackType( ) == Action.WEAK)
					o.subtractHealth( p.magicDmg );
				else
					o.subtractHealth( p.magicDmg * 2 );
				break;
			}
			break;
		case Action.HEAL:
			p.addHealth(100 + 10 * p.intellect);
			break;
		case Action.DEFEND:
			p.defend();
			break;
		}
		if(p instanceof Player)
			((Player)p).actionPoints -= a.getCost( );
	}
}

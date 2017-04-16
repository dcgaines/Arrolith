import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import arcadia.Game;

public class ArroGraphics {
	
	double DIM = 100; //dimension of a space when drawn on a 1080p screen
	
	Image tileStrip;
	Animation juggernaut;
	Animation savant;
	Animation mason;
	Animation operative;
	Image portraits;
	Image splash;
	Animation monster;
	Animation monster2;
	Animation monster3;
	Image HUD, HUD_pts, HUD_timer, HUD_stats, HUD_battle;
	Image opt_fight, opt_defend, opt_heal, opt_end, opt_default;
	Image enemyHealth;
	Image numbers;
	Image potion;
	Image AP;
	Image logo;
	Image HGD;
	private Font alegreya;
	private Font pressStart;
	private Font pressStart40;
	BasicStroke borderStroke;
	
	
	/*****			CUSTOM COLORS  			****/
	Color shade = new Color(0,0,0,125);
	Color gold = new Color(255,185,0);
	
	Color red = new Color(255, 0, 0, 125);
	Color redFull = new Color(255,0,0);
	Color blue = new Color(0,0,255,125);
	Color blueFull = new Color(0,0,255);
	Color green = new Color(0,255,0,125);
	Color greenFull = new Color(0,255,0);
	Color yellow = new Color(255,255,0,125);
	Color yellowFull = new Color(255,255,0);
	Color transparent = new Color(0,0,0,0);
	
	double offsetX, offsetY;
	float multiplyer = (float) (Game.HEIGHT / 1080.);
	
	public ArroGraphics() {
		try {
			//Loads images into memory
			this.tileStrip = ImageIO.read(new File("img/tileStrip.png"));
			this.splash = ImageIO.read(new File("img/splash.png"));
			this.monster = new Animation(ImageIO.read(new File("img/monster.png")), 32, 32, new int[]{60, 65, 70, 75}, false);
			this.monster2 = new Animation(ImageIO.read(new File("img/monster2.png")), 32, 32, new int[]{10,20,30,40}, false);
			this.monster3 = new Animation(ImageIO.read(new File("img/monster3.png")), 32, 32, new int[]{5,10,15,20}, false);
			this.juggernaut = new Animation(ImageIO.read(new File("img/juggernaut.png")), 32, 32, new int[]{15,30,45,60}, false);
			this.savant = new Animation(ImageIO.read(new File("img/savant.png")), 32, 32, new int[]{15,30,45,60}, false);
			this.mason = new Animation(ImageIO.read(new File("img/mason.png")), 32, 32, new int[]{15,30,45,60}, false);
			this.operative = new Animation(ImageIO.read(new File("img/operative.png")), 32, 32, new int[]{15,30,45,60}, false);
			this.portraits = ImageIO.read(new File("img/portraits.png"));
			this.HUD = ImageIO.read(new File("img/hud.png"));
			this.HUD_pts = ImageIO.read(new File("img/hud_pts.png"));
			this.HUD_timer = ImageIO.read(new File("img/hud_timer.png"));
			this.HUD_stats = ImageIO.read(new File("img/hud_stats.png"));
			this.numbers = ImageIO.read(new File("img/nums.png"));
			this.HUD_battle = ImageIO.read(new File("img/hud_battle.png"));
			this.borderStroke = new BasicStroke(multiplyer(15));
			this.opt_fight = ImageIO.read(new File("img/bttl_fight.png"));
			this.opt_defend = ImageIO.read(new File("img/bttl_defend.png"));
			this.opt_heal = ImageIO.read(new File("img/bttl_heal.png"));
			this.opt_default = ImageIO.read(new File("img/bttl_default.png"));
			this.opt_end = ImageIO.read(new File("img/bttl_end.png"));
			this.potion = ImageIO.read(new File("img/elixir.png"));
			this.AP = ImageIO.read(new File("img/bttl_AP.png"));
			this.enemyHealth = ImageIO.read(new File("img/bttl_enemy_health.png"));
			this.logo = ImageIO.read(new File("img/logo.png"));
			this.HGD = ImageIO.read(new File("img/HGD.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			alegreya = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Alegreya.ttf"))
					.deriveFont(100 * (float) multiplyer);
			pressStart = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/pressStart.ttf")).deriveFont(50 * (float) multiplyer);
			pressStart40 = pressStart.deriveFont(40 * (float) multiplyer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Color getPlayerColor(int currPlayer) {
		switch (currPlayer) {
		case 0: return blue;
		case 1: return red;
		case 2: return green;
		case 3: return yellow;
		}
		return transparent;
	}
	
	//Centers the map around the current player
	public void centerMap(Graphics2D g, Map map, Player player) {
		offsetX = ((1920 - DIM) / 2) - (player.getX() * DIM);
		offsetY = ((1080 - DIM) / 2) - (player.getY() * DIM) - 160;
	}
	
	//Draws the map and players
	public void drawMap(Graphics2D g, Map map, ArrayList<Player> players, ArrayList<Monster> monsters, ArrayList<Potion> potions)
	{
		Tile tile; //temporary tile space
		for (int i = 0; i < map.getHeight(); i++)
			for (int j = 0; j < map.getWidth(); j++)
			{
				tile = map.getTile(i, j);
				g.drawImage(tileStrip, 
						(int) ((j * DIM + offsetX) * multiplyer), 
						(int) ((i * DIM + offsetY)* multiplyer), 
						(int) (((j + 1) * DIM + offsetX) * multiplyer),
						(int) (((i + 1) * DIM + offsetY)* multiplyer),
						(int) tile.getType() * 32, 
						0, (tile.getType() + 1) * 32, 32, null);
				
				g.setColor(getPlayerColor(tile.getOwner()));
				g.fillRect((int) ((j * DIM + offsetX) * multiplyer), (int) ((i * DIM + offsetY)* multiplyer), (int) (DIM * multiplyer), (int) (DIM * multiplyer));
			}
		drawMonsters(g, monsters);
		drawPotions(g, potions);
		drawPlayers(g, players);
	}
	private int posX(double x) { return (int) ((x + offsetX) * multiplyer); }
	private int posY(double y) { return (int) ((y + offsetY) * multiplyer); }
	//Draws the players on the map
	private void drawPlayers(Graphics2D g, ArrayList<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			switch (players.get(i).getType()) {
			case 0:
				juggernaut.draw(g, posX(players.get(i).getX() * DIM), posY(players.get(i).getY() * DIM), multiplyer(DIM), multiplyer(DIM), false);
				break;
			case 1:
				savant.draw(g, posX(players.get(i).getX() * DIM), posY(players.get(i).getY() * DIM), multiplyer(DIM), multiplyer(DIM), false);
				break;
			case 2:
				mason.draw(g, posX(players.get(i).getX() * DIM), posY(players.get(i).getY() * DIM), multiplyer(DIM), multiplyer(DIM), false);
				break;
			case 3:
				operative.draw(g, posX(players.get(i).getX() * DIM), posY(players.get(i).getY() * DIM), multiplyer(DIM), multiplyer(DIM), false);
				break;
			}
		}
		juggernaut.calculate();
		savant.calculate();
		mason.calculate();
		operative.calculate();
	}
	public void drawBattleHUD(Graphics2D g, Player player) {
		g.drawImage(HUD_battle, 0,
				 multiplyer(800), 
				 multiplyer(1920), 
				 multiplyer(1080), 0, 0, 480, 70, null);
		g.drawImage(portraits, 
				multiplyer(832),
				multiplyer(811),
				multiplyer(1088),
				multiplyer(1067),
				(player.getType()) * 64, 
				0,
				(player.getType() + 1) * 64,
				64, null);
		drawNumber(g, (byte) player.perseverance,  multiplyer(76), multiplyer(920), multiplyer(36), multiplyer(20));
		drawNumber(g, (byte) player.observation,  multiplyer(196), multiplyer(920), multiplyer(36), multiplyer(20));
		drawNumber(g, (byte) player.intellect,  multiplyer(316), multiplyer(920), multiplyer(36), multiplyer(20));
		drawNumber(g, (byte) player.negotiation,  multiplyer(436), multiplyer(920), multiplyer(36), multiplyer(20));
		drawNumber(g, (byte) player.tact,  multiplyer(556), multiplyer(920), multiplyer(36), multiplyer(20));
		drawNumber(g, (byte) player.strength,  multiplyer(676), multiplyer(920), multiplyer(36), multiplyer(20));
		g.setColor(gold);
		double rectWidth = 480 * (player.getHealth() / (double) player.getMaxHealth());
		g.fillRect(multiplyer(1236), multiplyer(876), multiplyer(rectWidth), multiplyer(36));
		drawCustomNumbers(g, player.getHealth(), multiplyer(1256 + rectWidth), multiplyer(876), multiplyer(36), multiplyer(20));
		drawCustomNumbers(g, player.getAP(), multiplyer(1236), multiplyer(960), multiplyer(36), multiplyer(20));
	}
	private void drawAction(Graphics2D g, Player p, int index, int startX, int startY) {
		g.setColor(Color.white);
		drawCenteredString(g, p.getActions().get(index).getName(), startX + multiplyer(48), startY + multiplyer(48), multiplyer(864), multiplyer(101), pressStart40);
		String type = (p.getActions().get(index).getAttackType() == 1 ? "Strong" : "Weak") + " - " +
					  (p.getActions().get(index).getWeaponType() == 2 ? "Magic" : p.getActions().get(index).getWeaponType() == 1 ? "Melee" : "Ranged");
		drawCenteredString(g, type, startX + multiplyer(48), startY + multiplyer(120), multiplyer(864), multiplyer(101), pressStart40);
		g.drawImage(AP, startX + multiplyer(416), startY + multiplyer(224), startX + multiplyer(520), startY + multiplyer(328), 0, 0, 13, 13, null);
		drawCustomNumbers(g, p.getActions().get(index).getCost(), startX + multiplyer(512), startY + multiplyer(264), multiplyer(72), multiplyer(40));
	}
	public void drawCombatOptions(Graphics2D g, int selChoice, Player p) {
		if (selChoice >= 0 && selChoice <= 3) {
			//TODO: draw fighting options
			g.drawImage(opt_default, 0, 0, multiplyer(960), multiplyer(400), 0, 0, 120, 50, null);
			g.drawImage(opt_default, 0, multiplyer(400), multiplyer(960), multiplyer(800), 0, 0, 120, 50, null);
			g.drawImage(opt_default, multiplyer(960), 0, multiplyer(1920), multiplyer(400), 0, 0, 120, 50, null);
			g.drawImage(opt_default, multiplyer(960), multiplyer(400), multiplyer(1920), multiplyer(800), 0, 0, 120, 50, null);
			drawAction(g, p, 0, 0, 0);
			drawAction(g, p, 1, multiplyer(960), 0);
			drawAction(g, p, 2, 0, multiplyer(400));
			drawAction(g, p, 3, multiplyer(960), multiplyer(400));
		}
		else {
			g.drawImage(opt_fight, 0, 0, multiplyer(960), multiplyer(400), 0, 0, 120, 50, null);
			g.drawImage(opt_defend, 0, multiplyer(400), multiplyer(960), multiplyer(800), 0, 0, 120, 50, null);
			g.drawImage(opt_heal, multiplyer(960), 0, multiplyer(1920), multiplyer(400), 0, 0, 120, 50, null);
			g.drawImage(opt_end, multiplyer(960), multiplyer(400), multiplyer(1920), multiplyer(800), 0, 0, 120, 50, null);
		}
		
		g.setColor(shade);
		if (selChoice > 0 || selChoice == -2) g.fillRect(0, 0, multiplyer(960), multiplyer(400));
		if (selChoice != 4 && selChoice != 1) g.fillRect(multiplyer(960), 0, multiplyer(960), multiplyer(400));
		if (selChoice != 5 && selChoice != 2) g.fillRect(0, multiplyer(400), multiplyer(960), multiplyer(400));
		if (selChoice != 6 && selChoice != 3) g.fillRect(multiplyer(960), multiplyer(400), multiplyer(960), multiplyer(400));
		g.setColor(gold);
		g.setStroke(borderStroke);
		switch (selChoice) {
		case -1: case 0: g.drawRect(0, 0, multiplyer(960), multiplyer(400)); break;
		case 4: case 1: g.drawRect(multiplyer(960), 0, multiplyer(960), multiplyer(400)); break;
		case 5: case 2: g.drawRect(0, multiplyer(400), multiplyer(960), multiplyer(400)); break;
		case 6: case 3: g.drawRect(multiplyer(960), multiplyer(400), multiplyer(960), multiplyer(400)); break;
		}
	}
	public void drawCombatMonster(Graphics2D g, Monster m, boolean betweenTurn, int tFrame) {
		switch (m.getLevel()) {
		case 1:
			monster.draw(g, multiplyer(800), multiplyer(240), multiplyer(320), multiplyer(320), true);
			break;
		case 2:
			monster2.draw(g, multiplyer(800), multiplyer(240), multiplyer(320), multiplyer(320), true);
			break;
		case 3:
			monster3.draw(g, multiplyer(800), multiplyer(240), multiplyer(320), multiplyer(320), true);
			break;
		}
		
		g.setColor(Color.red);
		g.drawImage(enemyHealth, multiplyer(832), multiplyer(600), multiplyer(1088), multiplyer(664),
					0, 0, 64, 16, null);
		double width = 232 * (m.getHealth() / (double) m.getMaxHealth());
		g.fillRect(multiplyer(844), multiplyer(612), multiplyer(width), multiplyer(40));
		if (betweenTurn) {
			g.setColor(Color.white);
			g.setFont(pressStart);
			String dots = tFrame < 20 ? "." : tFrame < 40 ? ".." : "...";
			g.drawString(dots, multiplyer(890), multiplyer(200));
		}
	}
	public void drawSelAction(Graphics2D g, Action action, boolean playerTurn, Player player, int amount) {
		String drawn = playerTurn ? player.name + " " : "Monster ";
		if (action.getActionType() == Action.ATTACK)  {
			drawn += "attacked for " + amount + " damage!";
		}
		else if (action.getActionType() == Action.HEAL) {
			drawn += "regained " + amount + " health!";
		}
		else if (action.getActionType() == Action.DEFEND) {
			drawn += "raised their defense!";
		}
		g.setColor(Color.black);
		g.fillRect(multiplyer(86), 0, multiplyer(1748), multiplyer(119));
		g.setColor(gold);
		g.setStroke(borderStroke);
		g.drawRect(multiplyer(86), 0, multiplyer(1748), multiplyer(119));
		g.setColor(Color.white);
		drawCenteredString(g, drawn, multiplyer(86), 0, multiplyer(1748), multiplyer(119), pressStart);		
	}
	private void drawCenteredString(Graphics2D g, String text, int x1, int y1, int width, int height, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = (width - metrics.stringWidth(text)) / 2 + x1;
	    int y = ((height - metrics.getHeight()) / 2) + metrics.getAscent() + y1;
	    g.setFont(font);
	    g.drawString(text, x, y);
	}
	public void drawMonsters(Graphics2D g, ArrayList<Monster> monsters) {
		monster.calculate();
		monster2.calculate();
		monster3.calculate();
		for (int i = 0; i < monsters.size(); i++) {
			Monster temp = monsters.get(i);
			switch (temp.getLevel()) {
			case 1:
				monster.draw(g, posX(temp.getX() * DIM), posY(temp.getY() * DIM), (int) (DIM * multiplyer), (int) (DIM * multiplyer), false);
				break;
			case 2:
				monster2.draw(g, posX(temp.getX() * DIM), posY(temp.getY() * DIM), (int) (DIM * multiplyer), (int) (DIM * multiplyer), false);
				break;
			case 3:
				monster3.draw(g, posX(temp.getX() * DIM), posY(temp.getY() * DIM), (int) (DIM * multiplyer), (int) (DIM * multiplyer), false);
				break;
			}
		}
	}
	public void drawPotions(Graphics2D g, ArrayList<Potion> potions) {
		for (int i = 0; i < potions.size(); i++) {
			Potion temp = potions.get(i);
			g.drawImage(potion, posX(temp.getX() * DIM), posY(temp.getY() * DIM), 
					posX(temp.getX() * DIM) + (int)(DIM * multiplyer),
					posY(temp.getY() * DIM) + (int)(DIM * multiplyer), 0, 0, 32, 32, null);
		}
	}
	private void drawCustomNumbers(Graphics2D g, int number, int posX, int posY, int height, int width) {
		String num = Integer.toString(number);
		for (int i = 0; i < num.length(); i++) {
			drawNumber(g, Byte.parseByte(Character.toString(num.charAt(i))),
					posX, posY, height, width);
			posX += width + (width / 5);
		}
	}
	private void drawNumber(Graphics2D g, byte number, int posX, int posY, int height, int width) {
		g.drawImage(numbers, posX, posY, posX + width, posY + height, number * 5, 0, (number + 1) * 5, 9, null);
	}
	//Draws the HUD on the screen
	public void drawHud(Graphics2D g, ArrayList<Player> players, byte actionsUsed, int tFrame, byte currPlayer) {
		g.drawImage(HUD_timer, multiplyer(808), 0, multiplyer(1112), multiplyer(56), 0, 0, 76, 14, null);
		drawCustomNumbers(g, Update.players.get(currPlayer).getAP() - actionsUsed, multiplyer(916), multiplyer(8), multiplyer(40), multiplyer(20));
		drawCustomNumbers(g, (InGame.moveTime - tFrame) / 30, multiplyer(1024), multiplyer(8), multiplyer(40), multiplyer(20));
		for (int i = 0; i < 4; i++) {
			g.drawImage(HUD, multiplyer(i * 480),
							 multiplyer(800), 
							 multiplyer((i + 1) * 480), 
							 multiplyer(1080), 0, 0, 120, 70, null);
			if (i < players.size()) {
				g.drawImage(HUD_pts, multiplyer(i * 480 + 23), multiplyer(812), multiplyer(i * 480 + 64), multiplyer(1072), 0, 0, 13, 65, null);
				drawCustomNumbers(g, players.get(i).perseverance, multiplyer(i * 480 + 72), multiplyer(812), multiplyer(40), multiplyer(20));
				drawCustomNumbers(g, players.get(i).observation, multiplyer(i * 480 + 72), multiplyer(856), multiplyer(40), multiplyer(20));
				drawCustomNumbers(g, players.get(i).intellect, multiplyer(i * 480 + 72), multiplyer(900), multiplyer(40), multiplyer(20));
				drawCustomNumbers(g, players.get(i).negotiation, multiplyer(i * 480 + 72), multiplyer(944), multiplyer(40), multiplyer(20));
				drawCustomNumbers(g, players.get(i).tact, multiplyer(i * 480 + 72), multiplyer(988), multiplyer(40), multiplyer(20));
				drawCustomNumbers(g, players.get(i).strength, multiplyer(i * 480 + 72), multiplyer(1032), multiplyer(40), multiplyer(20));
				//draw portrait
				g.drawImage(portraits, 
					multiplyer(i * 480 + 112),
					multiplyer(811),
					multiplyer(i * 480 + 368),
					multiplyer(1067),
					(players.get(i).getType()) * 64, 
					0,
					(players.get(i).getType() + 1) * 64,
					64, null);
				g.setColor(gold);
				int top = (int) (960 - 136 * ((1. * players.get(i).getHealth() / players.get(i).getMaxHealth())));
				g.fillRect(multiplyer(i * 480 + 404), multiplyer(top), multiplyer(36), multiplyer(960-top));
				drawCustomNumbers(g, players.get(i).getLevel(), multiplyer(i * 480 + 420), multiplyer(968), multiplyer(40), multiplyer(20));
				drawCustomNumbers(g, players.get(i).getInsignias(), multiplyer(i * 480 + 420), multiplyer(1010), multiplyer(40), multiplyer(20));
				//g.drawImage(HUD_stats, multiplyer(i * 480 + 380), multiplyer(868), multiplyer(i * 480 + 416), multiplyer(1000), 0, 0, 9, 33, null);
				}
		}
	}
	public void drawReady(Graphics2D g, int currPlayer) {
		g.setColor(Color.black);
		g.setFont(pressStart);
		g.fillRect(multiplyer(370), multiplyer(270), multiplyer(1180), multiplyer(260));
		g.setStroke(borderStroke);
		g.setColor(gold);
		g.drawRect(multiplyer(370), multiplyer(270), multiplyer(1180), multiplyer(260));
		g.setColor(Color.white);
		drawCenteredString(g, "Press A to start!", multiplyer(370), multiplyer(380), multiplyer(1180), multiplyer(130), pressStart);
		drawCenteredString(g, "Player " + (currPlayer + 1), multiplyer(370), multiplyer(290), multiplyer(1180), multiplyer(130), pressStart);
		g.setColor(getPlayerColor(currPlayer));
		drawCenteredString(g, "Player " + (currPlayer + 1), multiplyer(370), multiplyer(290), multiplyer(1180), multiplyer(130), pressStart);
		
		
//		g.setColor(Color.BLACK);
//		g.setFont(alegreya);
//		g.fillRect(multiplyer(760), multiplyer(340), 
//			multiplyer(400), (int) (400 * multiplyer));
//		g.setColor(Color.WHITE);
//		g.setStroke(borderStroke);
//		g.drawRect(multiplyer(760), multiplyer(340), 
//			multiplyer(400), multiplyer(400));
//		g.drawString("Press A", multiplyer(800), multiplyer(504));
//		g.drawString("to start.", multiplyer(800), multiplyer(640));
	}
	private int multiplyer(double num) {
		return (int) (num * multiplyer);
	}
	//Zooms in on the map
	public void zoomIn(double speed) {
		DIM += speed;
	}
	
	//Zooms out on the map
	public void zoomOut(double speed) {
		DIM -= speed;
	}
	
	//Moves left on the map
	public void moveLeft(double speed) {
		offsetX += speed;
	}
	
	//Moves right on the map
	public void moveRight(double speed) {
		offsetX -= speed;
	}
	
	//Draws the menu
	//TODO: Change when menu has been designed
	public void drawMenu(Graphics2D g, int menuState, int currPlayer, int selChar) {
//		if (menuState == 1)
//			g.setColor(Color.white);
		g.fillRect(0, 0, multiplyer(1920), multiplyer(1080)); // allows a clean reset of the image
		if (menuState != 1)
			g.drawImage(splash,multiplyer(-233), multiplyer(-355), multiplyer(2167), multiplyer(996), 0,0, 4106, 2310, null);
		g.setColor(Color.WHITE);
		g.setFont(alegreya);
		switch (menuState)
		{
		case 0:
			g.drawString(">  Play  <", multiplyer(796), multiplyer(762));
			g.drawString("Sound", multiplyer(827), multiplyer(882));
			break;
		case 4: //sound
			g.drawString("Play", multiplyer(865), multiplyer(762));
			g.drawString(">  Sound  <", multiplyer(759), multiplyer(882));
			break;
		case 1:
			g.setColor(getPlayerColor(currPlayer));
			g.fillRect(0, 0, multiplyer(1920), multiplyer(1080));
			g.drawImage(portraits, 
					multiplyer(736),
					multiplyer(91),
					multiplyer(1184),
					multiplyer(539),
					selChar * 64, 
					0,
					(selChar + 1) * 64,
					64, null);
			g.setColor(Color.black);
			g.fillRect(0, multiplyer(630), multiplyer(1920), multiplyer(450));
			g.setColor(Color.white);
			switch (selChar) {
			case 0: drawCenteredString(g, "JUGGERNAUT of the Hornan Subjugation", 0, multiplyer(670), multiplyer(1920), multiplyer(50), pressStart);break; //draw juggernaut options
			case 1: drawCenteredString(g, "SAVANT of the Rumination Guild", 0, multiplyer(670), multiplyer(1920), multiplyer(50), pressStart);break; //draw savant options
			case 2: drawCenteredString(g, "MASON of the Hearth and Eye Clergy", 0, multiplyer(670), multiplyer(1920), multiplyer(50), pressStart);break; //draw mason options
			case 3: drawCenteredString(g, "OPERATIVE of the Caligo Company", 0, multiplyer(670), multiplyer(1920), multiplyer(50), pressStart);break; //draw operative options
			}
			break;
		}
	}

	public void drawEndGame(Graphics2D g, ArrayList<Player> players, int place1, int place2, int place3, int place4) {
		g.setColor(Color.black);
		g.fillRect(0, 0, multiplyer(1920), multiplyer(1080));
		
		g.setColor(Color.white);
		// draw winner up top
		g.drawImage(portraits, multiplyer(1145), multiplyer(50), multiplyer(1594), multiplyer(498),
				players.get(place1).getType() * 64, 0, (players.get(place1).getType() + 1) * 64, 64, null);
		drawCenteredString(g, players.get(place1).name + " wins!", multiplyer(340), multiplyer(100), multiplyer(700), multiplyer(50), pressStart);
		drawCustomNumbers(g, 1, multiplyer(590), multiplyer(200), multiplyer(200), multiplyer(100));
		
		// draw 2nd place bottom left
		g.drawImage(portraits, multiplyer(64), multiplyer(658), multiplyer(448), multiplyer(1042),
				players.get(place2).getType() * 64, 0, (players.get(place2).getType() + 1) * 64, 64, null);
		drawCenteredString(g, players.get(place2).name, multiplyer(64), multiplyer(595), multiplyer(384), multiplyer(50), pressStart);
		drawCustomNumbers(g, 2, multiplyer(500), multiplyer(762), multiplyer(150), multiplyer(75));
		
		if (players.size() > 2) {
			// draw 3rd place bottom middle
			g.drawImage(portraits, multiplyer(800), multiplyer(720), multiplyer(1120), multiplyer(1042),
					players.get(place3).getType() * 64, 0, (players.get(place3).getType() + 1) * 64, 64, null);
			drawCenteredString(g, players.get(place3).name, multiplyer(800), multiplyer(660), multiplyer(320), multiplyer(50), pressStart);
			drawCustomNumbers(g, 3, multiplyer(1160), multiplyer(813), multiplyer(100), multiplyer(50));
			
			if (players.size() > 3) {
				// draw 4th place bottom right
				g.drawImage(portraits, multiplyer(1442), multiplyer(786), multiplyer(1698), multiplyer(1042),
						players.get(place4).getType() * 64, 0, (players.get(place4).getType() + 1) * 64, 64, null);
				drawCenteredString(g, players.get(place4).name, multiplyer(1442), multiplyer(716), multiplyer(256), multiplyer(50), pressStart);
				drawCustomNumbers(g, 4, multiplyer(1720), multiplyer(862), multiplyer(50), multiplyer(25));
			}
		}

	}
	public void drawStartup(Graphics2D g) {
		g.drawImage(logo, multiplyer(489), multiplyer(62), multiplyer(1432), multiplyer(449), 0, 0, 943, 387, null);
		g.drawImage(HGD, multiplyer(120), multiplyer(462), multiplyer(1800), multiplyer(1062), 0, 0, 2100, 750, null);
	}
}


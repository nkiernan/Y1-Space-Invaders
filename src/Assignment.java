import processing.core.*;
public class Assignment extends PApplet{

	public static void main(String args[]) {
		PApplet.main(new String[] {"--present", "Assignment"});
	}

	Defender Player1;
	DefenderBullet defBullet;
	Enemy[][] Enemies = new Enemy[3][5]; //Use 3D array for enemies
	EnemyBullet eBullet;
	PImage background;
	int bY = 0;
	PImage logo;
	int GameLevel = 0; //Title screen will be loaded first (Level 0)
	int Hiscore = 0;
	int EnemyCount; //Used to keep track of enemies during each level	

	public void setup() //Done during launch
	{
		size(600, 660); //Window size will be 600 pixels wide by 660 pixels tall
		Player1 = new Defender(300, 600, 10, 3, 0, this); //Generate Player1
		background = loadImage("background.jpg"); //Load background image
		logo = loadImage("logo.png"); //Load title screen image
	}

	public void draw()
	{
		image (background, 0, bY);
		image (background, 0, bY + (background.height - 2));
		bY -= 2;
		if (bY == -background.height)
		{
			bY = 0;
		}
		gameInfo();

		switch (GameLevel) {

		case 0: //Title Screen
			image (logo, 90, 120); //Display title screen image
			break;

		case 1: //Level 1, 1 row of enemies with dx of 1
			handlePlayer();
			drawEnemies(1);
			enemyShots(1);
			hitEnemies(1);
			trackEnemies(1, 1);
			handleGameOver(1);
			if (EnemyCount == 0) //If all enemies are defeated:
			{
				nextLevel(2, 1); //Go to next level
			}
			break;

		case 2: //Level 2, 2 rows of enemies with dx of 1
			handlePlayer();			
			drawEnemies(2);
			enemyShots(2);
			hitEnemies(2);
			trackEnemies(2, 1);
			handleGameOver(2);
			if (EnemyCount == 0)
			{
				nextLevel(3, 1);
			}
			break;

		case 3: //Level 3, 3 rows of enemies with dx of 1
			handlePlayer();
			drawEnemies(3);
			enemyShots(3);
			hitEnemies(3);
			trackEnemies(3, 1);
			handleGameOver(3);
			if (EnemyCount == 0)
			{
				nextLevel(1, 2);
			}
			break;

		case 4: //Level 4, 1 row of enemies with dx of 2
			handlePlayer();
			drawEnemies(1);
			enemyShots(1);
			hitEnemies(1);
			trackEnemies(1, 2);
			handleGameOver(1);
			if (EnemyCount == 0)
			{
				nextLevel(2, 2);
			}
			break;

		case 5: //Level 5, 2 rows of enemies with dx of 2
			handlePlayer();
			drawEnemies(2);
			enemyShots(2);
			hitEnemies(2);
			trackEnemies(2, 2);
			handleGameOver(2);
			if (EnemyCount == 0)
			{
				nextLevel(3, 2);
			}
			break;

		case 6: //Level 6, 3 rows of enemies with dx of 2
			handlePlayer();
			drawEnemies(3);
			enemyShots(3);
			hitEnemies(3);
			trackEnemies(3, 2);
			handleGameOver(3);
			if (EnemyCount == 0)
			{
				nextLevel(1, 3);
			}
			break;

		case 7: //Level 7, 1 row of enemies with dx of 3
			handlePlayer();
			drawEnemies(1);
			enemyShots(1);
			hitEnemies(1);
			trackEnemies(1, 3);
			handleGameOver(1);
			if (EnemyCount == 0)
			{
				nextLevel(2, 3);
			}
			break;

		case 8: //Level 8, 2 rows of enemies with dx of 3
			handlePlayer();
			drawEnemies(2);
			enemyShots(2);
			hitEnemies(2);
			trackEnemies(2, 3);
			handleGameOver(2);
			if (EnemyCount == 0)
			{
				nextLevel(3, 3);
			}
			break;

		case 9: //Level 9, 3 rows of enemies with dx of 3
			handlePlayer();
			drawEnemies(3);
			enemyShots(3);
			hitEnemies(3);
			trackEnemies(3, 3);
			handleGameOver(3);
			if (EnemyCount == 0) //If all enemies are defeated:
			{
				Player1.deltaX = 0; //Stop player from moving
				fill(255); //Set text to white
				text("Congratulations!\n\t\t(Press Enter)", 240, 300); //Display text to player at coords 240, 300
			}
			break;
		}
	}

	public void keyPressed() //Player controls
	{
		if (key == ENTER && GameLevel == 0) //If player presses enter on title screen:
		{
			Player1 = new Defender(300, 600, 5, 3, 0, this); //Load new player
			nextLevel(1, 1); //Go to level 1
		}

		if (key == ENTER && Player1.lives == 0 || key == ENTER && Player1.deltaX == 0) //If player presses enter and game has ended:
		{
			GameLevel = 0; //Go back to menu
			Player1.lives = 3; //Reset player lives to 3
			Player1.score = 0; //Reset player's current score to 0
		}

		if (key == CODED)
		{
			if (keyCode == LEFT && Player1.x > 15) //If player presses left and isn't too far to the left:
			{
				Player1.x = Player1.x - Player1.deltaX; //Move player horizontally left by deltaX value
			}
			if (keyCode == RIGHT && Player1.x < width - 40) //If player presses left and isn't too far to the right:
			{
				Player1.x = Player1.x + Player1.deltaX; //Move player horizontally right by deltaX value
			}

			if (keyCode == UP && defBullet == null) //If player presses up and has not bullet already on screen:
			{
				defBullet = new DefenderBullet(Player1.x - 10, Player1.y - 10, 5, this); //Generate new bullet at player's coords
			}
		}
	}

	public void generateEnemies(int rows, int speed) //Generate enemies for level passing in amount of rows and enemy speed
	{
		{
			int y = 30; //First enemy y value
			int x = 25; //First enemy x value
			int minX = 20; //First enemy minimum x value
			int maxX = 325; //First enemy maximum x value
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					Enemies[i][j] = null;
					Enemies[i][j] = new Enemy(x, minX, maxX, speed, y, 25, this);
					x = x + 60; //Each enemy will be 60 pixels to the right of previous enemy
					//Keep enemy paths evenly spaced to prevent them overlapping each other:
					minX = minX + 60;
					maxX = maxX + 60;
				}
				//When each row reaches five enemies:
				y = y + 50; //Move new row down by 50 pixels
				x = 25; //Reset x value
				minX = 20; //Reset minX value
				maxX = 325; //Rest maxX value
			}
		}	
	}

	public void regenerateEnemies(int rows, int speed) //Similar to generateEnemies(), used when enemies reach bottom of screen
	{
		{
			int y = 30;
			int x = 25;
			int minX = 20;
			int maxX = 325;
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					//Moves enemies back to the top of the screen if they have not been shot, the rest are kept out of play:
					if (Enemies[i][j].inPlay == true)
					{
						Enemies[i][j] = new Enemy(x, minX, maxX, speed, y, 25, this);
					}
					x = x + 60;
					minX = minX + 60;
					maxX = maxX + 60;
				}
				y = y + 50;
				x = 25;
				minX = 20;
				maxX = 325;
			}
		}	
	}

	public void drawEnemies(int rows) //Draws enemies if they are in play
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (Enemies[i][j].inPlay == true)
				{
					Enemies[i][j].spawn();
				}
			}
		}
	}

	public void gameInfo() //Displays game information to player
	{
		if (Player1.score >= Hiscore) //If current score exceeds high score:
		{
			Hiscore = Player1.score; //Update high score
		}
		//Using a black rectangle to make text clearer:
		fill(0);
		rect(0, 0, 600, 12);
		//Set text to white:
		fill(255);
		text("Level: "+GameLevel, 20, 15); //Display current level
		text("Lives: "+Player1.lives, 170, 15); //Display player's lives
		text("Score: "+Player1.score, 320, 15); //Display player's score
		text("Hi-Score: " +Hiscore, 470, 15); //Display high score
		if (Player1.lives == 0) //If player loses all lives:
		{
			text(" Game Over!\n(Press Enter)", 270, 300); //Display game over message
		}
	}

	public void handlePlayer()
	{
		Player1.render(); //Draw player
		if (defBullet != null) //Is player bullet is not null:
		{
			defBullet.fire(); //Fire bullet
			if (defBullet.y <= 0) //If bullet reaches top of screen:
			{
				defBullet = null; //Remove bullet
			}
		}
	}

	public void handleGameOver(int rows) //Pass in amount of enemy rows
	{
		if (Player1.lives == 0) //If player loses all lives:
		{
			Player1.y = -100; //Move player off screen
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					Enemies[i][j].deltaX = 0; //Stop all enemies from moving
				}
			}
		}
	}

	public void nextLevel(int rows, int speed) //Generate next level passing in enemy rows and speed
	{
		EnemyCount = rows * 5; //EnemyCount will always be a multiple of 5
		generateEnemies(rows, speed); //Call generateEnemies passing in rows and speed
		GameLevel++; //Increase GameLevel by 1
		eBullet = null; //Remove enemy bullet
	}

	public void trackEnemies(int rows, int speed) //Used to deal with enemies reaching bottom of the screen
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (Enemies[i][j].y >= 580) //If enemy reaches bottom of the screen:
				{
					Player1.lives--; //Reduce player's lives by 1
					Player1.x = 300; //Move player back to starting position
					if (Player1.lives != 0) //If player still has lives:
					{
						for (int k = 0; k < rows; k++)
						{
							for (int l = 0; l < 5; l++)
							{
								regenerateEnemies(rows, speed); //Send all enemies still inPlay back to starting positions
							}
						}
					}
				}
			}
		}
	}

	public void hitEnemies(int rows)
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (Enemies[i][j].isShot() == true) //If an enemy has been shot:
				{
					Player1.score = Player1.score + 100; //Increase player's current score by 100
					defBullet = null; //Remove player's bullet
					Enemies[i][j].y = -100; //Move hit enemy off screen
					Enemies[i][j].deltaX = 0; //Stop offscreen enemy from moving
					Enemies[i][j].inPlay = false; //Set offscreen enemy to out of play
					EnemyCount--; //Reduce enemy count by 1
				}
			}
		}
	}

	public void enemyShots(int rows)
	{
		if (EnemyCount != 0 && Player1.lives != 0) //If there are still enemies and player has lives:
		{
			while (eBullet == null) //While there is no enemy bullet:
			{
				float ry = random(1, rows); //Pick a random row
				int randY = (int) ry; //Convert the float to an int value
				float rx = random(1, 6); //Pick a random column
				int randX = (int) rx; //Convert the float to an int value
				if (Enemies[randY - 1][randX - 1].inPlay == true) //If selected enemy is still in play:
				{
					eBullet = new EnemyBullet(Enemies[randY - 1][randX - 1].x - 10, Enemies[randY - 1][randX - 1].y + 20, 4, this); //Generate bullet
				}
			}
			eBullet.fire(); //Fire bullet
			if (eBullet.y >= height) //If enemy bullet reaches bottom of screen:
			{
				eBullet = null; //Remove enemy bullet
			}
		}
		if (Player1.isShot() == true) //If player is shot:
		{
			eBullet = null; //Remove enemy bullet
			Player1.lives--; //Reduce player's lives by 1
			Player1.x = 300; //Reset player's position
		}
	}
}
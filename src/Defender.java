import processing.core.*;
public class Defender extends PApplet{
	PApplet parent;
	int x, y, deltaX, lives, score;
	
	public boolean isShot() //Boolean to check if player has been shot
	{
		//Check top of Defender:
		for (int i = x - 15; i <= x + 35; i++) //Use range of values to check x
		{
			int c1 = parent.get(i, y); //y will always be same value during check
			if (c1 == parent.color(255, 255, 0)) //If colour of checked coords is same as enemy bullet:
			{
				return true; //Player has been shot
			}
		}
		//Check left of Defender:
		for (int i = y; i <= y + 40; i++) //Use range of values to check y
		{
			int c2 = parent.get(x - 15, i); //x will always be same value during check
			if (c2 == parent.color(255, 255, 0)) //If colour of checked coords is same as enemy bullet:
			{
				return true; //Player has been shot
			}
		}
		//Check right of Defender:
		for (int i = y; i <= y + 40; i++) //Use range of values to check y
		{
			int c3 = parent.get(x + 35, i); //x will always be same value during check
			if (c3 == parent.color(255, 255, 0)) //If colour of checked coords is same as enemy bullet:
			{
				return true; //Player has been shot
			}
		}
		return false; //Player has not been shot
	}

	public void render()
	{
		//Blue body:
		parent.fill(0, 0, 255);
		parent.rect(x, y, 19, 30);
		
		//Cyan wings and nose:
		parent.fill(0, 150, 150);
		parent.triangle(x + 19, y + 5, x + 35, y + 5, x + 19, y + 20);
		parent.triangle(x, y + 5, x - 15, y + 5, x, y + 20);
		parent.triangle(x + 19, y + 30, x + 1, y + 30, x + 10, y + 40);
		
		//Grey gun:
		parent.fill(190, 190, 190);
		parent.rect(x + 3, y + 7, 13, 10);
		parent.fill(105, 105, 105);
		parent.rect(x + 7, y + 2, 5, 10);
	}

	Defender (int x, int y, int dx, int lives, int score, PApplet p)
	{
		this.x = x; //Player X position
		this.y = y; //Player Y position
		this.deltaX = dx; //Player horizontal movement speed
		this.lives = lives; //Player's lives
		this.score = score; //Player's current score
		parent = p;
	}
}
import processing.core.*;
public class Enemy extends PApplet{
	PApplet parent;
	int x, minX, maxX, deltaX, y, deltaY;
	boolean inPlay = true; //Enemy is automatically inPlay when level starts

	public void spawn() //Call render(); and move(); together with only one line
	{
		render();
		move();
	}

	public boolean isShot() //Boolean to check if enemy has been shot
	{
		//Check bottom of Enemy:
		for (int i = x - 15; i <= x + 35; i++) //Use range of values to check x
		{
			int c1 = parent.get(i, y + 40); //y will always be same value during check
			if (c1 == parent.color(0, 255, 255)) //If colour of checked coords is same as player bullet:
			{
				return true; //Enemy has been shot
			}
		}
		//Check left of Enemy:
		for (int i = y; i <= y + 40; i++) //Use range of values to check y
		{
			int c2 = parent.get(x - 15, i); //x will always be same value during check
			if (c2 == parent.color(0, 255, 255)) //If colour of checked coords is same as player bullet:
			{
				return true; //Enemy has been shot
			}
		}
		//Check right of Enemy:
		for (int i = y; i <= y + 40; i++) //Use range of values to check y
		{
			int c3 = parent.get(x + 35, i); //x will always be same value during check
			if (c3 == parent.color(0, 255, 255)) //If colour of checked coords is same as player bullet:
			{
				return true; //Enemy has been shot
			}
		}
		return false; //Enemy has been not shot
	}
	
	public void move()
	{
		x = x + deltaX; //Move enemy horizontally
		if (x <= minX || x >= maxX) //If enemy moves too far to left/right
		{
			y = y + deltaY; //Move enemy down screen by set value (deltaY)
			deltaX = -deltaX; //Reverse horizontal direction of enemy movement
		}
	}

	public void render()
	{
		//Red body:
		parent.fill(255, 0, 0);
		parent.rect(x, y, 19, 30);
		
		//Orange wings and nose:
		parent.fill(255, 215, 0);
		parent.triangle(x + 19, y + 5, x + 35, y + 5, x + 19, y + 20);
		parent.triangle(x, y + 5, x - 15, y + 5, x, y + 20);
		parent.triangle(x + 19, y + 30, x + 1, y + 30, x + 10, y + 40);
		
		//Grey gun:
		parent.fill(190, 190, 190);
		parent.rect(x + 3, y + 7, 13, 10);
		parent.fill(105, 105, 105);
		parent.rect(x + 7, y + 12, 5, 10);
	}

	Enemy (int x, int minX, int maxX, int deltaX, int y, int deltaY, PApplet p)
	{
		this.x = x; //Enemy X position
		this.minX = minX; //Lowest value of enemy X position
		this.maxX = maxX; //Highest value of enemy X position
		this.deltaX = deltaX; //Enemy horizontal movement speed
		this.y = y; //Enemy Y position
		this.deltaY = deltaY; //How far enemy will move downwards when minX or maxX is reached
		parent = p;
	}
}
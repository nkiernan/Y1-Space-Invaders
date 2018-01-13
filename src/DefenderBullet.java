import processing.core.*;
public class DefenderBullet extends PApplet{
	PApplet parent;
	int x, y, deltaY;
	
	public void fire()
	{
		render();
		move();
	}
	
	public void render()
	{
		parent.fill(0, 255, 255); //Bullet is cyan
		parent.rect(x + 17, y + 2, 5, 10); //Bullet is 5 pixels wide by 10 pixels tall
	}
	
	public void move()
	{
		y = y - deltaY; //Causes bullet to move vertically upwards
	}

	DefenderBullet (int x, int y, int deltaY, PApplet p)
	{
		this.x = x; //Bullet's X position
		this.y = y; //Bullet's Y position
		this.deltaY = deltaY; //Bullet's vertical speed
		parent = p;
	}
}

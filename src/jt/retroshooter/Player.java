package jt.retroshooter;

public class Player implements IGamePiece
{
	private int health;
	private int x;
	private int y;
	
	public Player()
	{
		health = 10;
		x = 0;
		y = 0;
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getX()
	{
		return x;
	}
	
	@Override
	public int getY()
	{
		return y;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void damage()
	{
		health--;
	}
}

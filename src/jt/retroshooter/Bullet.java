package jt.retroshooter;

public class Bullet implements IGamePiece
{
	private int x;
	private int y;
	private int damage;
	
	public Bullet()
	{
		x = 0;
		y = 0;
		damage = 1;
	}
	
	public int getDamage()
	{
		return damage;
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
}

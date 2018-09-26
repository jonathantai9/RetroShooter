package jt.retroshooter;

public class Enemy implements IGamePiece
{
	private int health;
	private int x;
	private int y;
	private boolean isChallenge;
	
	public Enemy(boolean isChallenge)
	{
		x = 0;
		y = 0;
		this.isChallenge = isChallenge;
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
	
	public void damage(int damage)
	{
		health -= damage;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	public boolean isChallengeEnemy()
	{
		return isChallenge;
	}
}

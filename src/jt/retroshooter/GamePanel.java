package jt.retroshooter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{
	public static final int EMPTY = 0;
	public static final int PLAYER = 1;
	public static final int PLASMA_TORPEDO = 2;
	public static final int ENEMY = 10;
	public static final int ENEMY_CHALLENGE = 11;
	
	private int[][] cells = new int[40][70];
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Enemy> enemies;

	private Random random;
	
	private int fireRate;
	private int shotCooldown;
	
	public GamePanel(JFrame parentFrame)
	{
		this.setPreferredSize(new Dimension(400, 700));
		player = new Player();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		random = new Random();
		
		fireRate = 50;
		shotCooldown = 0;
	}
	
	public void update()
	{
		setCellType(player.getX(), player.getY(), PLAYER);
		
		for(Bullet bullet : bullets)
		{
			int bulletX = bullet.getX();
			int bulletY = bullet.getY();
			
			if(bullet.getY() == 0)
			{
				bullets.remove(bullet);
				setCellType(bulletX, bulletY, EMPTY);
				break;
			}
			
			if(cells[bulletX][bulletY - 1] >= ENEMY)
			{
				for(Enemy enemy : enemies)
				{
					if(enemy.getX() == bulletX && enemy.getY() == bulletY - 1)
					{
						enemy.damage(bullet.getDamage());
						if(enemy.getHealth() <= 0)
						{
							setCellType(enemy.getX(), enemy.getY(), EMPTY);
							enemies.remove(enemy);
						}
						break;
					}
				}
				bullets.remove(bullet);
				setCellType(bulletX, bulletY, EMPTY);
				break;
			}
			bullet.setLocation(bulletX, bulletY - 1);
			setCellType(bulletX, bulletY - 1, PLASMA_TORPEDO);
			setCellType(bulletX, bulletY, EMPTY);
		}
		
		for(Enemy enemy : enemies)
		{
			int enemyX = enemy.getX();
			int enemyY = enemy.getY();
			setCellType(enemyX, enemyY, enemy.isChallengeEnemy() ? ENEMY_CHALLENGE : ENEMY);
			
			if(enemy.getHealth() <= 0)
			{
				setCellType(enemyX, enemyY, EMPTY);
				enemies.remove(enemy);
			}
			else
			{
				if(random.nextInt(100) == 0)
				{
					if(enemy.getY() < 69)
					{
						setCellType(enemyX, enemyY, EMPTY);
						enemy.setLocation(enemyX, enemyY + 1);
					}
					else
					{
						setCellType(enemyX, enemyY, EMPTY);
						player.damage();
						enemies.remove(enemy);
						break;
					}
				}
			}
		}
		
		if(shotCooldown > 0)
			shotCooldown--;
	}
	
	public void fillCells()
	{
		for(int i = 0; i < cells.length; i++)
			for(int j = 0; j < cells[0].length; j++)
				setCellType(i, j, EMPTY);
	}
	
	public void placePlayer(int x, int y)
	{
		int playerX = player.getX();
		int playerY = player.getY();
		player.setLocation(x, y);
		setCellType(playerX, playerY, EMPTY);
	}
	
	public void setCellType(int x, int y, int type)
	{
		cells[x][y] = type;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void shoot()
	{
		if(shotCooldown == 0)
		{
			int x = player.getX();
			int y = player.getY() - 1;
			Bullet bullet = new Bullet();
			bullet.setLocation(x,  y);
			addBullet(bullet);
			
			shotCooldown = fireRate;
		}
	}
	
	public void addBullet(Bullet bullet)
	{
		bullets.add(bullet);
	}
	
	public void spawnEnemy()
	{
		int x = random.nextInt(40);
		int y = random.nextInt(10);
		
		boolean isChallengeEnemy = random.nextInt(20) == 0;
		Enemy enemy = new Enemy(isChallengeEnemy);
		
		if(isChallengeEnemy)
			enemy.setHealth(10);
		else
			enemy.setHealth(3);
		
		enemy.setLocation(x, y);
		enemies.add(enemy);
	}
	
	public Random getRandom()
	{
		return random;
	}
	
	@Override
	public void paintComponent(Graphics graphics)
	{
		for(int i = 0; i < cells.length; i++)
		{
			for(int j = 0; j < cells[0].length; j++)
			{
				Color color;
				switch(cells[i][j])
				{
					case EMPTY:
					default:
						color = Color.BLACK;
						break;
					case PLAYER:
						color = Color.WHITE;
						break;
					case PLASMA_TORPEDO:
						color = Color.YELLOW;
						break;
					case ENEMY:
						color = Color.GREEN;
						break;
					case ENEMY_CHALLENGE:
						color = Color.CYAN;
						break;
				}
				graphics.setColor(color);
				graphics.fillRect(i * 10, j * 10, 10, 10);
			}
		}
		
		graphics.setColor(Color.RED);
		graphics.drawLine(player.getX() * 10 + 5, player.getY() * 10, player.getX() * 10 + 5, 2);
		
		if(Game.getInstance().isPaused())
		{
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("Courier New", Font.PLAIN, 40));
			graphics.drawString("PAUSED", 140, 350);
			
		}
	}
}

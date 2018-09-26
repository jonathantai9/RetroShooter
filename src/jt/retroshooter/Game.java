package jt.retroshooter;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game implements ActionListener, KeyListener
{
	private static Game instance;
	
	public static void main(String[] args)
	{
		instance = new Game();
		instance.launch();
	}
	
	public static Game getInstance()
	{
		return instance;
	}
	
	private JFrame frame;
	private GamePanel gamePanel;
	private Timer timer;
	
	private boolean isPaused;
	
	public Game()
	{
		frame = new JFrame("Retro Shooter");
		frame.setSize(new Dimension(500, 900));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		gamePanel = new GamePanel(frame);
		frame.add(gamePanel);
		frame.addKeyListener(this);
		
		timer = new Timer(10, this);
		
		isPaused = false;
	}
	
	public void launch()
	{
		frame.setVisible(true);
		gamePanel.fillCells();
		gamePanel.placePlayer(35, 65);
		
		timer.start();
	}
	
	public boolean isPaused()
	{
		return isPaused;
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == timer)
		{
			gamePanel.update();
			gamePanel.repaint();
			
			if(gamePanel.getRandom().nextInt(200) == 0)
			{
				gamePanel.spawnEnemy();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		System.out.println(event.getKeyChar());
		if(!isPaused)
		{
			if(event.getKeyChar() == 'a' || event.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if(gamePanel.getPlayer().getX() > 0)
				{
					gamePanel.placePlayer(gamePanel.getPlayer().getX() - 1, gamePanel.getPlayer().getY());
				}
			}
			if(event.getKeyChar() == 'd' || event.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if(gamePanel.getPlayer().getX() < 39)
				{
					gamePanel.placePlayer(gamePanel.getPlayer().getX() + 1, gamePanel.getPlayer().getY());	
				}
			}
			if(event.getKeyCode() == KeyEvent.VK_SPACE || event.getKeyCode() == KeyEvent.VK_UP)
			{
				gamePanel.shoot();
			}	
		}
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			if(isPaused)
			{
				isPaused = false;
				timer.start();
			}
			else
			{
				isPaused = true;
				timer.stop();
			}
			gamePanel.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent event){}

	@Override
	public void keyTyped(KeyEvent event){}
}

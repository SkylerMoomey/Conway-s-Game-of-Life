package game;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Life implements MouseListener, ActionListener{

	
	public final static int WIDTH = 500;
	public final static int HEIGHT = WIDTH;
	protected boolean[][] currentGen;
	protected boolean[][] nextGen;
	
	private boolean running;
	private JButton start;
	private JButton step;
	private Container south;
	protected Panel p;
	
	public Life(int rows)
	{
		currentGen = new boolean[rows][rows];
		nextGen = new boolean[rows][rows];
		p = new Panel(currentGen);
		running = false;
		south = new Container();
		south.setLayout(new GridLayout(0, 2));
		start = new JButton("START");
		step = new JButton("STEP");
		south.add(step);
		south.add(start);
		
		JFrame lifeBoard = new JFrame("Conway's Game of Life");
		
		lifeBoard.setSize(WIDTH, HEIGHT + 50);
		lifeBoard.setLayout(new BorderLayout());
		
		lifeBoard.add(p, BorderLayout.CENTER);
		lifeBoard.add(south, BorderLayout.SOUTH);
		start.addActionListener(this);
		step.addActionListener(this);
		p.addMouseListener(this);
		
	
		
		lifeBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lifeBoard.setVisible(true);
	}
	
	public static void main(String [] args)
	{
		new Life(50);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		System.out.println("" + e.getX() + ", " + e.getY());
		
		double width = WIDTH / currentGen[0].length;
		double height = HEIGHT / currentGen.length;
		
		int column = (int) (e.getX() / width);
		int row = (int) (e.getY() / height);
		
		currentGen[column][row] = !currentGen[column][row];
		nextGen[column][row] = !nextGen[column][row];
		p.repaint();
		
	}
	
	public void step()
	{
		for(int x = 1; x < currentGen[0].length - 1; x++)
		{
			for(int y = 1; y < currentGen.length - 1; y++)
			{
				int aliveCellNeighbors = 0;
			
				//if statements to determine how many neighbors are alive
			
				//top three neighbors
				if(currentGen[x - 1][y - 1])
				{
					aliveCellNeighbors++;
				}
				if(currentGen[x][y - 1])
				{
					aliveCellNeighbors++;
				}
				if(currentGen[x + 1][y - 1])
				{
					aliveCellNeighbors++;
				}
			
				//neighbors on either side
				if(currentGen[x - 1][y])
				{
					aliveCellNeighbors++;
				}
				if(currentGen[x + 1][y])
				{
					aliveCellNeighbors++;
				}
			
				//neighbors underneath
				if(currentGen[x - 1][y + 1])
				{
					aliveCellNeighbors++;
				}
				if(currentGen[x][y + 1])
				{
					aliveCellNeighbors++;
				}
				if(currentGen[x + 1][y + 1])
				{	
					aliveCellNeighbors++;
				}
			
			//algorithm then recalculates if the cell is dead or alive
			
			/*
			 * rules:
			 * if number of live neighbors < 2, cell dies
			 * if number of live neighbors >= 4, cell dies
			 * if cell is dead, and number of live neighbors == 3, cell revives
			 * if cell is alive, and number of life neighbors == 2 or 3, no change
			 */
			
			if(currentGen[x][y])
			{
				if(aliveCellNeighbors < 2)
				{
					nextGen[x][y] = false;
				}
				if(aliveCellNeighbors >= 4)
				{
					nextGen[x][y] = false;
				}
			}
			else
			{
				if(aliveCellNeighbors == 3)
				{
					nextGen[x][y] = true;
				}
			}
		}
		
		}
		
		for(int x = 0; x < currentGen[0].length; x++)
		{
			for(int y = 0; y < currentGen.length; y++)
			{
				currentGen[x][y] = nextGen[x][y];
			}
		}
		p.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		
		if(e.getSource().equals(step))
		{
			step();
		}
		if(e.getSource().equals(start))
		{
			exec.scheduleAtFixedRate(new Runnable() {
			    @Override
			    public void run() {
			    	
					step();
					
			    }
			}, 0, 600, TimeUnit.MILLISECONDS);
		}
		
	}
	
	//unused MouseListeners
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}


	@Override
	public void mouseExited(MouseEvent e) {}

	
}

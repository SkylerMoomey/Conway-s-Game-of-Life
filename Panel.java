package game;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel{

	boolean [][] cells;
	
	public Panel(boolean [][] c)
	{
		cells = c;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.DARK_GRAY);
		
		double widthOneBox = (double)Life.WIDTH / cells[0].length;
		double heightOneBox = (double)Life.HEIGHT / cells.length;
		
		
		// populate x lines for cells
		for(int x = 0; x < cells[0].length + 1; x++)
		{
			g.drawLine((int)Math.round(x * widthOneBox), 0, (int)Math.round(x * widthOneBox), Life.HEIGHT);
		}
		
		// populate y lines for cells
		for(int y = 0; y < cells.length + 1; y++)
		{
			g.drawLine(0, (int)Math.round(y * heightOneBox), Life.WIDTH, (int)Math.round(y * heightOneBox));
		}
		
		
		
		
		/**
		 * color in boxes appropriately, white is dead, black is alive
		 */
		for(int i = 0; i < cells[0].length; i++)
		{
			for(int j = 0; j < cells.length; j++)
			{
				if(cells[i][j])
				{
					g.setColor(Color.BLACK);
					g.fillRect((int)Math.round(i * widthOneBox), (int)Math.round(j * heightOneBox), (int)Math.round(widthOneBox) - 1, (int)Math.round(heightOneBox) - 1);
				}
				else
				{
					g.setColor(Color.WHITE);
					g.fillRect((int)Math.round(i * widthOneBox), (int)Math.round(j * heightOneBox), (int)Math.round(widthOneBox) - 1, (int)Math.round(heightOneBox) - 1);
				}
			}
		}
	}
}

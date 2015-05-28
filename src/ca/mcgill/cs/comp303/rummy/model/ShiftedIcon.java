package ca.mcgill.cs.comp303.rummy.model;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * An Icon decorator that enables shifting.
 */
public class ShiftedIcon implements Icon
{
	private final Icon aDecorated;
	private int aX = 0;
	private int aY = 0;
	
	/**
	 * Constructor.
	 * @param pIcon Icon to decorate.
	 * @param pX x-shift.
	 * @param pY y-shift.
	 */
	public ShiftedIcon(Icon pIcon, int pX, int pY)
	{
		aDecorated = pIcon;
		aX = pX;
		aY = pY;
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		aDecorated.paintIcon(c, g, x+aX, y+aY);		
	}

	@Override
	public int getIconWidth()
	{
		return aDecorated.getIconWidth() + aX;
	}

	@Override
	public int getIconHeight()
	{
		return aDecorated.getIconHeight() + aY;
	}

}

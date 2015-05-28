package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.cs.comp303.rummy.engine.GameEngine;
import ca.mcgill.cs.comp303.rummy.model.CompositeIcon;
import ca.mcgill.cs.comp303.rummy.model.Player;
import ca.mcgill.cs.comp303.rummy.model.ShiftedIcon;

/**
 * Show the opponents Hand.
 */
public class OpponentPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final int H_SHIFT = 30;
	private static final int V_SHIFT = 20;
	private static final Color BACKGROUND_COLOR = new Color(7, 99, 36);
	
	private ScorePanel aScorePanel;
	private JLabel aCardLabel = new JLabel();
	private JLabel aLabel;
	
	/**
	 * Creates an empty hand panel.
	 * @param pMaxCards The maximum number of cards that will ever be displayed.
	 */
	public OpponentPanel(Player pPlayer, int pMaxCards)
	{
		super();
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOR);
		aScorePanel = new ScorePanel(pPlayer);
		aLabel = new JLabel( new ImageIcon(getAvatar()));
		
		JPanel lPanel = new JPanel();
		lPanel.setLayout(new BorderLayout());
		lPanel.setBackground(BACKGROUND_COLOR);
		lPanel.add(aLabel, BorderLayout.WEST);
		lPanel.add(aScorePanel, BorderLayout.EAST);
		
		GameEngine lGameEngine = GameEngine.getInstance();
		lGameEngine.addObserver(aScorePanel);

		add(aCardLabel, BorderLayout.EAST);
		add(lPanel, BorderLayout.WEST);
		paintCards();
	}
	
	/**
	 * Paint.
	 */
	private void paintCards()
	{
		CompositeIcon icon = new CompositeIcon();
		for( int i = 0; i < 10; i++ )
		{
			int upShift = V_SHIFT;
			icon.addIcon(new ShiftedIcon( CardImages.getBack(), i * H_SHIFT, upShift));
		}
		aCardLabel.setIcon(icon);
		repaint();
	}
	
	/**
	 * Load an avatar.
	 * @return Avatar image.
	 */
	private BufferedImage getAvatar()
	{
		BufferedImage lBufferedImage = null;
		try {
		    lBufferedImage = ImageIO.read(getClass().getResource("/avatarComputer.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lBufferedImage;
	}
}	

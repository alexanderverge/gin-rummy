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
import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Player;

@SuppressWarnings("serial")
public class HumanPlayerPanel extends JPanel
{
	private ScorePanel aScorePanel;
	private CardSelectionPanel aCardSelectionPanel;
	private JLabel aLabel;
	private static final Color BACKGROUND_COLOR = new Color(7, 99, 36);

	/**
	 * Constructor
	 * @param pPlayer THe player object representing the human player.
	 */
	public HumanPlayerPanel(Player pPlayer)
	{
		super();
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOR);
		aScorePanel = new ScorePanel(pPlayer);
		aCardSelectionPanel = new CardSelectionPanel(11);
		aLabel = new JLabel( new ImageIcon(getAvatar()));

		JPanel lPanel = new JPanel();
		lPanel.setLayout(new BorderLayout());
		lPanel.setBackground(BACKGROUND_COLOR);
		lPanel.add(aLabel, BorderLayout.WEST);
		lPanel.add(aScorePanel, BorderLayout.EAST);

		add(lPanel, BorderLayout.WEST);
		add(aCardSelectionPanel, BorderLayout.EAST);
		GameEngine lGameEngine = GameEngine.getInstance();
		lGameEngine.addObserver(aScorePanel);
		lGameEngine.addObserver(aCardSelectionPanel);
		aCardSelectionPanel.loadCards(null);
	}
	
	/**
	 * return the selected card
	 * @return
	 */
	public Card getSelectedCard()
	{
		return aCardSelectionPanel.getSelectedCard();
	}
	
	/**
	 * Load an avatar.
	 * @return Avatar image.
	 */
	private BufferedImage getAvatar()
	{
		BufferedImage lBufferedImage = null;
		try {
		    lBufferedImage = ImageIO.read(getClass().getResource("/avatarHuman.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lBufferedImage;
	}
}

package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.cs.comp303.rummy.engine.GameEngine;
import ca.mcgill.cs.comp303.rummy.engine.GameEngine.ObserverFlag;
import ca.mcgill.cs.comp303.rummy.model.Player;

/**
 * Show the players' score
 */
@SuppressWarnings("serial")
public class ScorePanel extends JPanel implements Observer
{
	private JLabel name;
	private JLabel score;
	private Player aPlayer;
	private static final Color BACKGROUND_COLOR = new Color(7, 99, 36);
	
	/**
	 * Constructor.
	 * @param pName The player's name.
	 * @param pPlayer The Player object.
	 */
	public ScorePanel(Player pPlayer)
	{
		aPlayer = pPlayer;
		setBackground(BACKGROUND_COLOR);
		setLayout(new GridLayout(2,1));
		name = new JLabel("<html><font color=white>Name: "+ pPlayer.getName() + "</font></html>");
		add(name);
		score = new JLabel("<html><font color=white>Score: " +"0</font></html>");
		add(score);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		ObserverFlag lObserverFlag = (ObserverFlag) arg;
		GameEngine lGameEngine = (GameEngine) o;
		if (lObserverFlag == ObserverFlag.KNOCKED)
		{
			if(aPlayer==lGameEngine.getPlayer1()){
				score.setText("<html><font color=white>Score: " + new Integer(lGameEngine.getPlayer1Score()).toString() + "</font></html>");
			}
			else{
				score.setText("<html><font color=white>Score: " + new Integer(lGameEngine.getPlayer2Score()).toString() + "</font></html>");
			}
		}
		if (lObserverFlag == ObserverFlag.NEW_GAME || lObserverFlag == ObserverFlag.LOADED_GAME || lObserverFlag == ObserverFlag.NAME_CHANGED)
		{
			name.setText("<html><font color=white>Name: "+ aPlayer.getName() + "</font></html>");
		}
	}
}

package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import ca.mcgill.cs.comp303.rummy.engine.GameEngine;
import ca.mcgill.cs.comp303.rummy.engine.GameEngine.ObserverFlag;
import ca.mcgill.cs.comp303.rummy.model.Player;

/**
 * This class builds a LoggerPanel that records all output from the Console.
 */
@SuppressWarnings("serial")
public class LoggerPanel extends JPanel implements Observer
{
   private JTextArea textArea = new JTextArea(15, 30);
   private GameEngine aGameEngine;
   private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea);
   private static final Color BACKGROUND_COLOR = new Color(7, 99, 36);

   /**
    * Constructor.
    * @param pGameEngine The GameEngine instance.
    */
   public LoggerPanel(GameEngine pGameEngine) {
      setLayout(new BorderLayout());
      setBackground(BACKGROUND_COLOR);
	  setPreferredSize(new Dimension(300,300));
	  textArea.setBackground(BACKGROUND_COLOR);
	  textArea.setForeground(Color.WHITE);
	  JScrollPane aScrollPane = new JScrollPane(textArea,
			  						ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
			  						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	  aScrollPane.setBackground(BACKGROUND_COLOR);							
      add(aScrollPane);
      System.setOut(new PrintStream(taOutputStream));
      aGameEngine=pGameEngine;
      aGameEngine.addObserver(this);
   }
   
   @Override
	public void update(Observable pObvservable, Object pObject)
	{
		ObserverFlag control=((ObserverFlag) pObject);
		Player currentPlayer= aGameEngine.getCurrentTurn();
		StringBuffer lStringBuffer = new StringBuffer();
		if(control==ObserverFlag.DRAW_DECK)
		{
			lStringBuffer.append(currentPlayer.getName());
			lStringBuffer.append(" drew from the deck.");
		}
		else if(control==ObserverFlag.DRAW_DISCARD)
		{
			lStringBuffer.append(currentPlayer.getName());
			lStringBuffer.append(" drew from discarded pile.");
		}
		else if (control==ObserverFlag.DISCARD_A_CARD)
		{
			lStringBuffer.append(currentPlayer.getName());
			lStringBuffer.append(" discarded " + aGameEngine.getCurrentDiscard().toString());
		}
		else if (control ==ObserverFlag.SAVE_ERROR)
		{
			lStringBuffer.append("IOException: Save Error");
		}
		else if (control ==ObserverFlag.LOAD_ERROR)
		{
			lStringBuffer.append("IOException: Load Error");
		}
		else if (control==ObserverFlag.KNOCKED)
		{
			lStringBuffer.append(currentPlayer.getName());
			lStringBuffer.append(" knocked.");
		}
		else if (control==ObserverFlag.NEW_GAME)
		{
			lStringBuffer.append("New game started.");
		}
		else if (control==ObserverFlag.LOADED_GAME)
		{
			lStringBuffer.append("Game loaded.");
		}
		String lString = lStringBuffer.toString();
		if (!lString.isEmpty())
		{
			System.out.println(lStringBuffer.toString());
		}
	}
}

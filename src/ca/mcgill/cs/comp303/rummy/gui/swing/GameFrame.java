package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.cs.comp303.rummy.engine.GameEngine;
import ca.mcgill.cs.comp303.rummy.engine.GameEngine.ObserverFlag;
import ca.mcgill.cs.comp303.rummy.model.SmartPlayer;

/**
 * Main game GUI.
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame implements Observer
{
	private GameEngine aGameEngine = GameEngine.getInstance();
	private HumanPlayerPanel aHumanPlayerPanel;
	private OpponentPanel aOpponentPanel;
	private LoggerPanel aLoggerPanel;
	private JDialog aStartDialog;
	private JDialog aLayOffDialog;
	private final LayOffPanel aLayOffPanel;
	private final JSlider aSlider1 = new JSlider(SwingConstants.HORIZONTAL,0,aMaxLevel,aMaxLevel/2);
	private final JSlider aSlider2 = new JSlider(SwingConstants.HORIZONTAL,0,aMaxLevel,aMaxLevel/2);
	private JButton aDiscardButton = new JButton("Discard");
	private JButton aKnockButton = new JButton("Knock");
	private JButton aLogButton = new JButton("Hide Log");
	private String aName = "Jane Doe";
	private static String aHalNumber = "5000";
	private static final int aMaxLevel = 9;
	private static final Level aLevel = new Level(5);
	private static final Color BACKGROUND_COLOR = new Color(7, 99, 36);
	private static final String showLogText = "Show Log";
	private static final String hideLogText = "Hide Log";
	
	/**
	 * Constructor.
	 */
	public GameFrame()
	{		
		super();
		setTitle("Gin Rummy");
		setLayout( new BorderLayout() );
		
		/* Set game state */
		aHalNumber = aLevel.getValue() + "00";
		aHalNumber += (aLevel.getValue() == 9) ? "1" : "0"; // Legal
		aGameEngine.setPlayers(new SmartPlayer(aName,0), new SmartPlayer("Hal-"+aHalNumber,aLevel.getValue()));
		aGameEngine.newGame(aLevel.getValue());
		aGameEngine.addObserver(this);

		/* Create Start Up Dialog */
		aStartDialog = new JDialog(this,"Game Setup");
		JPanel lNorth = new JPanel();
		JLabel lNameLabel = new JLabel("Name:");
		lNorth.add(lNameLabel, BorderLayout.EAST);
		final JTextField lTextField = new JTextField(10);
		lTextField.setText(aName);
		lNorth.add(lTextField, BorderLayout.WEST);
		JPanel lCenter = new JPanel();
		JLabel lLevelText = new JLabel("Level:");
		lCenter.add(lLevelText, BorderLayout.WEST);
		initializeSlider(aSlider1,aMaxLevel/2);
		lCenter.add(aSlider1, BorderLayout.EAST);
		JButton lEnter = new JButton("Enter");
		lEnter.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent pEvent)
			{
				aName = lTextField.getText();
				aGameEngine.setName(aName);
				aGameEngine.setOpponentName("Hal-"+aHalNumber);
				aStartDialog.dispose();
			}
		});	
		aStartDialog.add(lNorth, BorderLayout.NORTH);
		aStartDialog.add(lCenter, BorderLayout.CENTER);
		aStartDialog.add(lEnter, BorderLayout.SOUTH);
		aStartDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		aStartDialog.setLocationRelativeTo(this);
		aStartDialog.pack();
		aStartDialog.setVisible(true);
		
		/* LayOffPanel */
		aLayOffPanel = new LayOffPanel();
		aGameEngine.addObserver(aLayOffPanel);
		aLayOffDialog = new JDialog();
		aLayOffDialog.setTitle("Game Ended");
		aLayOffDialog.setLayout( new BorderLayout() );
		aLayOffDialog.add(aLayOffPanel, BorderLayout.NORTH);
		final JPanel aLayOffButtonPanel = new JPanel();
		aLayOffButtonPanel.setLayout(new BorderLayout());
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		initializeSlider(aSlider2,aLevel.getValue());
		JButton playAgain = new JButton("Play Again");
		playAgain.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				aHalNumber = aLevel.getValue() + "00";
				aHalNumber += (aLevel.getValue() == 9) ? "1" : "0"; // Legal
				aGameEngine.setOpponentName("Hal-"+aHalNumber);
				aGameEngine.newGame(aLevel.getValue());
			}	
		});
		aLayOffButtonPanel.add(playAgain, BorderLayout.WEST);
		aLayOffButtonPanel.add(aSlider2, BorderLayout.CENTER);
		aLayOffButtonPanel.add(quit, BorderLayout.EAST);
		aLayOffDialog.add(aLayOffButtonPanel, BorderLayout.SOUTH);
		aLayOffDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		aLayOffDialog.setLocationRelativeTo(null);
		aLayOffDialog.pack();
		aLayOffDialog.setVisible(false);
		
		/* South Panel */
		aHumanPlayerPanel = new HumanPlayerPanel(aGameEngine.getPlayer1());
		add(aHumanPlayerPanel, BorderLayout.SOUTH);
		
		/* LoggerPanel */
		aLoggerPanel = new LoggerPanel(aGameEngine);
		add(aLoggerPanel, BorderLayout.WEST);
		
		/* DiscardViewer */
		JPanel lPanel = new JPanel();
		lPanel.setBackground(BACKGROUND_COLOR);
		DiscardViewer aDiscardViewer = new DiscardViewer(CardImages.getCard(aGameEngine.getCurrentDiscard()));
	    GridBagLayout gridbag = new GridBagLayout();
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.CENTER;
	    gridbag.setConstraints(lPanel, constraints);
	    lPanel.setLayout(gridbag);
	    lPanel.add(aDiscardViewer);
		add(lPanel, BorderLayout.CENTER);
		
		/* Button Panel */
		JPanel lButtonPanel = new JPanel();
		lButtonPanel.setBackground(BACKGROUND_COLOR);
		lButtonPanel.setLayout(new BorderLayout());
		aLogButton = new JButton("Hide Log");
		aLogButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (aLogButton.getText() == showLogText)
				{
					aLogButton.setText(hideLogText);
					aLoggerPanel.setVisible(true);					
				}
				else
				{
					aLogButton.setText(showLogText);
					aLoggerPanel.setVisible(false);
				}
				revalidate();
				repaint();
			}	
		});
		lButtonPanel.add(aLogButton, BorderLayout.NORTH);
		aKnockButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent pEvent)
			{
				if(aGameEngine.getCurrentTurn().canKnock()){
					aGameEngine.endTurn();
				}
			}
		});	
		aKnockButton.setEnabled(true);
		lButtonPanel.add(aKnockButton, BorderLayout.CENTER);
		aDiscardButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent pEvent)
			{
				if(!aGameEngine.getCurrentTurn().getDiscardedCard().isEmpty()){
					if(!aHumanPlayerPanel.getSelectedCard().equals(aGameEngine.getCurrentTurn().getDiscardedCard().get(0))){
						aGameEngine.concreteDiscard(aHumanPlayerPanel.getSelectedCard());
					}
				}
				else if(!aGameEngine.getCurrentTurn().getDeckCard().isEmpty()){
					aGameEngine.concreteDiscard(aHumanPlayerPanel.getSelectedCard());
				}
				repaint();
			}
		});
		aDiscardButton.setEnabled(false);
		lButtonPanel.add(aDiscardButton, BorderLayout.SOUTH);
		JPanel lEastPanel = new JPanel();
		lEastPanel.setLayout(new BorderLayout());
		lEastPanel.setBackground(BACKGROUND_COLOR);
		lEastPanel.add(lButtonPanel, BorderLayout.SOUTH);
		JLabel lLabel = new JLabel();
		lLabel.setPreferredSize(new Dimension(0, 0));
		lLabel.setMinimumSize(new Dimension(0, 0));
		lLabel.setMaximumSize(new Dimension(0, 0));
		lEastPanel.add(lLabel, BorderLayout.NORTH);
		add(lEastPanel, BorderLayout.EAST);
		
		/* Opponent Panel */
		aOpponentPanel = new OpponentPanel(aGameEngine.getPlayer2(), 10);
		add(aOpponentPanel, BorderLayout.NORTH);
		
		/* Add Menu */
		JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        JMenuItem load = new JMenuItem("Load Last Game");
        load.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	try
				{
					aGameEngine.loadGame();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
    			revalidate();
    			repaint();
            }
        });
        menu.add(load);
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	try
				{
					aGameEngine.saveGame();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
            }
        });
        menu.add(save);
        JMenuItem close = new JMenuItem("Close");
        close.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(close);
        setJMenuBar(menuBar);
		
		/* Size */
		setMinimumSize(new Dimension(600,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		ObserverFlag lObserverFlag = (ObserverFlag) arg;
		if (lObserverFlag == ObserverFlag.KNOCKED)
		{
			aDiscardButton.setEnabled(false);
			aKnockButton.setEnabled(false);
			aLayOffDialog.revalidate();
			aLayOffDialog.repaint();
			aLayOffDialog.pack();
			aLayOffDialog.setVisible(true);
			revalidate();
			repaint();
		}
		if (aGameEngine.getCurrentTurn() == aGameEngine.getPlayer1())
		{
			if (lObserverFlag == ObserverFlag.DISCARD_A_CARD)
			{
				aDiscardButton.setEnabled(false);
			}
			if (lObserverFlag == ObserverFlag.DRAW_DECK
					|| lObserverFlag == ObserverFlag.DRAW_DISCARD)
			{
				aDiscardButton.setEnabled(true);
				aKnockButton.setEnabled(false);
			}
		}
		if (aGameEngine.getCurrentTurn() == aGameEngine.getPlayer2())
		{
			if (lObserverFlag == ObserverFlag.DISCARD_A_CARD)
			{
				aKnockButton.setEnabled(true);
			}
		}
		if (lObserverFlag == ObserverFlag.NEW_GAME || lObserverFlag == ObserverFlag.LOADED_GAME)
		{
			aLayOffDialog.dispose();
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Initialize the sliders to be put on panels.
	 * @param pSlider The slider to initialize.
	 * @param pLevel The default level.
	 */
	private static void initializeSlider(final JSlider pSlider, int pLevel)
	{
		pSlider.setValue(pLevel);
		Hashtable<Integer, JLabel> lTable = new Hashtable<Integer, JLabel>();
		lTable.put( new Integer( 0 ), new JLabel("Easy") );
		lTable.put( new Integer( aMaxLevel/2 ), new JLabel("Medium") );
		lTable.put( new Integer( aMaxLevel ), new JLabel("Hard") );
		pSlider.setMajorTickSpacing(aMaxLevel);
		pSlider.setLabelTable( lTable );
		pSlider.setMajorTickSpacing(aMaxLevel);
		pSlider.setPaintTicks(true);
		pSlider.setPaintLabels(true);
		pSlider.addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				mouseMoved(e);
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				aLevel.setValue(pSlider.getValue()+1);
				aHalNumber = aLevel.getValue() + "00";
				aHalNumber += (aLevel.getValue() == 9) ? "1" : "0"; // Legal
			}
		});
	}
	
	public static void main(String args[])
	{
		new GameFrame();
	}
}

/**
 * Class for managing the game difficulty settings.
 */
class Level
{
	private int aLevel;
	public Level(int pLevel){ aLevel = pLevel; }
	public void setValue(Integer pLevel){ aLevel = pLevel; }
	public int getValue() { return aLevel; }
}

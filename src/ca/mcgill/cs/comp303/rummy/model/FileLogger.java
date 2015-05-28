package ca.mcgill.cs.comp303.rummy.model;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.*;

/**
 * Logging Class
 */
public class FileLogger implements Observer
{
	private static FileHandler fileTxt;
	private static SimpleFormatter formatterTxt;
	private static Logger aLogger = Logger.getLogger(FileLogger.class.getName());
	//Uncomment out the reference to the model below for the Pull implementation.
	//private Model aModel;
	
	
	//This is the constructor. It will need a pModel explicit pModel parameter if the pull
	//Model is used.
	/**
	 * Constructor.
	 * @throws IOException
	 */
	public FileLogger() throws IOException
	{
		// suppress the logging output to the console
		aLogger.setUseParentHandlers(false);
	    aLogger.setLevel(Level.INFO);
	    fileTxt = new FileHandler("Logging.txt");

	    // create a TXT formatter
	    formatterTxt = new SimpleFormatter();
	    fileTxt.setFormatter(formatterTxt);
	    aLogger.addHandler(fileTxt);
	    
	    //Uncomment this to implement the Pull Model of the Observer pattern.
	    //Note that pModel is an explicit parameter passed to the constructor in this case.
//	    aModel=pModel;
//		aModel.addObserver(this);
	}
	
	    //The following method is called from the model upon notification.
		//Note that it will be changed slightly depending on a Push/Pull implementation.
	    @Override
		public void update(Observable o, Object arg)
		{
	    	//The following line is an example of a Model state that can be logged.
	    	//This will be modified to reflect the GameEngine being logged later. 
//			aLogger.log(Level.INFO,String.valueOf(aModel.getNumber()));
			
		}

}
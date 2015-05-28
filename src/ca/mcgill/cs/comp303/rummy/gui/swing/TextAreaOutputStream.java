package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * This class is used to build a TextAreaOutputStream. It handles
 * all the grunt work of moving the console's output to the Logger Panel.
 * The Logger Panel is composed of this class.
 */
public class TextAreaOutputStream extends OutputStream {

   private final JTextArea textArea;
   private final StringBuilder sb = new StringBuilder();

   /**
    * Constructor.
    * @param textArea The text area to decorate.
    */
   public TextAreaOutputStream(final JTextArea textArea) {
      this.textArea = textArea;
   }

   @Override
   public void flush()
   {
	   // Nothing
   }

   @Override
   public void close()
   {
	   // Nothing
   }

   @Override
   public void write(int b) throws IOException {

      if (b == '\r')
         return;

      if (b == '\n') {
         final String text = sb.toString() + "\n";
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               textArea.append(text);
            }
         });
         sb.setLength(0);
         return;
      }

      sb.append((char) b);
   }
}
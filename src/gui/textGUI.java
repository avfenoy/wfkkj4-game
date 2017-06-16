package gui;

import game.Game;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A simple GUI for the game "wfkkj4" with text and some images.
 * TODO: fix layout
 * TODO: change styles
 * 
 * @author Alberto V.F.
 * @version 15/01/2016
 */
public class textGUI extends JFrame
{
	private Game game;
    private File gameLog;
    private BufferedWriter writer;
    private Container contentPane;
    
    
    /**
     * Constructor for textGUI
     */
    public textGUI()
    {
        super("wfkkj4");
        setResizable(false);
        @SuppressWarnings("unused")
		myUIDefaults defaults = new myUIDefaults(); // UI default configuration
        game = new Game();
        gameLog = new File("wfkkj4log.txt");
        try
        {
            writer = new BufferedWriter(new FileWriter(gameLog));
        }
        catch(IOException e)
        {
            System.out.println("Error creating log file."); // display error if exception is caught
        }
        setup(); 
    }
    
    /**
     * Set up the GUI (panels, labels, buttons...)
     */
    public void setup()
    {
        makeMenuBar();
        setupWindow();
        
        contentPane = this.getContentPane();
        JPanel p = new JPanel(new GridBagLayout());
        contentPane.setPreferredSize(new Dimension(1024,768));

        
        
        GridBagConstraints c = new GridBagConstraints();
        
        //text panel
        final JPanel textPane = new JPanel(new GridBagLayout());
        GridBagConstraints textConstraints = new GridBagConstraints();


        // create buttons
        JButton northButton = new JButton("north");
        northButton.setActionCommand("north");
        northButton.setPreferredSize(new Dimension(100,25));
        textConstraints.fill = GridBagConstraints.VERTICAL;
        textConstraints.gridx = 1;
        textConstraints.gridy = 0;
        textPane.add(northButton, textConstraints);
        
        JButton westButton = new JButton("");
        westButton.setActionCommand("west");
        textConstraints.fill = GridBagConstraints.HORIZONTAL;
        textConstraints.gridx = 0;
        textConstraints.gridy = 1;
        textPane.add(westButton, textConstraints);
        
        JButton eastButton = new JButton("");
        eastButton.setActionCommand("east");
        textConstraints.fill = GridBagConstraints.HORIZONTAL;
        textConstraints.gridx = 2;
        textConstraints.gridy = 1;
        textPane.add(eastButton, textConstraints);
        
        JButton southButton = new JButton("");
        southButton.setActionCommand("south");
        textConstraints.fill = GridBagConstraints.HORIZONTAL;
        textConstraints.gridx = 1;
        textConstraints.gridy = 2;
        textPane.add(southButton, textConstraints);
        
        final ArrayList<JButton> buttons = new ArrayList<JButton>();
        buttons.add(northButton);
        buttons.add(southButton);
        buttons.add(westButton);
        buttons.add(eastButton);
        // deactivate buttons for startup
        for(JButton button : buttons) 
        {
            button.setBorderPainted(false);
            button.setEnabled(false);
        }
        northButton.setBorderPainted(true);
        northButton.setEnabled(true);
        
        // creates central text
        final JLabel gameText = new JLabel(breakLines(game.getResponse()), SwingConstants.CENTER);
        gameText.setPreferredSize(new Dimension(800, 400));
        gameText.setOpaque(true);        
        textConstraints.fill = GridBagConstraints.HORIZONTAL;
        textConstraints.gridx = 1;
        textConstraints.gridy = 1;
        textPane.add(gameText, textConstraints);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        p.add(textPane,c);
        
        // lower panel
        JPanel lowPane = new JPanel(new FlowLayout());
        
        // pick and use buttons
        JPanel commandsPane = new JPanel(new BorderLayout()); // nested command panel
        JButton lookButton = new JButton("Look");
        lookButton.setPreferredSize(new Dimension(200, 100)); 
        commandsPane.add(lookButton, BorderLayout.PAGE_START);
        JButton pickButton = new JButton("Pick: ");
        final JTextField pickField = new JTextField("");
        commandsPane.add(pickButton, BorderLayout.LINE_START);
        commandsPane.add(pickField, BorderLayout.CENTER);
        lowPane.add(commandsPane);

        // inventory
        JPanel imagePane = new JPanel(); // nested image panel
        imagePane.setLayout(new BoxLayout(imagePane, BoxLayout.PAGE_AXIS));
        JLabel inventLabel = new JLabel("Inventory:");
        imagePane.add(inventLabel);
        final JButton imageButton = new JButton();
        imagePane.add(imageButton);
        final JButton imageButton2 = new JButton();
        imagePane.add(imageButton2);
        
        lowPane.add(imagePane);
   
        final ArrayList<JButton> imageButtons = new ArrayList<JButton>();
        imageButtons.add(imageButton);
        imageButtons.add(imageButton2);
        
        // map panel
        ImageIcon map = createImage("map");
        JLabel mapLabel = new JLabel();
        if(map!=null)
        {
        	mapLabel.setIcon(map);
        }
        else
        {
        	mapLabel.setText("Error loading map");
        }
        lowPane.add(mapLabel);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        p.add(lowPane,c);

        // action for direction buttons
        ActionListener goListener = new ActionListener(){
         public void actionPerformed(ActionEvent e) 
            {
                String commandString = "go " + e.getActionCommand();
                game.processCommandString(commandString);
                gameText.setText(breakLines(game.getResponse()));
                Set<String> exits = game.getPlayer().getCharacter().getRoom().getExits();
                for(JButton button : buttons) 
                {
                    if(exits.contains(button.getActionCommand())) // show buttons for exits in this room
                    {
                        button.setEnabled(true);
                        button.setBorderPainted(true);
                        button.setText(button.getActionCommand());
                    }
                    else // disable buttons for directions with no door
                    { 
                        button.setEnabled(false);
                        button.setBorderPainted(false);
                        button.setText("");
                    }
                }
                writeLog(commandString);
                // show ending and disable direction buttons when game is over
                if(game.getFinished())
                {
                    gameText.setText(breakLines(game.getResponse()));
                    for(JButton button : buttons)
                    {
                        button.setEnabled(false);
                        button.setBorderPainted(false);
                        button.setText("");
                    }
                }
            }
        };
       
        for(JButton button : buttons) 
        {
            button.addActionListener(goListener);
        }
        
        // action listener for look button
        lookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String commandString = "look";
                game.processCommandString(commandString);
                gameText.setText(breakLines(game.getResponse()));
                writeLog(commandString);
            }
        });
        
        // action listener for pick button
        pickButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String pickedItem = pickField.getText();
                String commandString = "pick " + pickedItem;
                boolean pickedSuccessful = game.processCommandString(commandString);
                if(pickedSuccessful)
                {
                    HashSet<String> inventory = new HashSet<String>(game.getInventory());
                    Iterator<String> it = inventory.iterator();
                    // assign image and action to the items in inventary
                    for (JButton button : imageButtons)
                    {
                        if (it.hasNext())
                        {
                            String item = (String) it.next();
                            button.setIcon(createImage(item));
                            if(button.getIcon()==null)
                            {
                            	button.setText(item);
                            }
                            button.setActionCommand(item);
                        }
                    }
                }
                gameText.setText(breakLines(game.getResponse()));
                writeLog(commandString);
            }
        });
        
        //action listener for use command
        ActionListener useListener = new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                boolean useSuccessful = game.processCommandString("use " + e.getActionCommand());
                if(useSuccessful)
                {
                    JButton buttonPressed = (JButton) e.getSource();
                    buttonPressed.setIcon(null); //disable icon
                }
                gameText.setText(breakLines(game.getResponse()));  
            }
        };

        imageButton.addActionListener(useListener);
        imageButton2.addActionListener(useListener);

        
        this.add(p);
        this.pack();
        this.setVisible(true);
    }
    
    /**
     * Makes the menu bar.
     */
    private void makeMenuBar()
    {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        JMenu menuMenu = new JMenu("Menu");
        menubar.add(menuMenu);
            
        JMenuItem aboutItem = new JMenuItem("About wfkkj4");
        menuMenu.add(aboutItem);
        JMenuItem resetItem = new JMenuItem("Reset");
        menuMenu.add(resetItem);
        JMenuItem quitItem = new JMenuItem("Quit");
        menuMenu.add(quitItem);
        
        // action listener for about option
        aboutItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(new JFrame(),"wfkkj4 is a simple text-based adventure game, based on the World of Zuul game by Michael Kölling and David J. Barnes.");
            }
        });
        
        // reset game
        resetItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                int reply = JOptionPane.showConfirmDialog(new JFrame(),"Do you want to start again?", "Confirmation needed", JOptionPane.YES_NO_OPTION);
                if(reply==JOptionPane.YES_OPTION)
                {
                    contentPane.removeAll();
                    game = new Game();
                    setup();
                    writeLog("reset game");
                }
            }
        });
        
        // quit game
        quitItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                int reply = JOptionPane.showConfirmDialog(new JFrame(),"Are you sure do you want to quit the game?", "Confirmation needed", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                {
                    closeGame();
                }
            }
        });
    }
    
    /**
     * Set up action for closing window.
     */
    public void setupWindow()
    {
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                 closeGame();
            }
        });
    }
    
    /**
     * Transform text string to allow multiline display as JLabel.
     * @return text string in html.
     */
    public String breakLines(String text)
    {
        String htmlString = "<html><center><div WIDTH=800>" +text.replace("\n","<br>")+"</div></center></html>";
        return htmlString;
    }
    
    /**
     * Loads and image and creates an icon for display.
     * @param the name of the item, which is the key for loading image from its path
     * @return image icon of the item.
     */
    public ImageIcon createImage(String item)
    {
        try
        {
            BufferedImage im = ImageIO.read(new File("img/" + item + ".jpg"));
            ImageIcon returnImage = new ImageIcon(im);
            return returnImage;      
        }
        catch(IOException e)
        {
            return null;
        }
    }
    
    /**
     * Closes the game. Catches IOException from writing log.
     */
    public void closeGame()
    {
        try
        {
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("Error closing log file.");
        }
        finally
        {
            System.exit(0);
        }
    }
    
    /**
     * Write entry in the gamelog. Catches IOException.
     */
    public void writeLog(String entry)
    {
        try
        {
            writer.write(entry);
            writer.newLine();
        }
        catch (IOException ex)
        {
            System.out.println("Error writing log file");
        }
    }
}

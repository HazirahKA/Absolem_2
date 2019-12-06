/**
 * @author Hazirah Khair Anuar
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGUI extends JFrame implements ActionListener { //The listener interface for receiving action events. The class that is interested in processing
    // an action event implements this interface, and the object created with that class is registered
    // with a component, using the component's addActionListener method. When the action event occurs,
    // that object's actionPerformed method is invoked.
    private ImageIcon absolem = new ImageIcon(getClass().getResource("Images/Absolem3.jpg")); //Put an image.
    private JLabel backgroundImg; //Make a label for the background image.
    private JMenuBar dropMenu; //Creating drop down menu.
    private JMenu file; //Creating file menu.
    private JMenu info; //Creating info menu.
    private JMenuItem newGame; //Creating "Play Game" menu item.
    private JMenuItem highscore; //Creating "Score Board" menu item.
    private JMenuItem howTo; //Creating "How To Play" menu item.
    private JPanel backGround; //Creating a panel.

    public MenuGUI() {
        super("Menu - Absolem"); //Super is a keyword of Java which refers to the immediate parent of a class and is used inside the subclass method
        // definition for calling a method defined in the superclass.
        setSize(800, 800); //Set the size of JFrame.
        setLocationRelativeTo(null); //Sets the location of the window relative to the specified component. If the component is not currently showing, or c (the Component)
        // is null, the window is centered on the screen.
        JFrame mainFrame = new JFrame(); //Creating a new JFrame called mainFrame.
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The exit application default window close operation.
        this.howTo = new JMenuItem("How To Play"); //Setting the text to the "How To Play" menu item.
        this.howTo.addActionListener(this); //Add an action when this menu item is pressed/clicked.
        this.newGame = new JMenuItem("Play Game"); //Setting the text to the "Play Game" menu item.
        this.newGame.addActionListener(this); //Add an action when this menu item is pressed/clicked.
        this.highscore = new JMenuItem("Score Board"); //Setting the text to the "Score Board" menu item.
        this.highscore.addActionListener(this); //Add an action when this menu item is pressed/clicked.
        this.info = new JMenu("Information"); //Setting the text "Information" to the info menu.
        this.file = new JMenu("File"); //Setting the text "File" to the file menu.
        this.file.add(this.newGame); //Adding the "Play Game" JMenuItem inside the "File" menu.
        this.file.add(this.highscore); //Adding the "Score Board" JMenuItem inside the "File" menu.
        this.info.add(this.howTo); //Adding the "How To Play" JMenuItem inside the "Information" menu.
        this.dropMenu = new JMenuBar(); //Creating the JMenuBar.
        this.dropMenu.add(this.file); //Adding "File" menu inside the JMenuBar.
        this.dropMenu.add(this.info); //Adding "Information" menu inside the JMenuBar.
        this.backgroundImg = new JLabel(); //Creating the JLabel for the background image.
        this.backgroundImg.setIcon(this.absolem); //Setting the image as the background image.
        this.backGround = new JPanel(); //Creating the JPanel for the background.
        this.backGround.add(this.backgroundImg); //Adding the background image to the background panel.
        setJMenuBar(this.dropMenu); //Setting the JMenuBar.
        add(this.backGround); //Adding the background.
        setResizable(false); //Method for ensuring that a graphical interface looks the way it intended, to prevent the user from re-sizing it.
        // If the parameter is false then the user cannot re-size the frame.
        setVisible(true); //Method in Java that makes the GUI component visible to the user depending on the Boolean parameter you pass in the function.
        // For instance, if it is set as false then it will hide that component else if true, it will be shown to the user.
    }

    @Override //Overriding is a feature that allows a subclass or child class to provide a specific implementation of a method that is already provided
    // by one of its super-classes or parent classes.
    public void actionPerformed(ActionEvent e) { //Invoked when an action occurs.
        if (e.getSource() == this.newGame) //Returns the object on which the "Play Game" is clicked/pressed.
        {
            AbsolemGUI absolem = new AbsolemGUI(); //Open this class as a JFrame.
            this.dispose(); //Dispose current JFrame.
        }

        if (e.getSource() == this.highscore) //Returns the object on which the "Score Board" is clicked/pressed.
        {
            JOptionPane.showMessageDialog(null,"Current highscore is:\n\n" + Board.getHighScore(),"Score Board",JOptionPane.INFORMATION_MESSAGE);
            //Display the message dialog that contained the name and score of the current highscore.
        }

        if (e.getSource() == this.howTo) //Returns the object on which the "How To Play" is clicked/pressed.
        {
            JOptionPane.showMessageDialog(null, "How To Play\n\n- Use your arrow keys to navigate \"Absolem\" and eat the red " +
                            "apple \n- \"Absolem\" can't hit the border of the screen \n- \"Absolem\" can't hit his own body"
                    ,"How To Play",JOptionPane.INFORMATION_MESSAGE);
            //Display the message dialog that contained the instruction to play the game.
        }
    }
}
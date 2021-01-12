import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * This is the GUI window for the webale chess game.
 * Involved in MVC design pattern
 * Act as "View" of MVC design pattern
 * 
 * @author Chong Kai Siang 1171103564
 * @author Choong Lee Hung 1171103451
 * @author Khaw Wen Kang   1171103546
 * @author Soh Jing Guan   1171103482
 * 
 */
public class GameGUI extends JFrame {
    
    private JPanel panel; 
    private JMenuBar Main;
    private JMenu File;
    private JMenuItem Save;
    private JMenuItem Load;
    GameScreen gameScreen;
    
    /**
     * To create a new game GUI window
     * 
     * @author Chong Kai Siang 1171103564
     */
    public GameGUI() {
        initTools();
        init();
    }
    
    /**
     * To initializes the JPanel, and menu
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
    */
    private void initTools() {
    
        panel = new JPanel();
        Main = new JMenuBar();
        File = new JMenu();
        Save = new JMenuItem();
        Load = new JMenuItem();
        File.setText("File");
        Save.setText("Save");
        Save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                gameScreen.save();
            }
        });
        Load.setText("Load");
        Load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                gameScreen.load();
            }
        });
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Webale Chess"); 
        setVisible(true);
        
        Main.add(File);
        File.add(Save);
        File.add(Load);
        setJMenuBar(Main);

        panel.setMaximumSize(new Dimension(10000, 10000));
        panel.setMinimumSize(new Dimension(100, 100));
        
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Resize(e);
            }
        });

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
       
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
         
        
    }
    
    /**
     * To initializes and add the gamescreen to the JPanel
     * 
     * @author Chong Kai Siang 1171103564
     */
    private void init() {
        gameScreen = new GameScreen(panel.getWidth(), panel.getHeight());
        panel.add(gameScreen);
    }
    
    /**
     * To allow the resize of the screen.
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     */
    private void Resize(ComponentEvent e) {
        if ( gameScreen != null) {
            int smaller = panel.getHeight();
            
            if (panel.getWidth() < smaller){
                smaller = panel.getWidth();
            }
            //set the new size , the screen will be always square
            gameScreen.setSize(smaller, smaller);
        }
    }
    
    /**
     * 
     * @author Chong Kai Siang 1171103564
     */
    public static void main(String arg[]) {
        
        GameGUI Webale = new GameGUI();
    }

}

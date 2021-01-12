import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.List;
import java.awt.Color;

/**
 * This is a gamepanel which include the component for playing the game. 
 * Control the game flow.  
 * Subclass of JComponent.
 * Implements MouseListener.
 * 
 * @author Chong Kai Siang 1171103564
 * @author Choong Lee Hung 1171103451
 * @author Khaw Wen Kang   1171103546
 * @author Soh Jing Guan   1171103482
 */
public class GameScreen extends JComponent implements MouseListener{
    
    GameBoard gameBoard;
    boolean imagesLoaded = false;
    Chess selectedChess = null;
    Chess invalidChess = null;
    private enum GameStatus {Idle, Error, Started, End};
    GameStatus status = GameStatus.Idle;
    List<Move> ableMoves;
    
    
    /**
     * Creates a new GameScreen.
     * Set the size. 
     * Load the image of chess.
     * Initializes the game.
     * Add MouseListener.
     * 
     * @param w width 
     * @param h height
     */
    public GameScreen(int w, int h) {
        
        this.setSize(w, h);
        
        loadImages();
        
        Game();
        
        this.addMouseListener(this);
    }  
    
    /**
     * Initializes the game.
     * Create a new gameboard.
     * Draw the gameboard.
     */
    public void Game() {
        
        gameBoard = new GameBoard();
        
        status = GameStatus.Started;
        
        
        selectedChess = null;
        invalidChess = null;

        
        this.repaint();
    }
    
    /**
     * Saves the game to the file.
     */
    public void save() {
        try{
            String save = JOptionPane.showInputDialog("Save as: ");
            
            if (save == null)
                return;
                
            File saveFolder = new File("Saves");
            
            if (!saveFolder.exists())
                saveFolder.mkdir();
                
            FileOutputStream input = new FileOutputStream(new File("Saves/"+save+".TXT"));
            ObjectOutputStream object = new ObjectOutputStream(input);  
            object.writeObject(this.gameBoard);
            object.close();
            input.close();
        }
        catch (Exception e){
            String error = e.getMessage();
            JOptionPane.showMessageDialog(this,
                    error, "Error!",
                    JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    /**
     * Loads the game from the file
     */
    public void load() {
        selectedChess = null;
        invalidChess = null;
        try{
            File saveFolder = new File("Saves");

            if (!saveFolder.exists()){
                saveFolder.mkdir();
            }
            
            File[] s = saveFolder.listFiles();
            
            if (s.length == 0) {
                // inform the user
                JOptionPane.showMessageDialog(this,
                        "There is no games to load", 
                        "Load Game",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Object choose = JOptionPane.showInputDialog(this, "Select file to be load:", 
                "Load Game",
                JOptionPane.QUESTION_MESSAGE,null,s,s[0]);
                
            if (choose == null){
                return;
            }
            
            FileInputStream input = new FileInputStream((File)choose);
            ObjectInputStream output = new ObjectInputStream(input);
            this.gameBoard = (GameBoard)output.readObject();
            output.close();
            input.close();
        }
        catch (Exception e){
            String error = e.getMessage();
            JOptionPane.showMessageDialog(this,
                    error, "Error!",
                    JOptionPane.ERROR_MESSAGE); 
        }
        
        this.repaint();
    }
    
    /**
     * Loads the image of chess from a default folder.
     * If folder does not exist, it will be created.
     * The game will not run sucessfully until the images has been loaded.
     */
    private void loadImages(){
        try {
            
            BufferedImage[] redImages = new BufferedImage[6];            
            BufferedImage[] blueImages = new BufferedImage[6];

            
            File directory = new File ("Chess");
            if (!directory.exists()) {
                if (directory.mkdir()) {
                
                throw new Exception("The Chess directory did not exist. " +
                        "The folder is created.");
                }
            }


            // load all red images
            redImages[0] = ImageIO.read(new File("Chess/R_Arrow.PNG"));
            redImages[1] = ImageIO.read(new File("Chess/R_Plus.PNG"));
            redImages[2] = ImageIO.read(new File("Chess/R_Triangle.PNG"));
            redImages[3] = ImageIO.read(new File("Chess/R_Chevron.PNG"));
            redImages[4] = ImageIO.read(new File("Chess/R_SUN.PNG"));
            redImages[5] = ImageIO.read(new File("Chess/R_RevArrow.PNG"));
            
            
            // load all blue images
            blueImages[0] = ImageIO.read(new File("Chess/B_Arrow.PNG"));
            blueImages[1] = ImageIO.read(new File("Chess/B_Plus.PNG"));
            blueImages[2] = ImageIO.read(new File("Chess/B_Triangle.PNG"));
            blueImages[3] = ImageIO.read(new File("Chess/B_Chevron.PNG"));
            blueImages[4] = ImageIO.read(new File("Chess/B_SUN.PNG"));
            blueImages[5] = ImageIO.read(new File("Chess/B_RevArrow.PNG"));
            
            
            // set the red and blue images in the Chess class
            Chess.setRedImages(redImages);
            Chess.setBlueImages(blueImages);

            // images loaded without errors
            imagesLoaded = true;
            
        } catch (Exception e) {
            status = GameStatus.Error;
            
            String message = "Could not load chess images. " +
                    "Check that all images exist in the Chess folder with correct name."+
                    "\nError details: " + e.getMessage();
            
            JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Overrides the default paintComponent method. 
     * 
     * @param gr Graphics to be painted 
     */
    protected void paintComponent(Graphics gr) {
        int w = getWidth();
        int h = getHeight();

        // height and width of the square
        int sW = w / 7;
        int sH = h / 8;
        
        // create an off-screen buffer
        Image image = createImage(w, h);

        // get buffer's graphics context
        Graphics g = image.getGraphics();

        // draw the board to the buffer
        drawBoard(g, sW, sH);        
        
        // draw the hint of the available move
        Hint(g, sW, sH);
        
        //  draw the chess if the image has been loaded
        if (imagesLoaded)
             drawChess(g, sW, sH);

        // draw the contents of the buffer to the screen
        gr.drawImage(image, 0, 0, this);
    }
    
    /**
     * Draws the chess
     * 
     * @param g Graphics object to be painted
     * @param sH square height
     * @param sW square width
     */
    private void drawChess(Graphics g, int sW, int sH) {
        // for each chess on the board
        for(Chess ch : gameBoard.getChess()) {
            // if chess is red
            if(ch.getColor() == Chess.Color.Red) {
                // draw its red image
                g.drawImage(ch.getRedImage(), ch.getLocation().x * sW,
                        ch.getLocation().y * sH, sW, sH, null);
            } else {
                // draw its blue image
                g.drawImage(ch.getBlueImage(), ch.getLocation().x * sW,
                        ch.getLocation().y * sH, sW, sH, null);
            }
        }
    }
    
    /**
     * Draws an empty gameboard
     * 
     * @param g Graphics2D object to be painted
     * @param sW square width 
     * @param sH square height 
     */
    private void drawBoard(Graphics g, int sW, int sH) {
        
        g.setColor(new Color(240,248,255));
        g.fillRect(0, 0, sW * 8, sH * 8);
        
        boolean square = false;
        g.setColor(new Color(192,192,192));
        
        // draw the squares 
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(square) {
                    g.fillRect(x * sW, y * sH, sW, sH);
                }
                square = !square;
            }         
            square = !square;
        }
    }  
    
    
    /**
     * Draws the hint of available move 
     * 
     * @param g Graphics object to be painted
     * @param sW width of square
     * @param sH height of square
     */
    private void Hint(Graphics g, int sW, int sH){
 
        // if selected an invalid chess, draw red rectangle for that chess
        if (invalidChess != null){
            Point p = invalidChess.getLocation();
            g.setColor(Color.RED); 
            g.fillRect(p.x * sW, p.y * sH, sW, sH);
        }
        
        // draw circle for the chess selected
        if (selectedChess != null){
            Point p = selectedChess.getLocation();
            g.setColor(new Color(255,255,102,255)); 
            g.fillOval(p.x * sW, p.y * sH, sW, sH);
            
            g.setColor(new Color(127,255,212,150));
            
            // draw circle for the available moves
            for (Move m : ableMoves){
                Point pt = m.getMoveTo();
                g.fillOval(pt.x * sW, pt.y * sH, sW, sH);
            }
        }
    }
    
    /**
     * Gets the destination of the move from the list of moves
     * @param pt point to look for
     * @return destination of the move
     */
    private Move destination(Point pt) {
        for(Move m : ableMoves)
            if(m.getMoveTo().equals(pt)) 
                return m;
        return null;
    }    
    
     /**
     * Responds to a mousePressed event
     * Turn the mouse click coordinates into board coordinates.
     * Select the chess if no chess has been selected.
     * Perform chess movement if click on correct color chess,
     * and correct coordinates.
     * Display gameover message if "Sun" has been eaten.
     * 
     * @param e
     */
    public void mousePressed(MouseEvent e){
        if (status == GameStatus.Started){
            
            invalidChess = null;
            int w = getWidth();
            int h = getHeight();
        
            Point boardpoint = new Point(e.getPoint().x/(w/7),e.getPoint().y/(h/8));
        
            if (selectedChess == null){
                
                selectedChess = gameBoard.getChessAt(boardpoint);
                
                if (selectedChess != null){
                    ableMoves = selectedChess.getValidMoves(gameBoard);
                
                    if(selectedChess.getColor() != gameBoard.getRound()){
                        ableMoves = null;
                        invalidChess = selectedChess;
                        selectedChess = null;
                    }
                }
            }
            else{
                
                Move pMove = destination(boardpoint);
                
                if (pMove != null){
                    gameBoard.performMove(pMove);
                    selectedChess = null;
                    ableMoves = null;
                }
                else{
                    selectedChess = null;
                    ableMoves = null;
                }
    
            }

            if(gameBoard.getOver()== true){
                    status = GameStatus.End; 
                    // repaint the gamescreen immediately to show the "Sun" has been eaten
                    this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
                    if(gameBoard.getRound()==Chess.Color.Red){
                        JOptionPane.showMessageDialog(this,"The blue player win the game!",
                        "Game Over!",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(gameBoard.getRound()==Chess.Color.Blue){
                        JOptionPane.showMessageDialog(this,"The red player win the game!",
                        "Game Over!",JOptionPane.INFORMATION_MESSAGE);
                    }
                    // start a new game;
                    Game();
            }
            
        }
        this.repaint();
    }
 
    public void mouseExited(MouseEvent e) { }    
   
    public void mouseEntered(MouseEvent e) { } 

    public void mouseReleased(MouseEvent e) { }
 
    public void mouseClicked(MouseEvent e) { }
}

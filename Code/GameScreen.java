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
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is a gamepanel which include the component for playing the game. 
 * Control the game flow.  
 * Subclass of JComponent.
 * Implements MouseListener.
 * Involved in MVC design pattern
 * Act as "Controller" of MVC design pattern
 * 
 * @author Chong Kai Siang 1171103564
 * @author Choong Lee Hung 1171103451
 * @author Khaw Wen Kang   1171103546
 * @author Soh Jing Guan   1171103482
 */
public class GameScreen extends JComponent implements MouseListener {
    
    public GameBoard gameBoard;
    public boolean imagesLoaded = false;
    public Chess selectedChess = null;
    public Chess invalidChess = null;
    private enum GameStatus {Idle, Error, Started, End};
    public GameStatus status = GameStatus.Idle;
    public List<Move> ableMoves;
    
    
    /**
     * Creates a new GameScreen.
     * Set the size. 
     * Load the image of chess.
     * Initializes the game.
     * Add MouseListener.
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @param w width 
     * @param h height
     * 
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
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
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
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     */
    public void save() {
        try{
            String save = JOptionPane.showInputDialog("Save as: ");
            
            if (save == null)
                return;
                
            File saveFolder = new File("Saves");
            
            if (!saveFolder.exists())
                saveFolder.mkdir();
            
            
            List<Chess> chess = gameBoard.getChess();
            FileWriter myWriter = new FileWriter("Saves/"+save+".TXT");
            myWriter.write("Example: Type of chess,cordinate of x, cordinate of y, chess's color, chess's image number//\n");
            myWriter.write("Round:" + gameBoard.getRoundNo() + "\n");
            myWriter.write("Turn:" + gameBoard.getRound() + "\n");
            for(Chess ch: chess)
            {
               if(ch instanceof Triangle)
               {
                   myWriter.write("Next Transfer Time is:" + ((Triangle)ch).getTransferTime() + "\n");
                   myWriter.write("Now Triangle is remain:" + ((Triangle)ch).getIsTriangle() + "\n");
                   break;
               }
            }
            for(Chess i: chess)
            {
              myWriter.write(i.getType() + "," + i.getLocation().x + "," + i.getLocation().y + "," + i.getColor() + "," + i.getImageNumber() + "\n");
              myWriter.write("------\n");
            }
            myWriter.close();
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
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
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
            
            Scanner myReader = new Scanner((File)choose);
            List<Chess> chess = gameBoard.getChess();
            chess.clear();
            
            String[] array;
            myReader.nextLine();
            String text = myReader.nextLine();
            array = text.split(":");
            gameBoard.setRoundNo(Integer.parseInt(array[1]));
            text = myReader.nextLine();
            array = text.split(":");
            if(array[1].startsWith("R"))
            {
                gameBoard.setRound(Chess.Color.Red);
            }
            else if(array[1].startsWith("B"))
            {
                gameBoard.setRound(Chess.Color.Blue);
            }
            text = myReader.nextLine();
            array = text.split(":");
            String time = array[1];
            text = myReader.nextLine();
            array = text.split(":");
            String isTriangle = array[1];
            while(myReader.hasNextLine())
            {
                text = myReader.nextLine();
                if(text.startsWith("Ex"))
                {
                    continue;
                }
                else if(text.startsWith("--"))
                {
                    continue;
                }
                array = text.split(",");
                String type = array[0];
                String column = array[1];
                String row = array[2];
                String color = array[3];
                String image = array[4];
                int z = Integer.parseInt(image);
                int x = Integer.parseInt(column);
                int y = Integer.parseInt(row);
                switch(type)
                {
                    case "Arrow":
                    if(color.startsWith("R"))
                    {
                       chess.add(new Arrow(new Point(x, y), Chess.Color.Red));
                    }
                    else if(color.startsWith("B"))
                    {
                       chess.add(new Arrow(new Point(x, y), Chess.Color.Blue));
                    }
                    break;
                    
                    case "RevArrow":
                    if(color.startsWith("R"))
                    {
                       chess.add(new RevArrow(new Point(x, y), Chess.Color.Red));
                    }
                    else if(color.startsWith("B"))
                    {
                       chess.add(new RevArrow(new Point(x, y), Chess.Color.Blue));
                    }
                    break;
                    
                    case "Plus":
                    if(color.startsWith("R"))
                    {
                       chess.add(new Plus(new Point(x, y), Chess.Color.Red));
                    }
                    else if(color.startsWith("B"))
                    {
                       chess.add(new Plus(new Point(x, y), Chess.Color.Blue));
                    }
                    break;
                    
                    case "Triangle":
                    if(color.startsWith("R"))
                    {
                       chess.add(new Triangle(new Point(x, y), Chess.Color.Red));
                    }
                    else if(color.startsWith("B"))
                    {
                       chess.add(new Triangle(new Point(x, y), Chess.Color.Blue));
                    }
                    break;
                    
                    case "Chevron":
                    if(color.startsWith("R"))
                    {
                       chess.add(new Chevron(new Point(x, y), Chess.Color.Red));
                    }
                    else if(color.startsWith("B"))
                    {
                       chess.add(new Chevron(new Point(x, y), Chess.Color.Blue));
                    }
                    break;
                    
                    case "Sun":
                    if(color.startsWith("R"))
                    {
                       chess.add(new Sun(new Point(x, y), Chess.Color.Red));
                    }
                    else if(color.startsWith("B"))
                    {
                       chess.add(new Sun(new Point(x, y), Chess.Color.Blue));
                    }
                    break;
                    
                }
                
            }
            
            for(Chess ch: chess)
            {
                if(ch  instanceof Triangle)
                {
                    ((Triangle)ch).setTransferTime(Integer.parseInt(time));
                    ((Triangle)ch).setIsTriangle(Boolean.parseBoolean(isTriangle));
                }
            }
            gameBoard.LoadChess(gameBoard.getRound());
            gameBoard.setChess(chess);
            myReader.close();
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
     * 
     * @author Khaw Wen Kang   1171103546
     * 
     * @author Soh Jing Guan   1171103482
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     */
    private void loadImages() {
        try {
            
            BufferedImage[] redImages = new BufferedImage[9];            
            BufferedImage[] blueImages = new BufferedImage[9];

            
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
            redImages[6] = ImageIO.read(new File("Chess/R_TPlus.PNG"));
            redImages[7] = ImageIO.read(new File("Chess/R_RevChevron.PNG"));
            redImages[8] = ImageIO.read(new File("Chess/R_RevTriangle.PNG"));
            
            // load all blue images
            blueImages[0] = ImageIO.read(new File("Chess/B_Arrow.PNG"));
            blueImages[1] = ImageIO.read(new File("Chess/B_Plus.PNG"));
            blueImages[2] = ImageIO.read(new File("Chess/B_Triangle.PNG"));
            blueImages[3] = ImageIO.read(new File("Chess/B_Chevron.PNG"));
            blueImages[4] = ImageIO.read(new File("Chess/B_SUN.PNG"));
            blueImages[5] = ImageIO.read(new File("Chess/B_RevArrow.PNG"));
            blueImages[6] = ImageIO.read(new File("Chess/B_TPlus.PNG"));
            blueImages[7] = ImageIO.read(new File("Chess/B_RevChevron.PNG"));
            blueImages[8] = ImageIO.read(new File("Chess/B_RevTriangle.PNG"));
            
            
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
     * @author Chong Kai Siang 1171103564
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
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
     * @author Chong Kai Siang 1171103564
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
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
     * @author Chong Kai Siang 1171103564
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
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
     * @author Chong Kai Siang 1171103564
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * 
     * @param g Graphics object to be painted
     * @param sW width of square
     * @param sH height of square
     */
    private void Hint(Graphics g, int sW, int sH) {
 
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
     * 
     * @author Chong Kai Siang 1171103564
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * 
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
     * @author Chong Kai Siang 1171103564
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * 
     * @param e
     */
    public void mousePressed(MouseEvent e) {
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

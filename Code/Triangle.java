import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
/**
 * A subclass of Chess
 * Involved in MVC design pattern
 * Act as "Model" of MVC design pattern
 * Involved in Template design pattern
 * 
 * @author Soh Jing Guan   1171103482
 * 
 * @author Chong Kai Siang 1171103564
 * @author Choong Lee Hung 1171103451
 * @author Khaw Wen Kang   1171103546
 * 
 */
public class Triangle extends Chess {
       
    private Boolean triangle = true;
    private int transferTime = 4;
   /**
     * Creates a new "Triangle".
     * 
     * @param location chess location 
     * @param color chess color 
     */
    public Triangle(Point location, Color color) {

        this.color = color;
        this.location = location;
        type = "Triangle";
        this.imageNumber = 2;
    }
    
    /**
     * Get the transfer time of the transformation.
     * 
     * @return transferTime 
     */
    public int getTransferTime() {
        return transferTime;
    }
    
    /**
     * Set the transfer time of the transformation after the game is loaded.
     * 
     * @param time the transfer time to be set
     */
    public void setTransferTime(int time) {
        this.transferTime = time;
    }
    
    /**
     * Set the boolean of triangle after the game is loaded.
     * 
     * @param t the boolean to be set
     */
    public void setIsTriangle(boolean t) {
        this.triangle = t;
    }
    
    /**
     * Get the boolean of triangle.
     * 
     * @return true if the chess is triangle
     */
    public boolean getIsTriangle() {
        return triangle;
    }
    
    /**
     * 
     * To determine the image number of the chess.
     * To ensure the board is flipped properly
     * To determine the behaviour of the chess
     * Change the type of the chess every four round
     * 
     * @param gameBoard the board to get current round 
     * 
     * @return null
     */
    public void getRound(GameBoard gameBoard) {
        Color round = gameBoard.getRound();
        Color chessColor = this.getColor();
        int turn = gameBoard.getRoundNo();
        if(turn==transferTime)
        {
            transferTime = transferTime + 4;
            if(triangle == true)
            {
                triangle = false;
            }
            else if(triangle == false)
            {
                triangle = true;
            }
        }
        
        
        if(triangle == true)
        {
           if(round == Chess.Color.Red && chessColor == Color.Red)
           {
            this.imageNumber =2;
           }
           else if(round == Chess.Color.Blue && chessColor == Color.Blue)
           {
            this.imageNumber =8;
           }
           else if(round == Chess.Color.Red && chessColor == Color.Blue)
           {
            this.imageNumber =2;
           }
           else if(round == Chess.Color.Blue && chessColor == Color.Red)
           {
            this.imageNumber =8;
           }
        }   
        else if(triangle == false)
        {
           if(round == Chess.Color.Red && chessColor == Color.Red)
           {
            this.imageNumber =1;
           }
           else if(round == Chess.Color.Blue && chessColor == Color.Blue)
           {
            this.imageNumber =6;
           }
           else if(round == Chess.Color.Red && chessColor == Color.Blue)
           {
            this.imageNumber =1;
           }
           else if(round == Chess.Color.Blue && chessColor == Color.Red)
           {
            this.imageNumber =6;
           } 
            
            
        }
    }
    
    /**
     * Get the move that are available for the chess
     * 
     * @param gameboard the board to get valid moves on for the chess.
     * @return List containing valid move location
     */
    public List<Move> getValidMoves(GameBoard gameboard) {       
        List<Move> moves = new ArrayList<Move>();
        // if no board given, return empty list
        if (gameboard == null)
            return moves;
        // add moves in diagonal lines to the list          
        if(triangle == true)
        {
            addMove(gameboard, moves, -1, -1);
            addMove(gameboard, moves, 1, -1);
            addMove(gameboard, moves, -1, 1);
            addMove(gameboard, moves, 1, 1);
        }
        // add moves in straight lines to the list  
        else if(triangle == false)
        {
            addMove(gameboard, moves, -1, 0);
            addMove(gameboard, moves, 0, -1);
            addMove(gameboard, moves, 1, 0);
            addMove(gameboard, moves, 0, 1);
        }
        return moves;
    }
    
    /**
     * Checks if a given move is valid 
     * Add the moves in the gameboard and the 
     * location has no chess or has different color chess.
     * 
     * @param gameboard the board to add move on
     * @param moves list to add to
     * @param xi x direction of line 
     * @param yi y direction of line 
     */
    private void addMove(GameBoard gameboard, List<Move> moves, int xi, int yi) {
        int x = location.x;
        int y = location.y;
        
        Point pt = new Point(x + xi, y + yi);
        Chess ch;
        
        while(gameboard.validLocation(pt)) {
            ch = gameboard.getChessAt(pt);
            
            if(ch == null) {
                moves.add(new Move(this, pt, ch));
            } else if(ch.getColor() != this.color) {
                moves.add(new Move(this, pt, ch));
                break;
            } else {
                break;
            }
            //To make sure the available move can be added sucessfully
            pt = new Point(pt.x + xi, pt.y + yi);
        }
    }
    
     /**
     * Get the index of the Chess image in an array.
     * Can be used to determine the type of chess.
     * [0]:arrow [1]:plus [2]:triangle [3]:chevron [4]:sun [5]:revarrow [6]:tplus [7]:revchevron [8]:revtriangle
     * 
     * @return image number of the chess
     */
    public int getImageNumber() {
        return imageNumber;
    }

    /**
     * Get the red image for this chess
     * 
     * @return red image
     */
    public BufferedImage getRedImage() {
        return redImages[imageNumber];
    }
    
    /**
     * Get the blue image for this chess
     * 
     * @return blue image
     */
    public BufferedImage getBlueImage() {
        return blueImages[imageNumber];
    }
}

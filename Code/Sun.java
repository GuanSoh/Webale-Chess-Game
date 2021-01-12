
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
/**
 * A subclass of Chess
 * Involved in MVC design pattern
 * Act as "Model" of MVC design pattern
 * Involved in Template design pattern
 * 
 * @author Choong Lee Hung 1171103451
 * 
 * @author Chong Kai Siang 1171103564
 * @author Khaw Wen Kang   1171103546
 * @author Soh Jing Guan   1171103482
 */
public class Sun extends Chess {
       
    /**
     * Creates a new "Sun".
     * 
     * @param location chess location 
     * @param color chess color 
     */
    public Sun(Point location, Color color) {
      
        this.color = color;
        this.location = location;
        type = "Sun";
        this.imageNumber = 4;
    }
    
     /**
     * 
     * To determine the image number of the chess.
     * To ensure the board is flipped properly
     * 
     * @param gameBoard the board to get current round
     * 
     * @return null
     */
    public void getRound(GameBoard gameBoard){ }
    
    /**
     * Get the move that are available for the chess
     * 
     * @param gameboard the board to get valid moves on for the chess.
     * 
     * @return List containing valid move location
     */
    public List<Move> getValidMoves(GameBoard gameboard) {
        int x = location.x;
        int y = location.y;

        List<Move> moves = new ArrayList<Move>();

        // if no board given, return empty list
        if (gameboard == null)
            return moves;

        
        addMove(gameboard, moves, new Point(x - 1, y - 1));
        addMove(gameboard, moves, new Point(x, y - 1));
        addMove(gameboard, moves, new Point(x + 1, y - 1));
        addMove(gameboard, moves, new Point(x + 1, y));
        addMove(gameboard, moves, new Point(x + 1, y + 1));
        addMove(gameboard, moves, new Point(x, y + 1));
        addMove(gameboard, moves, new Point(x - 1, y + 1));
        addMove(gameboard, moves, new Point(x - 1, y));

        return moves;
    }
    
    /**
     * Checks if a given move is valid
     * Add the move if it is in the gameboard and the 
     * location has no chess or has different color chess.
     * 
     * @param the board to add the move on
     * @param moves list to add the move to
     * @param pt location to be checked 
     */
    private void addMove(GameBoard gameboard, List<Move> moves, Point pt) {
        // if the location is valid
        if(gameboard.validLocation(pt)) {
            // and the location does not contain same color piece
            Chess ch = gameboard.getChessAt(pt);
            if(ch == null || ch.getColor() != this.color) {
                // all the move to the list
                moves.add(new Move(this, pt, ch));
            }
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
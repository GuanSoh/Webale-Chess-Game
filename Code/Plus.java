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
 * @author Khaw Wen Kang   1171103546
 * 
 * @author Chong Kai Siang 1171103564
 * @author Choong Lee Hung 1171103451
 * @author Soh Jing Guan   1171103482
 */
public class Plus extends Chess {
       
    /**
     * Creates a new "Plus".
     * 
     * @param location chess location 
     * @param color chess color 
     */
    public Plus(Point location, Color color) {
     
        this.color = color;
        this.location = location;
        type = "Plus";
        this.imageNumber = 1;
    }

    /**
     * 
     * To determine the image number of the chess.
     * To ensure the board is flipped properly
     * 
     * @return null
     */
    public void getRound(GameBoard gameBoard) { }
    
    /**
     * Get the move that are available for the chess
     * 
     * @param gameboard the board to get valid moves on for the chess.
     * @return List containing valid move location
     */
    public List<Move> getValidMoves(GameBoard gameboard) {       
        List<Move> moves = new ArrayList<Move>();

        // if no gameboard given, return empty list
        if (gameboard == null)
            return moves;
        
        addMove(gameboard, moves, -1, 0);
        addMove(gameboard, moves, 0, -1);
        addMove(gameboard, moves, 1, 0);
        addMove(gameboard, moves, 0, 1);

        return moves;
    }
    
    /**
     * Checks if a given move is valid 
     * Add the move in straight line if it is in the gameboard and the 
     * location has no chess or has different color chess.
     * 
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

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

/**
 * This is the controller to control the game flow
 * Initializes the chess to the array list.
 * Implements Serializable.
 * Involved in MVC design pattern
 * Act as "Controller" of MVC design pattern
 * 
 * @author Chong Kai Siang 1171103564
 * @author Choong Lee Hung 1171103451
 * @author Khaw Wen Kang   1171103546
 * @author Soh Jing Guan   1171103482
 */
public class GameBoard{

    private Chess.Color round;
    private int roundNo=1;
    public boolean over = false;
    private List<Chess> chess = new ArrayList<Chess>();
    
    /**
     * Checks if a position is in the gameboard.
     * 
     * @author Chong Kai Siang 1171103564
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * 
     * @param p point to be check 
     * @return true if valid
     */
    public boolean validLocation(Point p) {
        return (p.x >= 0 && p.x <= 6) && (p.y >= 0 && p.y <= 7);
    }
    
    /**
     * Creates a new gameboard object
     * Add all the chess
     * 
     * @author Choong Lee Hung 1171103451
     * 
     * @author Khaw Wen Kang   1171103546
     * @author Chong Kai Siang 1171103564
     * @author Soh Jing Guan   1171103482
     */
    public GameBoard() {
        round = Chess.Color.Red;
        
               
            // add blue chess to the list
            chess.add(new Arrow(new Point(0, 1), Chess.Color.Blue));
            chess.add(new Arrow(new Point(2, 1), Chess.Color.Blue));
            chess.add(new Arrow(new Point(4, 1), Chess.Color.Blue));
            chess.add(new Arrow(new Point(6, 1), Chess.Color.Blue));
            
            chess.add(new Plus(new Point(0, 0), Chess.Color.Blue));
            chess.add(new Triangle(new Point(1, 0), Chess.Color.Blue));
            chess.add(new Chevron(new Point(2, 0), Chess.Color.Blue));
            chess.add(new Sun(new Point(3, 0), Chess.Color.Blue));
            chess.add(new Chevron(new Point(4, 0), Chess.Color.Blue));
            chess.add(new Triangle(new Point(5, 0), Chess.Color.Blue));
            chess.add(new Plus(new Point(6, 0), Chess.Color.Blue));
            

            // add red chess to the list
            chess.add(new Arrow(new Point(0, 6), Chess.Color.Red));
            chess.add(new Arrow(new Point(2, 6), Chess.Color.Red));
            chess.add(new Arrow(new Point(4, 6), Chess.Color.Red));
            chess.add(new Arrow(new Point(6, 6), Chess.Color.Red));

            chess.add(new Plus(new Point(0, 7), Chess.Color.Red));
            chess.add(new Triangle(new Point(1, 7), Chess.Color.Red));
            chess.add(new Chevron(new Point(2, 7), Chess.Color.Red));
            chess.add(new Sun(new Point(3, 7), Chess.Color.Red));
            chess.add(new Chevron(new Point(4, 7), Chess.Color.Red));
            chess.add(new Triangle(new Point(5, 7), Chess.Color.Red));
            chess.add(new Plus(new Point(6, 7), Chess.Color.Red));
            
        
    }
    
    /**
     * Check whether a game is over 
     * 
     * @author Chong Kai Siang 1171103564
     * 
     * @return true if game is over
     * 
     */
    public boolean getOver() {
        return over;
    }
    
    /**
     * Set the chess when the game is loaded.
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @param c array list of chess to be set
     */
    public void setChess(List<Chess> c) {
        this.chess = c;
    }
 
    /**
     * Get the color of current round.
     * 
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @return the color that can move
     */
    public Chess.Color getRound() {
        return round;
    }
    
    /**
     * Set the color of current round after the game is loaded.
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @param x color to be set
     */
    public void setRound(Chess.Color x) {
        this.round = x;
    }
    
    
    /**
     * Returns the list of chess 
     * 
     * @author Choong Lee Hung 1171103451
     * 
     * @return List<Chess> containing all chess
     */
    public List<Chess> getChess() {
        return chess;
    }
    
    /**
     * Get the round number.
     * 
     * @author Soh Jing Guan   1171103482
     * 
     */
    public int getRoundNo() {
        return roundNo;
    }
    
    /**
     * Set the round number after the game is loaded.
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @param y round number to be set
     */
    public void setRoundNo(int y) {
        this.roundNo = y;
    }
    
    /**
     * To get the chess at the selected location
     * 
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Chong Kai Siang 1171103564
     * 
     * @param p location
     * @return the chess at the location. null if no chess
     */
    public Chess getChessAt(Point p) {
        for(Chess ch : chess) {
            if(ch.getLocation().x == p.x &&
               ch.getLocation().y == p.y)
                return ch;
        }
        return null;
    }
    
    /**
     * Removes the chess 
     * 
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Chong Kai Siang 1171103564
     * 
     * @param c the chess to remove
     */
    public void removeChess(Chess c) {
        if(chess.contains(c)){
            chess.remove(c);
        }
    }
    
    /**
     * Performs the given move. 
     * Change direction of "Arrow" if meet the requirement.
     * Check if a "Sun" has been eaten.
     * 
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Chong Kai Siang 1171103564
     * 
     * @param m move to perform
     */
    public void performMove(Move m) {
        
        // eat the chess if the destination have a different color chess
        if(m.getEaten() != null){
            this.removeChess(m.getEaten());
            
        }
        
        m.getChess().moveTo(m.getMoveTo());         
        
        reverseArrow(m.getChess());
      
        checkGameOver(m.getEaten());
        
        // Change the color to move for next round
        if (round == Chess.Color.Red){
            round = Chess.Color.Blue;
            
        }
        else if (round == Chess.Color.Blue){
            round = Chess.Color.Red; 
            
        }
        flipBoard(round);
        roundNo++;
    }

    /**
     * Check if a sun has been eaten.
     *
     * @author Chong Kai Siang 1171103564
     * 
     * @param sun "Sun" to be checked
     */
    private void checkGameOver(Chess sun) {
        if(sun instanceof Sun){
            over = true;
        }
    }
    
    /**
     * Check if an "Arrow" can change its moving direction.
     * Change the "Arrow" moving direction.
     * 
     * @author Chong Kai Siang 1171103564
     * 
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     *
     * @param arrow "Arrow" to be checked
     */
    private void reverseArrow(Chess arrow) {
        // if an arrow reach the top or botton, 
        // reverse its direction
        if (arrow instanceof Arrow){  
            if (arrow.getLocation().y == 0 || arrow.getLocation().y == 7){
                Chess reverse;  
                reverse = new RevArrow(arrow.getLocation(), arrow.getColor());
                chess.remove(arrow);
                chess.add(reverse);
            }
        }
        
        // if an reversed arrow reach the top or bottom, 
        // reverse its direction back
        else if (arrow instanceof RevArrow){  
            if (arrow.getLocation().y == 0 || arrow.getLocation().y == 7){
                Chess revBack;  
                revBack = new Arrow(arrow.getLocation(), arrow.getColor());
                chess.remove(arrow);
                chess.add(revBack);
            }
        }
    }
    
    /**
     * Flip the game board
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Chong Kai Siang 1171103564
     * 
     * @param r the color of current round 
     */
    private void flipBoard(Chess.Color r) {
            for(Chess ch: chess)
            {
                  Point temp = ch.getLocation();
                  temp.y = 7-temp.y;
                  ch.moveTo(temp);
                  ch.getRound(this);
            }
    }
    
    /**
     * To ensure the chess is loaded correctly.
     * 
     * @author Chong Kai Siang 1171103564
     * 
     * @author Soh Jing Guan   1171103482
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * 
     * @param r the color of current round
     */
    public void LoadChess(Chess.Color r) {
            for(Chess ch: chess)
            {
                ch.getRound(this);
            }
    }
}

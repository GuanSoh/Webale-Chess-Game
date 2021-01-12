import java.awt.Point;

/**
 * For handling movement of the chess.
 * Involved in MVC design pattern
 * Act as "Model" of MVC design pattern
 * 
 * @author Chong Kai Siang 1171103564
 * @author Choong Lee Hung 1171103451
 * @author Khaw Wen Kang   1171103546
 * @author Soh Jing Guan   1171103482
 */
public class Move {
    private Chess toMove;
    private Point moveTo;
    private Chess toEat;
    
    /**
     * Creates a new move object
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @param toMove the chess being selected
     * @param moveTo the destination location
     * @param toEat the chess to be eaten
     */
    public Move(Chess toMove, Point moveTo, Chess toEat) {
        this.toMove = toMove;
        this.moveTo = moveTo;
        this.toEat = toEat;
    }
    
    /**
     * Get the move destination
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @return destination 
     */
    public Point getMoveTo() {
        return moveTo;
    }
    
    /**
     * Get the chess being selected
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @return chess being selected
     */
    public Chess getChess() {
        return toMove;
    }
    
    /**
     * Get the chess to be eaten
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @return chess the chess to be eaten
     */
    public Chess getEaten() {
        return toEat;
    }
}

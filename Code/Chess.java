import java.awt.Point;
import java.util.List;
import java.awt.image.BufferedImage;

/**
 * Generic Chess class. Other chess inherit this class.
 * Involved in MVC design pattern
 * Act as "Model" of MVC design pattern
 * Involved in Template design pattern
 * 
 * @author Chong Kai Siang 1171103564
 * @author Choong Lee Hung 1171103451
 * @author Khaw Wen Kang   1171103546
 * @author Soh Jing Guan   1171103482
 */

public abstract class Chess {
    public static enum Color {Red, Blue};

    // [0]:arrow [1]:plus [2]:triangle [3]:chevron [4]:sun [5]:revarrow 
    protected static BufferedImage[] redImages;
    protected static BufferedImage[] blueImages;
    protected String type;
    protected int imageNumber;

    protected Color color;
    protected Point location;
    /**
     * Gets a list of moves that a chess are able to perform
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @return valid moves
     */
    public abstract List<Move> getValidMoves(GameBoard gameboard);
    
    /**
     * Set the image number after the game is loaded.
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @param x the image number to be set
     */
    public void setImageNumber(int x)
    {
        imageNumber = x;
    }
    
    /**
     * Gets the color of the chess
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @return chess's color
     */
    public Color getColor() {
        return this.color;
    }
    
     /**
     * Gets the type of the chess
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @return chess's type
     */
    public String getType()
    {
        return type;
    }
    
    /**
     * 
     * Get round of the game.To determine the state of the chess.
     * 
     * @author Soh Jing Guan   1171103482
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * 
     */
    public abstract void getRound(GameBoard gameBoard);
    
    /**
     * Moves the chess to new location
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @param p the new location
     */
    public void moveTo(Point p) {
        this.location = p;
  
    }
    
    /**
     * Get the location of the chess
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @return the location 
     */
    public Point getLocation() {
        return this.location;
    }
    
    
    
    /**
     * Get the index of the Chess image in an array.
     * Can be used to determine the type of chess.
     * [0]:arrow [1]:plus [2]:triangle [3]:chevron [4]:sun [5]:revarrow [6]:tplus [7]:revchevron [8]:revtriangle
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @return image number of the chess
     */
    public abstract int getImageNumber() ;
    
    /**
     * Gets the red image 
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @return red image
     */
    public abstract BufferedImage getRedImage() ; 
    
    /**
     * Gets the blue image 
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @return blue image
     */
    public abstract BufferedImage getBlueImage() ;
    
    /**
     * Sets the array of BufferedImages to be used for drawing red chess.
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @param images array of buffered images. 
     */
    public static void setRedImages(BufferedImage[] images) {
        redImages = images;
    }
  
    /**
     * Sets the array of BufferedImages to be used for drawing blue chess.
     * 
     * @author Chong Kai Siang 1171103564
     * @author Choong Lee Hung 1171103451
     * @author Khaw Wen Kang   1171103546
     * @author Soh Jing Guan   1171103482
     * 
     * @param images array of buffered images. 
     */
    public static void setBlueImages(BufferedImage[] images) {
        blueImages = images;
    }

}
import java.awt.*;

/**
 * This class repreents the custom line drawn by the user.
 */
@SuppressWarnings("WeakerAccess")
public class CustomLine {
    private int oldX,oldY,currX,currY;
    private int basicStrokeSize=10;
    private Color color=Color.BLACK;

    public CustomLine(){}

    /**
     * Instantiates the custom line with the corresponding coordinates and attributes.
     * @param oldX the X coordinate when the mouse is pressed or the last current X coordinate.
     * @param oldY the Y coordinate when the mouse is pressed or the last current Y coordinate.
     * @param currX the X coordinate produced when the mouse is dragged.
     * @param currY the Y coordinate produced when the mouse is dragged.
     * @param basicStrokeSize the thickness of the line.
     * @param color the color of the line.
     */
    public CustomLine(int oldX,int oldY,int currX,int currY,int basicStrokeSize,Color color){
        this.oldX=oldX;
        this.oldY=oldY;
        this.currX=currX;
        this.currY=currY;
        this.basicStrokeSize=basicStrokeSize;
        this.color=color;
    }

    public int getOldX() {
        return this.oldX;
    }

    public int getOldY() {
        return this.oldY;
    }

    public int getCurrX() {
        return this.currX;
    }

    public int getCurrY() {
        return this.currY;
    }

    public int getBasicStrokeSize(){
        return this.basicStrokeSize;
    }

    public Color getColor() {
        return this.color;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public void setCurrX(int currX) {
        this.currX = currX;
    }

    public void setCurrY(int currY) {
        this.currY = currY;
    }

    public void setBasicStrokeSize(int basicStrokeSize){
        this.basicStrokeSize=basicStrokeSize;
  }

    public void setColor(Color color) {
        this.color = color;
    }
}

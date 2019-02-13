import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The panel in which all the drawing is done.
 */
@SuppressWarnings("WeakerAccess")
public class DrawPanel extends JPanel {
    private int numOfSectorLines = 12;
    private CustomLine customLine;
    private boolean linesVisible = true;
    private boolean isReflected = false;
    private ArrayList<ArrayList<CustomLine>> customLines;
    private ArrayList<ArrayList<CustomLine>> customLinesRedo;

    public DrawPanel(CustomLine cl) {
        this.customLine= cl;
        customLines =new ArrayList<>();
        customLinesRedo =new ArrayList<>();
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        this.addMouseMotionListener(new MouseFollower(this));
        this.addMouseListener(new MouseFollower(this));
    }

    /**
     * Changes the visibility of the sector lines.
     * @param linesVisible true or false
     */
    public void setSectorLinesVisibility(boolean linesVisible) {
        this.linesVisible = linesVisible;
    }

    /**
     * Switches between eraser mode and normal drawing.
     * @param isEraser starts eraser mode if true
     */
    public void setEraser(boolean isEraser) {
        if(isEraser){
            customLine.setColor(getBackground());
        }
    }

    /**
     * Sets the lines reflections.
     * @param isReflected lines are reflected if true
     */
    public void setReflected(boolean isReflected) {
        this.isReflected = isReflected;
    }

    /**
     * Changes the number of sector lines drawn.
     * @param numOfSectorLines the new number of sector lines.
     */
    public void setNumOfSectorLines(int numOfSectorLines) {
        this.numOfSectorLines = numOfSectorLines;
    }

    /**
     * Clears all lines that were drawn (Apart from the sector lines).
     */
    public void clearCustomLines() {
        this.customLines.clear();
    }

    /**
     * Removes the last line drawn from mouse pressed to mouse released.
     */
    public void undo() {

        if(customLines.size()!=0){
            customLinesRedo.add(customLines.get(customLines.size()-1));
            customLines.remove(customLines.size()-1);
        }
    }

    /**
     * Draws back the previously removed line.
     */
    public void redo(){
        if(!customLinesRedo.isEmpty()){
            customLines.add(customLinesRedo.get(customLinesRedo.size()-1));
            customLinesRedo.remove(customLinesRedo.size()-1);
        }
    }

    /**
     * Saves the current panel drawing.
     * @param panel the panel where the drawing is.
     * @return the saved drawing.
     */
    public BufferedImage saveImage(JPanel panel) {
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        panel.paint(image.getGraphics());
        return image;
    }

    /**
     * Paints all sector and custom lines.
     * @param g the Graphics object.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double startAngle = 0;
        int divisions = numOfSectorLines;
        double delta = 360.0 / divisions;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        drawCustomLines(g2d, delta, centerX, centerY);
        if(linesVisible){
            drawSectorLines(g2d, startAngle, divisions, delta, centerX, centerY);
        }
        g2d.dispose();
    }

    /**
     * Calculates the x and y coordinates of a point.
     * Used for drawing the sector lines.
     * @param radians angle between lines
     * @param radius screen radius
     */
    private Point2D pointAt(double radians, double radius) {
        double x = radius * Math.cos(radians);
        double y = radius * Math.sin(radians);

        return new Point2D.Double(x, y);
    }

    /**
     * Sets a new location to the passed point.
     * @param initialPoint initial point
     * @param destinationPoint destinationPoint
     */
    private Point2D translate(Point2D initialPoint, Point2D destinationPoint) {
        Point2D newPoint = new Point2D.Double(initialPoint.getX(), initialPoint.getY());
        newPoint.setLocation(initialPoint.getX() + destinationPoint.getX(), initialPoint.getY() + destinationPoint.getY());
        return newPoint;
    }

    /**
     * Draws the sector lines.
     * @param g2d the Graphics object.
     * @param startAngle the starting angle is 0.
     * @param divisions the number of sectors.
     * @param delta the angle between two lines.
     * @param centerX the point in the middle of the X coordinate.
     * @param centerY the point in the middle of the Y coordinate.
     */
    private void drawSectorLines(Graphics2D g2d, double startAngle, int divisions, double delta, int centerX, int centerY) {
        Point2D centerPoint = new Point2D.Double(centerX, centerY);
        int radius = Math.min(centerX, centerY) * 2; // Overshoot the visible bounds
        double angle = startAngle;

        g2d.setStroke(new BasicStroke(1));

        if(!linesVisible){
            g2d.setColor(getBackground());
        }else {
            g2d.setColor(Color.BLACK);
        }

        for (int index = 0; index < divisions; index++) {
            Point2D point = pointAt(Math.toRadians(angle), radius);
            point = translate(point, centerPoint);
            g2d.draw(new Line2D.Double(centerPoint, point));
            angle += delta;
        }
    }

    /**
     * Generates all lines drawn by the user.
     * @param g2d the Graphics object.
     * @param delta the angle between two lines.
     * @param centerX the point in the middle of the X coordinate.
     * @param centerY the point in the middle of the Y coordinate.
     */
    private void drawCustomLines(Graphics2D g2d, double delta, int centerX, int centerY) {
        for(ArrayList<CustomLine> singleLine: customLines){
            for (CustomLine customLine1 : singleLine) {

                g2d.setStroke(new BasicStroke(customLine1.getBasicStrokeSize(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
                g2d.setColor(customLine1.getColor());
                g2d.drawLine(customLine1.getOldX(), customLine1.getOldY(), customLine1.getCurrX(), customLine1.getCurrY());

                for (int k = 0; k < numOfSectorLines; k++) {
                    g2d.rotate(Math.toRadians(delta), centerX, centerY);
                    g2d.drawLine(customLine1.getOldX(), customLine1.getOldY(), customLine1.getCurrX(), customLine1.getCurrY());

                    if (isReflected) {
                        g2d.drawLine(customLine1.getOldX(), customLine1.getOldY() - 2 * (customLine1.getOldY() - centerY),
                                customLine1.getCurrX(), customLine1.getCurrY() - 2 * (customLine1.getCurrY() - centerY));
                    }
                }
            }
        }
    }

    /**
     * Follows when the mouse is dragged or pressed.
     */
    class MouseFollower extends MouseAdapter {
        private DrawPanel drawPanel;

        public MouseFollower(DrawPanel drawPanel) {
            this.drawPanel = drawPanel;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            this.addLineOnDrag(e);
            drawPanel.repaint();
        }

        public void mousePressed(MouseEvent e) {
            customLinesRedo.clear();
            customLines.add(new ArrayList<>());
            this.addLineOnPress(e);
            drawPanel.repaint();
        }

        /**
         * Creates and adds the generated lines in the array list when the mouse is dragged.
         * @param e Mouse event
         */
        private void addLineOnDrag(MouseEvent e){
            customLine.setCurrX(e.getX());
            customLine.setCurrY(e.getY());
            customLines.get(customLines.size()-1).add(new CustomLine(customLine.getOldX(), customLine.getOldY(), customLine.getCurrX(), customLine.getCurrY(),
                    customLine.getBasicStrokeSize(), customLine.getColor()));
            customLine.setOldX(customLine.getCurrX());
            customLine.setOldY(customLine.getCurrY());
        }

        /**
         * Creates and adds a line in the array list when the mouse is pressed.
         * @param e Mouse event
         */
        private void addLineOnPress(MouseEvent e){
            customLine.setOldX(e.getX());
            customLine.setOldY(e.getY());
            customLines.get(customLines.size()-1).add(new CustomLine(customLine.getOldX(), customLine.getOldY(), customLine.getOldX(), customLine.getOldY(),
                    customLine.getBasicStrokeSize(), customLine.getColor()));
        }
    }
}

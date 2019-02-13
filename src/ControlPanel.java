import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Contains all components required for drawing.
 */
@SuppressWarnings("WeakerAccess")
public class ControlPanel extends JPanel {
    private Color color;
    private DrawPanel drawPanel;
    private ImageDisplay imageDisplay;
    private CustomLine customLine;
    private ArrayList<File> files;

    /**
     * Adds all components with their functionalities to the panel.
     * @param drawPanel the panel to which changes in the drawing options will be made
     * @param images the panel which is going to display the saved images.
     * @param cl the line which will have its options changed.
     */
    public ControlPanel(DrawPanel drawPanel, ImageDisplay images, CustomLine cl) {
        this.drawPanel = drawPanel;
        this.imageDisplay = images;
        this.customLine=cl;
        this.files=new ArrayList<>();
        this.add(getSlider());
        this.add(getChangeColorButton());
        this.add(getEmptyPanel());
        this.addCheckBoxes();
        this.add(getEmptyPanel());
        this.add(getSectorsSlider());
        this.add(getEmptyPanel());
        this.add(getClearButton());
        this.add(getEmptyPanel());
        this.add(getUndoButton());
        this.add(getEmptyPanel());
        this.add(getRedoButton());
        this.add(getEmptyPanel());
        this.add(getSaveButton());
        this.add(getEmptyPanel());
        this.add(getRemoveButton());
        this.add(getEmptyPanel());
    }

    private JSlider getSlider() {
        JSlider penSizeSlider = new JSlider(0,40,10);
        penSizeSlider.setMajorTickSpacing(5);
        penSizeSlider.setPaintTicks(true);
        penSizeSlider.setPaintLabels(true);
        penSizeSlider.addChangeListener(e -> {
            customLine.setBasicStrokeSize(penSizeSlider.getValue());
            drawPanel.repaint();
        });

        JLabel penSizeLabel=getPenSizeLabel();
        this.add(penSizeLabel);

        penSizeSlider.addChangeListener(e -> penSizeLabel.setText("Current pen size: " + penSizeSlider.getValue()));
        return penSizeSlider;
    }

    private JLabel getPenSizeLabel(){
        JLabel penSizeLabel = this.getJLabel("Current pen size: 10");
        penSizeLabel.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        return penSizeLabel;
    }

    /**
     * Creates a new JLabel with a given title.
     * @param title title of the label.
     * @return the created label.
     */
    private JLabel getJLabel(String title){
        JLabel label = new JLabel(title);
        label.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        return label;
    }

    private JButton getChangeColorButton() {

        JButton changeColour = new JButton("Change Colour");
        changeColour.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        changeColour.addActionListener(e -> {
            color = JColorChooser.showDialog(null, "Pick colour", color);
            customLine.setColor(color);
            if (color == null) {
                color = (Color.BLACK);
            }
            drawPanel.repaint();
        });

        return changeColour;
    }

    /**
     * Creates an empty panel, which aims to make the control panel look better.
     * @return an empty panel.
     */
    private JPanel getEmptyPanel() {
        return new JPanel();
    }

    private void addCheckBoxes() {
        JCheckBox showLines = new JCheckBox("Hide lines");
        JCheckBox reflect = new JCheckBox("Reflect");
        JCheckBox eraser = new JCheckBox("Eraser");

        showLines.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        reflect.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        eraser.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));

        showLines.addActionListener(e -> {
            if (showLines.isSelected()) {
                drawPanel.setSectorLinesVisibility(false);
                drawPanel.repaint();
            } else {
                drawPanel.setSectorLinesVisibility(true);
                drawPanel.repaint();
            }
        });

        reflect.addActionListener(e -> {
            if (reflect.isSelected()) {
                drawPanel.setReflected(true);
                drawPanel.repaint();
            } else {
                drawPanel.setReflected(false);
                drawPanel.repaint();
            }
        });

        eraser.addActionListener(e -> {

            if (eraser.isSelected()) {
                color=customLine.getColor();
                drawPanel.setEraser(true);
                drawPanel.repaint();
            } else {
                customLine.setColor(color);
                drawPanel.setEraser(false);
                drawPanel.repaint();
            }
        });

        JPanel checkBoxPanels = new JPanel();
        checkBoxPanels.setLayout(new GridLayout(3, 1));

        checkBoxPanels.add(showLines);
        checkBoxPanels.add(reflect);
        checkBoxPanels.add(eraser);

        this.add(checkBoxPanels);
    }

    private JSlider getSectorsSlider() {
        JSlider sectorsSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 12);
        sectorsSlider.setMajorTickSpacing(10);
        sectorsSlider.setPaintTicks(true);
        sectorsSlider.setPaintLabels(true);
        sectorsSlider.addChangeListener(e -> {
            drawPanel.setNumOfSectorLines(sectorsSlider.getValue());
            drawPanel.repaint();
        });

        JLabel sectorsSizeLabel = getSectorsSizeLabel();
        this.add(sectorsSizeLabel);

        sectorsSlider.addChangeListener(e -> sectorsSizeLabel.setText("Current number of lines: " + sectorsSlider.getValue()));
        return sectorsSlider;
    }

    private JLabel getSectorsSizeLabel() {
        JLabel sectorsSizeLabel = this.getJLabel("Current number of lines: 12");
        sectorsSizeLabel.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        return sectorsSizeLabel;
    }

    private JButton getClearButton() {
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        clearButton.addActionListener(e -> {
            drawPanel.clearCustomLines();
            drawPanel.repaint();
        });
        return clearButton;
    }

    private JButton getUndoButton() {
        JButton undoButton = new JButton("Undo");
        undoButton.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        undoButton.addActionListener(e -> {
            drawPanel.undo();
            drawPanel.repaint();
        });
        return undoButton;
    }

    private JButton getRedoButton() {
        JButton redoButton = new JButton("Redo");
        redoButton.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        redoButton.addActionListener(e -> {
            drawPanel.redo();
            drawPanel.repaint();
        });
        return redoButton;
    }

    /**
     * Creates a save button that saves the current image, as a label, to
     * the image panel and to the Gallery folderon the computer by using a new Thread.
     * @return the save button.
     */
    private JButton getSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        saveButton.addActionListener(e -> {
            if (imageDisplay.getImages().size() <= 11) {
                BufferedImage temp = drawPanel.saveImage(drawPanel);
                BufferedImage image = resize(temp, Settings.SAVED_SCREEN_IMAGE_WIDTH, Settings.SAVED_SCREEN_IMAGE_HEIGHT);

                //Start a new thread for saving the image
                (new Thread(() -> saveToFile(temp))).start();
                imageDisplay.addImage(new JLabel(new ImageIcon(image)));
                imageDisplay.revalidate();

            }else{
                JOptionPane.showMessageDialog(null,"You can't add more doilies!");
            }
        });
        return saveButton;
    }

    /**
     * Saves the passed image in the Gallery folder as a .png file.
     * The name of the file is the Local date time.
     * @param image the image to be saved.
     */
    private void saveToFile(BufferedImage image){
        try{
            File outputfile = new File("Gallery/"+"IMG_"+String.valueOf(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS")))+".png");
            files.add(outputfile);

            ImageIO.write(image,"png",outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resizes the passed image to a specified size.
     * @param image the last drawing on the draw panel.
     * @param width the width after resizing of the image.
     * @param height the height after resizing the image.
     * @return the resized image.
     */
    private static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage saved = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = saved.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return saved;
    }

    /**
     * Creates a remove button that removes a selected image from both the image panel and
     * the Gallery folder.
     * @return the remove button
     */
    private JButton getRemoveButton() {
        JButton removeButton = new JButton("Remove");
        removeButton.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE));
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String userInput=JOptionPane.showInputDialog("Enter the number of doily to be removed");

                try{
                    if (Integer.parseInt(userInput) >= 0 && Integer.parseInt(userInput) < imageDisplay.getImages().size()) {
                        imageDisplay.removeImage(Integer.parseInt(userInput));
                        removeFile(userInput);
                        imageDisplay.repaint();
                    }else{
                        JOptionPane.showMessageDialog(null,"No such doily exists!");
                    }
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"Wrong input format!");
                }
            }
        });
        return removeButton;
    }

    /**
     * Removes the selected image from the Gallery folder.
     * @param userInput the index of the image to be removed.
     */
    private void removeFile(String userInput) {
        if(files.get(Integer.parseInt(userInput)).delete()) {
            files.remove(Integer.parseInt(userInput));
            JOptionPane.showMessageDialog(null,"Doily successfully deleted!");
            imageDisplay.revalidate();
        }
    }
}

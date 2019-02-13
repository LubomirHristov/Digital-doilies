import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Contains the saved images.
 */
@SuppressWarnings("WeakerAccess")
public class ImageDisplay extends JPanel {
    private ArrayList<JLabel> images;
    private JPanel imagePanel;

    /**
     * Instantiates the gallery.
     */
    public ImageDisplay(){
        this.images = new ArrayList<>();
        this.imagePanel = new JPanel();
        JLabel galleryLabel = new JLabel("Gallery");
        galleryLabel.setFont(new Font(Settings.TEXT_FONT_NAME, Settings.TEXT_FONT_STYLE, Settings.TEXT_SIZE+10));

        this.setLayout(new BorderLayout());
        this.add(galleryLabel,BorderLayout.NORTH);
        this.add(imagePanel, BorderLayout.CENTER);
    }

    public ArrayList<JLabel> getImages() {
        return this.images;
    }

    /**
     * Adds an image to the array list of images that are added to the panel.
     * @param image the last saved image.
     */
    public void addImage(JLabel image){
        this.images.add(image);
        this.imagePanel.setLayout(new GridLayout(6,2));
        this.imagePanel.add(image);
    }

    /**
     * Removes an image from the array list of images and from the panel.
     * @param index the index of the image to be removed.
     */
    public void removeImage(int index){
        this.images.remove(index);
        this.imagePanel.remove(index);
    }
}

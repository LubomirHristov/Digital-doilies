import javax.swing.*;
import java.awt.*;

/**
 * Combines all panels and contains their adjustment settings (layout, size, bounds, etc.).
 */
@SuppressWarnings({"SuspiciousNameCombination", "WeakerAccess"})
public class DigitalDoilies extends  JPanel{

    public DigitalDoilies(){
        init();
    }

    /**
     * Makes the main window with the different panels.
     */
    private void init(){

        JFrame window = new JFrame("Digital Doilies");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container panel = window.getContentPane();
        panel.setLayout(new BorderLayout());

        CustomLine cl=new CustomLine();

        DrawPanel drawPanel = getDrawPanel(cl);
        ImageDisplay imagePanel = getImageDisplay();
        JPanel controlPanel = getControlPanel(cl, drawPanel, imagePanel);

        panel.add(controlPanel, BorderLayout.WEST);
        panel.add(drawPanel, BorderLayout.CENTER);
        panel.add(imagePanel, BorderLayout.EAST);

        window.setPreferredSize(new Dimension(Settings.dimension));
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }

    private JPanel getControlPanel(CustomLine cl, DrawPanel drawPanel, ImageDisplay imagePanel) {
        JPanel controlPanel = new ControlPanel(drawPanel,imagePanel,cl);
        controlPanel.setLayout(new GridLayout(19,1));
        controlPanel.setPreferredSize(new Dimension((Settings.SCREEN_WIDTH-Settings.SCREEN_HEIGHT)/3,Settings.SCREEN_HEIGHT));
        return controlPanel;
    }

    private ImageDisplay getImageDisplay() {
        ImageDisplay imagePanel = new ImageDisplay();
        imagePanel.setPreferredSize(new Dimension(2*(Settings.SCREEN_WIDTH-Settings.SCREEN_HEIGHT)/3,Settings.SCREEN_HEIGHT));
        return imagePanel;
    }

    private DrawPanel getDrawPanel(CustomLine cl) {
        DrawPanel drawPanel = new DrawPanel(cl);
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setPreferredSize(new Dimension(Settings.SCREEN_HEIGHT,Settings.SCREEN_HEIGHT));
        drawPanel.setBounds((Settings.SCREEN_WIDTH-Settings.SCREEN_HEIGHT)/2,0,Settings.SCREEN_HEIGHT,Settings.SCREEN_HEIGHT);
        return drawPanel;
    }
}

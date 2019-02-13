import java.awt.*;

/**
 * This class contains some global settings for often used variables that make their changing easier.
 */
@SuppressWarnings("WeakerAccess")
public class Settings {
    private Settings(){}

    public static final Dimension dimension= Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH=(int)dimension.getWidth();
    public static final int SCREEN_HEIGHT=(int)dimension.getHeight();

    public static final String TEXT_FONT_NAME="Arial";
    public static final int TEXT_FONT_STYLE= Font.PLAIN;
    public static final int TEXT_SIZE=20;

    public static final int SAVED_SCREEN_IMAGE_WIDTH=150;
    public static final int SAVED_SCREEN_IMAGE_HEIGHT=150;
}

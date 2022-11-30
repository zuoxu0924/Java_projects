import java.awt.*;

public abstract class GameParent {
    //image
    public Image image;
    //position
    public int pointX;
    public int pointY;
    //interface
    public GamePanel gamePanel;

    public GameParent(String image, int pointX, int pointY, GamePanel gamePanel) {
        this.image = Toolkit.getDefaultToolkit().getImage(image);
        this.pointX = pointX;
        this.pointY = pointY;
        this.gamePanel = gamePanel;
    }

    public abstract void paintSelf(Graphics graphics);

    public abstract Rectangle getRec();
}

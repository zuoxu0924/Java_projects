import java.awt.*;

public abstract class GameParent {
    //image
    public Image image;
    //position
    public int pointX;
    public int pointY;
    //interface
    public GamePannel gamePannel;

    public GameParent(String image, int pointX, int pointY, GamePannel gamePannel) {
        this.image = Toolkit.getDefaultToolkit().getImage(image);
        this.pointX = pointX;
        this.pointY = pointY;
        this.gamePannel = gamePannel;
    }

    public abstract void paintSelf(Graphics graphics);

    public abstract Rectangle getRec();
}

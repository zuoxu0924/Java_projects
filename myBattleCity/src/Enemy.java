import java.awt.*;

public class Enemy extends Tank {

    public Enemy(String image, int pointX, int pointY, GamePannel gamePannel,
                 String upImg, String leftImg, String rightImg, String downImg) {
        super(image, pointX, pointY, gamePannel, upImg, leftImg, rightImg, downImg);
    }

    @Override
    public void paintSelf(Graphics graphics) {
        graphics.drawImage(image, pointX, pointY, null);
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(pointX, pointY, tankWidth, tankHeight);
    }
}

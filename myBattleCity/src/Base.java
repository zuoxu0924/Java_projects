import java.awt.*;

public class Base extends GameParent {

    //size
    int size = 50;

    public Base(String image, int pointX, int pointY, GamePanel gamePanel) {
        super(image, pointX, pointY, gamePanel);
    }

    @Override
    public void paintSelf(Graphics graphics) {
        graphics.drawImage(image, pointX, pointY, null);
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(pointX, pointY, size, size);
    }
}

import java.awt.*;

public class Wall extends GameParent {

    //size
    int wallWidth;
    int wallHeight;
    //墙的类型是砖墙还是铁墙
    boolean destroyable = true;

    public Wall(String image, int pointX, int pointY, GamePanel gamePanel, boolean destroyable, int width, int height) {
        super(image, pointX, pointY, gamePanel);
        this.destroyable = destroyable;
        this.wallWidth = width;
        this.wallHeight = height;
    }

    public int getWallWidth() {
        return wallWidth;
    }

    public int getWallHeight() {
        return wallHeight;
    }

    @Override
    public void paintSelf(Graphics graphics) {
        graphics.drawImage(image, pointX, pointY, gamePanel);
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(pointX, pointY,this.getWallWidth(), this.getWallHeight());
    }
}

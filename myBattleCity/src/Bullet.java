import java.awt.*;

public class Bullet extends GameParent {
    //size
    int bulletWidth = 2;
    int bulletHeight = 3;
    //speed
    int speed = 7;
    //direction
    Direction direction;

    public Bullet(String image, int pointX, int pointY, GamePannel gamePannel, Direction direction) {
        super(image, pointX, pointY, gamePannel);
        this.direction = direction;
    }

    //子弹移动
    public void bulletUpWard() {
        pointY -= speed;
    }

    public void bulletLeftWard() {
        pointX -= speed;
    }

    public void bulletRightWard() {
        pointX += speed;
    }

    public void bulletDownWard() {
        pointY += speed;
    }

    public void bulletMove() {
        switch (direction) {
            case UP:
                bulletUpWard();
                break;
            case LEFT:
                bulletLeftWard();
                break;
            case RIGHT:
                bulletRightWard();
                break;
            case DOWN:
                bulletDownWard();
                break;
        }
    }
    @Override
    public void paintSelf(Graphics graphics) {
        graphics.drawImage(image, pointX, pointY, null);
        this.bulletMove();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(pointX, pointY, bulletWidth, bulletHeight);
    }
}

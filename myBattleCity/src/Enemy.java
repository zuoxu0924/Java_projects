import java.awt.*;
import java.util.Random;

public class Enemy extends Tank {

    int moveTimes = 0;
    public Enemy(String image, int pointX, int pointY, GamePanel gamePanel,
                 String upImg, String leftImg, String rightImg, String downImg) {
        super(image, pointX, pointY, gamePanel, upImg, leftImg, rightImg, downImg);
    }

    public Direction getRandomDirection() {
        Random random = new Random();
        int randDirection = random.nextInt(4);
        return switch (randDirection) {
            case 0 -> Direction.UP;
            case 1 -> Direction.LEFT;
            case 2 -> Direction.RIGHT;
            default -> Direction.DOWN;
        };
    }

    public void enemyMove() {
        enemyFire();
        if(moveTimes > 15) {
            direction = this.getRandomDirection();
            moveTimes = 0;
        } else  {
            moveTimes++;
        }

        switch (direction) {
            case UP -> tankUpWard();
            case LEFT -> tankLeftWard();
            case RIGHT -> tankRightWard();
            case DOWN -> tankDownWard();
        }
    }

    //敌军攻击
    public void enemyFire() {
        Point p = getTankHeadPoint();
        Random random = new Random();
        int rand = random.nextInt(400);
        if(rand < 10) {
            this.gamePanel.bulletArrayList.add(new EnemyBullet("images/bullet/enemyBullet.png",
                    p.x, p.y, this.gamePanel, direction));
        }
    }
    @Override
    public void paintSelf(Graphics graphics) {
        graphics.drawImage(image, pointX, pointY, null);
        enemyMove();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(pointX, pointY, tankWidth, tankHeight);
    }
}

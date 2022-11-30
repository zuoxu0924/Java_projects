import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameParent {
    //size
    int bulletWidth = 2;
    int bulletHeight = 3;
    //speed
    int speed = 7;
    //direction
    Direction direction;

    public Bullet(String image, int pointX, int pointY, GamePanel gamePanel, Direction direction) {
        super(image, pointX, pointY, gamePanel);
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
            case UP -> bulletUpWard();
            case LEFT -> bulletLeftWard();
            case RIGHT -> bulletRightWard();
            case DOWN -> bulletDownWard();
        }
        this.hitWall();
        this.hitBorderWall();
        this.outOfBorder();
    }

    //碰撞检测
    public void hitEnemy() {
        ArrayList<Enemy> enemyArrayList = this.gamePanel.enemyArrayList;
        for(Enemy enemy : enemyArrayList) {
            if(this.getRec().intersects(enemy.getRec())) {
                this.gamePanel.explodeArrayList.add(
                        new Explode("", enemy.pointX, enemy.pointY, this.gamePanel));
                this.gamePanel.enemyArrayList.remove(enemy);
                this.gamePanel.removeBulletList.add(this);
                break;
            }
        }
    }

    //子弹和墙的碰撞检测
    public void hitWall() {
        ArrayList<Wall> wallArrayList = this.gamePanel.wallArrayList;
        for(Wall wall : wallArrayList) {
            if(this.getRec().intersects(wall.getRec()) && wall.destroyable) {
                this.gamePanel.wallArrayList.remove(wall);
                this.gamePanel.removeBulletList.add(this);
                break;
            }
        }
    }

    public void hitBorderWall() {
        ArrayList<Wall> borderWallList = this.gamePanel.borderWallList;
        for(Wall wall : borderWallList) {
            if(this.getRec().intersects(wall.getRec())) {
                this.gamePanel.removeBulletList.add(this);
                break;
            }
        }
    }

    //删除已出屏幕的子弹,防止overflow
    public void outOfBorder() {
        if(pointX < 0 || pointX + bulletWidth > this.gamePanel.getWidth()
                || pointY < 0 || pointY + bulletHeight > this.gamePanel.getHeight()) {
            this.gamePanel.removeBulletList.add(this);
        }
    }

    @Override
    public void paintSelf(Graphics graphics) {
        graphics.drawImage(image, pointX, pointY, null);
        this.bulletMove();
        this.hitEnemy();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(pointX, pointY, bulletWidth, bulletHeight);
    }
}

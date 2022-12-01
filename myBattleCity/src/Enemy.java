import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Tank {

    int moveTimes = 0;
    int fireType = 0;
    private boolean fireCoolDown = true;

    public Enemy(String image, int pointX, int pointY, GamePanel gamePanel,
                 String upImg, String leftImg, String rightImg, String downImg, int fireType) {
        super(image, pointX, pointY, gamePanel, upImg, leftImg, rightImg, downImg);
        this.fireType = fireType;
    }

    class FireCoolDown extends Thread {
        public void run() {
            fireCoolDown = false;
            try {
                int fireInterval = 8000;
                Thread.sleep(fireInterval);
            } catch (Exception e) {
                e.printStackTrace();
            }
            fireCoolDown = true;
            //this.stop();
        }
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
        if(moveTimes > 30) {
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
        crashPlayer();
    }

    //敌军攻击
    public void enemyFire() {
        Point p = getTankHeadPoint();
        if(fireType == 0) {
            Random random = new Random();
            int rand = random.nextInt(400);
            if (rand < 8) {
                this.gamePanel.bulletArrayList.add(new EnemyBullet("images/bullet/enemyBullet.png",
                        p.x, p.y, this.gamePanel, direction));
            }
        } else if (fireType == 1) {
            if(fireCoolDown) {
                this.gamePanel.bulletArrayList.add(new EnemyBullet("images/bullet/enemyBullet.png",
                        p.x, p.y, this.gamePanel, direction));
                new FireCoolDown().start();
            }
        }
    }

    //若敌军撞上玩家，玩家同样失败
    public void crashPlayer() {
        ArrayList<Tank> playerList = this.gamePanel.playerList;
        for(Tank player : playerList) {
            if(this.getRec().intersects(player.getRec())) {
                this.gamePanel.explodeArrayList.add(
                        new Explode("", player.pointX, player.pointY, this.gamePanel));
                this.gamePanel.playerList.remove(player);
                player.alive = false;
                break;
            }
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

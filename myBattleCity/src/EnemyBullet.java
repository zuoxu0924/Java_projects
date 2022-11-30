import java.awt.*;
import java.util.ArrayList;

public class EnemyBullet extends Bullet{

    public EnemyBullet(String image, int pointX, int pointY, GamePanel gamePanel, Direction direction) {
        super(image, pointX, pointY, gamePanel, direction);
    }

    public void hitPlayer() {
        ArrayList<Tank> playerList = this.gamePanel.playerList;
        for(Tank player : playerList) {
            if(this.getRec().intersects(player.getRec())) {
                this.gamePanel.explodeArrayList.add(
                        new Explode("", player.pointX, player.pointY, this.gamePanel));
                this.gamePanel.playerList.remove(player);
                player.alive = false;
                this.gamePanel.removeBulletList.add(this);
                break;
            }
        }
    }

    //子弹与基地碰撞检测
    public void hitBase() {
        ArrayList<Base> baseArrayList = this.gamePanel.baseArrayList;
        for(Base base : baseArrayList) {
            if(this.getRec().intersects(base.getRec())) {
                this.gamePanel.baseArrayList.remove(base);
                this.gamePanel.removeBulletList.add(this);
                break;
            }
        }
    }

    @Override
    public void paintSelf(Graphics graphics) {
        graphics.drawImage(image, pointX, pointY, null);
        this.bulletMove();
        this.hitPlayer();
        this.hitBase();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(pointX, pointY, bulletWidth, bulletHeight);
    }
}

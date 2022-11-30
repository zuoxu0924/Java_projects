import java.awt.*;

public class Explode extends GameParent {

    //爆炸过程
    static Image[] explodeImg = new Image[9];
    //表示爆炸到哪个阶段
    int phase = 0;

    static  {
        for(int i = 0; i < 9; i++) {
            explodeImg[i] = Toolkit.getDefaultToolkit().getImage("images/explode/explode" + (i + 1) + ".png");
        }
    }
    public Explode(String image, int pointX, int pointY, GamePanel gamePanel) {
        super(image, pointX, pointY, gamePanel);
    }

    @Override
    public void paintSelf(Graphics graphics) {
        if(phase < 9) {
            graphics.drawImage(explodeImg[phase], pointX, pointY, null);
            phase++;
        }
    }

    @Override
    public Rectangle getRec() {
        return null;
    }
}

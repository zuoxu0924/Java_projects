import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class Player extends Tank {
    private boolean up = false;
    private boolean left = false;
    private boolean right = false;
    private boolean down = false;
    public Player(String image, int pointX, int pointY, GamePannel gamePannel,
                  String upImg, String leftImg, String rightImg, String downImg) {
        super(image, pointX, pointY, gamePannel, upImg, leftImg, rightImg, downImg);
    }

    /*
     * 响应键盘事件控制tank移动
     * 通过一个按下/松开避免单次点击方向键然后逐个响应
     * 实现按住一个方向键后tank持续移动
     */
    //键盘按下事件
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_SPACE:
                tankFire();
            default:
                break;
        }
    }
    //键盘松开事件
    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            default:
                break;
        }
    }

    //tank移动响应
    public void tankMove() {
        if(up) {
            tankUpWard();
        } else if (left) {
            tankLeftWard();
        } else if (right) {
            tankRightWard();
        } else if (down) {
            tankDownWard();
        }
    }

    @Override
    public void paintSelf(Graphics graphics) {
        graphics.drawImage(image, pointX, pointY, null);
        tankMove();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(pointX, pointY, tankWidth, tankHeight);
    }
}

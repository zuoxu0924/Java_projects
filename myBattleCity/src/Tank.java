import java.awt.*;

public abstract class Tank extends GameParent{
    //tank size
    public int tankWidth = 40;
    public int tankHeight = 50;
    //speed
    private int speed = 3;
    //direction
    private Direction direction = Direction.UP;
    //定义坦克转向用的图片
    private String upImg;
    private String leftImg;
    private String rightImg;
    private String downImg;

    /*
     * 规范化游戏，添加新规则->攻击冷却时间
     * tank不能连续不停地发射子弹，时间间隔为1000ms
     */
    private boolean fireCoolDown = true;
    private int fireInterval = 500;

    public Tank(String image, int pointX, int pointY, GamePannel gamePannel,
                String upImg, String leftImg, String rightImg, String downImg) {
        super(image, pointX, pointY, gamePannel);
        this.upImg = upImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.downImg = downImg;
    }
    /*
     * 改变tank方向的函数
     */
    public void tankUpWard() {
        pointY -= speed;
        setImage(upImg);
        direction = Direction.UP;
    }

    public void tankLeftWard() {
        pointX -= speed;
        setImage(leftImg);
        direction = Direction.LEFT;
    }

    public void tankRightWard() {
        pointX += speed;
        setImage(rightImg);
        direction = Direction.RIGHT;
    }

    public void tankDownWard() {
        pointY += speed;
        setImage(downImg);
        direction = Direction.DOWN;
    }

    //坦克发射子弹
    public void tankFire() {
        if(fireCoolDown) {
            Point p = this.getTankHeadPoint();
            String bulletImg = null;
            //根据tank朝向选择子弹方向
            if (direction == Direction.UP) {
                bulletImg = "images/bullet/bullet-u.png";
            } else if (direction == Direction.LEFT) {
                bulletImg = "images/bullet/bullet-l.png";
            } else if (direction == Direction.RIGHT) {
                bulletImg = "images/bullet/bullet-r.png";
            } else if (direction == Direction.DOWN) {
                bulletImg = "images/bullet/bullet-d.png";
            }
            Bullet bullet = new Bullet(bulletImg, p.x, p.y, this.gamePannel, direction);
            this.gamePannel.bulletArrayList.add(bullet);

            new FireCoolDown().start();
        }
    }

    /*
     * 因为tank从头发射子弹，所以要计算坦克头的坐标
     * 但是坦克朝向不同时，获取的头部坐标不同，因此添加一个函数获取其坐标
     */
    public Point getTankHeadPoint() {
        switch (direction) {
            case UP:
                return new Point(pointX + tankWidth / 3, pointY);
            case LEFT:
                return new Point(pointX, pointY + tankHeight / 3);
            case RIGHT:
                return new Point(pointX + tankWidth, pointY + tankHeight / 3);
            case DOWN:
                return new Point(pointX + tankWidth / 3, pointY + tankHeight);
            default:
                return null;
        }
    }

    //新建一个线程，用来计算攻击冷却时间
    class FireCoolDown extends Thread {
        public void run() {
            fireCoolDown = false;
            try {
                Thread.sleep(fireInterval);
            } catch (Exception e) {
                e.printStackTrace();
            }
            fireCoolDown = true;
            this.stop();
        }
    }
    public void setImage(String image) {
        this.image = Toolkit.getDefaultToolkit().getImage(image);
    }

    @Override
    public abstract void paintSelf(Graphics graphics);
    @Override
    public abstract Rectangle getRec();
}

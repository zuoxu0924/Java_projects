import java.awt.*;
import java.util.ArrayList;

public abstract class Tank extends GameParent{
    //tank size
    public int tankWidth = 32;
    public int tankHeight = 32;
    //speed
    public int speed = 3;
    //生命变量
    public boolean alive = false;
    //direction
    public Direction direction = Direction.UP;
    //定义坦克转向用的图片
    private String upImg;
    private String leftImg;
    private String rightImg;
    private String downImg;

    /*
     * 规范化游戏，添加新规则->攻击冷却时间
     * tank不能连续不停地发射子弹，时间间隔为500ms
     */
    private boolean fireCoolDown = true;
    private int fireInterval = 500;

    public Tank(String image, int pointX, int pointY, GamePanel gamePanel,
                String upImg, String leftImg, String rightImg, String downImg) {
        super(image, pointX, pointY, gamePanel);
        this.upImg = upImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.downImg = downImg;
    }
    /*
     * 改变tank方向的函数
     */
    public void tankUpWard() {
        setImage(upImg);
        direction = Direction.UP;
        if(!tankHitWall(pointX, pointY - speed) && !reachBorder(pointX, pointY - speed)) {
            pointY -= speed;
        }
    }

    public void tankLeftWard() {
        setImage(leftImg);
        direction = Direction.LEFT;
        if(!tankHitWall(pointX - speed, pointY) && !reachBorder(pointX - speed, pointY)) {
            pointX -= speed;
        }
    }

    public void tankRightWard() {
        setImage(rightImg);
        direction = Direction.RIGHT;
        if(!tankHitWall(pointX + speed, pointY) && !reachBorder(pointX + speed, pointY)) {
            pointX += speed;
        }
    }

    public void tankDownWard() {
        setImage(downImg);
        direction = Direction.DOWN;
        if(!tankHitWall(pointX, pointY + speed) && !reachBorder(pointX, pointY + speed)) {
            pointY += speed;
        }
    }

    //坦克发射子弹
    public void tankFire() {
        if(fireCoolDown && alive) {
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
            Bullet bullet = new Bullet(bulletImg, p.x, p.y, this.gamePanel, direction);
            this.gamePanel.bulletArrayList.add(bullet);

            new FireCoolDown().start();
        }
    }

    /*
     * 因为tank从头发射子弹，所以要计算坦克头的坐标
     * 但是坦克朝向不同时，获取的头部坐标不同，因此添加一个函数获取其坐标
     */
    public Point getTankHeadPoint() {
        return switch (direction) {
            case UP -> new Point(pointX + tankWidth / 2, pointY);
            case LEFT -> new Point(pointX, pointY + tankHeight / 2);
            case RIGHT -> new Point(pointX + tankWidth, pointY + tankHeight / 2);
            case DOWN -> new Point(pointX + tankWidth / 2, pointY + tankHeight);
        };
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

    //tank和wall碰撞检测
    public boolean tankHitWall(int x, int y) {
        ArrayList<Wall> wallArrayList = this.gamePanel.wallArrayList;
        Rectangle next = new Rectangle(x, y, tankWidth, tankHeight);
        for(Wall wall : wallArrayList) {
            if(next.intersects(wall.getRec())) {
                return true;
            }
        }
        return false;
    }

    //wall边界和tank的碰撞检测
    public boolean reachBorder(int x, int y) {
        //出界返回true
        ArrayList<Wall> borderArrayList = this.gamePanel.borderWallList;
        Rectangle next = new Rectangle(x, y, tankWidth, tankHeight);
        for(Wall wall : borderArrayList) {
            if(next.intersects(wall.getRec())) {
                return true;
            }
        }
        return false;
    }

    public void setImage(String image) {
        this.image = Toolkit.getDefaultToolkit().getImage(image);
    }

    @Override
    public abstract void paintSelf(Graphics graphics);
    @Override
    public abstract Rectangle getRec();
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JFrame {
    int winWidth = 650;
    int winHeight = 450;
    Image select = Toolkit.getDefaultToolkit().getImage("images/myTank/my-r.png");
    //双缓存图片解决屏幕闪烁问题
    Image offScreenImage = null;
    /*
     * 游戏状态
     * state: 0, 初始状态;
     * state: 1, 游戏开始;
     * state: 2, 游戏失败;
     * state: 3, 游戏胜利;
     */
    int state = 0;
    //控制添加敌军的速递
    int pCount = 0;
    //控制添加敌军的数量
    int enemyCount = 0;
    //游戏内墙的横纵坐标
    static int[] wallPositionX = {
            75, 125, 75, 75, 75, 250, 250, 250,
            300, 350, 350, 350, 450, 450, 500, 500,
            500, 250, 300, 350, 250, 350, 300
    };
    static int[] wallPositionY = {
            75, 75, 175, 225, 275, 125, 175, 225,
            175, 125, 175, 225, 75, 125, 225, 275,
            325, 325, 325, 325, 375, 375, 25
    };
    //金属墙横纵坐标
    static int[] ironWallPositionX = {
            100, 300, 325, 475, 25, 400, 575, 125, 200, 500
    };
    static int[] ironWallPositionY = {
            25, 275, 275, 375, 250, 150, 275, 175, 325, 75
    };
    //敌军出生地
    static int[] enemyPositionX = {
            75, 25, 300, 350, 575
    };
    static int[] enemyPositionY = {
            125, 300, 75, 275, 325
    };
    //添加子弹列表
    ArrayList<Bullet> bulletArrayList = new ArrayList<>();
    //添加敌军列表
    ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    //删除的子弹列表
    ArrayList<Bullet> removeBulletList = new ArrayList<>();
    //添加玩家列表, 游戏只有一个玩家可以不用列表, 用列表为了可扩展性
    ArrayList<Tank> playerList = new ArrayList<>();
    //添加围墙列表
    ArrayList<Wall> wallArrayList = new ArrayList<>();
    //添加边框以及不可摧毁的墙, 用铁墙做围栏
    ArrayList<Wall> borderWallList = new ArrayList<>();
    //添加基地列表
    ArrayList<Base> baseArrayList = new ArrayList<>();
    //添加爆炸元素列表
    ArrayList<Explode> explodeArrayList = new ArrayList<>();
    //定义玩家tank, 初始位置: x - 125, y - 390
    Player player = new Player("images/myTank/my-u.png", 125, 390, this,
            "images/myTank/my-u.png", "images/myTank/my-l.png",
            "images/myTank/my-r.png", "images/myTank/my-d.png");
    //添加基地
    Base base = new Base("images/utils/base.png", 300, 375, this);
    //添加草坪背景
    Image backgroundImg = Toolkit.getDefaultToolkit().getImage("images/utils/background.jpg");
    public void loadPanel() {
        //窗口标题
        setTitle("My BATTLE CITY For 2022Fall Java");
        //窗口大小
        setSize(winWidth, winHeight);
        //屏幕居中，方便进行游戏
        setLocationRelativeTo(null);
        //关闭事件
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //固定窗口大小
        setResizable(false);
        //窗口可见
        setVisible(true);
        //添加键盘事件监视器
        this.addKeyListener(new GamePanel.KeyMonitor());
        //添加border
        for(int i = 0; i < 9; i++) {
            borderWallList.add(
                    new Wall("images/wall/borderWall-v.png",
                            0, i * 50, this, false, 25, 50)
            );
        }
        for(int i = 0; i < 9; i++) {
            borderWallList.add(
                    new Wall("images/wall/borderWall-v.png",
                            this.getWidth() - 25, i * 50, this, false, 25 ,50)
            );
        }
        for(int i = 0; i < 12; i++) {
            borderWallList.add(
                    new Wall("images/wall/borderWall-h.png",
                            i * 50 + 25, 0, this, false, 50, 25)
            );
        }
        for(int i = 0; i < 12; i++) {
            borderWallList.add(
                    new Wall("images/wall/borderWall-h.png",
                            i * 50 + 25, this.getHeight() - 25, this, false, 50, 25)
            );
        }
        //添加不可摧毁的墙
        for(int i = 0; i < 4; i++) {
            borderWallList.add(
                    new Wall("images/wall/ironWall-v.png", ironWallPositionX[i], ironWallPositionY[i], this, false, 25, 50)
            );
        }
        for(int i = 4; i < 10; i++) {
            borderWallList.add(
                    new Wall("images/wall/ironWall-h.png", ironWallPositionX[i], ironWallPositionY[i], this, false, 50, 25)
            );
        }
        //添加围墙元素
        for(int i = 0; i < 23; i++) {
            wallArrayList.add(
                    new Wall("images/wall/brickWall.png", wallPositionX[i], wallPositionY[i], this, true, 50, 50)
            );
        }
        //添加基地
        baseArrayList.add(base);

        while (true) {
            //游戏胜利判定
            if(baseArrayList.size() > 0 && enemyCount == 5 && enemyArrayList.size() == 0) {
                state = 3;
            }
//            游戏失败判定
            if(state == 1 && (playerList.size() == 0 || baseArrayList.size() == 0)) {
                state = 2;
            }
            if(state == 1 &&pCount % 100 == 0 && enemyCount < 5) {
                Random random = new Random();
                int randDirection = random.nextInt(4);
                String img = switch (randDirection) {
                    case 1 -> "images/enemyTank/enemy-u.png";
                    case 2 -> "images/enemyTank/enemy-r.png";
                    case 3 -> "images/enemyTank/enemy-d.png";
                    default -> "images/enemyTank/enemy-l.png";
                };

                //添加敌军
                enemyArrayList.add(new Enemy(img, enemyPositionX[enemyCount], enemyPositionY[enemyCount], this,
                        "images/enemyTank/enemy-u.png", "images/enemyTank/enemy-l.png",
                        "images/enemyTank/enemy-r.png", "images/enemyTank/enemy-d.png"));
                enemyCount++;
            }
            repaint();
            pCount++;
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //paint()
    @Override
    public void paint(Graphics graphics) {
        //创建和窗口一样大小的图片
        if(offScreenImage == null) {
            offScreenImage = this.createImage(winWidth, winHeight);
        }
        //获得画笔
        Graphics gImage = offScreenImage.getGraphics();
        //设置窗口背景颜色
//        gImage.setColor(Color.GRAY);
        gImage.fillRect(0,0, winWidth, winHeight);
        //设置首页字体颜色
        gImage.setColor(Color.CYAN);
        //设置首页显示字体以及内容
        gImage.setFont(new Font("微软雅黑", Font.BOLD, 30));
        if(state == 0) {
            Image bkgImg = Toolkit.getDefaultToolkit().getImage("images/utils/bkg.gif");
            gImage.drawImage(bkgImg, 125, 100, null);
            gImage.drawString("バトルシティー", 230, 300);
            gImage.drawString("JAVA 2022Fall坦克大战", 160, 350);
            gImage.setColor(Color.PINK);
            gImage.setFont(new Font("微软雅黑", Font.PLAIN, 20));
            gImage.drawString("按enter键开始游戏", 250, 400);
            gImage.setColor(Color.LIGHT_GRAY);
            gImage.setFont(new Font("微软雅黑", Font.PLAIN, 15));
            gImage.drawString("课程项目版权所有@左旭, 2020141010097", 195, 430);
        } else if (state == 1) {
            //gImage.drawString("Hello", 300, 400);
            gImage.drawImage(backgroundImg, 0, 0, null);
            //添加边框
            for(Wall wall : borderWallList) {
                wall.paintSelf(gImage);
            }
            //添加玩家
            for (Tank player : playerList) {
                player.paintSelf(gImage);
            }
//            player.paintSelf(gImage);
            //添加子弹
            for (Bullet bullet : bulletArrayList) {
                bullet.paintSelf(gImage);
            }
            bulletArrayList.removeAll(removeBulletList);
            //添加爆炸效果
            for (Explode explode : explodeArrayList) {
                explode.paintSelf(gImage);
            }
            //添加敌军
            for (Enemy enemy : enemyArrayList) {
                enemy.paintSelf(gImage);
            }
            //添加围墙
            for (Wall wall : wallArrayList) {
                wall.paintSelf(gImage);
            }
            //添加基地
            for (Base base : baseArrayList) {
                base.paintSelf(gImage);
            }
            //pCount++;
        } else if (state == 2) {
            Image gameoverImg = Toolkit.getDefaultToolkit().getImage("images/utils/gameover.png");
            gImage.drawImage(gameoverImg, 0, 0, null);
        } else if (state == 3) {
            gImage.drawString("Victory!", 280, 200);
        }
        //将图片加载到窗口中
        graphics.drawImage(offScreenImage, 0, 0, null);
    }

    //键盘事件监视器
    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            //System.out.println(keyEvent.getKeyChar());
            int key = keyEvent.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                playerList.add(player);
                player.alive = true;
                state = 1;
            } else {
                player.keyPressed(keyEvent);
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            player.keyReleased(keyEvent);
        }
    }
    //main
    public static void main(String[] args) {
        GamePanel winOfBattleCity = new GamePanel();
        winOfBattleCity.loadPanel();
    }


}

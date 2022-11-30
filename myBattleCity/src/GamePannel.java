import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePannel extends JFrame {
    int winWidth = 800;
    int winHeight = 600;
    Image select = Toolkit.getDefaultToolkit().getImage("images/myTank/my-r.png");
    //双缓存图片解决屏幕闪烁问题
    Image offScreenImage = null;
    //游戏状态
    int state = 0;
    //控制添加敌军的速递
    int paintCount = 0;
    //控制添加敌军的数量
    int enemyCount = 0;
    //添加子弹列表
    ArrayList<Bullet> bulletArrayList = new ArrayList<>();
    //添加敌军列表
    ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    //定义玩家tank
    Player player = new Player("images/myTank/my-u.png", 125, 510, this,
            "images/myTank/my-u.png", "images/myTank/my-l.png",
            "images/myTank/my-r.png", "images/myTank/my-d.png");

    /*定义敌军enemy
    Enemy enemy = new Enemy("images/enemyTank/enemy-l.png", 500, 110, this,
            "images/enemyTank/enemy-u.png", "images/enemyTank/enemy-l.png",
            "images/enemyTank/enemy-r.png", "images/enemyTank/enemy-d.png");

     */
    public void loadPannel() {
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
        this.addKeyListener(new GamePannel.KeyMonitor());

        while (true) {
            if(paintCount % 100 == 1 && enemyCount < 7) {
                Random random = new Random();
                int randX = random.nextInt(700);
                int randY = random.nextInt(400);
                int randDirection = random.nextInt(4);
                String img;
                switch (randDirection) {
                    case 1:
                        img = "images/enemyTank/enemy-u.png";
                        break;
                    case 2:
                        img = "images/enemyTank/enemy-r.png";
                        break;
                    case 3:
                        img = "images/enemyTank/enemy-d.png";
                        break;
                    default:
                        img = "images/enemyTank/enemy-l.png";
                        break;
                }
                //不断添加敌军
                enemyArrayList.add(new Enemy(img, randX, randY, this,
                        "images/enemyTank/enemy-u.png", "images/enemyTank/enemy-l.png",
                        "images/enemyTank/enemy-r.png", "images/enemyTank/enemy-d.png"));
                enemyCount++;
            }
            repaint();
            try {
                Thread.sleep(25);
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
        gImage.setColor(Color.GRAY);
        gImage.fillRect(0,0, winWidth, winHeight);
        //设置首页字体颜色
        gImage.setColor(Color.CYAN);
        //设置首页显示字体以及内容
        gImage.setFont(new Font("Menlo", Font.BOLD, 50));
        if(state == 0) {
            gImage.drawString("Welcome!", 280, 200);
            gImage.drawString("My BATTLE CITY", 200, 300);
            gImage.drawString("Start Game", 260, 400);
            //绘制指针
            gImage.drawImage(select, 180, 365,null);
        } else {
            gImage.drawString("Hello", 300, 400);

            //添加玩家
            player.paintSelf(gImage);
            //添加子弹
            for(Bullet bullet : bulletArrayList) {
                bullet.paintSelf(gImage);
            }
            //添加敌军
            for(Enemy enemy : enemyArrayList) {
                enemy.paintSelf(gImage);
            }

            paintCount++;
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
            switch (key) {
                /*case KeyEvent.VK_DOWN:
                    System.out.println("Down");
                    break;
                case KeyEvent.VK_UP:
                    System.out.println("Up");
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("Right");
                    break;
                case KeyEvent.VK_LEFT:
                    System.out.println("Left");
                    break;*/
                case KeyEvent.VK_ENTER:
                    state = 1;
                    break;
                default:
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
        GamePannel winOfBattleCity = new GamePannel();
        winOfBattleCity.loadPannel();
    }


}

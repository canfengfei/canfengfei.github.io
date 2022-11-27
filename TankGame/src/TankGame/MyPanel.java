package TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable {
    MyTank myTank;
    Vector<EnemyTank> enemyTank = new Vector<EnemyTank>();
    //Vector<Shot> enemyShot = new Vector<Shot>();
    private int enemyTankNum = 6;
    private Vector<Boom> boom = new Vector<Boom>();
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel() {
        System.out.println("重新开始输入：1, 继续上一局输入：2 \n你的选择是：");
        Scanner scanner = new Scanner(System.in);
        String key = scanner.next();
        File fTDP = new File(Record.getTankDataFile());
        if(fTDP.exists() == false && key.equals("2")){
            System.out.println("无法开始上一局");
            key = "1";
        }
        while(true) {
            if (key.equals("1")) {
                this.myTank = new MyTank(500, 100, 0);
                myTank.setMp1(this);
                for (int i = 0; i < enemyTankNum; i++) {
                    enemyTank.add(new EnemyTank(100 * (1 + i), 0, 1));
//            enemyTank.get(i).EnemyShotMT();
                    new Thread(enemyTank.get(i)).start();
                }
                break;
            } else if (key.equals("2")) {
                this.myTank = Record.getMyTank();
                this.enemyTank = Record.getEnemyTank();
                break;
            } else {
                System.out.println("请重新输入");
                System.out.println(key);
                key = scanner.next();
            }
        }
        Record.setTank(enemyTank,myTank);
        //路径问题说明：https://blog.csdn.net/zijikanwa/article/details/80571678
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/imageExplode1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/imageExplode2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/imageExplode3.png"));
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.fillRect(0,0,1000,750); //填充矩形，默认黑色。
        showIfo(g);
        //myTank.setSpeed(5);
        if(myTank.getLive()){
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect() ,myTank.type); //绘制我的坦克
        }

        for (int i = 0; i < enemyTank.size(); i++) {  //绘制敌方坦克
            //System.out.println("敌方坦克： " + i);
            if(!enemyTank.get(i).getLive()){
                //System.out.println("跳过敌方坦克");
                continue;
            }
            drawTank(enemyTank.get(i).getX(), enemyTank.get(i).getY(), g, enemyTank.get(i).getDirect() ,1);
        }

        for (int i = 0; i < myTank.shot.size(); i++) {//绘制我方子弹
            if(myTank.shot.get(i) != null && myTank.shot.get(i).getIsLive()){
                drawShot(g, myTank);
            }
        }

        for (int i = 0; i < enemyTank.size(); i++) { //绘制敌方子弹
            for (int j = 0; j < enemyTank.get(i).shot.size(); j++) {
                if(enemyTank.get(i).shot.get(j) != null &&enemyTank.get(i).shot.get(j).getIsLive()){ //绘制我方子弹
                    drawShot(g, enemyTank.get(i));
                }
            }
        }

        for (int i = 0; i < boom.size(); i++) {
            while(boom.get(i).isLive()) {

                boom.get(i).lifeDown();
                if (boom.get(i).getLife() < 0) {
                    boom.get(i).setLive(false);
                }
                else if(boom.get(i).getLife() < 12){
                    g.drawImage(image3, boom.get(i).getX(), boom.get(i).getY(), 60, 60, this );
                }
                else if(boom.get(i).getLife() < 18){
                    g.drawImage(image2, boom.get(i).getX(), boom.get(i).getY(), 60, 60, this );
//                    try {
//                        Thread.sleep(20);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                }
                else{
                    g.drawImage(image1, boom.get(i).getX(), boom.get(i).getY(), 60, 60, this );
//                    try {
//                        Thread.sleep(20);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                }

//                System.out.println("boom[" + i + "].Life():" + boom.get(i).getLife());
            }
        }
    }

    /**
     *
     * @param x         横坐标
     * @param y         纵坐标
     * @param g         画笔
     * @param direct    坦克方向
     * @param type      坦克类型
     */

    public void drawTank(int x, int y, Graphics g, int direct, int type){
        switch(type){
            case 0:
                g.setColor(Color.cyan); //我的坦克
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }

        switch(direct){
            case 0:  //表示向上
                g.fill3DRect(x, y, 10, 60, false); //画出坦克左轮
                g.fill3DRect(x + 30, y, 10, 60, false); //画出坦克右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false); //画出坦克身体
                g.fillOval(x + 10, y + 20, 20, 20); //画出坦克左轮
                g.drawLine(x + 20, y +30, x + 20, y);
                break;
            case 1:  //表示向右
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false); //画出坦克左轮
                g.fill3DRect(x + 30, y, 10, 60, false); //画出坦克右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false); //画出坦克身体
                g.fillOval(x + 10, y + 20, 20, 20); //画出坦克左轮
                g.drawLine(x + 20, y +30, x + 20, y + 60);
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
        }

    }

    public void drawShot(Graphics g,Tank tank){  //绘制子弹
        switch (tank.type){
            case 0:
                g.setColor(Color.cyan); //我的坦克
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }
        for (int i = 0; i < tank.shot.size(); i++) {
            g.fill3DRect(tank.shot.get(i).getX(),tank.shot.get(i).getY(),3,3,false);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!myTank.getLive()){
            return;
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            myTank.setDirect(0);
            if((myTank.OverlapJud(myTank))){
                return;
            }
            myTank.moveUp();
            if(!myTank.shotJudgePostion()){
                myTank.setY(0);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_D){

            myTank.setDirect(1);
            if((myTank.OverlapJud(myTank))){
                return;
            }
            myTank.moveRight();
            if(!myTank.shotJudgePostion()){
                myTank.setX(1000-60);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_S){
            myTank.setDirect(2);
            if((myTank.OverlapJud(myTank))){
                return;
            }
            myTank.moveDown();
            if(!myTank.shotJudgePostion()){
                myTank.setY(750-60);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_A){
            myTank.setDirect(3);
            if((myTank.OverlapJud(myTank))){
                return;
            }
            myTank.moveLeft();
            if(!myTank.shotJudgePostion()){
                myTank.setX(0);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_J){
            myTank.shotOppositeTank();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while(true){
            repaint();
            for (int j = 0; j < myTank.shot.size(); j++) { //我方子弹击中敌方坦克判定传参
                if(myTank.shot.get(j) != null && myTank.shot.get(j).getIsLive()){
                    for (int i = 0; i < enemyTank.size(); i++) {
                        if(enemyTank.get(i).getLive()) {
                            //System.out.println("进入判定");
                            hitTank(myTank.shot.get(j),enemyTank.get(i));
                            if(!enemyTank.get(i).getLive()){
                                enemyTank.remove(enemyTank.get(i));
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < enemyTank.size(); i++) { //敌方子弹击中我方判定传参
                for (int j = 0; j < enemyTank.get(i).shot.size(); j++) {
                    if(enemyTank.get(i).shot.get(j).getIsLive()){
                        hitTank(enemyTank.get(i).shot.get(j),myTank);
                    }
                }
            }


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public  void hitTank(Shot shot, Tank tank){ //判定是否击中，生成炸弹
        switch(tank.getDirect()){
            case 0:
            case 2:
                if(shot.getX() > tank.getX() && shot.getX() < tank.getX() + 40 && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 60){
                    tank.lifeReduce();
                    shot.setLive(false);
                    if(!tank.getLive()){
                        boom.add(new Boom(tank.getX(),tank.getY()));
                        if(tank!=myTank){
                            Record.allDestroyNum++;
                        }
                    }
                }
                    break;
            case 1:
            case 3:
                if(shot.getX() > tank.getX() && shot.getX() < tank.getX() + 60 && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 40){
                    tank.lifeReduce();
                    shot.setLive(false);
                    if(!tank.getLive()){
                        boom.add(new Boom(tank.getX(),tank.getY()));
                        if(tank!=myTank){
                            Record.allDestroyNum++;
                        }
                    }
                    //enemyTank.remove(enemySingleTank);
                }
                    break;
        }
    }

    public Vector<EnemyTank> getEnemyTank() {
        return enemyTank;
    }

    public MyTank getMyTank() {
        return myTank;
    }

    public void showIfo(Graphics g){
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累计摧毁的坦克数", 1020, 30);
        drawTank(1020, 60, g, 0, 1);
        g.setColor(Color.black);
        g.drawString(Record.allDestroyNum + "" , 1080, 100);
    }
}


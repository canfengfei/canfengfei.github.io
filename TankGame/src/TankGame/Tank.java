package TankGame;

import java.io.Serializable;
import java.util.Vector;

public class Tank implements Serializable {
    private int x,x1;
    private int y,y1;
    Vector<Shot> shot = new Vector<>();
    private int shotMyNum = 3;
    private int shotEneNum =1;
    private int speed = 3;
    private int direct = 0;
    private Boolean isLive = true;
    static MyPanel mp1 = null;
    int type;
    private int life;
    private  int myLife = 10;
    private  int enemyLife = 1;

    public void moveUp(){
        y -= speed;
        X1Y1Update();
    }
    public void moveRight(){
        x += speed;
        X1Y1Update();
    }
    public void moveDown(){
        y += speed;
        X1Y1Update();
    }
    public void moveLeft(){
        x -= speed;
        X1Y1Update();
    }

    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }

    public void shotOppositeTank(){  //设置子弹的横纵坐标、方向
        //System.out.println("shot.size: " + shot.size());
        for (int i = 0; i < shot.size(); i++) {
            if(!shot.get(i).getIsLive()){
                shot.remove(i);
                //System.out.println("remove启用");
            }

        }

        switch(type){
            case 0:
                if(shot.size() == shotMyNum){
                    return;
                }
                break;
            case 1:
                if(shot.size() == shotEneNum){
                    return;
                }
                break;
        }

        switch (getDirect()){
            case 0:
                shot.add(new Shot(getX() + 20, getY(), 0));
                break;
            case 1:
                shot.add(new Shot(getX() + 60, getY() + 20, 1));
                break;
            case 2:
                shot.add(new Shot(getX() + 20, getY() + 60, 2));
                break;
            case 3:
                shot.add(new Shot(getX() , getY() + 20, 3));
                break;
        }
        new Thread(shot.get(shot.size()-1)).start();
        //System.out.println(shot);
    }

    public boolean shotJudgePostion(){
        if(x >= 0 && x + 60 <= 1000 && y >= 0 && y + 60 <= 750){
            return  true;
        }
        return false;
    }

    public Tank(int x, int y,int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        switch (type){
            case 0:
                this.life = myLife;
                break;
            case 1:
                this.life = enemyLife;
                break;
        }
        X1Y1Update();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        X1Y1Update();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        X1Y1Update();
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
        X1Y1Update();
    }

    public void lifeReduce(){
        if(life>0){
            life--;
        }

        if(life<1){
            isLive =false;
        }
    }

    public void X1Y1Update(){
        switch(direct){
            case 0:
            case 2:
                x1 = x + 40;
                y1 = y + 60;
                break;
            case 1:
            case 3:
                x1 = x + 60;
                y1 = y + 40;
                break;
        }
    }

    public  void setMp1(MyPanel mp1){
        this.mp1 = mp1;
    }
    public  Boolean OverlapJud(Tank t1){ //判断坦克移动后是否重叠
        if(mp1 == null){
            return false;
        }
        int xt = 0,xt1 = 0,yt = 0,yt1 = 0;
        switch(t1.direct){
            case 0:
                xt = t1.x;
                xt1 = t1.x1;
                yt = t1.y -3;
                yt1 = t1.y1 -3;
                break;
            case 1:
                xt = t1.x + 3;
                xt1 = t1.x1 + 3;
                yt = t1.y;
                yt1 = t1.y1;
                break;
            case 2:
                xt = t1.x;
                xt1 = t1.x1;
                yt = t1.y + 3;
                yt1 = t1.y1 + 3;
                break;
            case 3:
                xt = t1.x - 3;
                xt1 = t1.x1 - 3;
                yt = t1.y;
                yt1 = t1.y1;
                break;
        }

        Vector<EnemyTank> eTmp = mp1.getEnemyTank();
        Tank t2 = mp1.getMyTank();
        for (int i = 0; i <= eTmp.size(); i++) {
            if(t2 == t1){
                if(i<eTmp.size()) {
                    t2 = eTmp.get(i);
                }
                continue;
            }
            if(((xt >= t2.x && xt <= t2.x1) || (xt1 >= t2.x && xt1 <= t2.x1)) && ((yt >= t2.y && yt <= t2.y1) || (yt1 >= t2.y && yt1 <= t2.y1))){
                return true;
            }
            if(i < eTmp.size()){
                t2 = eTmp.get(i);
            }
        }

        return false;
    }
}

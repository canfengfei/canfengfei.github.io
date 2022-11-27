package TankGame;

public class EnemyTank extends Tank implements Runnable{


    public EnemyTank(int x, int y, int type) {
        super(x, y, type);
        super.setDirect(2);
    }




//    public void EnemyShotMT(){
//        switch(getDirect()){
//            case 0:
//                shot = new Shot(getX() + 20, getY(), 0);
//                break;
//            case 1:
//                shot = new Shot(getX() + 60, getY() + 20, 1);
//                break;
//            case 2:
//                shot = new Shot(getX() + 20, getY() + 60, 2);
//                break;
//            case 3:
//                shot = new Shot(getX() , getY() + 20, 3);
//                break;
//        }
//
//
//        //System.out.println(shot.getX());
//        new Thread(shot).start();
//    }

    public void enemyTankMove(){
        switch (getDirect()){
            case 0:
                for (int i = 0; i < (int)(Math.random() * 100) + 50; i++) {
                    if(OverlapJud(this)){
                        break;
                    }
                    shotOppositeTank();
                    moveUp();
                    if(!shotJudgePostion()){
                        setY(0);
                        break;
                    }
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case 1:
                for (int i = 0; i < (int)(Math.random() * 100) + 50; i++) {
                    if(OverlapJud(this)){
                        break;
                    }
                    shotOppositeTank();
                    moveRight();
                    if(!shotJudgePostion()){
                        setX(1000-60);
                        break;
                    }
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < (int)(Math.random() * 100) + 50; i++) {
                    if(OverlapJud(this)){
                        break;
                    }
                    shotOppositeTank();
                    moveDown();
                    if(!shotJudgePostion()){
                        setY(750-60);
                        break;
                    }
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case 3:
                for (int i = 0; i < (int)(Math.random() * 100) + 50; i++) {
                    if(OverlapJud(this)){
                        break;
                    }
                    shotOppositeTank();
                    moveLeft();
                    if(!shotJudgePostion()){
                        setX(0);
                        break;
                    }
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
        }
        setDirect((int)(Math.random()*4));
    }

    @Override
    public void run() {
        while(true){
            //System.out.println("label1");
            enemyTankMove();
            if(getLive() == false){
                break;
            }
        }
    }
}


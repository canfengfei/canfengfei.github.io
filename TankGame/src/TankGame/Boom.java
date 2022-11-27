package TankGame;

public class Boom {
    private int x;
    private int y;
    private int life = 27;
    private boolean isLive = true;
    public void lifeDown(){
            life--;
    }

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLife() {
        return life;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}

package TankGame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 开始游戏
 */
public class TankGameStart extends JFrame {
    MyPanel mp;

    public static void main(String[] args) {
        TankGameStart tankGame = new TankGameStart();
    }

    public TankGameStart(){
        this.mp = new MyPanel();
        new Thread(mp).start();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1300,798); //设置画框大小，应该和矩形背景一样。
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Record.keepRecord();
                System.exit(0);
            }
        });

    }

}

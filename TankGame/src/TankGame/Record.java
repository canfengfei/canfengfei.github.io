package TankGame;

import java.io.*;
import java.util.Vector;


public class Record {
    static int allDestroyNum = 0;
    private static String killDataPath = "killDataPath.txt";
    private static String tankDataPath = "tankDataPath";
    private static Vector<EnemyTank> enemyTanks = null;
    private static MyTank myTank;
//    private FileReader fr = null;


    public static void keepRecord(){
        BufferedWriter fw = null;
        ObjectOutputStream fw1 = null;
        try {
            fw = new BufferedWriter(new FileWriter(killDataPath));
            fw1 = new ObjectOutputStream(new FileOutputStream(tankDataPath));

            fw.write(allDestroyNum +"");
            fw1.writeObject(myTank);
            if(enemyTanks!=null){
                for (int i = 0; i < enemyTanks.size(); i++) {
                    if(enemyTanks.get(i).getLive()==true){
                        fw1.writeObject(enemyTanks.get(i));
                    }
                }
            }
            fw1.writeObject(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if(fw!=null){
                    fw.close();
                }
                if(fw1!=null){
                    fw1.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static MyTank getMyTank(){
        ObjectInputStream fr1 = null;
        BufferedReader fr = null;
        MyTank tMT;
        try {
            fr = new BufferedReader(new FileReader(killDataPath));
            allDestroyNum = Integer.parseInt(fr.readLine());
            fr1 = new ObjectInputStream(new FileInputStream(tankDataPath));
            tMT =  (MyTank)(fr1.readObject());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(fr1!=null){
                try {
                    fr1.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (int i = 0; i < tMT.shot.size(); i++) {
            new Thread(tMT.shot.get(i)).start();
        }
        return tMT;
    }
    public static Vector<EnemyTank> getEnemyTank(){
        ObjectInputStream fr2 = null;
        Vector<EnemyTank> enemyTank = new Vector<EnemyTank>();
        EnemyTank tET;
        try {
            fr2 = new ObjectInputStream(new FileInputStream(tankDataPath));
            fr2.readObject();
            while((tET = (EnemyTank)fr2.readObject())!=null){
                enemyTank.add(tET);
                new Thread(tET).start();
                for (int i = 0; i < tET.shot.size(); i++) {
                    new Thread(tET.shot.get(i)).start();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(fr2!=null){
                try {
                    fr2.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return enemyTank;
    }
    public static void setTank(Vector<EnemyTank> enemyTanks, MyTank myTank){
        Record.enemyTanks = enemyTanks;
        Record.myTank = myTank;
    }
    public static String getTankDataFile(){
        return "tankDataPath";
    }
}

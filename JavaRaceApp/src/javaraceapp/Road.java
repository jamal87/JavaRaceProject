/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaraceapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics; /*Класс Image*/
import java.awt.Graphics2D; /*Класс Image*/
import java.awt.Image;
import java.awt.event.ActionEvent; /*класс исп. для таймера*/
import java.awt.event.ActionListener; /*интерфейс исп. для таймера*/
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger; 
import javax.swing.ImageIcon; /*Класс ImageIcon*/
import javax.swing.JOptionPane;
import javax.swing.JPanel; /*Класс Panel*/
import javax.swing.Timer; /*класс таймера*/

/**
 *
 * @author jamal
 */

/**
 * Класс дороги, расширяет класс JPanel, так
 * как, чтобы что-то поместить во фрейм надо это поместить
 * в панель
 * Так же для таймера реализуем интерфейс ActionListener
 * для работы с потоками реализуем интерфейс Runnable
 */
public class Road extends JPanel implements ActionListener{
    // данные поля нужны для GenericThread, так как нам туда надо передать тип своего класса
    // не знал как сделать по другому, поэтому создаю фиктивные объекты    
    // список врагов        
    private volatile List<Enemy> enem;
    private volatile List<Coins> coin;
    private final List<Image> lifeImg;    
    // поток врагов, который будет создаваться рандомно
    // сюда передаем интерфейс, который реализует раннабле
    //Thread enemies = new Thread(new EnemyThread());
    //Thread coins = new Thread(new CoinsThread());    
    private final Thread enemies;
    private final Thread coins;
    // создаем таймер
    private final Timer mTimer;
    // создаем рисунок
    //.getClassLoader(). "javaraceapp/Res/road.png"    
    private final Image img;
    // создаем машину
    protected final Player p;
    /**
     * Конструктор класса
     */
    public Road(){                        
        lifeImg = new LinkedList<>(Arrays.asList(new ImageIcon(getClass().getResource("Res/car_7989.png")).getImage(), 
                                                 new ImageIcon(getClass().getResource("Res/car_7989.png")).getImage(),
                                                 new ImageIcon(getClass().getResource("Res/car_7989.png")).getImage()));
        coin = new LinkedList<>();
        enem = new LinkedList<>();
        p = new Player(this);
        img = new ImageIcon(getClass().getResource("Res/road.png")).getImage();
        mTimer = new Timer(20, this);
        enemies = new Thread(new GenericThread<>(enem, new EnemyCreator()));
        coins = new Thread(new GenericThread<>(coin, new CoinsCreator()));
        mTimer.start();
        //стартуем поток врагов
        enemies.start();
        coins.start();         
        // регистрируем слушателя событий
        addKeyListener(new MyKeyAdapter());
        // фокусируемся на фрейм
        setFocusable(true);        
    } 
    // методы класса
    private void testCollisionWithEnemies() {
        Iterator<Enemy> i = enem.iterator();        
        //бежим по итератору пока есть враги
        while(i.hasNext()){
            // получаем врага
            Enemy e = i.next();                         
            // проверка столкновения
            // intersect принимает второй прямоугольник
            // и проверяет на их пересичение
            if(p.getRect().intersects(e.getRect())){                
                if ((Player.CNT_LIFE == 0)) {
                    JOptionPane.showMessageDialog(null, "Game over");
                    System.exit(0);
                } else if (!e.isCollisioned()){                                        
                    Player.CNT_LIFE--;                                                            
                    lifeImg.remove(Player.CNT_LIFE);
                    e.setCollision(true);                    
                    e.img = new ImageIcon(getClass().getResource("Res/exp.gif")).getImage();
                    e.v = 0;
                    break;
                }                
            }
        }        
    }
    
    private void testBulletCollision(){
        Iterator<Bullet> i = p.bullet.iterator();
        Iterator<Enemy> j = enem.iterator();        
        while(i.hasNext()){
            Bullet b = i.next();            
            while(j.hasNext()){
                Enemy e = j.next();
                if(b.getRect().intersects(e.getRect())&&!e.isCollisioned()){                                    
                    e.setCollision(true);
                    e.img = new ImageIcon(getClass().getResource("Res/exp.gif")).getImage();
                    e.v = 0;
                    i.remove();
                }
            }
        }
    }
    
    private void coinsCollect() {
        Iterator<Coins> i = coin.iterator();        
        while(i.hasNext()){
            Coins c = i.next();                                    
            if(p.getRect().intersects(c.getRect())){
               if (c.isGolden()){
                   Player.CNT_COINS+=10;
               } else {
                   Player.CNT_COINS++;                   
               }
               i.remove();
            }            
        }        
    }
    
    private void drawObjects(Iterator i, Graphics2D g){
        while(i.hasNext()){            
            MyObjects e = (MyObjects)i.next();
            // если враг или монетка уползли за пределы экрана - удаляем его
            if(e.x>=1500||e.x<=-1500){
                i.remove();
            } else {
                e.move();
                g.drawImage(e.img, e.x, e.y, null);
            }            
        }                
    }          
    /**
     * 
     * @param g контекст графики   
     */
    //переопределение метода paint у JPanel, который вызывается автоматически
    // можно вызвать самому
    @Override
    public void paint(Graphics g){
        //так как сам графикс рисовать не умеет, то делаем приведение типов
        g = (Graphics2D)g;
        // рисуем дорогу со смещением слоя, которое указывается в классе машины (эффект движения)
        g.drawImage(img, p.layer1, 0, null);
        // рисуем вторую дорогу
        g.drawImage(img, p.layer2, 0, null);
        // рисуем машину с ее координатами (она стоит на месте)
        g.drawImage(p.img, p.x, p.y, null);
        
        double v = (300.0/Player.MAX_V)*p.v; // км/ч в пикселе
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.drawString("Speed: "+v+" km\\h", 700, 30);
        g.drawString("Coins: "+Player.CNT_COINS, 400, 30);
       
        Iterator<Image> k = lifeImg.iterator();
        int cnt = 0;
        while(k.hasNext()){            
            Image t = k.next();            
            g.drawImage(t, 0+(cnt*20), 10, null);                        
            cnt++;
        }        
        //рисуем врагов и монетки
        Iterator<Enemy> i = enem.iterator();
        Iterator<Coins> j = coin.iterator();
        Iterator<Bullet> b = p.bullet.iterator();
        //бежим по итератору пока есть враги или монетки
        drawObjects(i, (Graphics2D)g);
        drawObjects(j, (Graphics2D)g);        
        drawObjects(b, (Graphics2D)g);
    }    
     
    // реализуем интерфейс для таймера 
    @Override
    public void actionPerformed(ActionEvent e){
        // дивгаемся
        p.move();
        // вызов метода paint
        repaint();
        // проверка столконовений
        testBulletCollision();
        testCollisionWithEnemies();
        coinsCollect();
    }
    // внутренние классы
    /**
    * <H1>Нажатие клавиш </H1>  
    * либо можно использовать интерфейс KeyListener, 
    * и реализуем все методы. Если используем адаптер, то он реализует 
    * эти методы с пустым телом методов, далее у себя можем переопределить методы
    * какие нам надо и как нам надо
    */
    private class MyKeyAdapter extends KeyAdapter{
        //KeyEven - какую клавишу нажимали        
        // нажатие клавиши        
        @Override
        public void keyPressed(KeyEvent e){
            p.keyPressed(e);
        };
        // клавишу отжали        
        @Override
        public void keyReleased(KeyEvent e){
            p.keyReleased(e);
        };
    }
    
    /**
    * парарлелльный запуск программы
    * переделал на generic
    */
    private class GenericThread <T extends MyObjects> implements Runnable {
        private final List<T> ob;
        private final ObjCreator<T> creator;
        private boolean isGold = false;        
        GenericThread(List<T> ob, ObjCreator creator){
            this.ob = ob;
            this.creator = creator;
        };
        
        @Override        
        public void run(){
            while(true){
                Random rand = new Random();
                //засыпаем поток врогов или монеток на несколько милисекунд
                try {
                    Thread.sleep(rand.nextInt(2000));                     
                    // создаем врагов, монетки и прочее если что
                    isGold = rand.nextInt(10) == 1;                                        
                    T object = creator.createObject(1100, rand.nextInt(400), rand.nextInt(50), Road.this, isGold);
                    synchronized(ob){ ob.add(object); }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Road.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
    }        
}

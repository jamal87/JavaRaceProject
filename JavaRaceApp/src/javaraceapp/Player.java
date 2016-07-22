/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaraceapp;

import java.awt.Image; /*Класс Image*/
import java.awt.Rectangle;
import java.awt.event.KeyEvent; /* ивенты */
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
//import javax.swing.JOptionPane; /* диалоги */

/**
 *
 * @author jamal
 * пускай просто реализуем интерфейс, а не наследуемся от класса
 * сделано сознательно
 */
public class Player implements IMove {
    /**
     * Объявление полей
    */ 
    public static int CNT_COINS = 0;
    public static int CNT_LIFE = 3;
    List<Bullet> bullet = new ArrayList<>();    
    Road road;
    // рисунок    
    Image img_main = new ImageIcon(getClass().getResource("Res/straight.jpg")).getImage();
    Image img_up = new ImageIcon(getClass().getResource("Res/left.jpg")).getImage();
    Image img_down = new ImageIcon(getClass().getResource("Res/right.jpg")).getImage();
    Image img = img_main;    
    // поле скорости
    int v = 0;
    // поле ускорения
    int dv = 0;
    int dy = 0;
    // путь
    int s = 0;
    // слой бэкграунда
    int layer1 = 0;
    // второй слой бэкграунда
    int layer2 = 900;
    
    // координаты машины
    int x = 30;
    int y = 200;        

    public Player(Road road) {        
        this.road = road;
    }
    
    // движение 
    @Override
    public void move(){
        // накапливаем расстояние
        s+=v;
        v+=dv;
        if (v<=0){
            v=0;
        } else if (v>=MAX_V){
            v=MAX_V;
        }
        y-=dy;
        if (y<=MAX_TOP){
            y = MAX_TOP;
        } else if (MAX_BOTTOM< y){
            y = MAX_BOTTOM;
        }
        // создаем иллюзию движения машины сдвигая слой назад
        if (layer2 - v <= 0){ 
            // если координата второго слоя стала равной нулю, 
            //  то обнуляем координаты слоев для смещения
            // и воспроизводим опять первый слой
            layer1 = 0;
            layer2 = 1000;
        } else {
            layer1 -= v;
            layer2 -= v;
        }
    }
    // реализация нажатия клавиши
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (key ==  KeyEvent.VK_RIGHT){
            // вывод диалога
            //JOptionPane.showMessageDialog(null, "pressed");
            //v +=1;
            dv = 3;
        }else if (key ==  KeyEvent.VK_LEFT &&  v>1){
            //v -=1;
            dv = -2;
        } else if (key ==  KeyEvent.VK_UP ){
            //v -=1;
            img = img_up;
            dy = 10;
        } else if (key ==  KeyEvent.VK_DOWN ){
            //v -=1;
            img = img_down;
            dy = -10;
        } else if (key ==  KeyEvent.VK_SPACE){
            bullet.add(new Bullet(x, y, road));
        }
    }
    // реализация отжатия клавиши
    public void keyReleased(KeyEvent e){
        // вывод диалога
        //JOptionPane.showMessageDialog(null, "released");
        int key = e.getKeyCode();        
        if (key ==  KeyEvent.VK_RIGHT||key == KeyEvent.VK_LEFT){            
            //JOptionPane.showMessageDialog(null, "pressed");            
            dv = 0;            
        } else if (key==KeyEvent.VK_UP||key==KeyEvent.VK_DOWN){
            dy = 0;
            img = img_main;
        }        
    }
    
    public Rectangle getRect(){
        return new Rectangle(x,y,img.getWidth(null), img.getHeight(null));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaraceapp;

import javax.swing.ImageIcon;

/**
 *
 * @author jamal
 */
public class Enemy extends MyObjects {   
    protected int v;
    private boolean isCollision;
    // создаем объект врага
    public Enemy(int x, int y, int v, Road road){        
        this.x = x;
        this.y = y;
        this.v = v;
        isCollision = false;
        img = new ImageIcon(getClass().getResource("Res/enemy.jpg")).getImage();
        this.road = road;        
    }
    /**
     * реализация интерфейся IMove
     * описываем движение врагов
     */   
    @Override
    public void move(){
        x = x-road.p.v + v;
        if (y <= MAX_TOP){
            y = MAX_TOP;
        } else if (y > MAX_BOTTOM){
            y = MAX_BOTTOM;
        }
    }
    
    public void setCollision(boolean collis){
        isCollision = collis;
    }
    
    public boolean isCollisioned(){
        return isCollision;
    }
}

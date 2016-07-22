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
public class Coins extends MyObjects {    
    private boolean isGold = false;    
    // создаем объект монеток
    public Coins(int x, int y, Road road, boolean isGold){
        this.x = x;
        this.y = y;        
        this.isGold = isGold;
        if (isGold){
            img = new ImageIcon(getClass().getResource("Res/0001.gif")).getImage();            
        } else {
            img = new ImageIcon(getClass().getResource("Res/animated-coin-image-0006.gif")).getImage();            
        }
        this.road = road;
    }
    //описываем движение монеток

    /**
     * реализация интерфейся IMove
     */
    @Override
    public void move(){
        x = x-road.p.v;
        if (y <= MAX_TOP){
            y = MAX_TOP;
        } else if (y > MAX_BOTTOM){
            y = MAX_BOTTOM;
        }        
    }     
    
    public boolean isGolden(){
        return isGold;
    }
}

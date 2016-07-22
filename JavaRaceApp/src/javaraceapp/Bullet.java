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
public class Bullet extends MyObjects{
    
    public Bullet(int x, int y, Road road){
        this.x = x+120;
        this.y = y;
        this.road = road;
        img = new ImageIcon(getClass().getResource("Res/bul1.png")).getImage();
    };    
    @Override
    public void move(){        
        x = x+20;        
    }
}

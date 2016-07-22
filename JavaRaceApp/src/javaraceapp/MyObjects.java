/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaraceapp;

import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author jamal
 */
public abstract class MyObjects implements IMove {
    public int x,y;
    public Image img;
    public Road road;
    
    //метод, который возвращает текущие координаты объекта
    public Rectangle getRect(){
        return new Rectangle(x,y,img.getWidth(road), img.getHeight(road));
    }  
}

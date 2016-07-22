/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaraceapp;

/**
 *
 * @author jamal
 * пробуем интерфейсы. Не запихнул сознательно в абстрактный класс
 */
public interface IMove {
    public static final int MAX_V = 120;
    public static final int MAX_TOP = 70;    
    public static final int MAX_BOTTOM = 400;
    public void move();
}

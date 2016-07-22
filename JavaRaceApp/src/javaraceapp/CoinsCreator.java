/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaraceapp;

/**
 *
 * @author jamal
 * фабрика монет
 */
public class CoinsCreator implements ObjCreator{
    @Override
    public MyObjects createObject(int x, int y, int v, Road road, boolean isGold){
        return new Coins(x,y,road,isGold);
    }   
}

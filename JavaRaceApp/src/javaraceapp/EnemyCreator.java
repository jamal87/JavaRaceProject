/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaraceapp;

/**
 *
 * @author jamal
 * фабрика противников
 */
public class EnemyCreator implements ObjCreator {
    @Override
    public MyObjects createObject(int x, int y, int v, Road road, boolean isGold){
        return new Enemy(x,y,v,road);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaraceapp;

/**
 *
 * @author jamal
 * @param <T>
 * интерфейс для фабрики классов
 */
public interface ObjCreator <T> {
    public T createObject(int x, int y, int v, Road road, boolean isGold);
}

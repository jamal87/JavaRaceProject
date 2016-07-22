/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaraceapp;
// импорт swing для созданя фрейма
import javax.swing.*;

/**
 *
 * @author jamal
 */
public class JavaRaceApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // create the frame with name
        JFrame f = new JFrame("Java Racing");
        // Выход из приложения по нажатию крестика (по умолчанию не выходит)
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // устанавливаем размер фрейма
        f.setSize(900,500);
        // предварительно рисуем дорогу, создав объект класса Road
        f.add(new Road());
        // Отображаем фрейм        
        f.setVisible(true);        
    }
    
}

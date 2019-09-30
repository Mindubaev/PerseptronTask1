/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tradeline;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artur
 */
public class App {
    
    public static void main(String[] args) {try {
        File file =new File("src/main/resources/TestData.txt");
        TradeLine tradeLine=new TradeLine(file, 7);
        Perceptron perceptron=new Perceptron(tradeLine);
        perceptron.test();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
                
    }
    
}

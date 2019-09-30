/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tradeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Artur
 */
public class TradeLine {
    
    public List<double[]> statData=new ArrayList<>();
    public List<double[]> tradeLineData=new ArrayList<>();
    public double a;
    public double b;

    public TradeLine(File file,int columnNum) throws FileNotFoundException, IOException, Exception {
        this.statData=extarctStatisticsFromFile(file, columnNum);
        this.tradeLineData=builtTradeLine(statData);
    }
    
    private List<double[]> extarctStatisticsFromFile(File file,int columnNum) throws FileNotFoundException, IOException{
        FileReader reader=new FileReader(file);
        BufferedReader bufferedReader=new BufferedReader(reader);
        int dayCounter=0;
        double[] point;
        List<double[]> statData=new ArrayList<>();
        while (bufferedReader.ready())
        {
            point=new double[2];
            dayCounter++;
            String[] str=bufferedReader.readLine().split(",");
            if (columnNum>str.length)
                throw new IOException("неверно указан номер калонки");
            point[0]=dayCounter;
            point[1]=Double.parseDouble(str[columnNum]);
            statData.add(point);
        }
        return statData;
    }
    
    private List<double[]> builtTradeLine(List<double[]> statData) throws Exception{
        double averX=0;
        double averY=0;
        double averXY=0;
        double averPowX=0;
        for (double[] point:statData)
        {
            averX+=point[0];
            averY+=point[1];
            averXY+=point[0]*point[1];
            averPowX+=Math.pow(point[0], 2);
        }
        int size=statData.size();
        if (averX>0)
            averX=averX/size;
        if (averY>0)
            averY=averY/size;
        if (averXY>0)
            averXY=averXY/size;
        if (averPowX-Math.pow(averX, 2)!=0)
        {
            b=(averXY-averX*averY)/(averPowX-Math.pow(averX, 2));
            a=averY-b*averX;
            List<double[]> linePoints=new ArrayList<>();
            for (double[] point:statData)
            {
                double[] newPoint=new double[2];
                newPoint[0]=point[0];
                newPoint[1]=a+b*point[0];
                linePoints.add(newPoint);
            }
            return linePoints;
        }
        else
            throw new Exception("некорректные входные данные, невозможно построить линию трендов");
    }
    
    public double[][] getStaticticForNeuroStuding(){
        double[][] studingData=new double[statData.size()][3];
        int i=0;
        for (double[] point:statData)
        {
            studingData[i][0]=point[0];
            studingData[i][1]=point[1];
            if (point[1]>=tradeLineData.get(i)[1])
                studingData[i][2]=1;
            else
                studingData[i][2]=0;
            i++;
        }
        return studingData;
    }
    
}

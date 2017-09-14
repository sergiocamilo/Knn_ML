/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

/**
 *
 * @author sergiofernandez
 */
public class Iris 
{
    public double sepalLength;
    public double sepalWidth;
    public double petalLength;
    public double petalWidth;
    public int variety;

    public Iris() 
    {  
    }

    public Iris(double sepalLength, double sepalWidth, double petalLength, 
            double petalWidth, int variety) 
    {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.variety = variety;
    }

    @Override
    public String toString() {
        return "Iris{" + "sepalLength=" + sepalLength + ", sepalWidth=" + sepalWidth + ", petalLength=" + petalLength + ", petalWidth=" + petalWidth + ", variety=" + variety + '}';
    }
    
    
    
    
}

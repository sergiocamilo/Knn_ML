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
public class Distance {

    public int index;
    public double distance;
    public int variety; 
    
    public Distance() 
    {
        
    }

    public Distance(double distance, int variety) {
        this.distance = distance;
        this.variety = variety;
    }

    public Distance(int index, double distance, int variety) {
        this.index = index;
        this.distance = distance;
        this.variety = variety;
    }

    @Override
    public String toString() {
        return "Distance{" + "index=" + index + ", distance=" + distance + ", variety=" + variety + '}';
    }
    
    
    
    
    
    
}

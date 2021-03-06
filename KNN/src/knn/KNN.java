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
public class KNN {

    public int k;
    public Iris[] train;
    public Iris[] test;
    
    public Iris[] resultTest;

    void Classify() {
        double distance;
        Distance[] shortDist = new Distance[k];
        

        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < train.length; j++) {
                distance = Distance(test[i], train[j]);

                if (j < k) {
                    shortDist[j] = new Distance(j,distance, train[j].variety);
                } else {
                    
                    for (int l = 0; l < k; l++) {
                        if (shortDist[l].distance > distance) {
                            shortDist[l].index = j;
                            shortDist[l].distance = distance;
                            shortDist[l].variety = train[j].variety;
                            break;
                        }
                    }
                    
                }

            }
            classifyVote(i, shortDist);
            
        }
    }

    private double Distance(Iris i1, Iris i2) {
        //i1 train
        //i2 test
        
        
        return Math.sqrt(Math.pow((i1.petalLength - i2.petalLength), 2)
                + Math.pow((i1.petalWidth - i2.petalWidth), 2)
                + Math.pow((i1.sepalLength - i2.sepalLength), 2)
                + Math.pow((i1.sepalWidth - i2.sepalWidth), 2));
    }

    private void classifyVote(int i, Distance[] shortDist) {

        int countSet = 0; 
        int countVir = 0; 
        int countVer = 0; 

        for (int x = 0; x < k; x++) {
            
            switch(shortDist[x].variety){
                    case 1://setosa
                        countSet++;
                        break;
                    case 2://virginica
                        countVer++;
                        break;
                    case 3://versicolor
                        countVir++;
                        break;
            }
        }
        
//        System.out.println("i"+ i);
//        System.out.println("ver"+countVer);        
//        System.out.println("vir"+countVir);
//        System.out.println("set"+countSet);
//        System.out.println("");



        //Cual ocurre más veces
        if (countSet >= countVer && countSet >= countVir) 
        {
            resultTest[i].variety = 1;//setosa
        } 
        else if (countVer >= countSet && countVer >= countVir) 
        {
            resultTest[i].variety = 2;//virginica
        } 
        else if (countVir >= countVer && countVir >= countSet) 
        {
            resultTest[i].variety = 3;//versicolor
        }

    }
    
    public double CalculateCertain(){
        int[][] CertainMatrix = new int[3][3];
        
        
        
        for (int i = 0; i < 15; i++) {
//            System.out.println("test "+"i"+i+" "+ test[i].toString());
//           System.out.println("result: "+"i"+i+" "+ resultTest[i].toString());
//            System.out.println("");

            if (test[i].variety == 1) {
                CertainMatrix[0][resultTest[i].variety-1]++;
            }
            if (test[i].variety == 2) {
                CertainMatrix[1][resultTest[i].variety-1]++;
            }
            if (test[i].variety == 3) {
                CertainMatrix[2][resultTest[i].variety-1]++;
            }
        }
        
        double certainValue = (CertainMatrix[0][0]+ CertainMatrix[1][1]+
                CertainMatrix[2][2])/15.0;
        
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(CertainMatrix[i][j]+ " ");
//            }
//            System.out.println("");
//        }
        //System.out.println("Certeza: " + certainValue);
        return certainValue;
    }
    

}

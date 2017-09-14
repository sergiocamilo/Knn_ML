/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import java.io.FileReader;
import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author sergiofernandez
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Iris[] fullIris = new Iris[150];
        
        //Leer el CSV___________________________________________________________
        CsvReader irisCsv = new CsvReader("iris.csv");
        irisCsv.readHeaders();

        //"sepal.length","sepal.width","petal.length","petal.width","variety"
        int i=0;
        while (irisCsv.readRecord()) {
            Iris irisAux = new Iris();

            irisAux.petalLength = Double.parseDouble(irisCsv.get("petal.length"));
            irisAux.petalWidth = Double.parseDouble(irisCsv.get("petal.width"));

            irisAux.sepalLength = Double.parseDouble(irisCsv.get("sepal.length"));
            irisAux.sepalWidth = Double.parseDouble(irisCsv.get("sepal.width"));

            irisAux.variety = SelectVariety(irisCsv.get("variety"));
            
            fullIris[i++] = irisAux;
        }
        
        irisCsv.close();
        //Fin leer csv__________________________________________________________
        
        //particiona en 10 los datos
        HashMap<Integer, Iris[]> PartitionIris = DoPartition(fullIris);
        
        //test
        for (int j = 0; j < 10; j++) {
            KNN knnTest = new KNN();
            knnTest.k = 1;
            knnTest.train = UnionPartitionsExcept(PartitionIris,j);
            knnTest.test = PartitionIris.get(j);
            
            knnTest.resultTest = copyTest(knnTest.test);
            knnTest.Classify();
            knnTest.CalculateCertain();
            
            
        }
    }

    public static int SelectVariety(String variety) {
        switch (variety) {
            case "Setosa":
                return 1;
            case "Versicolor":
                return 2;
            default:
                return 3;
        }
    }

    private static HashMap<Integer, Iris[]> DoPartition(Iris[] fullIris) {
        
        HashMap<Integer, Iris[]> PartitionIris = new HashMap<Integer , Iris[]>();
        
        for (int i = 0; i < 10; i++) {
            Iris[] aux = new Iris[15];
            for (int j = 0; j < 15; j++) {
                aux[j] = fullIris[(int) (Math.random() * 150)];
            }
            PartitionIris.put(i, aux);
        }
        return PartitionIris;
    }

    private static Iris[] UnionPartitionsExcept(HashMap<Integer, 
            Iris[]> PartitionIris, int n) {
        
        Iris[] mergeList = new Iris[140];
        int j = 0;
        
        for (int i = 0; i < 10; i++) {
            if (i != n) {
                for (int k = 0; k < 15; k++) {
                    mergeList[j++] = PartitionIris.get(i)[k];
                } 
            }
        }
        
        return mergeList;
    }

    private static Iris[] copyTest(Iris[] test) {
        
        Iris[] newTest = new Iris[10];
        
        for (int i = 0; i < 10; i++) {
            Iris irisAux = new Iris();

            irisAux.petalLength = test[i].petalLength;
            irisAux.petalWidth = test[i].petalWidth;

            irisAux.sepalLength = test[i].sepalLength;
            irisAux.sepalWidth = test[i].sepalWidth;

            irisAux.variety = 0;
            
            newTest[i] = irisAux;
        }
        
        return newTest;
    }

}

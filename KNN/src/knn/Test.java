/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knn;

import java.io.FileReader;
import com.csvreader.CsvReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 *
 * @author sergiofernandez
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Iris[] fullIris = new Iris[150];

        //Leer el CSV___________________________________________________________
        Path dataFilePath = Paths.get("src/knn/iris.csv").toAbsolutePath().normalize();
        BufferedReader reader = Files.newBufferedReader(dataFilePath);
        CsvReader irisCsv = new CsvReader(reader);
        irisCsv.readHeaders();

        //"sepal.length","sepal.width","petal.length","petal.width","variety"
        int i = 0;
        while (irisCsv.readRecord()) {
            Iris irisAux = new Iris();

            irisAux.petalLength = Double.parseDouble(irisCsv.get("petal_length"));
            irisAux.petalWidth = Double.parseDouble(irisCsv.get("petal_width"));

            irisAux.sepalLength = Double.parseDouble(irisCsv.get("sepal_length"));
            irisAux.sepalWidth = Double.parseDouble(irisCsv.get("sepal_width"));

            irisAux.variety = SelectVariety(irisCsv.get("species"));

            fullIris[i++] = irisAux;
        }

        irisCsv.close();
        //Fin leer csv__________________________________________________________

        //particiona en 10 los datos
        HashMap<Integer, Iris[]> PartitionIris = DoPartition(fullIris);

//        for (int k = 0; k < fullIris.length; k++) {
//                System.out.println(fullIris[k].toString());
//        }
        //test
        System.out.println("--KNN iris--");
        double avg = 0;
        for (int j = 1; j <= 10; j++) {
            for (int k = 0; k < 10; k++) {
                KNN knnTest = new KNN();
                knnTest.k = j;
                knnTest.train = UnionPartitionsExcept(PartitionIris, k);
                knnTest.test = PartitionIris.get(k);

//            for (int k = 0; k < knnTest.test.length; k++) {
//                System.out.println(knnTest.test[k].toString());
//            }
                knnTest.resultTest = copyTest(knnTest.test);
                knnTest.Classify();
                
                avg+=knnTest.CalculateCertain();
            }
            System.out.println("Avg k= "+j+": "+avg/10.0);
            avg=0;
        }
    }

    public static int SelectVariety(String variety) {

        switch (variety) {
            case "setosa":
                return 1;
            case "versicolor":
                return 2;
            default:
                return 3;
        }
    }

    private static HashMap<Integer, Iris[]> DoPartition(Iris[] fullIris) {

        HashMap<Integer, Iris[]> PartitionIris = new HashMap<Integer, Iris[]>();

        for (int i = 0; i < 10; i++) {
            Iris[] aux = new Iris[15];
            for (int j = 0; j < 15; j++) {
                aux[j] = fullIris[(int) (Math.random() * 150)];
            }
            PartitionIris.put(i, aux);
        }
        return PartitionIris;
    }

    private static Iris[] UnionPartitionsExcept(HashMap<Integer, Iris[]> PartitionIris, int n) {

        Iris[] mergeList = new Iris[135];
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

        Iris[] newTest = new Iris[15];

        for (int i = 0; i < 15; i++) {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k.nn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Afa
 */
public class KNN {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        BufferedReader br = new BufferedReader(new FileReader("src/labeleddata.txt"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        int lineCounter = 0;
        double[][] baris = new double[lineCounter][0];
        while (line != null) {
            lineCounter++;
            baris = Arrays.copyOf(baris, lineCounter);
            String[] v = line.split(",");
//            String[] v = line.split("\t");
            double[] data = new double[v.length];
            for (int i = 0; i < v.length; i++) {
                data[i] = Double.parseDouble(v[i]);
            }
            baris[lineCounter - 1] = data;
            line = br.readLine();
        }
        double[][] dataMula = baris;

//        double[][] dataMula = new double[][]{
//            {1, 1, 0},
//            {1, 2, 0},
//            {2, 1, 0},
//            {2, 3, 1},
//            {3, 3, 1}
//        };
        System.out.println("Labeled Data: ");
        for (int i = 0; i < dataMula.length; i++) {
            System.out.println("Data " + (i + 1) + ": " + Arrays.toString(dataMula[i]));
        }

        Scanner scan = new Scanner(System.in);
        double[] dataTest = new double[dataMula[0].length-1];
        System.out.println("");
        System.out.println("Masukkan Data Baru sebanyak "+(dataMula[0].length-1)+" element!");
        for (int i = 0; i < dataMula[0].length-1; i++) {
            System.out.print("Elemen "+(i+1)+":");
            dataTest[i]=scan.nextDouble();
        }
//        double[] dataTest = new double[]{1, 3};

        System.out.println("");
        System.out.println("Anda telah selesai memasukkan Data Baru.");
        System.out.println("Data Baru: ");
        System.out.println(Arrays.toString(dataTest));
//        System.out.println("");
//        System.out.print("Masukkan nilai K:");
//        int k = scan.nextInt();
        int k = 3;
        System.out.println("");
        System.out.println("Anda telah selesai memasukkan nilai K.");
        System.out.println("Nilai K: " + k);
        System.out.println("");
        double[] jarak = new double[dataMula.length];
        for (int i = 0; i < dataMula.length; i++) {
            double temp = 0;
            for (int j = 0; j < dataMula[i].length - 1; j++) {
                temp = temp + Math.pow((dataTest[j] - dataMula[i][j]), 2);
            }
            jarak[i] = Math.sqrt(temp);
        }
        int[] tedekat;
        System.out.println("Jarak Data Baru terhadap masing-masing Labeled Data: ");
        for (int i = 0; i < jarak.length; i++) {
            System.out.println("Jarak " + (i + 1) + " = " + jarak[i]);

        }

        
        System.out.println("");
        int[] indexTerdekat = getIndexTerdekat(jarak, k);
//        System.out.println(Arrays.toString(indexTerdekat));
        System.out.println("Labeled Data terdekat:");
        int y = 0;
        int n = 0;
        for (int i = 0; i < indexTerdekat.length; i++) {
            System.out.println("Data " + (indexTerdekat[i] + 1) + ": " + Arrays.toString(dataMula[indexTerdekat[i]]));
            if (dataMula[indexTerdekat[i]][dataMula[indexTerdekat[i]].length - 1] == 0) {
                n++;
            } else {
                y++;
            }
        }

        //Klasifikasi data baru
        System.out.println("");
        if (y == n) {
            System.out.println("Klasifikasi error, karena tidak ada Labeled Data yang mayoritas.");
        } else if (y > n) {
            System.out.println("Klasifikasi sukses, Data baru termasuk berlabel \"Ya\"");
        } else {
            System.out.println("Klasifikasi sukses, Data baru termasuk berlabel \"Tidak\"");
        }

    }

    public static int[] getIndexTerdekat(double[] jarak, int k) {
        int[] indexTerdekat = new int[k];
        double[] jarakUrut = jarak.clone();
        Arrays.sort(jarakUrut);
        for (int i = 0; i < k; i++) {
            int index = -1;
            int a = 0;
            while (index < 0 && a<jarak.length) {
                if (jarak[a] == jarakUrut[i]) {
                    if (i > 0) {
                        boolean sudahAda=false;
                        for (int j = 0; j < i; j++) {
                            if (a == indexTerdekat[j] ) {
                                sudahAda=true;
                            } 
                        }
                        if(!sudahAda){
                            index = a;
                        }else{
                            a++;
                        }
                    } else {
                        index = a;
                    }
                } else {
                    a++;
                }
            }
            indexTerdekat[i] = index;
        }
        return indexTerdekat;
    }

}

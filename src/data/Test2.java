package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Test2 {


    public static void main(String[] args) {

        // abc.csvを生成
        String filePath = "src/data/abc.csv";

        try {
            // fileWriter
            // FileWriterによりabc.csvを書き込む
            FileWriter writer = new FileWriter(filePath);
            // abc.csvにデータ２行（２件）を書き込む
            writer.write("aaa,bbb,ccc\n");
            writer.write("ddd,eee,fff\n");
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader bf = new BufferedReader(new FileReader(filePath));

            String line;

            while( (line = bf.readLine()) != null) {

                String[] strArr = line.split(",");

                for(String str : strArr) {
                    System.out.println(str);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        // fileReader
        // BufferedReaderによりabc.csvを読み込む
        // １行ずつ読み込み、カンマ区切りで出力する

    }

}

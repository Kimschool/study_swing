package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test1 {

    public static void main(String[] args) {
        String fileName = "src/data/test.csv";

        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;

            while ( (line = reader.readLine()) != null) {

                String[] strArr = line.split(",");

                for(String str : strArr) {
                    System.out.println(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

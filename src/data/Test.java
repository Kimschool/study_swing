package data;

import java.io.FileWriter;

public class Test {

    public static void main(String[] args) {
        String fileName = "src/data/test.csv";
        String[] data = {
            "1, 田中, 男, 25",
            "2, 山田, 女, 22",
            "3, 鈴木, 男, 30"
        };

        try {
            FileWriter writer = new FileWriter(fileName);

            for (String line : data) {
                writer.write(line + "\n");
            }

            writer.close();
            System.out.println("CSVファイルにデータを書き込みました: " + fileName);
        } catch (Exception e) {
            // 例外処理
            // 例外の内容を
            e.printStackTrace();
            System.out.println("CSVファイルの作成が失敗しました。");
        }
    }
}
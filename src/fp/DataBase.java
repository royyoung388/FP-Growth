package fp;

import java.io.*;
import java.util.Calendar;
import java.util.Random;

public class DataBase {
    //随机生成数据
    public static String[] genData() {
        long seed = Calendar.getInstance().getTimeInMillis();
        Random random = new Random(seed);

        int dataSize = (int) (1000 + 1000 * random.nextDouble());
        int goodSize = (int) (10 + 10 * random.nextDouble());

//        int dataSize = 10;
//        int goodSize = 7;

        System.out.println("dataSize = " + dataSize);
        System.out.println("goodSize = " + goodSize);

        String[] commodity = new String[dataSize];

        for (int i = 0; i < dataSize; i++) {
            StringBuilder builder = new StringBuilder();

            for (int j = 0; j < goodSize; j++) {
                if (random.nextBoolean()) {
                    builder.append(j + ",");
                }
            }
            if (builder.length() > 1)
                builder.deleteCharAt(builder.length() - 1);

            commodity[i] = builder.toString();
//            System.out.println("commodity = " + commodity[i]);
        }


        return commodity;
    }

    public static void writeData(String filePath, String[] data) {
        try {
            File file = new File(filePath);

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String s : data) {
                if (s.length() > 0)
                    writer.write(s + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

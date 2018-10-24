package fp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
//        String[] data = DataBase.genData();
//        DataBase.writeData("trolley.txt", data);
        int line = 1946;

        double i = 0.0088;

        System.out.println("support = " + (int) (i * line));
        calc(0.8, (int) (i * line));
        System.out.println("support = " + 16);
        calc(0.8, 16);
    }

    private static void calc(double confident, int support) throws IOException {
        String infile = "trolley.txt";
        FPTree fpTree = new FPTree();
        fpTree.setConfident(confident);
        fpTree.setMinSuport(support);

        List<List<String>> trans = fpTree.readTransRocords(new String[]{infile});
        long begin = System.currentTimeMillis();
        fpTree.buildFPTree(trans);
        long end = System.currentTimeMillis();
//        System.out.println("buildFPTree use time " + (end - begin));
        Map<List<String>, Integer> pattens = fpTree.getFrequentItems();

        String outfile = "pattens.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(outfile));
//        System.out.println("模式\t频数");
        bw.write("模式\t频数");
        bw.newLine();
        for (Map.Entry<List<String>, Integer> entry : pattens.entrySet()) {
//            System.out.println(entry.getKey() + "\t" + entry.getValue());
            bw.write(joinList(entry.getKey()) + "\t" + entry.getValue());
            bw.newLine();
        }
        bw.close();
//        System.out.println();
        List<StrongAssociationRule> rules = fpTree.getAssociateRule();

        outfile = "rule.txt";
        bw = new BufferedWriter(new FileWriter(outfile));
//        System.out.println("条件\t结果\t支持度\t置信度");
        bw.write("条件\t结果\t支持度\t置信度");
        bw.newLine();
        DecimalFormat dfm = new DecimalFormat("#.##");
        for (StrongAssociationRule rule : rules) {
//            System.out.println(rule.condition + "->" + rule.result + "\t" + dfm.format(rule.support)
//                    + "\t" + dfm.format(rule.confidence));
            bw.write(rule.condition + "->" + rule.result + "\t" + dfm.format(rule.support) + "\t"
                    + dfm.format(rule.confidence));
            bw.newLine();
        }

        System.out.println(rules.size());

        bw.close();
    }

    private static String joinList(List<String> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String ele : list) {
            sb.append(ele);
            sb.append(",");
        }
        //把最后一个逗号去掉
        return sb.substring(0, sb.length() - 1);
    }
}

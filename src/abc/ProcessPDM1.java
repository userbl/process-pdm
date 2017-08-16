package abc;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/31.
 */
public class ProcessPDM1 {


    public static void main(String[] args) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File("e:/out.pdm")));
            BufferedWriter bw = new BufferedWriter(osw);

            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\feiq-recv-files\\于鑫(305A3A0B5556)\\初中_Oracle.pdm")));
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            boolean out = false;
            int[] on_off={0,0,0};
            while((line=br.readLine()) != null) {

                if(line.contains("<c:Tables>")) {
                    out = true;
                }
                if(out) {
                    if(line.contains("<o:Table")) {
                        //ods_
                        on_off[0]=1;
                    }else  if(line.contains("<a:Name>")) {
                        on_off[1]=1;
                        //ods_
                        if(on_off[0] ==1 && on_off[1]==1) {
                            line =  processTableName(line, on_off);
                        }
                    }else if(line.contains("<a:Code>")) {
                        if(on_off[0] ==1 && on_off[1]==1) {
                            line =  processTableName(line, on_off);
                            on_off[0]=0;
                            on_off[1]=0;
                        }
                    }else if(line.contains("<a:DataType>")) {

                        line = processColumnType(line);
                    }
                }
                bw.write(line+"\n");



            }

            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String processTableName(String line, int[] on_off) {
        Pattern p = Pattern.compile("\\>.{1,}\\<");
        Matcher m = p.matcher(line);
        if(m.find()) {
            String g = m.group();
            String substring = g.substring(1, g.length() - 1);
            line = line.replace(substring,"ODS_"+substring);
            System.out.println(line);
        }
        return line;
    }

    private static String processColumnType(String line) {
        Pattern p = Pattern.compile("\\>.{1,}\\<");
        Matcher m = p.matcher(line);
        if(m.find()) {
            String g = m.group();
            String substring = g.substring(1, g.length() - 1);
            String newType =null;
            System.out.println(substring);

            // <10  int   >10 bigint   小数 decimal
            //nvachar2  翻倍 -> varchar    varchar2->varchar

            if(substring.contains("NUMBER")) {
                System.out.println("--------------number ------------------------------");

                Pattern p2 = Pattern.compile("\\d{1,}");
                Matcher m2 = p2.matcher(substring);
                if (substring.contains(",")) {
                    newType = substring.replace("NUMBER", "DECIMAL");
                } else {
                    if (m2.find()) {
                        String group = m2.group();
                        int i = Integer.parseInt(group);
                        if (i < 10) {
                            newType = substring.replace("NUMBER", "INT");
                        } else if (i >= 10) {
                            newType = substring.replace("NUMBER", "BIGINT");
                        }
                    }
                }
            }else  if(substring.contains("NVARCHAR2")) {

                System.out.println("--------------NVARCHAR2 ------------------------------");
                Pattern p2 = Pattern.compile("\\(\\d{1,}\\)");
                Matcher m2 = p2.matcher(substring);
                if(m2.find()) {
                    String group = m2.group();
                    int i = Integer.parseInt(group.substring(1,group.length()-1));
                    newType = m2.replaceAll("("+String.valueOf(i*2)+")");
                    newType = newType.replace("NVARCHAR2", "VARCHAR");
                }

            }else  if(substring.contains("NVARCHAR")) {

                System.out.println("--------------NVARCHAR ------------------------------");
                Pattern p2 = Pattern.compile("\\d{1,}");
                Matcher m2 = p2.matcher(substring);
                if(m2.find()) {
                    String group = m2.group();
                    int i = Integer.parseInt(group);
                    newType = m2.replaceAll(String.valueOf(i*2));
                    newType = newType.replace("NVARCHAR", "VARCHAR");
                }

            }else  if(substring.contains("VARCHAR2")) {

                System.out.println("--------------VARCHAR2 ------------------------------");
                newType = substring.replace("VARCHAR2", "VARCHAR");

            }

            System.out.println(newType);
            if(null != newType) {
                line = line.replace(substring, newType);
            }
            System.out.println(line);
            System.out.println();

        }
        return line;
    }
}

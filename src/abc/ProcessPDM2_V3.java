package abc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/31.
 */
public class ProcessPDM2_V3 {


    public static void main(String[] args) {

        long oid= 10000;

        try {
            String s = "C:\\Users\\Administrator\\Desktop\\";
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File(s +"basedb_base_out.pdm")));
            BufferedWriter bw = new BufferedWriter(osw);

            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(s + "basedb_base.pdm")));
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            boolean out = false;
            int[] on_off={0,0,0};
            Map<String,String> o_key = new HashMap<>();

            while((line=br.readLine()) != null) {

//                if(line.contains("<c:Tables>")) {
//                    out = true;
//                }
//                if(out) {

                    if(line.contains("</c:Columns>")) {

//                        ++oid;
//                        bw.write(getEnd(oid) + "\n");
//                        ++oid;
//                        bw.write(getEnd2(oid) + "\n");

                    }

//                    else if(line.contains("<o:Column>")) {
//
//                        if(on_off[2]==1) {
//                            ++oid;
//                            bw.write(getStart(oid) + "\n");
//                            on_off[2]=0;
//                        }
//                    }else
                        if(line.contains("<o:Table")) {
                        //ods_
                        on_off[0]=1;
//                        bw.write(line+"\n");
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

//                        line = processColumnType(line);
                    }
//                }
                bw.write(line+"\n");
                if(line.contains("<c:Columns>")) {

                        ++oid;
                        bw.write(getStart(oid) + "\n");

//                        if(oid == 0) {
////                            Pattern ppp = Pattern.compile("\\d{1,}");
////                            Matcher m2 = ppp.matcher(line);
////                            if (m2.find()) {
////                                String group = m2.group();
////
////                                oid = Long.parseLong(group);
////                                System.out.println("----------------------" + group);
////                            }
//                        }

                }



            }

            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String processTableName(String line, int[] on_off) {

        //t_base_
//        System.out.println("------------>"+line +"   " +line.indexOf(">"));
        Pattern p = Pattern.compile("\\>.{1,}\\<");
        Matcher m = p.matcher(line);
        if(m.find()) {
            String g = m.group();
            String substring = g.substring(1, g.length() - 1);
            line = line.replace(substring,"t_thr_base_"+substring);
//            System.out.println("_____///"+line);
        }
        return line;
    }

    private static String getStart(long oid) {
        String start = "<o:Column Id=\"o"+oid+"\"> "
                +"<a:ObjectID>"+ UUID.randomUUID().toString()+"</a:ObjectID>"
                +"<a:Name>DD</a:Name>"
                +"<a:Code>DD</a:Code>"
                +"<a:CreationDate>1490691743</a:CreationDate>"
                +"<a:Creator>Administrator</a:Creator>"
                +"<a:ModificationDate>1490748801</a:ModificationDate>"
                +"<a:Modifier>Administrator</a:Modifier>"
                +"<a:Comment>入库日期</a:Comment>"
                +"<a:DataType>VARCHAR(8)</a:DataType>"
                +"<a:Length>8</a:Length>" +
                "</o:Column>";

        return start;
    }

      static String start = "<o:Column Id=\"o1136\"> "
        +" <a:ObjectID>EBE5011D-3D92-41E5-A10C-605F49896D69</a:ObjectID>"
        +" <a:Name>DD</a:Name>"
        +" <a:Code>DD</a:Code>"
        +" <a:CreationDate>1490946835</a:CreationDate>"
        +" <a:Creator>Administrator</a:Creator>"
        +" <a:ModificationDate>1490946958</a:ModificationDate>"
        +" <a:Modifier>Administrator</a:Modifier>"
        +" <a:Comment>入库日期</a:Comment>"
        +" <a:DataType>VARCHAR(8)</a:DataType>"
        +" <a:Length>8</a:Length>"
        +" </o:Column>";
    static  String end = "  <o:Column Id=\"o1149\">  "
                +"<a:ObjectID>F5FBDF8A-7217-4831-A067-FD3FAEE4FC1C</a:ObjectID>  "
                +"<a:Name>TAB_NAME</a:Name>  "
                +"<a:Code>TAB_NAME</a:Code>  "
                +"<a:CreationDate>1490946835</a:CreationDate>  "
                +"<a:Creator>Administrator</a:Creator>  "
                +"<a:ModificationDate>1490946958</a:ModificationDate>  "
                +"<a:Modifier>Administrator</a:Modifier>  "
                +"<a:Comment>源表名称</a:Comment>  "
                +"<a:DataType>VARCHAR(30)</a:DataType>  "
                +"<a:Length>30</a:Length>  "
                +"</o:Column>  "
                +  ""
                +"<o:Column Id=\"o1150\">  "
                +"<a:ObjectID>BCC68913-CF21-460D-804A-BB5F151AF2D2</a:ObjectID>  "
                +"<a:Name>TAB_OWNER</a:Name>  "
                +"<a:Code>TAB_OWNER</a:Code>  "
                +"<a:CreationDate>1490946835</a:CreationDate>  "
                +"<a:Creator>Administrator</a:Creator>  "
                +"<a:ModificationDate>1490946958</a:ModificationDate>  "
                +"<a:Modifier>Administrator</a:Modifier>  "
                +"<a:Comment>源表所属SCHEMA</a:Comment>  "
                +"<a:DataType>varchar(30)</a:DataType>  "
                +"<a:Length>30</a:Length>  "
                +"</o:Column>  "
                +"</c:Columns>  ";

}

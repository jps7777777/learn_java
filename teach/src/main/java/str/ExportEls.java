package str;

import cn.jp.bean.ExcelData;
import cn.jp.utils.ExcelUtils;
import com.alibaba.druid.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExportEls {

    /**
     *
     */
    public void methodTwo() {
        String str = "aid:sss";

        // 不能直接清空数组值
        String[] arr = {"b","a","c"};
        for (String s:arr){
            System.out.println("->"+s);
        }
        arr = null;
        System.out.println("->"+arr[0]);




//        String[] arr = str.split(":");
//        System.out.println(arr[0] + "-->" + arr[1]);
//
//        // 可以判断字符串包含另一个字符串
//        if (str.contains("aid")) {
//            System.out.println("contains by contains");
//        }
//        // 也可以判断字符串包含另一个字符串
//        if (str.indexOf("aid") != -1) {
//            System.out.println("contains by indexOf");
//        }


        // 不能判断是否包含
//        Pattern p = Pattern.compile("aib");
//        Matcher m = p.matcher("aib_osd");
//        boolean b = m.matches();
//
//        System.out.println(b);
////        Pattern reg = Pattern.compile("/aid/");
////        reg.pattern("/aid/");
//        if (Pattern.matches("aid", str)) {
//            System.out.println("包含");
//        } else {
//            System.out.println("没有包含");
//        }

    }

    /**
     * 读Excel文件
     */
    public List<String[][]> method(){
        File xlsFile = new File("D:\\demo_use\\els\\poi.xls");
        // 获得工作簿
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(xlsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String[][]> list = new ArrayList<>();
        // 获得工作表个数
        int sheetCount = workbook.getNumberOfSheets();
        // 遍历工作表
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            // 获得行数
            int rows = sheet.getLastRowNum() + 1;
            // 获得列数，先获得一行，在得到该行列数
            Row tmp = sheet.getRow(0);
            if (tmp == null) {
                continue;
            }
            int cols = tmp.getPhysicalNumberOfCells();
            String[][] table = new String[rows][cols];
            // 读取数据
            for (int row = 0; row < rows; row++) {
                Row r = sheet.getRow(row);
                for (int col = 0; col < cols; col++) {
                    System.out.printf("%10s", r.getCell(col).getStringCellValue());
                }
                System.out.println();
            }
            list.add(table);
        }
        return list;
    }

    /**
     *
     */
    public void method1() {
        File file = new File("D:\\demo_use\\files\\file.txt");
        String[] value = new String[13];
        String[] titile = new String[13];
        // 制表内容
        List<String[]> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = "";
            int flag = 0;
            while ((str = br.readLine()) != null) {
                if (str.equals("")) {
                    continue;
                }
                if (str.contains("aid")) {
                    flag = 0;
                    list.add(value);
                    value = new String[13];
                }
                String[] up = str.split(":");
                titile[flag] = up[0];
                value[flag] = up[1];
                flag++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        for (String s:titile             ) {
//            System.out.println(s);
//        }
        String[][] table = new String[list.size()][list.get(0).length];
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[0].length;j++){
                table[i][j] = list.get(i)[j];
                if(i == 0){
                    table[0][j] = titile[j];
                }
            }
        }
        ExcelUtils.writeExcel(table,"D:\\demo_use\\els","poi.xls");
    }





    public static void main(String[] args) {
        ExportEls ee = new ExportEls();
        ee.method();
    }

}

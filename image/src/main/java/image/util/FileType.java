package image.util;

import image.action.ImageAction;

import java.util.*;

public class FileType {
    public final static Map<String,String> FILE_MAP ;
    public final static Map<String,String> FILE_MAP_TYPE ;
    public final static Map IMAGE_TYPE_MAP = new HashMap(){{
        put("47494638", "gif");
        put("89504E47", "png");
        put("FFD8FF", "jpg");
        put("000001BA", "mpg");
    }};
    static {
        // 文件格式
        FILE_MAP_TYPE = new HashMap<>();
        FILE_MAP_TYPE.put("38425053", "psd");
        FILE_MAP_TYPE.put("252150532D41646F6265", "ps");
        FILE_MAP_TYPE.put("424D", "bmp");
        FILE_MAP_TYPE.put("E3828596", "pwl");
        FILE_MAP_TYPE.put("47494638", "gif");
        FILE_MAP_TYPE.put("44656C69766572792D646174653A", "eml");
        FILE_MAP_TYPE.put("4D546864", "mid");
        FILE_MAP_TYPE.put("D0CF11E0", "office");
        FILE_MAP_TYPE.put("49492A00", "tif");
        FILE_MAP_TYPE.put("2142444E", "pst");
        FILE_MAP_TYPE.put("41564920", "avi");
        FILE_MAP_TYPE.put("6D6F6F76", "mov");
        FILE_MAP_TYPE.put("3C3F786D6C", "xml");
        FILE_MAP_TYPE.put("68746D6C3E", "html");
        FILE_MAP_TYPE.put("2E7261FD", "ram");
        FILE_MAP_TYPE.put("FFD8FF", "jpg");
        FILE_MAP_TYPE.put("504B0304", "zip");
        FILE_MAP_TYPE.put("52617221", "rar");
        FILE_MAP_TYPE.put("7B5C727466", "rtf");
        FILE_MAP_TYPE.put("000001BA", "mpg");
        FILE_MAP_TYPE.put("89504E47", "png");
//        FILE_MAP_TYPE.put("252150532D41646F6265", "eps");
        FILE_MAP_TYPE.put("000100005374616E64617264204A", "mdb");
        FILE_MAP_TYPE.put("FF575043", "wpd");
        FILE_MAP_TYPE.put("57415645", "wav");
        FILE_MAP_TYPE.put("AC9EBD8F", "qdf");
        FILE_MAP_TYPE.put("255044462D312E", "pdf");
        FILE_MAP_TYPE.put("41433130", "dwg");
        FILE_MAP_TYPE.put("3026B2758E66CF11", "asf");
        FILE_MAP_TYPE.put("CFAD12FEC5FD746F", "dbx");
        FILE_MAP_TYPE.put("2E524D46", "rm");
        // 第二种方式定义
        Map<String,String> FILE_TYPE_MAP = new HashMap<>();
        FILE_TYPE_MAP.put("jpg", "FFD8FF"); //JPEG
        FILE_TYPE_MAP.put("png", "89504E47"); //PNG
        FILE_TYPE_MAP.put("gif", "47494638"); //GIF
        FILE_TYPE_MAP.put("tif", "49492A00"); //TIFF
        FILE_TYPE_MAP.put("bmp", "424D"); //Windows Bitmap
        FILE_TYPE_MAP.put("dwg", "41433130"); //CAD
        FILE_TYPE_MAP.put("html", "68746D6C3E"); //HTML
        FILE_TYPE_MAP.put("rtf", "7B5C727466"); //Rich Text Format
        FILE_TYPE_MAP.put("xml", "3C3F786D6C");
        FILE_TYPE_MAP.put("zip", "504B0304");
        FILE_TYPE_MAP.put("rar", "52617221");
        FILE_TYPE_MAP.put("psd", "38425053"); //PhotoShop
        FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A"); //Email [thorough only]
        FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F"); //Outlook Express
        FILE_TYPE_MAP.put("pst", "2142444E"); //Outlook
        FILE_TYPE_MAP.put("office", "D0CF11E0"); //office类型，包括doc、xls和ppt
        FILE_TYPE_MAP.put("mdb", "000100005374616E64617264204A"); //MS Access
        FILE_TYPE_MAP.put("wpd", "FF575043"); //WordPerfect
        FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
        FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
        FILE_TYPE_MAP.put("pdf", "255044462D312E"); //Adobe Acrobat
        FILE_TYPE_MAP.put("qdf", "AC9EBD8F"); //Quicken
        FILE_TYPE_MAP.put("pwl", "E3828596"); //Windows Password
        FILE_TYPE_MAP.put("wav", "57415645"); //Wave
        FILE_TYPE_MAP.put("avi", "41564920");
        FILE_TYPE_MAP.put("ram", "2E7261FD"); //Real Audio
        FILE_TYPE_MAP.put("rm", "2E524D46"); //Real Media
        FILE_TYPE_MAP.put("mpg", "000001BA"); //
        FILE_TYPE_MAP.put("mov", "6D6F6F76"); //Quicktime
        FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); //Windows Media
        FILE_TYPE_MAP.put("mid", "4D546864"); //MIDI (mid)
        FILE_MAP = Collections.unmodifiableMap(FILE_TYPE_MAP);
    }


}

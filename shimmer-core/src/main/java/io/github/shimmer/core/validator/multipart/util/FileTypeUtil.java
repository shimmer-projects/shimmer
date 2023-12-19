package io.github.shimmer.core.validator.multipart.util;

import io.github.shimmer.core.validator.multipart.constant.FileType;
import io.github.shimmer.utils.Utils;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根据文件"魔数"判断文件类型
 */
public class FileTypeUtil {
    /**
     * 缓存文件头信息-文件头信息
     */
    private static final Map<String, String> FILE_TYPE_MAP = new ConcurrentHashMap<>();

    static {
        FILE_TYPE_MAP.put("ffd8ff", FileType.JPG.getName()); // JPEG (jpg)
        FILE_TYPE_MAP.put("89504e47", FileType.PNG.getName()); // PNG (png)
        FILE_TYPE_MAP.put("4749463837", FileType.GIF.getName()); // GIF (gif)
        FILE_TYPE_MAP.put("4749463839", FileType.GIF.getName()); // GIF (gif)
        FILE_TYPE_MAP.put("49492a00227105008037", FileType.TIF.getName()); // TIFF (tif)
        FILE_TYPE_MAP.put("424d228c010000000000", FileType.BMP.getName()); // 16色位图(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", FileType.BMP.getName()); // 24位位图(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", FileType.BMP.getName()); // 256色位图(bmp)
        FILE_TYPE_MAP.put("41433130313500000000", FileType.DWG.getName()); // CAD (dwg)
        FILE_TYPE_MAP.put("7b5c727466315c616e73", FileType.RTF.getName()); // Rich Text Format (rtf)
        FILE_TYPE_MAP.put("38425053000100000000", FileType.PSD.getName()); // Photoshop (psd)
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", FileType.EML.getName()); // Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("5374616E64617264204A", FileType.MDB.getName()); // MS Access (mdb)
        FILE_TYPE_MAP.put("252150532D41646F6265", FileType.PS.getName());
        FILE_TYPE_MAP.put("255044462d312e", FileType.PDF.getName()); // Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("2e524d46000000120001", FileType.RMVB.getName()); // rmvb/rm相同
        FILE_TYPE_MAP.put("464c5601050000000900", FileType.FLV.getName()); // flv与f4v相同
        FILE_TYPE_MAP.put("00000020667479706d70", FileType.MP4.getName());
        FILE_TYPE_MAP.put("4944330300000000", FileType.MP3.getName());
        FILE_TYPE_MAP.put("000001ba210001000180", FileType.MPG.getName()); //
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", FileType.WMV.getName()); // wmv与asf相同
        FILE_TYPE_MAP.put("52494646e27807005741", FileType.WAV.getName()); // Wave (wav)
        FILE_TYPE_MAP.put("52494646d07d60074156", FileType.AVI.getName());
        FILE_TYPE_MAP.put("4d546864000000060001", FileType.MID.getName()); // MIDI (mid)
        FILE_TYPE_MAP.put("526172211a0700cf9073", FileType.RAR.getName());// WinRAR
        FILE_TYPE_MAP.put("235468697320636f6e66", FileType.INI.getName());
        FILE_TYPE_MAP.put("504B03040a0000000000", FileType.JAR.getName());
        FILE_TYPE_MAP.put("504B0304140008000800", FileType.JAR.getName());
        // MS Excel 注意：word、msi 和 excel的文件头一样
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10", FileType.XLS.getName());
        FILE_TYPE_MAP.put("504B0304", FileType.ZIP.getName());
        FILE_TYPE_MAP.put("4d5a9000030000000400", FileType.EXE.getName());// 可执行文件
        FILE_TYPE_MAP.put("3c25402070616765206c", FileType.JSP.getName());// jsp文件
        FILE_TYPE_MAP.put("4d616e69666573742d56", FileType.MF.getName());// MF文件
        FILE_TYPE_MAP.put("7061636b616765207765", FileType.JAVA.getName());// java文件
        FILE_TYPE_MAP.put("406563686f206f66660d", FileType.BAT.getName());// bat文件
        FILE_TYPE_MAP.put("1f8b0800000000000000", FileType.GZ.getName());// gz文件
        FILE_TYPE_MAP.put("cafebabe0000002e0041", FileType.CLASS.getName());// bat文件
        FILE_TYPE_MAP.put("49545346030000006000", FileType.CHM.getName());// bat文件
        FILE_TYPE_MAP.put("04000000010000001300", FileType.MXP.getName());// bat文件
        FILE_TYPE_MAP.put("6431303a637265617465", FileType.TORRENT.getName());
        FILE_TYPE_MAP.put("6D6F6F76", FileType.MOV.getName()); // Quicktime (mov)
        FILE_TYPE_MAP.put("FF575043", FileType.WPD.getName()); // WordPerfect (wpd)
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", FileType.DBX.getName()); // Outlook Express (dbx)
        FILE_TYPE_MAP.put("2142444E", FileType.PST.getName()); // Outlook (pst)
        FILE_TYPE_MAP.put("AC9EBD8F", FileType.QDF.getName()); // Quicken (qdf)
        FILE_TYPE_MAP.put("E3828596", FileType.PWL.getName()); // Windows Password (pwl)
        FILE_TYPE_MAP.put("2E7261FD", FileType.RAM.getName()); // Real Audio (ram)
    }

    /**
     * 根据文件流判断文件类型
     *
     * @param is 输入流
     * @return 文件头信息
     */
    @SneakyThrows
    public static String getFileType(InputStream is) {
        // 读取前 28 个字节
        byte[] bytes = readBytes(is, 28);
        return getTypeByFileStreamHexHead(bytesToHexString(bytes));
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath 文件路径
     * @return 文件头信息
     */
    @SneakyThrows
    public static String getFileType(String filePath) {
        return getFileType(new File(filePath));
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param file 文件
     * @return 文件头信息
     */
    @SneakyThrows
    public static String getFileType(File file) {
        @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
        String typeName = getFileType(fileInputStream);
        if (null == typeName) {
            // 未成功识别类型，扩展名辅助识别
            typeName = FileUtil.extName(file);
        } else if ("xls".equals(typeName)) {
            // xls、doc、msi的头一样，使用扩展名辅助判断
            final String extName = FileUtil.extName(file);
            if ("doc".equalsIgnoreCase(extName)) {
                typeName = "doc";
            } else if ("msi".equalsIgnoreCase(extName)) {
                typeName = "msi";
            }
        } else if ("zip".equals(typeName)) {
            // zip可能为docx、xlsx、pptx、jar、war等格式，扩展名辅助判断
            final String extName = FileUtil.extName(file);
            if ("docx".equalsIgnoreCase(extName)) {
                typeName = "docx";
            } else if ("xlsx".equalsIgnoreCase(extName)) {
                typeName = "xlsx";
            } else if ("pptx".equalsIgnoreCase(extName)) {
                typeName = "pptx";
            } else if ("jar".equalsIgnoreCase(extName)) {
                typeName = "jar";
            } else if ("war".equalsIgnoreCase(extName)) {
                typeName = "war";
            }
        }
        return typeName;
    }

    /**
     * 将要读取文件头信息的文件的 byte 数组转换成 String 类型表示
     *
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            String hv = Integer.toHexString(b & 0xFF);
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }


    /**
     * 读取指定长度的byte数组，不关闭流
     *
     * @param in     {@link InputStream}，为null返回null
     * @param length 长度，小于等于0返回空byte数组
     * @return bytes
     * @throws IOException IO异常
     */
    public static byte[] readBytes(InputStream in, int length) throws IOException {
        if (null == in) {
            return null;
        }
        if (length <= 0) {
            return new byte[0];
        }

        byte[] b = new byte[length];
        int readLength;
        readLength = in.read(b);
        if (readLength > 0 && readLength < length) {
            byte[] b2 = new byte[length];
            System.arraycopy(b, 0, b2, 0, readLength);
            return b2;
        } else {
            return b;
        }
    }

    /**
     * 根据文件流的头部信息获得文件类型
     *
     * @param fileStreamHexHead 文件流头部16进制字符串
     * @return 文件类型，未找到为<code>null</code>
     */
    public static String getTypeByFileStreamHexHead(String fileStreamHexHead) {
        for (Map.Entry<String, String> fileTypeEntry : FILE_TYPE_MAP.entrySet()) {
            if (Utils.useString(fileStreamHexHead).startsWith(fileTypeEntry.getKey())) {
                return fileTypeEntry.getValue();
            }
        }
        return null;
    }
}

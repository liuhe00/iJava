package com.liujavabei.tools;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

public class SvnUploadTool {
    private static String targetPrePath = "C:\\Users\\liuhe\\Desktop\\上线\\";
    private static final List<String> pathList = new ArrayList<>();
    private static final String ZJSrcPath = "D:\\TaiYue\\ZheJiang\\project\\uflow6\\war\\WEB-INF\\classes\\";
    private static final String HBSrcPath = "D:\\TaiYue\\HeBei\\code\\uflow6.0\\war\\WEB-INF\\classes\\";
    private static final String javaFilePathPattern = "(com/ultra.+/)(\\w+(?:\\$\\w+)?)\\.\\w+";
    private static final String staticFilePathPattern = "war/((.+/)(.+\\.\\w+))";
    private static boolean created = false;


    public static void main(String[] args) throws Exception {
        //提交的文件目录
        String localFilesPath = "D:/TaiYue/HeBei/code/uflow6.0/src-ccm/com/ultra/uflow/extend/job/autoSubmitJob.java\n" +
                "D:/TaiYue/HeBei/code/uflow6.0/src-core/com/ultra/uflow/core/process/dao/ProcessCustomerFilterDao.java\n" +
                "D:/TaiYue/HeBei/code/uflow6.0/src-core/com/ultra/uflow/core/process/dao/ProcessInfoDao.java\n" +
                "D:/TaiYue/HeBei/code/uflow6.0/src-core/com/ultra/uflow/core/process/dao/ProcessUnsolvedDao.java\n" +
                "D:/TaiYue/HeBei/code/uflow6.0/src-core/com/ultra/uflow/core/process/model/ProcessCustomerFilter.java\n" +
                "D:/TaiYue/HeBei/code/uflow6.0/src-core/com/ultra/uflow/core/process/model/ProcessUnsolved.java\n" +
                "D:/TaiYue/HeBei/code/uflow6.0/src-core/com/ultra/uflow/core/process/service/ProcessService.java";
        localFilesPath = localFilesPath.replace("\\", "/");
        String[] files = localFilesPath.split("\n");
        for (String file : files) {
            if (getType(file).equals("java")) {
                copyJavaFile2(file, HBSrcPath);
            } else {
                copyStaticFile2(file);
            }
        }
        //写入目录
        writeCatalog();
        FileOutputStream fos1 = new FileOutputStream(new File("C:\\Users\\liuhe\\Desktop\\上线\\上线.zip"));
        ZipTool.toZip("C:\\Users\\liuhe\\Desktop\\上线\\uflow", fos1, true);
        //ZipTool.compress(new File(targetPrePath),new ZipOutputStream(new FileOutputStream("C:\\Users\\liuhe\\Desktop\\哈哈")),"压缩",true);
    }

    private static void writeCatalog() throws IOException {
        for (String s : pathList) {
            String[] proNameAndPath = s.split(":");
            File file = new File(targetPrePath + proNameAndPath[0] + "/目录.txt");
            if (!file.exists()) {
                boolean b = file.createNewFile();
                System.out.printf("文件%s创建成功?%s%n", file.getName(), b);
            }
           FileWriter writer = new FileWriter(file, true);
            writer.write(proNameAndPath[1] + "\n");
            writer.close();
        }
    }

    private static void copyStaticFile(String filePath) {
        Pattern r = Pattern.compile(staticFilePathPattern);
        Matcher m = r.matcher(filePath);
        if (m.find()) {
            //提取文件基本路径及名称
            String srcBaseFolders = m.group(2);
            copy(new File(filePath), new File(targetPrePath + m.group(1)));
        }
    }

    private static void copyStaticFile2(String filePath) {
        Pattern r = Pattern.compile(staticFilePathPattern);
        Matcher m = r.matcher(filePath);
        if (m.find()) {
            //提取文件基本路径及名称
            String srcBaseFolders = m.group(2);
            String fileName = m.group(3);
            String nameByPath = getProjectNameByPath(filePath);
            pathList.add(nameByPath+":/" + srcBaseFolders + fileName);
            String finalPath = targetPrePath + getProjectNameByPath(filePath);
            copy(new File(filePath), new File(finalPath + "/" + m.group(3)));
        }
    }

    private static void copyJavaFile(String filePath, String area) {
        String targetPrePath = "C:\\Users\\liuhe\\Desktop\\svn_upload\\" + getProjectNameByPath(filePath) + "\\";
        Pattern r = Pattern.compile(javaFilePathPattern);
        Matcher m = r.matcher(filePath);
        File srcFile;
        File targetFile;
        if (m.find()) {
            //提取文件基本路径及名称
            String srcBaseFolders = m.group(1);
            String srcFilename = m.group(2);
            //创建输出目录start------>
            File finalOutputFolder = new File(targetPrePath + "WEB-INF\\classes\\" + srcBaseFolders);
            if (!finalOutputFolder.exists()) {
                boolean isSucceed = finalOutputFolder.mkdirs();
                System.out.printf("创建输出目录成功?%s%n", isSucceed);
            }//创建输出目录end------>
            //判断class文件所在目录里有没有内部类生成的class文件，如果有，连同这些文件一并复制
            File finalSrcFolders = new File(area + srcBaseFolders);
            for (String filename : Objects.requireNonNull(finalSrcFolders.list())) {
                if (filename.equals(srcFilename + ".class") || filename.startsWith(srcFilename + "$")) {
                    //class文件路径（以com目录为开头）
                    String baseFilePath = srcBaseFolders + filename;
                    System.out.println(area + baseFilePath);
                    srcFile = new File(area + baseFilePath);
                    targetFile = new File(targetPrePath + "WEB-INF\\classes\\" + baseFilePath);
                    copy(srcFile, targetFile);
                }
            }
        }
    }

    //类文件+文本目录的形式
    private static void copyJavaFile2(String filePath, String area) {
        Pattern r = Pattern.compile(javaFilePathPattern);
        Matcher m = r.matcher(filePath);
        File srcFile;
        File targetFile;
        if (m.find()) {
            //提取文件基本路径
            String srcBaseFolders = m.group(1);
            //提取文件名称
            String srcFilename = m.group(2);
            //创建输出目录
            String nameByPath = getProjectNameByPath(filePath);
            if (!created) {
                createTargetCatalog(nameByPath);
            }
            //判断class文件所在目录里有没有内部类生成的class文件，如果有，连同这些文件一并复制
            File finalSrcFolders = new File(area + srcBaseFolders);
            for (String filename : Objects.requireNonNull(finalSrcFolders.list())) {
                if (filename.equals(srcFilename + ".class") || filename.startsWith(srcFilename + "$")) {
                    //class文件路径（以com目录为开头）
                    String baseFilePath = srcBaseFolders + filename;
                    pathList.add(nameByPath + ":WEB-INF/classes/" + baseFilePath);
                    srcFile = new File(area + baseFilePath);
                    targetFile = new File(targetPrePath + nameByPath + "/" + filename);
                    copy(srcFile, targetFile);
                }
            }
        }
    }

    private static void createTargetCatalog(String projectName) {
        File finalOutputFolder = new File(targetPrePath+projectName);
        if (!finalOutputFolder.exists()) {
            FileTool.createFolder(targetPrePath+projectName);
        } else {
            //如果存在，递归删除里面的文件
            clearFiles(new File(targetPrePath+projectName));
        }
        created = true;
    }

    private static void clearFiles(File finalOutputFolder) {
        File[] files = finalOutputFolder.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                boolean delete = file.delete();
                System.out.printf("删除文件[%s]：%s%n", file.getName(), delete);
            } else if (file.isDirectory()) {
                clearFiles(file);
            }
        }
    }

    private static String getType(String path) {
        String pattern = ".+\\.(\\w+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(path);
        if (m.find()) {
            return m.group(1);
        }
        return "java";
    }

    private static void copy(File src, File target) {
        DataInputStream dataInput = null;
        DataOutputStream dataOutput = null;
        try {
            dataInput = new DataInputStream(new FileInputStream(src));
            dataOutput = new DataOutputStream(new FileOutputStream(target));
            int temp;
            byte[] buffer = new byte[1024];
            while ((temp = dataInput.read(buffer)) != -1) {
                dataOutput.write(buffer, 0, temp);
            }
            System.out.printf(src.getName() + ":%s%n", "copy succeed!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataInput != null) {
                    dataInput.close();
                }
                if (dataOutput != null) {
                    dataOutput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getProjectNameByPath(String path) {
        if (path.contains("java")||path.contains("uflow")) {
            return "uflow";
        } else if (path.contains("uflowUI")) {
            return "uflowUI";
        }
        return "uflowRelation";
    }
}

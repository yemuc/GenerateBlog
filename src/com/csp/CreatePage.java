package com.csp;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author csp
 * @date 2021/12/19 15:30
 * @description 创建页面
 */
public class CreatePage {


    public void create(String fileName){

        String content = FileUtils.read(fileName);
    }

    public void createHtml(){

    }

    /**
     * 更新首页
     * @param modelFile 模板文件
     * @param markDownDirectory markdown文件夹路径
     * @param target 目标文件
     * @param basePath 目标库路径
     */
    public static void updateIndexHtml(String modelFile,String markDownDirectory,String target,String basePath){
        String html =  FileUtils.read(basePath+modelFile);
        StringBuilder contentStringBuilder = new StringBuilder();

        List<File> fileList = new ArrayList<>();
        FileUtils.getFileList(basePath+markDownDirectory,fileList);

        // 根据文件路径排序（由于md文件按日期分类其实是按日期排序）
        fileList.sort(Comparator.comparing(File::getPath).reversed());

        for (File file:fileList){
            String fileName =  file.getName();
            String suffix = FileUtils.getSuffix(file);
            // 排除非md文件
            if (!suffix.equals(".md")){
                continue;
            }

            fileName = fileName.substring(0,fileName.lastIndexOf("."));

            String filePath = file.getPath().replaceAll("\\\\","/");
            String relativePath = filePath.replace(basePath,"");
            String li = "<li><a href=\"./blog.html?path="+relativePath+"\">"+fileName+"</a></li>";
            contentStringBuilder.append(li).append(System.lineSeparator());

            System.out.println(relativePath);
        }

        String  newHtml = html.replace("{{blog-list}}",contentStringBuilder.toString());

        FileUtils.write(newHtml,basePath+target,false);
    }


}

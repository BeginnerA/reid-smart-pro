package top.reid.smart.base.core.io;

import org.junit.Test;
import top.reid.smart.base.core.io.pojo.FileTree;

import java.io.File;
import java.util.List;

public class FileToolsTest {

    @Test
    public void getChildFileTree() {
        List<FileTree> childFileTree = FileTools.getChildFileTree("E:\\test");
        childFileTree.forEach(System.out :: println);
    }

    @Test
    public void getFileTree() {
        FileTree fileTree = FileTools.getFileTree("E:\\test");
        System.out.println("下级文件夹以及文件名称列表：" + fileTree.getChildAllNames());
        System.out.println("下级文件夹名称列表：" + fileTree.getChildFolderNames());
        System.out.println("下级文件名称列表：" + fileTree.getChildFileNames());
        System.out.println("下级指定后缀文件名称列表：" + fileTree.getChildFileNames("shp"));

        System.out.println("下级文件夹以及文件列表：" + fileTree.getChildAllFiles());
        System.out.println("下级文件夹列表：" + fileTree.getChildFolderFiles());
        System.out.println("下级文件列表：" + fileTree.getChildFiles());
        System.out.println("下级指定后缀文件列表：" + fileTree.getChildFiles("shp"));
        System.out.println("下级指定文件名称文件：" + fileTree.getChildFile("test111.shp"));
        // 不带文件本身的文件结构树
        FileTree fileTree1 = FileTools.getFileTree(new File("E:\\test"), false);

    }

    @Test
    public void contentEquals() {
        String path1 = "E:\\test";
        String path2 = "E:\\test1";
        System.out.println("比较文件结构：" + FileTools.contentEquals(FileTools.getFileTree(path1), FileTools.getFileTree(path2), false));
        System.out.println("比较文件结构和内容：" + FileTools.contentEquals(FileTools.getFileTree(path1), FileTools.getFileTree(path2), true));
    }
}

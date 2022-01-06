package top.reid.smart.core.io;

import org.junit.jupiter.api.Test;
import top.reid.smart.core.io.pojo.FileTree;

import java.util.List;

class FileToolsTest {

    @Test
    void getChildFileTree() {
        List<FileTree> childFileTree = FileTools.getChildFileTree("E:\\test");
        childFileTree.forEach(System.out :: println);
    }

    @Test
    void getFileTree() {
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
    }

    @Test
    void contentEquals() {
        String path1 = "E:\\test";
        String path2 = "E:\\test1";
        System.out.println("检查文件结构：" + FileTools.contentEquals(FileTools.getFileTree(path1), FileTools.getFileTree(path2), false));
        System.out.println("检查文件结构和内容：" + FileTools.contentEquals(FileTools.getFileTree(path1), FileTools.getFileTree(path2), true));
    }
}

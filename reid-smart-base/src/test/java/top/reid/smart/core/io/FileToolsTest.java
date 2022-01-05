package top.reid.smart.core.io;

import org.junit.jupiter.api.Test;
import top.reid.smart.core.io.pojo.FileTree;

import java.util.List;

class FileToolsTest {

    @Test
    void getChildFileTree() {
        List<FileTree> childFileTree = FileTools.getChildFileTree("E:\\test");
        childFileTree.forEach(fileTree -> {
            System.out.println(fileTree.getFileName());
        });
    }

    @Test
    void getFileTree() {
        FileTree fileTree = FileTools.getFileTree("E:\\test");
        System.out.println("下级文件夹以及文件名称列表：" + fileTree.getChildAllNames().toString());
        System.out.println("下级文件夹名称列表：" + fileTree.getChildFolderNames().toString());
        System.out.println("下级文件名称列表：" + fileTree.getChildFileNames().toString());

        System.out.println("下级文件夹以及文件列表：" + fileTree.getChildAllFiles().toString());
        System.out.println("下级文件夹列表：" + fileTree.getChildFolderFiles().toString());
        System.out.println("下级文件列表：" + fileTree.getChildFiles().toString());
    }

}

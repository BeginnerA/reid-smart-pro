package top.reid.smart.core.io;

import cn.hutool.core.io.FileUtil;
import top.reid.smart.core.io.pojo.FileTree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文件工具类
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/5
 * @Version V1.0
 **/
public class FileTools extends FileUtil {

    /**
     * 获取指定目录下文件树，包含指定目录下所有文件夹和文件
     * @param path 文件路径
     * @return 文件树列表
     */
    public static List<FileTree> getChildFileTree(String path) {
        return getChildFileTree(file(path));
    }

    /**
     * 获取指定目录下文件树，包含指定目录下所有文件夹和文件
     * @param file 当前文件
     * @return 文件树列表
     */
    public static List<FileTree> getChildFileTree(File file) {
        List<FileTree> fileTrees = new ArrayList<>();
        final File[] files = ls(file.getPath());
        for (File f : files) {
            fileTrees.add(getFileTree(f));
        }
        return fileTrees;
    }

    /**
     * 获取文件树，包含当前文件夹以及子目录中的所有文件夹和文件，返回{@link FileTree}
     * @param path 当前文件路径
     * @return 文件树形列表
     */
    public static FileTree getFileTree(String path) {
        return getFileTree(file(path));
    }

    /**
     * 获取文件树，包含当前文件夹以及子目录中的所有文件夹和文件，返回{@link FileTree}
     * @param file 当前文件
     * @return 文件树形列表
     */
    public static FileTree getFileTree(File file) {
        FileTree fileTree = new FileTree();
        if (file != null) {
            fileTree.setFile(file);
            fileTree.setFileName(file.getName());
            fileTree.setFileSize(size(file));
            fileTree.setExtName(extName(file));
            fileTree.setAbsolutePath(getAbsolutePath(file));
            String path = file.getPath();
            if (isDirectory(path)) {
                final File[] files = ls(path);
                for (File f : files) {
                    fileTree.getFileTrees().add(getFileTree(f));
                }
            }
        }

        return fileTree;
    }
}

package top.reid.smart.core.io;

import cn.hutool.core.io.FileUtil;
import top.reid.smart.core.io.pojo.FileTree;
import top.reid.smart.core.util.StrTools;

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
     * 获取指定目录下文件结构树，包含指定目录下所有文件夹和文件<br>
     * 返回内容包含文件本身 {@link File}<br>
     * 如果只需要文件结构直接使用 {@link #getFileTree(File, boolean)} 方法
     * @param path 文件路径
     * @return 文件结构树列表
     */
    public static List<FileTree> getChildFileTree(String path) {
        return getChildFileTree(file(path));
    }

    /**
     * 获取指定目录下文件结构树，包含指定目录下所有文件夹和文件<br>
     * 返回内容包含文件本身 {@link File}<br>
     * 如果只需要文件结构直接使用 {@link #getFileTree(File, boolean)} 方法
     * @param file 当前文件
     * @return 文件结构树列表
     */
    public static List<FileTree> getChildFileTree(File file) {
        List<FileTree> fileTrees = new ArrayList<>();
        final File[] files = ls(file.getPath());
        for (File f : files) {
            fileTrees.add(getFileTree(f, true));
        }
        return fileTrees;
    }

    /**
     * 获取文件结构树，包含当前文件夹以及子目录中的所有文件夹和文件，返回 {@link FileTree}<br>
     * 返回内容包含文件本身 {@link File}<br>
     * 如果只需要文件结构直接使用 {@link #getFileTree(File, boolean)} 方法
     * @param path 当前文件路径
     * @return 文件结构树列表
     */
    public static FileTree getFileTree(String path) {
        return getFileTree(file(path), true);
    }

    /**
     * 获取文件结构树，包含当前文件夹以及子目录中的所有文件夹和文件信息，返回 {@link FileTree}
     * @param file 当前文件
     * @param includeFiles 是否包含文件本身 {@link File}
     * @return 文件结构树列表
     */
    public static FileTree getFileTree(File file, boolean includeFiles) {
        FileTree fileTree = new FileTree();
        if (file != null) {
            if (includeFiles) {
                fileTree.setFile(file);
            }
            fileTree.setFileName(file.getName());
            fileTree.setFileSize(size(file));
            fileTree.setExtName(extName(file));
            fileTree.setAbsolutePath(getAbsolutePath(file));
            fileTree.isFile(isFile(file));
            String path = file.getPath();
            if (isDirectory(path)) {
                final File[] files = ls(path);
                for (File f : files) {
                    fileTree.getFileTrees().add(getFileTree(f, includeFiles));
                }
            }
        }

        return fileTree;
    }

    /**
     * 比较两个文件结构是否相同<br>
     * 比较两个文件结构名称和数量相同<br>
     * 此方法不检查文件具体内容<br>
     * 如果也需要检查文件具体内容，直接使用 {@link #contentEquals(FileTree, FileTree, boolean)} 方法
     * @param path1 文件路径1
     * @param path2 文件路径2
     * @return 两个文件结构一致返回 true，否则 false
     */
    public static boolean contentEquals(String path1, String path2) {
        return contentEquals(getFileTree(path1), getFileTree(path2), false);
    }

    /**
     * 比较两个文件结构是否相同<br>
     * 比较两个文件结构名称和数量相同<br>
     * 此方法不检查文件具体内容
     * 如果也需要检查文件具体内容，直接使用 {@link #contentEquals(FileTree, FileTree, boolean)} 方法
     * @param file1 文件1
     * @param file2 文件2
     * @return 两个文件结构一致返回 true，否则 false
     */
    public static boolean structureEquals(File file1, File file2) {
        return contentEquals(getFileTree(file1, false), getFileTree(file2, false), false);
    }

    /**
     * 比较两个文件结构是否相同<br>
     * 比较两个文件结构名称和数量相同<br>
     * 比较两个文件内容是否相同，首先比较长度，长度一致再比较内容<br>
     * @param fileTree1 文件结构树对象1
     * @param fileTree2 文件结构树对象2
     * @param checkFileContent 是否比较文件内容
     * @return 两个文件结构一致返回 true，否则 false
     */
    public static boolean contentEquals(FileTree fileTree1, FileTree fileTree2, boolean checkFileContent) {
        boolean mark = false;
        if (fileTree1 != null && fileTree2 != null) {
            mark = StrTools.equals(fileTree1.getFileName(), fileTree2.getFileName());
            if (checkFileContent && fileTree1.isFile() && fileTree2.isFile()) {
                mark = fileTree1.getFileSize() == fileTree2.getFileSize();
                if (mark) {
                    mark = contentEquals(fileTree1.getFile(), fileTree2.getFile());
                }
            }

            if (mark) {
                List<FileTree> childFileTree1 = fileTree1.getFileTrees();
                List<FileTree> childFileTree2 = fileTree2.getFileTrees();
                if (childFileTree1.size() != childFileTree2.size()) {
                    return false;
                }
                int i = 0;
                for (FileTree fileTree : childFileTree1) {
                    mark = contentEquals(fileTree, childFileTree2.get(i), checkFileContent);
                    if (!mark) {
                        return false;
                    }
                    i++;
                }
            }
        }
        return mark;
    }
}

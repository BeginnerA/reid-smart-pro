package top.reid.smart.core.io.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件树
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/5
 * @Version V1.0
 **/
public class FileTree {
    /**
     * 文件
     */
    private File file;
    /**
     * 文件或目录名称
     */
    private String fileName;
    /**
     * 文件或目录总大小
     */
    private long fileSize;
    /**
     * 扩展名（如果是文件夹没有扩展名）
     */
    private String extName;
    /**
     * 文件绝对路径
     */
    private String absolutePath;
    /**
     * 是否是文件（文件：true；文件夹：false）
     */
    private boolean isFile;
    /**
     * 子文件树列表
     */
    private List<FileTree> fileTrees = new ArrayList<>();

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public List<FileTree> getFileTrees() {
        return fileTrees;
    }

    public void setFileTrees(List<FileTree> fileTrees) {
        this.fileTrees = fileTrees;
    }

    public boolean isFile() {
        return isFile;
    }

    public void isFile(boolean file) {
        isFile = file;
    }

    /**
     * 获取当前目录下一级文件夹以及文件名称列表
     * @return 文件夹以及文件名称列表
     */
    public List<String> getChildAllNames() {
        return this.fileTrees.stream().map(FileTree::getFileName).collect(Collectors.toList());
    }

    /**
     * 获取当前目录下一级文件夹以及文件列表
     * @return 文件夹以及文件列表
     */
    public List<File> getChildAllFiles() {
        return this.fileTrees.stream().map(FileTree::getFile).collect(Collectors.toList());
    }

    /**
     * 获取当前目录下一级文件夹名称列表
     * @return 文件夹名称列表
     */
    public List<String> getChildFolderNames() {
        return this.fileTrees.stream().filter(fileTree -> !fileTree.isFile()).map(FileTree::getFileName).collect(Collectors.toList());
    }

    /**
     * 获取当前目录下一级文件夹文件列表
     * @return 文件夹文件列表
     */
    public List<File> getChildFolderFiles() {
        return this.fileTrees.stream().filter(fileTree -> !fileTree.isFile()).map(FileTree::getFile).collect(Collectors.toList());
    }

    /**
     * 获取当前目录下一级文件名称列表
     * @return 文件名称列表
     */
    public List<String> getChildFileNames() {
        return this.fileTrees.stream().filter(FileTree::isFile).map(FileTree::getFileName).collect(Collectors.toList());
    }

    /**
     * 获取当前目录下一级指定扩展名文件名称列表
     * @param extName 扩展名（文件后缀）
     * @return 文件名称列表
     */
    public List<String> getChildFileNames(String extName) {
        return this.fileTrees.stream().filter(fileTree -> {
            return fileTree.isFile && fileTree.getExtName().equals(extName);
        }).map(FileTree::getFileName).collect(Collectors.toList());
    }

    /**
     * 获取当前目录下一级文件列表
     * @return 文件列表
     */
    public List<File> getChildFiles() {
        return this.fileTrees.stream().filter(FileTree::isFile).map(FileTree::getFile).collect(Collectors.toList());
    }

    /**
     * 获取当前目录下一级指定扩展名文件列表
     * @param extName 扩展名（文件后缀）
     * @return 文件列表
     */
    public List<File> getChildFiles(String extName) {
        return this.fileTrees.stream().filter(fileTree -> {
            return fileTree.isFile && fileTree.getExtName().equals(extName);
        }).map(FileTree::getFile).collect(Collectors.toList());
    }

    /**
     * 获取当前目录下一级指定文件完整名称文件
     * @param fileName 文件完整名称
     * @return 文件
     */
    public File getChildFile(String fileName) {
        List<File> files = this.fileTrees.stream().filter(fileTree -> {
            return fileTree.isFile && fileTree.getFileName().equals(fileName);
        }).map(FileTree::getFile).toList();
        return files.size() == 0 ? null : files.get(0);
    }
}

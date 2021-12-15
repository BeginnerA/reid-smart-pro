package top.reid.smart.lang.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @Author 杨明春
 * @Data 2021/12/15
 * @Version V1.0
 **/
public class TreeTools extends TreeUtil {

    /**
     * 获取当前同级节点最大叶子节点深度
     * @param treeList 同级节点列表
     * @param <T> T 数据源里的对象类型
     * @return 同级节点最大叶子节点深度
     */
    public static <T> int sameLevelMaxDepth(List<Tree<T>> treeList) {
        int max = 0;
        for (Tree<T> node : treeList) {
            max = Math.max(maxDepth(node), max);
        }
        return max;
    }

    /**
     * 获取当前节点树的最大深度
     * @param node 树节点
     * @param <T> T 数据源里的对象类型
     * @return 前节点树的最大深度
     */
    public static <T> int maxDepth(Tree<T> node) {
        if (node == null) {
            return 0;
        }
        int maxChildDepth = 0;
        List<Tree<T>> children = node.getChildren();
        if (children != null) {
            for (Tree<T> child : children) {
                int childDepth = maxDepth(child);
                maxChildDepth = Math.max(maxChildDepth, childDepth);
            }
        }
        return maxChildDepth + 1;
    }

    /**
     * 获取树的所有叶子节点数
     * @param treeList 数数据
     * @param sum 当前节点数
     * @param <T> T 数据源里的对象类型
     * @return 叶子节点数
     */
    public static <T> int leafNodeSum(List<Tree<T>> treeList, int sum) {
        if (CollUtil.isNotEmpty(treeList)) {
            for(Tree<T> node : treeList) {
                List<Tree<T>> children = node.getChildren();
                if(children != null && children.size() > 0) {
                    sum = leafNodeSum(children, sum);
                }else {
                    sum += 1;
                }
            }
        }
        return sum;
    }
}

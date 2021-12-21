package top.reid.smart.lang.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 树工具
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2021/12/15
 * @Version V1.0
 **/
public class TreeTools extends TreeUtil {

    /**
     * MapTree 属性名配置字段
     */
    private static final String ID_KEY = "key";
    private static final String PARENT_ID_KEY = "parentId";
    private static final String VALUE_KEY = "value";
    private static final String TEST_KEY = "title";
    private static final String CHILDREN_KEY = "children";
    /**
     * MapTree 构造树数据集合 key 配置字段
     */
    private static final String KEY_FIELD ="code";
    private static final String PARENT_ID_FIELD = "pcode";
    private static final String TEXT_FIELD = "value";
    private static final String VALUE_FIELD = "code";

    /**
     * 快到飞起的树构建
     * @param treeMap 树形结构数据
     * @return 树
     */
    public static List<Map<String, Object>> buildMapTree(List<Map<String, Object>> treeMap) {
        return buildMapTree(treeMap, false, null, null, null, null);
    }

    /**
     * 快到飞起的树构建
     * @param treeMap        树形结构数据
     * @param parentDisabled 是否禁用复选框
     * @return 树
     */
    public static List<Map<String, Object>> buildMapTree(List<Map<String, Object>> treeMap, boolean parentDisabled) {
        return buildMapTree(treeMap, parentDisabled, null, null, null, null);
    }

    /**
     * 快到飞起的树构建
     * @param treeMap        树形结构数据
     * @param parentDisabled 是否禁用复选框
     * @param keyField      id字段，一般是"id"，用于下拉框的唯一key
     * @param parentIdField 父id字段，一般是"parentId", 用于子父级关系
     * @param textField     文本字段，一般是"text", 用于下拉框的text
     * @param valueField    值字段，一般是"id"， 用于下拉框的值
     * @return 树
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> buildMapTree(List<Map<String, Object>> treeMap, boolean parentDisabled, String keyField, String parentIdField, String textField, String valueField) {

        if (CollUtil.isEmpty(treeMap)) {
            return null;
        }
        String finalKeyField = StrUtil.isEmpty(keyField) ? KEY_FIELD : keyField;
        String finalParentIdField = StrUtil.isEmpty(parentIdField) ? PARENT_ID_FIELD : keyField;
        String finalTextField = StrUtil.isEmpty(textField) ? TEXT_FIELD : textField;
        String finalValueField = StrUtil.isEmpty(valueField) ? VALUE_FIELD : valueField;

        // 将数据存储为 以 id 为 KEY 的 map 索引数据列
        Map<Object, Map<String, Object>> map = new HashMap<>(treeMap.size());
        treeMap.forEach(m -> map.put(m.get(finalKeyField), convertToAntdTreeNodeFromMap(m, finalKeyField, finalParentIdField, finalTextField, finalValueField)));

        List<Map<String, Object>> treeList = new ArrayList<>();

        // 遍历, 组装返回结果
        treeMap.forEach(mm -> {
            // 获取当前资源的id和父id
            Object curId = mm.get(finalKeyField);
            Object curPid = mm.get(finalParentIdField) == null ? null : mm.get(finalParentIdField);

            // 如果父节点不存在或id 和 pid值一样，则当前节点是顶级节点
            Map<String, Object> parentNode = map.get(curPid);
            Map<String, Object> childNode = map.get(curId);
            if (parentNode == null || curId.equals(curPid)) {
                // 是顶级节点
                treeList.add(childNode);
            } else {
                // 子节点，需要把自己添加到他父节点的children中去
                List<Object> children = (List<Object>) parentNode.get(CHILDREN_KEY);
                if (children == null) {
                    children = new ArrayList<>();
                    parentNode.put(CHILDREN_KEY, children);
                }
                children.add(childNode);
                parentNode.put("isLeaf", false);
                if (parentDisabled) {
                    parentNode.put("disableCheckbox", true);
                    parentNode.put("disabled", true);
                    parentNode.put("selectable", false);
                }
            }
        });

        return treeList;
    }

    /**
     * 将Map数据转换成符合ant-design的Map树结构
     *
     * @param map           数据
     * @param keyField      id字段
     * @param parentIdField 父id字段
     * @param textField     文本字段
     * @param valueField    值字段
     * @return 树结构
     */
    private static Map<String, Object> convertToAntdTreeNodeFromMap(Map<String, Object> map, String keyField, String parentIdField, String textField, String valueField) {
        Map<String, Object> treeNode = new HashMap<>(6);
        treeNode.put(ID_KEY, map.get(keyField));
        treeNode.put(PARENT_ID_KEY, null == map.get(parentIdField) ? null : map.get(parentIdField));
        treeNode.put(VALUE_KEY, map.get(valueField));
        treeNode.put(TEST_KEY, map.get(textField));
        treeNode.put("isLeaf", true);
        treeNode.put("otherInfo", map);
        return treeNode;
    }

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

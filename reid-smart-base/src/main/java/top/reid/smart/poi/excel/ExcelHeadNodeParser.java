package top.reid.smart.poi.excel;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.map.MapUtil;

import java.util.Map;

/**
 * <p>
 * Excel 数据表头转换器
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/12/15
 * @Version V1.0
 **/
public class ExcelHeadNodeParser<T> implements NodeParser<ExcelHeadTreeNode<T>, T> {

    @Override
    public void parse(ExcelHeadTreeNode<T> treeNode, Tree<T> tree) {
        tree.setId(treeNode.getId());
        tree.setParentId(treeNode.getParentId());
        tree.setWeight(treeNode.getWeight());
        tree.setName(treeNode.getName());
        tree.putExtra("cellStyle", treeNode.getStyle());

        //扩展字段
        final Map<String, Object> extra = treeNode.getExtra();
        if(MapUtil.isNotEmpty(extra)){
            extra.forEach(tree::putExtra);
        }
    }
}

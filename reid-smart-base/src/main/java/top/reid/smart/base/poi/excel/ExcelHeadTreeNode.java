package top.reid.smart.base.poi.excel;

import cn.hutool.core.lang.tree.Node;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.Serial;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * Excel 导出数据表头配置类<br>
 * 树节点 每个属性都可以在{@link TreeNodeConfig}中被重命名<br>
 * 在你的项目里它可以是部门实体、地区实体等任意类树节点实体
 * 类树节点实体: 包含key，父Key.不限于这些属性的可以构造成一颗树的实体对象
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/12/15
 * @Version V1.0
 **/
public class ExcelHeadTreeNode<T>  implements Node<T> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private T id;

    /**
     * 父节点ID
     */
    private T parentId;

    /**
     * 名称
     */
    private CharSequence name;

    /**
     * 顺序 越小优先级越高 默认0
     */
    private Comparable<?> weight = 0;

    /**
     * 单元格样式
     */
    private CellStyle style;

    /**
     * 扩展字段
     */
    private Map<String, Object> extra;


    /**
     * 空构造
     */
    public ExcelHeadTreeNode() {
    }

    /**
     * 构造
     *
     * @param id       ID
     * @param parentId 父节点ID
     * @param name     名称
     * @param weight   权重
     */
    public ExcelHeadTreeNode(T id, T parentId, String name, Comparable<?> weight) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        if (weight != null) {
            this.weight = weight;
        }
    }

    /**
     * 构造
     *
     * @param id       ID
     * @param parentId 父节点ID
     * @param name     名称
     * @param weight   权重
     * @param style 单元格样式
     */
    public ExcelHeadTreeNode(T id, T parentId, String name, Comparable<?> weight, CellStyle style) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.style = style;
        if (weight != null) {
            this.weight = weight;
        }
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public ExcelHeadTreeNode<T> setId(T id) {
        this.id = id;
        return this;
    }

    @Override
    public T getParentId() {
        return this.parentId;
    }

    @Override
    public ExcelHeadTreeNode<T> setParentId(T parentId) {
        this.parentId = parentId;
        return this;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public ExcelHeadTreeNode<T> setName(CharSequence name) {
        this.name = name;
        return this;
    }

    @Override
    public Comparable<?> getWeight() {
        return weight;
    }

    @Override
    public ExcelHeadTreeNode<T> setWeight(Comparable<?> weight) {
        this.weight = weight;
        return this;
    }

    /**
     * 获取单元格样式
     * @return 单元格样式
     */
    public CellStyle getStyle() {
        return style;
    }

    /**
     * 设置单元格样式
     * @param style 单元格样式
     */
    public void setStyle(CellStyle style) {
        this.style = style;
    }

    /**
     * 获取扩展字段
     *
     * @return 扩展字段Map
     * @since 5.2.5
     */
    public Map<String, Object> getExtra() {
        return extra;
    }

    /**
     * 设置扩展字段
     *
     * @param extra 扩展字段
     * @return this
     * @since 5.2.5
     */
    public ExcelHeadTreeNode<T> setExtra(Map<String, Object> extra) {
        this.extra = extra;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExcelHeadTreeNode<?> treeNode = (ExcelHeadTreeNode<?>) o;
        return Objects.equals(id, treeNode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

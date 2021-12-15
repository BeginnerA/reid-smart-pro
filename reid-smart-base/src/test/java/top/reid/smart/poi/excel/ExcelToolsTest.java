package top.reid.smart.poi.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.poi.excel.ExcelWriter;
import org.junit.jupiter.api.Test;

import java.util.List;

class ExcelToolsTest {

    @Test
    void getIncludeTableHeadWriter() {
        // 构建导出表头
        List<ExcelHeadTreeNode<String>> nodeList = CollUtil.newArrayList();

        nodeList.add(new ExcelHeadTreeNode<>("1", "0", "检测项目", 1));
        nodeList.add(new ExcelHeadTreeNode<>("2", "0", "采样类型", 2));
        nodeList.add(new ExcelHeadTreeNode<>("3", "0", "点位数量", 3));
        nodeList.add(new ExcelHeadTreeNode<>("4", "0", "未检出数", 4));

        nodeList.add(new ExcelHeadTreeNode<>("5", "0", "顺序统计量", 5));
        nodeList.add(new ExcelHeadTreeNode<>(UUID.randomUUID().toString(), "5", "最小值", 1));
        nodeList.add(new ExcelHeadTreeNode<>(UUID.randomUUID().toString(), "5", "中位值", 2));
        nodeList.add(new ExcelHeadTreeNode<>(UUID.randomUUID().toString(), "5", "最大值", 3));

        nodeList.add(new ExcelHeadTreeNode<>("6", "0", "算术", 6));
        nodeList.add(new ExcelHeadTreeNode<>(UUID.randomUUID().toString(), "6", "平均值", 1));
        nodeList.add(new ExcelHeadTreeNode<>(UUID.randomUUID().toString(), "6", "标准差", 2));

        nodeList.add(new ExcelHeadTreeNode<>("7", "0", "几何", 7));
        nodeList.add(new ExcelHeadTreeNode<>(UUID.randomUUID().toString(), "7", "平均值", 1));
        nodeList.add(new ExcelHeadTreeNode<>(UUID.randomUUID().toString(), "7", "标准差", 2));

        nodeList.add(new ExcelHeadTreeNode<>("8", "0", "变异系数（%）", 8));
        nodeList.add(new ExcelHeadTreeNode<>("9", "0", "偏度系数", 9));
        nodeList.add(new ExcelHeadTreeNode<>("10", "0", "峰度系数", 10));

        // 构建数据表头树
        List<Tree<String>> trees = ExcelTools.buildExcelHeadData(nodeList, "0");

        // 自动化标题
        //ExcelWriter writer = ExcelTools.getIncludeTableHeadWriter(trees, "d:/test/temporary/自动化数据标题.xlsx");
        // 自动化标题
        ExcelWriter writer = ExcelTools.getIncludeTableHeadWriter(trees, "d:/test/temporary/自动化数据标题-头部对角线.xlsx", 2);
        // 关闭 writer，释放内存
        writer.close();
    }

    @Test
    void writeSecHeadRow() {
    }

    @Test
    void createHead() {
    }

    @Test
    void setSizeColumn() {
    }
}

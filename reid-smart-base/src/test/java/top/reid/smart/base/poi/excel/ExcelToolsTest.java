package top.reid.smart.base.poi.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.style.StyleUtil;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import top.reid.smart.base.core.lang.tree.TreeTools;
import java.util.*;

public class ExcelToolsTest {

    @Test
    public void getIncludeTableHeadWriter() {
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
        // 自动化标题-头部对角线
        ExcelWriter writer = ExcelTools.getIncludeTableHeadWriter(trees, "d:/test/temporary/自动化数据标题-头部对角线.xlsx", 2);
        // 关闭 writer，释放内存
        writer.close();
    }

    @Test
    public void createHead() {
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/test/temporary/自动生成复杂数据表头.xlsx");

        // 自定义单元格样式
        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
        cellStyle.setWrapText(false);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置标题内容字体
        Font font = writer.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 15);
        font.setFontName("Arial");
        //设置边框样式
        StyleUtil.setBorder(cellStyle, BorderStyle.THICK, IndexedColors.RED);
        cellStyle.setFont(font);

        // 构建导出表头
        List<ExcelHeadTreeNode<String>> nodeList = CollUtil.newArrayList();
        nodeList.add(new ExcelHeadTreeNode<>("1", "0", "工资表", 0, cellStyle));

        nodeList.add(new ExcelHeadTreeNode<>("11", "1", "编号", 1));
        nodeList.add(new ExcelHeadTreeNode<>("12", "1", "姓名", 2));
        nodeList.add(new ExcelHeadTreeNode<>("13", "1", "部门", 3));

        nodeList.add(new ExcelHeadTreeNode<>("14", "1", "应发栏", 4));

        nodeList.add(new ExcelHeadTreeNode<>("111", "14", "基本工资", 1));
        nodeList.add(new ExcelHeadTreeNode<>("1111", "111", "底薪", 1));
        nodeList.add(new ExcelHeadTreeNode<>("1112", "111", "全勤奖", 2));
        nodeList.add(new ExcelHeadTreeNode<>("1113", "111", "补助", 3));
        nodeList.add(new ExcelHeadTreeNode<>("1114", "111", "合计（元）", 4));

        nodeList.add(new ExcelHeadTreeNode<>("112", "14", "加班栏", 2));

        nodeList.add(new ExcelHeadTreeNode<>("1115", "112", "平时加班", 1));
        nodeList.add(new ExcelHeadTreeNode<>("11111", "1115", "工时（h）", 1));
        nodeList.add(new ExcelHeadTreeNode<>("11112", "1115", "金额（元）", 2));

        nodeList.add(new ExcelHeadTreeNode<>("1116", "112", "假日加班", 2));
        nodeList.add(new ExcelHeadTreeNode<>("11113", "1116", "工时（h）", 1));
        nodeList.add(new ExcelHeadTreeNode<>("11114", "1116", "金额（元）", 2));

        nodeList.add(new ExcelHeadTreeNode<>("1117", "112", "合计（元）", 3));

        nodeList.add(new ExcelHeadTreeNode<>("113", "14", "应发合计（元）", 3));

        nodeList.add(new ExcelHeadTreeNode<>("15", "1", "扣款栏", 5));

        nodeList.add(new ExcelHeadTreeNode<>("114", "15", "请假栏", 1));
        nodeList.add(new ExcelHeadTreeNode<>("1118", "114", "天数", 1));
        nodeList.add(new ExcelHeadTreeNode<>("1119", "114", "扣款（元）", 2));

        nodeList.add(new ExcelHeadTreeNode<>("115", "15", "迟到栏", 2));
        nodeList.add(new ExcelHeadTreeNode<>("1119-0", "115", "迟到（次）", 1));
        nodeList.add(new ExcelHeadTreeNode<>("1119-1", "115", "扣款（元）", 2));

        nodeList.add(new ExcelHeadTreeNode<>("116", "15", "其它扣款（元）", 3));
        nodeList.add(new ExcelHeadTreeNode<>("117", "15", "全勤奖（元）", 4));
        nodeList.add(new ExcelHeadTreeNode<>("118", "15", "合计（元）", 5));

        nodeList.add(new ExcelHeadTreeNode<>("16", "1", "实发工资", 6));
        nodeList.add(new ExcelHeadTreeNode<>("17", "1", "签字", 7));

        List<Tree<String>> trees = ExcelTools.buildExcelHeadData(nodeList, "0");
        int i = TreeTools.sameLevelMaxDepth(trees);
        ExcelTools.createHead(writer, trees, i, 0);
        writer.setCurrentRow(i);
        // hutool 自适应无效
        ExcelTools.setSizeColumn(writer);
        // 关闭writer，释放内存
        writer.close();
    }

    @Test
    public void setSizeColumn() {
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/test/temporary/单元格自适应.xlsx");
        writer.merge(1, "来啦老弟！来啦老弟！来啦老弟！来啦老弟！来啦老弟！来啦老弟！来啦老弟！来啦老弟！");
        // hutool 自适应无效
        ExcelTools.setSizeColumn(writer);
        // 关闭writer，释放内存
        writer.close();
    }

    @Test
    public void onlyRowMergeWrite() {
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/test/temporary/指定列同值行合并.xlsx");

        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
        cellStyle.setWrapText(false);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置标题内容字体
        Font font = writer.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 15);
        font.setFontName("Arial");
        //设置边框样式
        StyleUtil.setBorder(cellStyle, BorderStyle.THICK, IndexedColors.RED);
        cellStyle.setFont(font);

        // 构建导出表头
        List<ExcelHeadTreeNode<String>> nodeList = CollUtil.newArrayList();
        nodeList.add(new ExcelHeadTreeNode<>("1", "0", "土壤", 0, cellStyle));

        nodeList.add(new ExcelHeadTreeNode<>("11", "1", "无污染", 1));
        nodeList.add(new ExcelHeadTreeNode<>("111", "11", "地块数", 1));
        nodeList.add(new ExcelHeadTreeNode<>("1111", "11", "比例（%）", 2));

        nodeList.add(new ExcelHeadTreeNode<>("12", "1", "轻微污染", 2));
        //nodeList.add(new HeadTreeNode<>("112", "12", "地块数", 1));
        //nodeList.add(new HeadTreeNode<>("1112", "12", "比例（%）", 2));

        nodeList.add(new ExcelHeadTreeNode<>("13", "1", "轻度污染", 3));
        nodeList.add(new ExcelHeadTreeNode<>("113", "13", "地块数", 1));
        nodeList.add(new ExcelHeadTreeNode<>("1113", "13", "比例（%）", 2));

        nodeList.add(new ExcelHeadTreeNode<>("14", "1", "中度污染", 4));
        //nodeList.add(new HeadTreeNode<>("114", "14", "地块数", 1));
        //nodeList.add(new HeadTreeNode<>("1114", "14", "比例（%）", 2));

        List<Tree<String>> trees = ExcelTools.buildExcelHeadData(nodeList, "0");
        int i = TreeTools.sameLevelMaxDepth(trees);
        ExcelTools.createHead(writer, trees, i, 0);
        writer.setCurrentRow(i);

        // data
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("1", "1.1", "2", "3", "3.3", "4"));
        lists.add(Arrays.asList("1", "1.1", "2", "3", "3.3", "4"));
        lists.add(Arrays.asList("1", "1.1", "1", "3", "3.3", "4"));

        ExcelTools.onlyRowMergeWrite(writer, lists, 5);
        // 关闭writer，释放内存
        writer.close();
    }

    @Test
    public void test1() {
        Map<String, Object> row1 = new HashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateUtil.date());

        Map<String, Object> row2 = new HashMap<>();
        row2.put("姓名", "李四");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", DateUtil.date());


        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/test/temporary/writeMapTest.xlsx");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "一班成绩单");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();
    }
}

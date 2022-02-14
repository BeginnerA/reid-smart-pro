package top.reid.smart.geo;

import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

class GeoToolsTest {

    String geoJsonStr = "{\n" +
            "            \"type\":\"Point\",\n" +
            "            \"coordinates\":[105.380859375,31.57853542647338]\n" +
            "            }";

    String geoJsonStr2 = "{\"type\":\"Feature\",\n" +
            "        \"properties\":{},\n" +
            "        \"geometry\":{\n" +
            "            \"type\":\"Point\",\n" +
            "            \"coordinates\":[105.380859375,31.57853542647338]\n" +
            "            }\n" +
            "        }";

    String geoJsonStr3 = "{\n" +
            "  \"type\": \"FeatureCollection\",\n" +
            "  \"features\": [\n" +
            "        {\"type\":\"Feature\",\n" +
            "        \"properties\":{},\n" +
            "        \"geometry\":{\n" +
            "            \"type\":\"Point\",\n" +
            "            \"coordinates\":[105.380859375,31.57853542647338]\n" +
            "            }\n" +
            "        },\n" +
            "\t\t{\"type\":\"Feature\",\n" +
            "        \"properties\":{},\n" +
            "        \"geometry\":{\n" +
            "            \"type\":\"Point\",\n" +
            "            \"coordinates\":[105.380859375,31.57853542647338]\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    String[] wktStr = new String[]{"POINT(6 10)", "LINESTRING(3 4,10 50,20 25)", "POLYGON((1 1,5 1,5 5,1 5,1 1),(2 2,2 3,3 3,3 2,2 2))",
            "MULTIPOINT(3.5 5.6, 4.8 10.5)", "MULTILINESTRING((3 4,10 50,20 25),(-5 -8,-10 -8,-15 -4))", "MULTIPOLYGON(((1 1,5 1,5 5,1 5,1 1),(2 2,2 3,3 3,3 2,2 2)),((6 3,9 2,9 4,6 3)))",
            "GEOMETRYCOLLECTION(POINT(4 6),LINESTRING(4 6,7 10))", "POINT ZM (1 1 5 60)", "POINT M (1 1 80)"};

    @Test
    void getGeometryType() {
        for (String s : wktStr) {
            System.out.println("Geo 几何类型：" + GeoTools.getGeometryType(s));
        }
    }

    @Test
    void isWkt() {
        for (String s : wktStr) {
            System.out.println("是规范的 WKT：" + GeoTools.isWkt(s));
        }
    }

    @Test
    void isNotWkt() {
        for (String s : wktStr) {
            System.out.println("不是规范的 WKT：" + GeoTools.isNotWkt(geoJsonStr2));
        }
    }

    @Test
    void isGeoJson() {
        System.out.println("是规范的 GeoJson：" + GeoTools.isGeoJson(geoJsonStr3));
    }

    @Test
    void isNotGeoJson() {
        System.out.println("不是规范的 GeoJson：" + GeoTools.isNotGeoJson(geoJsonStr3));
    }

    @Test
    void geoJsonToWkt() {
        System.out.println("GeoJson 转 WKT：" + GeoTools.geoJsonToWkt(JSONUtil.parseObj(geoJsonStr)));
        System.out.println("GeoJson 转 WKT：" + GeoTools.geoJsonToWkt(JSONUtil.parseObj(geoJsonStr2)));
        System.out.println("GeoJson 转 WKT：" + GeoTools.geoJsonToWkt(JSONUtil.parseObj(geoJsonStr3)));
    }

    @Test
    void wktToGeoJson() {
        for (String s : wktStr) {
            System.out.println("WKT 转 GeoJson：" + GeoTools.wktToGeoJson(s));
        }
    }

    @Test
    void wktToFeature() {
        for (String s : wktStr) {
            System.out.println("WKT 转 Feature：" + GeoTools.wktToFeature(s));
        }
    }

    @Test
    void wktToFeatureCollection() {
        for (String s : wktStr) {
            System.out.println("WKT 转 FeatureCollection：" + GeoTools.wktToFeatureCollection(s));
        }
    }

    @Test
    void readSHP() throws Exception {
        Map<String, List> stringListMap = GeoTools.readSHP(new File("E:\\信飞科技\\项目\\矿产资源规划\\530112西山区矿产资源规划数据库\\ArcGIS\\Shape\\D1_530112_E_2020_GJGHKQ.shp"));
        stringListMap.forEach((k, v) -> {
            System.out.println(v);
        });
    }
}

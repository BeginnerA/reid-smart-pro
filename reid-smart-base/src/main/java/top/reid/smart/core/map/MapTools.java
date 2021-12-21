package top.reid.smart.core.map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.TextSimilarity;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * Map 相关工具类
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2021/12/21
 * @Version V1.0
 **/
public class MapTools extends MapUtil {

    /**
     * 相似度，为了匹配的准确度相似度值最少在0.8以上
     */
    private static final double SIMILARITY = 0.9;

    /**
     * map 按 key 升序排序
     * @param map 需排序map
     * @param <K> K
     * @param <V> V
     * @return 升序后的map
     */
    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map) {
        return sortByKey(map, false);
    }

    /**
     * map 按 key 降序排序
     * @param map 需排序map
     * @param <K> K
     * @param <V> V
     * @return 降序后的map
     */
    public static <K extends Comparable<? super K>, V>Map<K, V> sortByKeyDesc(Map<K, V> map) {
        return sortByKey(map, true);
    }

    /**
     * map 按 key 升序排序
     * @param map 需排序map
     * @param <K> K
     * @param <V> V
     * @return 升序后的map
     */
    public static <K, V extends Comparable<? super V>>Map<K, V> sortByValue(Map<K, V> map) {
        return sortByValue(map, false);
    }

    /**
     * map 按 key 降序排序
     * @param map 需排序map
     * @param <K> K
     * @param <V> V
     * @return 降序后的map
     */
    public static <K, V extends Comparable<? super V>>Map<K, V> sortByValueDesc(Map<K, V> map) {
        return sortByValue(map, true);
    }

    /**
     * map 按 key 升序排序
     * @param map 需排序map
     * @param isDesc 是否降序：true：降序，false：升序
     * @return 排序后的map
     */
    public static <K extends Comparable<? super K>, V>Map<K, V> sortByKey(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = new LinkedHashMap<>(map.size());
        if (isDesc) {
            map.entrySet().stream()
                    .sorted(Map.Entry.<K, V>comparingByKey().reversed())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }else {
            map.entrySet().stream()
                    .sorted(Map.Entry.<K, V>comparingByKey())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }

        return result;
    }

    /**
     * 根据 map 的 value 排序
     * @param map 待排序的 map
     * @param isDesc 是否降序：true：降序，false：升序
     * @return 排序好的map
     */
    public static <K, V extends Comparable<? super V>>Map<K, V> sortByValue(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = new LinkedHashMap<>(map.size());
        if (isDesc) {
            map.entrySet().stream()
                    .sorted(Map.Entry.<K, V>comparingByValue().reversed())
                    .forEach(e -> result.put(e.getKey(), e.getValue()));
        } else {
            map.entrySet().stream()
                    .sorted(Map.Entry.<K, V>comparingByValue())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }

    /**
     * 指定排序字段对 mapList 升序排序
     * @param mapList mapList
     * @param sortField 排序字段
     */
    public static <V> void sortMapListAsc(List<Map<String, V>> mapList, String sortField) {
        sortMapList(mapList, sortField, false);
    }

    /**
     * 指定排序字段对 mapList 降序排序
     * @param mapList mapList
     * @param sortField 排序字段
     */
    public static <V> void sortMapListDesc(List<Map<String, V>> mapList, String sortField) {
        sortMapList(mapList, sortField, true);
    }

    /**
     * 指定排序字段对 mapList 排序
     * @param mapList mapList
     * @param sortField 排序字段
     * @param isDesc 是否降序：true：降序，false：升序
     */
    public static <V> void sortMapList(List<Map<String, V>> mapList, String sortField, boolean isDesc) {
        mapList.sort(new Comparator<Map<String, V>>() {
            @Override
            public int compare(Map<String, V> o1, Map<String, V> o2) {
                String v1 = o1.get(sortField).toString();
                String v2 = o2.get(sortField).toString();
                if (isDesc) {
                    try {
                        return Integer.valueOf(v2).compareTo(Integer.valueOf(v1));
                    }catch (NumberFormatException e) {
                        // 不是数值类型将 mapList 集合按照 Map 的 name 进行排序(c>b>a ...)
                        Collator instance = Collator.getInstance(Locale.CHINA);
                        return instance.compare(v2, v1);
                    }
                }else {
                    try {
                        return Integer.valueOf(v1).compareTo(Integer.valueOf(v2));
                    }catch (NumberFormatException e) {
                        // 不是数值类型将 mapList 集合按照 Map 的 name 进行排序(a>b>c ...)
                        Collator instance = Collator.getInstance(Locale.CHINA);
                        return instance.compare(v1, v2);
                    }
                }
            }
        });
    }

    /**
     * 获取map中第一个非空数据值
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> V getFirstNotNull(Map<K, V> map) {
        V obj = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }

    /**
     * 获取 Map 中相似度大于{@link #SIMILARITY}的 key
     * @param map Map
     * @param reference 参考值
     * @return 相似度最大的key
     */
    public static <K, V> K similarKey(Map<K, V> map, String reference) {
        return similarKey(map, reference, SIMILARITY);
    }

    /**
     * 获取 Map 中相似度大于{@link #SIMILARITY}的 key
     * @param map Map
     * @param reference 参考值
     * @param similarity 相似度：最大1.0，最小是0.0
     * @return 相似度最大的 key
     */
    public static <K, V> K similarKey(Map<K, V> map, String reference, double similarity) {
        Map<Double, K> m = new HashMap<>(map.size());
        for (K key : map.keySet()) {
            double similar = TextSimilarity.similar(Convert.toStr(key), reference);
            if (similar == 1) {
                return key;
            }else if (similar > similarity) {
                m.put(similar, key);
            }
        }
        if (CollUtil.isEmpty(m)) {
            return null;
        }
        return getFirstNotNull(sortByKey(m));
    }

    /**
     * mapList 转 Map
     * @param mapList mapList
     * @param keyField Map Key字段
     * @param valueField Map value字段
     * @param <K> key
     * @param <V> value
     * @return 转后的Map
     */
    public static <K, V> Map<String, V> listMapToMap(List<Map<K, V>> mapList, String keyField, String valueField) {
        return mapList.stream().collect(Collectors.toMap(m-> Convert.toStr(m.get(keyField)), m->m.get(valueField)));
    }

    /**
     * 对 mapList 通过指定 Map key 排名
     * @param mapList mapList
     * @param rankField Map key 排名字段
     * @return 新的有排名字段的 mapList
     */
    public static List<Map<String, Object>> rank(List<Map<String, Object>> mapList, String rankField) {
        List<Map.Entry<Double, List<Map<String, Object>>>> entries = mapList.stream().collect(Collectors.groupingBy(m -> Convert.toDouble(m.get(rankField)))).entrySet().stream().sorted((s1, s2) -> -Double.compare(s1.getKey(), s2.getKey())).collect(Collectors.toList());
        // 设置排名
        int rank = 1;
        // 设置排序号
        int index = 1;
        for (Map.Entry<Double, List<Map<String, Object>>> entry : entries) {
            for (Map<String, Object> map : entry.getValue()) {
                map.put("rank", rank);
                map.put("index", index++);
            }
            rank++;
        }
        return mapList;
    }
}

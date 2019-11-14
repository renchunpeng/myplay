package com.cpren.utils.yw;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe 集合操作工具类
 * @author y.you
 * @date 2018年11月12日 20:50
 * @version V5.0
 */
public class YWListUtils {

    /**
     * 禁止实例化
     */
    private YWListUtils() {

    }

    /**
     * @describe 按指定大小，分割集合，将集合按规定个数分为n个部分
     * @author y.you
     * @date 2018年11月12日 20:50
     * @version V5.0
     * @param list  被分割的集合
     * @param len   分割长度
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

}

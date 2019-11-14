package com.cpren.utils.yw;

import java.util.Map;

/**
 * @describe Map工具类
 * @author cp.ren
 * @date 2019-06-24 16:25:23
 * @version V5.0
 **/
public class YWMapUtils {

    /**
     * @describe 根据value获取key
     * @author cp.ren
     * @date 2019-06-25 09:05:18
     * @param map map对象
     * @param v value值
     * @return
     * @version V5.0
     **/
    public static Long getValueByKey(Map<Long, String> map, String v) {
        Long key = null;
        for (Map.Entry<Long, String> m : map.entrySet()) {
            if(m.getValue().equals(v)){
                key = m.getKey();
            }
        }
        return key;
    }
}

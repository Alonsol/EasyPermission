package com.yy.permissioinlib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuyang on 2020/6/14.
 * function: 数据转换工具类
 */
public class NumUtils {

    public static String[] var2Array(String... args) {
        String[] array = new String[args.length];
        System.arraycopy(args, 0, array, 0, args.length);
        return array;
    }


    public static String[] map2Array(HashMap<String, String> map) {
        ArrayList<String> datas = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry != null) {
                datas.add(entry.getKey());
            }
        }

        String[] array = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            array[i] = datas.get(i);
        }
        return array;
    }
}

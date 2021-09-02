package com.selflearning.tw;


import com.selflearning.tw.vo.Album;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("When running EqualsAndHashCodeTests")
public class EqualsAndHashCodeTests {

    @Test
    @DisplayName("equals test")
    void equalsTest(){

        Album a1 = new Album(1, "ooh-ahh");
        Album a2 = new Album(1, "ooh-ahh");
//        Album a2 = new Album(2, "cheer up");
        System.out.println(a1 + " / " + a2);
        System.out.println(a1.hashCode() + " / " + a2.hashCode());
        System.out.println("兩個物件是否相等: " + a1.equals(a2));
        System.out.println("兩個物件的地址是否相等: " + (a1 == a2));

    }

    @Test
    @DisplayName("hashMap put/get test")
    void hashMap_put_get_test(){
        Map<Album, Integer> map = new HashMap();
        Album a1 = new Album(1, "ooh-ahh");
        Integer first = map.put(a1, 1); // 返回該key 之前的value, 返回null 代表沒有該key 或該key的值就是null
        System.out.println(first);

        Integer second = map.put(a1, 2);
        System.out.println(second);

        System.out.println(map.get(a1));
    }

    @Test
    @DisplayName("hashMap hashCode/equals test")
    void hashMap_hashCode_equals_test(){
        Map<Album, Integer> map = new HashMap();
        Album a1 = new Album(1, "ooh-ahh");
        Album a2 = new Album(1, "ooh-ahh");
        Album a3 = new Album(2, "ooh-ahh");
        map.put(a1, 1);
        System.out.println("###################### 1");
        map.put(a2, 2);
        System.out.println("###################### 2");
        map.put(a3, 3);
        System.out.println("###################### 3");
        System.out.println(map.get(a1));
        System.out.println("###################### 4");
        System.out.println(map.get(a2));
        System.out.println("###################### 5");
        System.out.println(map.get(a3));

    }
}

package com.selflearning.tw;


import com.selflearning.tw.vo.Album;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Test
    @DisplayName("list stream groupby test")
    void streamGroupByTest(){
        BlogPost b1 = new BlogPost("t1", "a1", "type1",1);
        BlogPost b2 = new BlogPost("t2", "a1", "type2",2);
        BlogPost b3 = new BlogPost("t3", "a2", "type1",3);
        BlogPost b4 = new BlogPost("t4", "a2", "type1",4);
        List<BlogPost> list = new ArrayList<>();
        list.add(b1);
        list.add(b4);
        list.add(b2);
        list.add(b3);
        list.forEach(System.out::println);

        System.out.println("========================================================");
        list.stream().collect(Collectors.groupingBy(BlogPost::getType))
                .forEach((k,v) -> System.out.println(k + "|" + v));
        System.out.println("========================================================");
        Map<String, Map<String, List<BlogPost>>> map = list.stream()
                .collect(Collectors.groupingBy(BlogPost::getAuthor, Collectors.groupingBy(BlogPost::getType)));
        System.out.println(map);
        System.out.println("=========================== Group by Multi Fields =============================");
        Map<String, BlogPost> groupByTypeGetMaxLikeMap = list.stream()
                .collect(Collectors.toMap(BlogPost::getType, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(BlogPost::getLikes))
                ));
//                .collect(Collectors.groupingBy(BlogPost::getType,
////                                    Collectors.mapping()
//                                Collectors.maxBy(Comparator.comparing(BlogPost::getLikes))
//
//                        )
//                );

        System.out.println(groupByTypeGetMaxLikeMap);
        System.out.println("=========================== Final =============================");
        list.stream().peek(l -> {
            if(groupByTypeGetMaxLikeMap.containsKey(l.getType())){
                l.setLikes(groupByTypeGetMaxLikeMap.get(l.getType()).getLikes());
            }
        }).forEach(System.out::println);

//                .forEach((k,v) -> {
//                    System.out.println(k + " @@@@ " + v);
//                    if (list.forEach(l -> l.getType().contains(k))) {
//                        System.out.println(v);
//                    } else {
//                        System.out.println("not contain");
//                    }
//                });

    }


    @Test
    @DisplayName("functional interface test")
    void functionalTest(){
        ArrayList<Integer> numberList
                = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        numberList.forEach(action);
    }

    private final Consumer<Integer> action = i -> {
        if (i % 2 == 0) {
            System.out.println("Even number :: " + i); //Or any other user action we want to do
        } else {
            System.out.println("Odd  number :: " + i);  //Or any other user action we want to do
        }
    };

}

@Data
class BlogPost {
    String title;
    String author;
    String type;
    Integer likes;

    public BlogPost(String t1, String a1, String type1, Integer i) {
        title = t1;
        author = a1;
        type = type1;
        likes = i;
    }
}

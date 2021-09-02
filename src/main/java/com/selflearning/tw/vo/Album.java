package com.selflearning.tw.vo;

import lombok.Data;

import java.util.Objects;

//@Data
public class Album {

    private Integer number;
    private String albumName;

    public Album(Integer number, String albumName) {
        this.number = number;
        this.albumName = albumName;
    }

    // 重寫equals() -> 物件中所有內容都一致就表示相等。
    // 原本 Object中的equals() 是比較兩個物件的地址是否相等(this == that)。
    @Override
    public boolean equals(Object o) {
        System.out.print(" ===== equals ===== ");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        System.out.println(String.format("(%s , %s) vs (%s , %s)", number,albumName,album.number,album.albumName));
        return Objects.equals(number, album.number) && Objects.equals(albumName, album.albumName);
    }

    // hashCode 是 hash(物件的地址) 產生的一串數值。
    // hashCode() 只有在集合中，物件作為key時才會用到，e.g. HashMap,HashTable,HashSet
    //
    // e.g. HashMap<物件, String>，物件為key時，
    // put: 會將該物件的hashCode存放到雜湊表中，雜湊表 -> hashCode : 物件。以便快速查找。
    // get: 利用該物件計算出hashCode後，到雜湊表中查找該hashCode是否存在，
    //
    // (1) put(物件, String) -> 對key調用 hashCode() 方法 -> 得到hash值
    // 實際上在 HashMap中會保存每個對象的hash值，如果雜湊表中沒有該hash值，就直接存入；
    // 如果存在該hash值就接著調用equals方法與新物件進行比較，相同的話就用新值覆蓋，
    // 不相同(碰撞: 不同的物件碰巧產生相同的hashCode)就加到LinkedList中。大幅降低調用equals方法的次數。
    //
    // (2) 判斷要放入的對象的hashCode 與雜湊表中任一hashCode是否相等。不相等就直接塞入
    // (3) 如果有相等的hashCode，再用equals 方法判斷要放入的對象與
    // 該key下的value集合(LinkedList)中是否有內容相同的對象? 覆蓋: 放入集合中
    @Override
    public int hashCode() {
        System.out.println(number + ", " + albumName + " ===== hashCode: " + Objects.hash(number, albumName));
        return Objects.hash(number, albumName);
    }
}

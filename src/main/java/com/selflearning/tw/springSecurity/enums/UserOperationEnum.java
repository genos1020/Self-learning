package com.selflearning.tw.springSecurity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public enum UserOperationEnum {

    // API名稱對應中文操作
    USER_LOGIN("userLogin", "使用者登入"),
    USER_LOGOUT("userLogout", "使用者登出"),
    INIT_STATION_TABLE("initStationTable","初始化場站列表"),
    INIT_UPGRADE_TABLE("initUpgradeTable","初始化場站升級列表"),
    GET_FOLDER_NAMES("getFolderNames","取得核心版本資料夾名稱"),
    CREATE_STATION_INFO("createStationInfo","創建場站"),
    MODIFY_STATION_INFO("modifyStationInfo","修改場站資訊"),
    UPDATE_STATION_INFO("updateStationInfo","上傳場站核心版本"),
    NOTIFY_SYSTEM_UPDATE("notifySystemUpdate","通知場站系統更新"),
    GET_TOTAL_COUNT("getTotalCount","取得總場站數"),
    GET_WAIT_OPEN_COUNT("getWaitOpenCount","取得等待開啟場站數"),
    GET_CLOSE_COUNT("getCloseCount","取得關閉案場數"),
    GET_UNUSUAL_COUNT("getUnusualCount","取得場站狀態異常數"),
    GET_PARKING_TYPE_COUNT("getParkingTypeCount","取得場站類別分布"),
    GET_VERSION_COUNT("getVersionCount","取得版本分布"),
    GET_RECENT_UPDATE("getRecentUpdate","取得近期更新"),
    GET_BUILDING_LIST("getBuildingList","取得尚未開啟的場站列表"),
    GET_ALL_USER("getAllUser","取得所有使用者"),
    GET_USER("getUser","取得某使用者"),
    ADD_USER("addUser","新增使用者"),
    UPDATE_USER("updateUser","更新使用者密碼或權限"),
    DELETE_USER("deleteUser","刪除使用者"),
    GET_ALL_ROLES("getAllRoles","取得所有角色列表"),
    GET_PHOTOS("getPhotos","取得所有照片"),
    GET_PHOTO("getPhoto","取得某照片"),
    ADD_PHOTO("addPhoto","新增照片");

    @Getter
    private String action;
    @Getter
    private String description;

    private static final Map<String, UserOperationEnum> actionMap = new LinkedHashMap<>();
    static {
        for (UserOperationEnum uoe : UserOperationEnum.values()) {
            actionMap.put(uoe.getAction(), uoe);
        }
    }

    public static UserOperationEnum findByAction(String action) {
        return actionMap.get(action);
    }
}

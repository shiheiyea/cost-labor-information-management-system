package com.heiye.clims.framework.common.util;

import java.util.regex.Pattern;

/**
 * @author: 犬小哈
 * @date: 2024/4/15 16:42
 * @version: v1.0.0
 * @description: 参数条件工具
 **/
public final class ParamUtils {
    private ParamUtils() {
    }

    // ============================== 校验昵称 ==============================
    // 定义昵称长度范围
    private static final int NICK_NAME_MIN_LENGTH = 2;
    private static final int NICK_NAME_MAX_LENGTH = 24;

    // 定义特殊字符的正则表达式
    public static final String NICK_NAME_REGEX = "[!@#$%^&*(),.?\":{}|<>]";

    /**
     * 昵称校验
     *
     * @param nickname
     * @return
     */
    public static boolean checkNickname(String nickname) {
        // 检查长度
        if (nickname.length() < NICK_NAME_MIN_LENGTH || nickname.length() > NICK_NAME_MAX_LENGTH) {
            return false;
        }

        // 检查是否含有特殊字符
        Pattern pattern = Pattern.compile(NICK_NAME_REGEX);
        return !pattern.matcher(nickname).find();
    }

    // ============================== 校验用户名 ==============================
    // 定义 ID 长度范围
    private static final int USER_NAME_MIN_LENGTH = 4;
    private static final int USER_NAME_MAX_LENGTH = 16;

    // 定义正则表达式
    public static final String USER_NAME_REGEX = "^[a-zA-Z0-9]{4,16}$";

    /**
     * 用户名校验
     *
     * @param username
     * @return
     */
    public static boolean checkUserName(String username) {
        // 检查长度
        if (username.length() < USER_NAME_MIN_LENGTH || username.length() > USER_NAME_MAX_LENGTH) {
            return false;
        }
        // 检查格式
        Pattern pattern = Pattern.compile(USER_NAME_REGEX);
        return pattern.matcher(username).matches();
    }

    /**
     * 字符串长度校验
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean checkLength(String str, int length) {
        // 检查长度
        if (str.isEmpty() || str.length() > length) {
            return false;
        }
        return true;
    }

    // ============================== 校验邮箱 ==============================
    // 定义正则表达式
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 邮箱校验
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        // 检查格式
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    // ============================== 校验密码 ==============================
    // 定义正则表达式
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$";

    /**
     * 密码校验
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        // 检查格式
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        return pattern.matcher(password).matches();
    }
}

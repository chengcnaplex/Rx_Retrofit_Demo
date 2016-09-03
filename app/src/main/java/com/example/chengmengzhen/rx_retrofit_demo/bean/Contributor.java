package com.example.chengmengzhen.rx_retrofit_demo.bean;

/**
 * Created by chengmengzhen on 16/7/5.
 */
public class Contributor {
    public final String login;
    public final int contributions;

    /**
     * 如下是访问(https://api.github.com/repos/square/retrofit/contributors)返回的数据格式
     * 这里只取json数据的2个字段
     * [
     * {
     * "login": "JakeWharton",
     * ......
     * "contributions": 795
     * },
     * {
     * "login": "swankjesse",
     * ......
     * "contributions": 174
     * },
     * {
     * "login": "pforhan",
     * ......
     * "contributions": 48
     * },
     * ......
     * ]
     * @param login
     * @param contributions
     */
    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }

}

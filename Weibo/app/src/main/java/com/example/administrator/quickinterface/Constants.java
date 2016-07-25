package com.example.administrator.quickinterface;

/**
 * Created by Administrator on 2016/7/20.
 */
public class Constants {

        public static final String APP_KEY      = "397564444";		   // 应用的APP_KEY
        public static final String REDIRECT_URL = "http://www.sina.com.cn";// 应用的回调页
        public static final String SCOPE = 							   // 应用申请的高级权限
                "email,direct_messages_read,direct_messages_write,"
                        + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                        + "follow_app_official_microblog," + "invitation_write";
        public static final String BASE_URL_STATUSES="https://api.weibo.com/2/statuses/";
}

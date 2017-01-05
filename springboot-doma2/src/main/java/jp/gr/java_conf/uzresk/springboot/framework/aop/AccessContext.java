package jp.gr.java_conf.uzresk.springboot.framework.aop;

import jp.gr.java_conf.uzresk.springboot.framework.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AccessContext {

    private static final String KEY_SYSTEM_DATE = "systemDate";

    private static final String KEY_USERNAME = "userName";

    private static final ThreadLocal<Map<String, Object>> HOLDER = new ThreadLocal<>();

    private static void init() {
        HOLDER.set(new HashMap<>());
    }

    static void destroy() {
        HOLDER.remove();
    }

    private static Map<String, Object> getHolder() {
        return HOLDER.get();
    }

    private static void setHolder(String key, Object value) {
        if (HOLDER.get() == null) {
            init();
        }

        if (StringUtils.isNotEmpty(key) && value != null) {
            HOLDER.get().put(key, value);
        }
    }

    private static Object getValue(String key) {
        if (getHolder() == null) {
            return "";
        }
        return getHolder().get(key);
    }


    static void setSystemDate() {
        setSystemDate(DateUtils.getSysdate());
    }

    public static void setSystemDate(LocalDateTime systemDate) {
        setHolder(KEY_SYSTEM_DATE, systemDate);
    }

    public static LocalDateTime getSystemDate() {
        return (LocalDateTime) getValue(KEY_SYSTEM_DATE);
    }

    static void setUserName() {
        setUserName(getUserNameFromSecurityContext());
    }

    public static void setUserName(String userName) {
        setHolder(KEY_USERNAME, userName);
    }

    public static String getUserName() {
        return (String) getValue(KEY_USERNAME);
    }

    private static String getUserNameFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            // 認証前ならAnonymous
            return "Anonymous";
        } else {
            // 認証後ならprincipalのusername
            return ((User) authentication.getPrincipal()).getUsername();
        }
    }
}

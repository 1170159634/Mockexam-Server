/**
 * https://www.leimingtech.com
 * <p>
 * 版权所有，侵权必究！
 */

package me.zhengjie.modules.mockexam.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;


/**
 * 国际化
 *
 *
 * @return:
 * @Author: Mingxuan_X
 * @Date: 2021/4/11
 */
public class MessageUtils {
    private MessageUtils() {
    }

    private static MessageSource messageSource;

    static {
        messageSource = (MessageSource) SpringContextUtils.getBean("messageSource");
    }

    public static String getMessage(int code) {
        return getMessage(code, new String[0]);
    }

    public static String getMessage(int code, String... params) {
        return messageSource.getMessage(code + "", params, LocaleContextHolder.getLocale());
    }
}

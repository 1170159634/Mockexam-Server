package me.zhengjie.modules.mockexam.constant.enums;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 驾考题科目
 * @Telephone :      15135964789
 * @createDate :     2021/4/10 15:29
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/10 15:29
 * @updateRemark :   修改内容
 **/

public enum SubjectTypeEnum {

    //科目1
    SUBJECT_ONE(1, "1"),
    //科目四
    SUBJECT_FOUR(4, "4");
    private Integer code;

    private String msg;

    SubjectTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getEnumMessage(Integer value) {
        SubjectTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getCode().equals(value)) {
                return crc[i].getMsg();
            }
        }
        return null;
    }
    public static Integer getEnumCode(String value) {
        SubjectTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getMsg().equals(value)) {
                return crc[i].getCode();
            }
        }
        return null;
    }
}

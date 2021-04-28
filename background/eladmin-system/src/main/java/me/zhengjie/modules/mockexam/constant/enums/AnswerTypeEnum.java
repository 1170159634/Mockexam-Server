package me.zhengjie.modules.mockexam.constant.enums;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 正确答案类型
 * code:对应数据库字段
 * msg: 1:A 2 B  3:C 4:D
 * @Telephone :      15135964789
 * @createDate :     2021/4/12 9:36
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/12 9:36
 * @updateRemark :   修改内容
 **/
public enum AnswerTypeEnum {

    A("1", "1"),
    B("2", "2"),
    C("3", "3"),
    D("4", "4"),
    AB("7", "12"),
    AC("8", "13"),
    AD("9", "14"),
    BC("10", "23"),
    BD("11", "24"),
    CD("12", "34"),
    ABC("13", "123"),
    ABD("14", "124"),
    ACD("15", "134"),
    BCD("16", "234"),
    ABCD("17", "1234");

    private String code;

    private String msg;

    AnswerTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getEnumMessage(String value) {
        AnswerTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getCode().equals(value)) {
                return crc[i].getMsg();
            }
        }
        return null;
    }

    public static String getEnumCode(String value) {
        AnswerTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getMsg().equals(value)) {
                return crc[i].getCode();
            }
        }
        return null;
    }


}

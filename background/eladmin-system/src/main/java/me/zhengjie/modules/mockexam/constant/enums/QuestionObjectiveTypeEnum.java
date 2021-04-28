package me.zhengjie.modules.mockexam.constant.enums;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 试题所属客观题类型
 * @Telephone :      15135964789
 * @createDate :     2021/4/13 14:14
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/13 14:14
 * @updateRemark :   修改内容
 **/
public enum QuestionObjectiveTypeEnum {


    //小车手动挡
    SINGLE(1, "单选题"),
    //小车自动挡
    MULTPIE(2, "多选题"),
    //货车
    JUDGE(3, "判断题");


    private Integer code;

    private String msg;

    QuestionObjectiveTypeEnum(Integer code, String msg) {
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

    public static String getEnumMessage(String value) {
        QuestionObjectiveTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getCode().equals(value)) {
                return crc[i].getMsg();
            }
        }
        return null;
    }

    public static Integer getEnumCode(String value) {
        QuestionObjectiveTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getMsg().equals(value)) {
                return crc[i].getCode();
            }
        }
        return null;
    }

}

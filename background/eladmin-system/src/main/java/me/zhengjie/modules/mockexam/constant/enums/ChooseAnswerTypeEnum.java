package me.zhengjie.modules.mockexam.constant.enums;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 试题选择对或错
 * @Telephone :      15135964789
 * @createDate :     2021/4/12 10:26
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/12 10:26
 * @updateRemark :   修改内容
 **/
public enum ChooseAnswerTypeEnum {

    RIGNT(0, "正确"),
    WORING(1, "错误");
    private Integer code;

    private String msg;

    ChooseAnswerTypeEnum(Integer code, String msg) {
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

    public static String getEnumMessage(Integer code) {
        ChooseAnswerTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getCode().equals(code)) {
                return crc[i].getMsg();
            }
        }
        return null;
    }

    public static Integer getEnumCode(String value) {
        ChooseAnswerTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getMsg().equals(value)) {
                return crc[i].getCode();
            }
        }
        return null;
    }

}

package com.ih2ome.sunflower.vo.thirdVo.sms.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/3
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum SMSCodeEnum {

    //【水滴管家】欢迎入住,您的开门密码是@var(password)，有效期自@var(startTime)至@var(endTime)止，在门锁按键输入“密码+#”后，便可开门。感谢您使用果加互联网智能锁
    ADD_OR_UPDATE_DOOR_LOCK_PASSWORD("RNQ4X3"),
    //【水滴管家】亲爱的租客@var(customerName)，您好，您租住的房间@var(roomName)的房租账单在@var(payTime)即将到期，到期若未交租，将在@var(powerOffTime)上午9:00~10:00停电。请您及时交租，谢谢配合。祝您居住愉快
    EXCESS_POWER_OFF("tIwPu1"),
    //【公寓提示】（@var(realName)）您好！您的房租将于@var(date)到期，到期若未交租，您的门锁密码将被冻结。请您及时交租，祝您生活愉快！谢谢！（@var(brand)）
    WILL_FREEZE("NIbnl"),
    //【公寓提示】（@var(realName)）您好！因您的房租超期未交，您的门锁密码已被冻结。您可以交租进行解冻，谢谢！（@var(brand)）
    HAVE_FROZEN("X66ED"),
    //【公寓提示】（@var(realName)）您好！您的门锁密码已经解冻，祝您生活愉快！（@var(brand)）
    HAVE_UNFROZEN("QVunH4");

    String name;

    SMSCodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

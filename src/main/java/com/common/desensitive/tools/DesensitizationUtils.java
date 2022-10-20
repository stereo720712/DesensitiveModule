package com.common.desensitive.tools;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sean
 */
public class DesensitizationUtils {


    /**
     * @description: 名字脱敏
     * 脱敏规则: 隐藏中中间部分，比如:李某人 置换为 李*人 , 李某置换为 *某，司徒司翘置换为 司**翘
     */
    public static String desensitizedName(String name){

        if (!Strings.isNullOrEmpty(name)){
            int len = name.length();
            if (len == 2){
                return  "*".concat(name.substring(1));
            }else if (len == 3){
                return StringUtils.left(name,1).concat("*").concat(StringUtils.right(name,1));
            } else if (len > 3) {
                return  StringUtils.left(name,1).concat(generateAsterisk(name.substring(1,len-1).length())).concat(StringUtils.right(name,1));
            }else{
                return  name;
            }
        }
       return name;
    }

    /**
     * @description: 手机号脱敏，脱敏规则: 保留前三后四, 比如15566026528置换为155****6528
     * @return:
     */
    public static String desensitizedPhoneNum(String phone){
        if (StringUtils.isNotEmpty(phone)){
            int len = phone.length();
            if (len == 11){
                return phone.replaceAll("(\\w{3})\\w*(\\w{4})","$1****$2");
            } else if (len >= 8) {
                return StringUtils.left(phone,3).concat(generateAsterisk(4)).concat(StringUtils.right(phone,4));
            } else if (len > 2){
                return StringUtils.left(phone,1).concat(generateAsterisk(phone.substring(1,len-2).length())).concat(StringUtils.right(phone,1));
            }
        }
        return phone;
    }

    /**
     * @description: 身份证脱敏
     * 脱敏规则: 保留前六后三, 适用于15位和18位身份证号：
     * 原身份证号(15位)：210122198401187，脱敏后的身份证号：210122******187
     * 原身份证号(18位)：210122198401187672，脱敏后的身份证号：210122*********672
     */

    public static String desensitizedIdNumber(String idNumber){
        if (!Strings.isNullOrEmpty(idNumber)) {
            int length = idNumber.length();
            if (length == 15){
                return idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
            }else if (length == 18){
                return idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
            }else if(length > 9){
                return StringUtils.left(idNumber,6).concat(generateAsterisk(idNumber.substring(6,length-3).length())).concat(StringUtils.right(idNumber,3));
            }
        }
        return idNumber;
    }

    /**
     * @description: 电子邮箱脱敏，脱敏规则：电子邮箱隐藏@前面的3个字符
     * @return:
     */
    public static String desensitizationEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return email;
        }
        String encrypt = email.replaceAll("(\\w+)\\w{3}@(\\w+)", "$1***@$2");
        if (email.equalsIgnoreCase(encrypt)) {
            encrypt = email.replaceAll("(\\w*)\\w{1}@(\\w+)", "$1*@$2");
        }
        return encrypt;
    }

    /**
     * @description: 地址脱敏，脱敏规则：从第4位开始隐藏,隐藏8位
     * @return:
     */
    public static String desensitizedAddress(String userAddress){
        if (!Strings.isNullOrEmpty(userAddress)) {
            int length = userAddress.length();
            if(length > 4 && length <= 12){
                return StringUtils.left(userAddress, 3).concat(generateAsterisk(userAddress.substring(3).length()));
            }else if(length > 12){
                return StringUtils.left(userAddress,3).concat("********").concat(userAddress.substring(11));
            }else {
                return userAddress;
            }
        }
        return userAddress;
    }

    /**
     * @description: 银行账号脱敏, 脱敏规则：银行账号保留前六后四
     * @return:
     */
    public static String desensitizedAddressBankCardNum(String acctNo) {
        if (StringUtils.isNotEmpty(acctNo)) {
            String regex = "(\\w{6})(.*)(\\w{4})";
            Matcher m = Pattern.compile(regex).matcher(acctNo);
            if (m.find()) {
                String rep = m.group(2);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < rep.length(); i++) {
                    sb.append("*");
                }
                acctNo = acctNo.replaceAll(rep, sb.toString());
            }
        }
        return acctNo;
    }

    /**
     * @description: 存币地址，判断是否是电话，会用冒号区隔区码 ，是电话就拖敏
     * @return:
     */
    public static String desensitizedRehargeAddress(String rechargeAddress){
        String[] phoneArr = rechargeAddress.split(":");
        if (phoneArr.length  != 2){
            return rechargeAddress;
        }else if (!StringUtils.isNumeric(phoneArr[1])){
            return rechargeAddress;
        }else{
            return new StringBuilder().append(phoneArr[0]).append(" ").append(desensitizedPhoneNum(phoneArr[1])).toString();
        }
    }


    private static String generateAsterisk(int length){
        String result = "";
        for (int i = 0; i < length; i++) {
            result += "*";
        }
        return result;
    }
}

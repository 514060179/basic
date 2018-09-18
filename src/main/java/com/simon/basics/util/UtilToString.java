package com.simon.basics.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author simon.feng
 * @Date Created in 22:40 2018/9/18/018
 * @Modificd
 */
public class UtilToString {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private static String strBase = "ABCDEFGHIJKOMNOPQRSTUVWXYZ123456789";

    private static Calendar calendar = Calendar.getInstance();

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 重写toString 方法 (实体类,应优先使用此方法)
     *
     * @param object
     * @return
     */
    public static String getString(Object object) {
        try {
            if (null == object) {
                return null;
            } else {
                // 获取此类所有声明的字段
                Field[] field = object.getClass().getDeclaredFields();

                // 用来拼接所需保存的字符串
                StringBuffer sb = new StringBuffer();

                // 循环此字段数组，获取属性的值
                for (int i = 0; i < field.length && field.length > 0; i++) {

                    field[i].setAccessible(true);


                    if (i == field.length - 1) {
                        sb.append(field[i].getName() + ": "
                                + field[i].get(object) + ".");

                    } else {
                        sb.append(field[i].getName() + ": "
                                + field[i].get(object) + ", ");
                    }
                }
                return sb.toString();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 生成微信需要签名的字符串(优先使用此类)
     *
     * @param object
     * @return
     */
    public static String getXmlstr(Object object) {
        try {
            if (null == object) {
                return null;
            } else {
                // 获取此类所有声明的字段
                Field[] field = object.getClass().getDeclaredFields();

                // 用来拼接所需保存的字符串
                StringBuffer sb = new StringBuffer();

                // 循环此字段数组，获取属性的值
                for (int i = 0; i < field.length && field.length > 0; i++) {

                    field[i].setAccessible(true);

                    if (i == field.length - 1 || i == field.length - 2) {
                        if (null != field[i].get(object)) {
                            sb.append(field[i].getName() + "="
                                    + field[i].get(object));
                        }
                    } else {
                        if (null != field[i].get(object)) {
                            if ("packageValue".equals(field[i].getName())) {
                                sb.append("package" + "="
                                        + field[i].get(object) + "&");

                            } else {
                                sb.append(field[i].getName() + "="
                                        + field[i].get(object) + "&");
                            }
                        }
                    }
                }
                return sb.toString().replace("\\n", "");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 发送给微信服务器的xml格式的字符串
     *
     * @param object
     * @return
     */
    public static String getXml(Object object) {
        try {
            if (null == object) {
                return null;
            } else {
                // 获取此类所有声明的字段
                Field[] field = object.getClass().getDeclaredFields();

                // 用来拼接所需保存的字符串
                StringBuffer sb = new StringBuffer();
                sb.append("<xml>");
                // 循环此字段数组，获取属性的值
                for (int i = 0; i < field.length && field.length > 0; i++) {

                    field[i].setAccessible(true);


                    if (null != field[i].get(object)) {
                        sb.append("<" + field[i].getName() + ">"
                                + "<![CDATA[" + field[i].get(object) + "]]>" + "</" + field[i].getName() + ">");

                    }

                }
                sb.append("</xml>");
                return sb.toString();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成云家政请求的字符串(优先使用此类)
     *
     * @param object
     * @return
     */
    public static String getYumParams(Object object) {
        try {
            if (null == object) {
                return null;
            } else {
                // 获取此类所有声明的字段
                Field[] field = object.getClass().getDeclaredFields();

                Field[] supField = object.getClass().getSuperclass().getDeclaredFields();

                // 用来拼接所需保存的字符串
                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < supField.length; i++) {
                    supField[i].setAccessible(true);
                    if (null != supField[i].get(object)) {
                        sb.append(supField[i].getName() + "="
                                + supField[i].get(object) + "&");
                    }
                }

                // 循环此字段数组，获取属性的值
                for (int i = 0; i < field.length && field.length > 0; i++) {
                    field[i].setAccessible(true);
                    if (null != field[i].get(object)) {


                        sb.append(field[i].getName() + "="
                                + field[i].get(object) + "&");

                    }

                }

                return sb.toString().replace("\\n", "");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 解析request请求参数,生成实体对象
     *
     * @param map
     * @return null or 有属性对象 (null可能请求无参数或者无对应实体对象,)
     */
    public static Object mapToBean(Map map, Class c) {
        Object object = null;
        try {
            if (map != null && map.size() > 0) {
                object = c.newInstance();
                Field[] fields = c.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    if (map.containsKey(fields[i])) {
                        fields[i].set(object, ((String[]) (map.get(fields[i])))[0]);
                    }

                }
            }

        } catch (InstantiationException e) {
            //开发环境便于观察异常.现网应该注释

            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {

            e.printStackTrace();
            return null;
        }


        return object;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * 采用第三方jar 方法   map转成对象
     *
     * @return
     */
    public static Object mapTobean(Map map, Object o) {
        try {
            BeanUtils.populate(o, map);

        } catch (Exception e) {
            e.printStackTrace();
//            logger.error("mapTobean,转换失败:", e);
            return o;
        }


        return o;
    }

    public static String getOrderNum() {
        String date = sdf.format(new Date());
        String random = getRandomString(2);

        String sb = date + random;
        return sb;
    }

    public static String getOrderNum(final String code) {
        String date = sdf.format(new Date());
        String random = getRandomString(2);

        String sb = code + date + random;
        return sb;
    }

    public static String getBusinessNum(String str) {
        String date = sdf.format(new Date());
        String random = getRandomString(2);

        String sb = str + date + random;
        return sb;
    }


    public static String getParkDev() {

        String random = getRandomString(6);

        String sb =   random;
        return sb;
    }



    /**
     * 订单随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        // length表示生成字符串的长度

        String numBase = "0123456789";

        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            int number = random.nextInt(numBase.length());
            sb.append(numBase.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取券码
     * @return
     */
    public static String getFourRandomString() {

        Random random = new Random();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int number = random.nextInt(strBase.length());
            sb.append(strBase.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public static int getSms() {
        Random r = new Random();
        int sms;
        do {
            sms = r.nextInt(10000);
        }
        while (sms < 1000);
        return sms;

    }



    private static HashMap<Integer, String> map = new HashMap<Integer, String>(2, 0.75f);

    public static void main(String[] args) {

        calendar.setTime(new Date());

        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        if ((hour * 60 + minute) > (19 * 60 + 30)) {
            System.out.println(11);
        }   else{
            System.out.println(2);
        }
        System.out.println(hour +":"+minute);
    }

}

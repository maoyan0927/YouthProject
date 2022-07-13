package com.youth.Util;

import com.alibaba.fastjson.JSONArray;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: KingRainGrey
 * Date: 2020/7/11
 */
public class StringUtil {

    //处理身高接口返回的json array
    public final static String jsonArrayToString(JSONArray array) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<array.size();i++) {
            JSONArray temp = (JSONArray) array.get(i);
            if (temp.size() < 2) {
                return null;
            }
            sb.append(temp.get(0) + "-" + temp.get(1));
            if(i != (array.size()-1)) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    public final static List<List<Float>> stringToArray(String point) {
        if (point == null) {
            return null;
        }
        List<List<Float>> result = new ArrayList<>();
        String[] pointArray = point.split("-");
        for (int i=0;i<pointArray.length;i+=2) {
            List<Float> in = new ArrayList<>();
            in.add(Float.parseFloat(pointArray[i]));
            in.add(Float.parseFloat(pointArray[i+1]));
            result.add(in);
        }
        return result;
    }


    public final static String encryption(Integer id) {
        byte[] encode_bytes = com.youth.Util.EnDecoderUtil.DESEncrypt("20200710", id + "");
        return Base64.getEncoder().encodeToString(encode_bytes);
    }

    public final static Integer decrypt(String id) {
        byte[] byteStr = Base64.getDecoder().decode(id.replaceAll(" ", "+"));
        byte[] decode_bytes = com.youth.Util.EnDecoderUtil.DESDecrypt("20200710", byteStr);
        Integer idInt = Integer.parseInt(new String(decode_bytes));
        return  idInt;
    }

    //param 2020-12-20
    public final static Float getAge(Date time, Date checkTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(time);
        String[] data = date.split("-");

        Calendar birthday =new GregorianCalendar(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]));
        //Calendar now = Calendar.getInstance();

        String nowTime = format.format(checkTime);
        String[] nowTimeData = nowTime.split("-");
        Calendar now =new GregorianCalendar(Integer.valueOf(nowTimeData[0]), Integer.valueOf(nowTimeData[1]), Integer.valueOf(nowTimeData[2]));

        int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        int month = now.get(Calendar.MONTH) +1 - birthday.get(Calendar.MONTH);
        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);

        float result = year + 1.0f*month/12;
        DecimalFormat decimalFormat = new DecimalFormat(".0");
        String str = decimalFormat.format(result);
        return Float.parseFloat(str);
    }

}

package com.ossjk.myUtil;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;


public class ObjectUtil {

    /**
     * 判断对象是否完全为空
     *
     * @param object
     * @return
     */
    public static boolean objectCheckIsNull(Object object) {
        boolean flag = true;
        if (Objects.isNull(object)) {
            flag = true;
        } else {
            Class clazz =  object.getClass();//通过对象获取反射类
            Field fields[] = clazz.getDeclaredFields();//得到所有类字段
            System.out.println(fields.length);
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = null;
                try {
                    fieldValue = field.get(object);
                } catch (IllegalArgumentException e) {
                   e.printStackTrace();
                } catch (IllegalAccessException e) {
                   e.printStackTrace();
                }
                if(field.getName().equals("serialVersionUID")){
                    continue;
                }else{
                    if (fieldValue != null) {
                        flag = false;
                        break;
                    }
                }

            }
        }

        return flag;
    }


}

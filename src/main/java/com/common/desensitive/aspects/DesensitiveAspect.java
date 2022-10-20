package com.common.desensitive.aspects;

import com.common.desensitive.annotations.DesensitiveField;
import com.common.util.page.Page;
import com.enums.domian.Result;
import com.common.desensitive.enums.SensitiveType;
import com.common.desensitive.tools.DesensitizationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;


/**
 * @author sean
 */
@Component
@Aspect
public class DesensitiveAspect {

    @Pointcut("@annotation(com.common.desensitive.annotations.DesensitiveList)")
    public void pointCutList() {
    }

    @Pointcut("@annotation(com.common.desensitive.annotations.DesensitivePage)")
    public void pointCutPage() {
    }

    @Pointcut("@annotation(com.common.desensitive.annotations.DesensitiveResult)")
    public void pointCutResult() {
    }

    @Around("pointCutResult()")
    public Object aroundResult(ProceedingJoinPoint joinPoint) throws Throwable {
        //todo: add user permission check
        System.out.print("pointCutResult() start");
        Object obj = joinPoint.proceed();
        if (obj == null || isPrimitive(obj.getClass())) {
            return obj;
        }
        if (obj instanceof Result) {
            processList((((Result<Page<?>>)obj).getData().getRecords()));
        }
        return obj;
    }
    @Around("pointCutPage()")
    public Object aroundPage(ProceedingJoinPoint joinPoint) throws  Throwable{
        Object obj = joinPoint.proceed();
        if (obj instanceof Page){
            processList(((Page<?>) obj).getRecords());
        }
        return obj;
    }

    @Around("pointCutList()")
    public Object aroundList(ProceedingJoinPoint joinPoint) throws  Throwable{
        Object obj = joinPoint.proceed();
        if (obj instanceof List){
            processList(obj);
        }
        return obj;
    }


    private void processList(Object obj){
        if (obj == null){
            return ;
        }
        List<?> data = (List<?>) obj;
        if (data != null){
            for (Object tempObj : data){
                replace(tempObj.getClass().getDeclaredFields(), tempObj);
            }
        }
    }

    private void replace(Field[] fields,Object _obj ){
        try {
              for (Field f: fields){
                  if (f == null){
                      continue;
                  }
                  f.setAccessible(true);
                  DesensitiveField annotation = f.getAnnotation(DesensitiveField.class);
                  if (annotation == null){
                      continue;
                  }
                  f.getType();
                  String value = (String) f.get(_obj);
                  SensitiveType typeEnum = annotation.value();
                  switch (typeEnum){
                      case DEFAULT:
                          break;
                      case RECHARGE_ADDRESS:
                           f.set(_obj, DesensitizationUtils.desensitizedRehargeAddress(value));
                          break;
                      case BANK_CARD_NO:
                          f.set(_obj,DesensitizationUtils.desensitizedAddressBankCardNum(value));
                          break;
                      case PHONE:
                          f.set(_obj, DesensitizationUtils.desensitizedPhoneNum(value));
                          break;
                      case ID_CARD:
                          f.set(_obj,DesensitizationUtils.desensitizedIdNumber(value));
                          break;
                      case EMAIL:
                          f.set(_obj,DesensitizationUtils.desensitizationEmail(value));
                          break;
                      case ADDRESS:
                          f.set(_obj,DesensitizationUtils.desensitizedAddress(value));
                         break;
                      case NAME:
                          f.set(_obj,DesensitizationUtils.desensitizedName(value));
                        break;
                  }
              }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * primitive type determine
     *
     * @param clz
     * @return
     */
    private boolean isPrimitive(Class<?> clz) {
        try {
            if (String.class.isAssignableFrom(clz) || clz.isPrimitive()) {
                return true;
            } else {
                return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
            }
        } catch (Exception e) {
            return false;
        }

    }
}



package cn.cjf.validation.annotation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * @author chenjunfan
 * @date 2019/5/14
 */
public class CheckTimeIntervalValidator implements ConstraintValidator<CheckTimeInterval, Object> {

    private String startTime;

    private String endTime;

    @Override
    public void initialize(CheckTimeInterval constraintAnnotation) {
        this.startTime = constraintAnnotation.startTime();
        this.endTime = constraintAnnotation.endTime();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object start = beanWrapper.getPropertyValue(startTime);
        Object end = beanWrapper.getPropertyValue(endTime);

        if (null == start || null == end) {
            return true;
        }

        int result = ((Date) end).compareTo(((Date) start));
        if (result >= 0) {
            return true;
        }

        return false;
    }
}

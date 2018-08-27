package cn.hlvan.configure;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

@ControllerAdvice
public class GlobalWebBindingInitializer implements WebBindingInitializer {

    @InitBinder
    @Override
    public void initBinder(@NonNull WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

}

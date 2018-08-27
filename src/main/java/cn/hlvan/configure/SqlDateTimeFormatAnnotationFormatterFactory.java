package cn.hlvan.configure;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SqlDateTimeFormatAnnotationFormatterFactory extends EmbeddedValueResolutionSupport
    implements AnnotationFormatterFactory<DateTimeFormat> {

    private static final Set<Class<?>> FIELD_TYPES;

    static {
        Set<Class<?>> fieldTypes = new HashSet<>(2);
        fieldTypes.add(Timestamp.class);
        fieldTypes.add(java.sql.Date.class);
        FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
    }

    @Override
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
        return (object, locale) -> DateFormatUtils.format((java.util.Date) object, annotation.pattern());
    }

    @Override
    public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
        return (text, locale) -> {
            java.util.Date date = DateUtils.parseDate(text, annotation.pattern());
            if (fieldType == Timestamp.class) {
                return new Timestamp(date.getTime());
            } else if (fieldType == java.sql.Date.class) {
                return new java.sql.Date(date.getTime());
            } else {
                return date; // impossible
            }
        };
    }
}

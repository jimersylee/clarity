package skadistats.clarity.two.processor.reader;

import com.google.protobuf.GeneratedMessage;
import skadistats.clarity.two.framework.annotation.UsagePointMarker;
import skadistats.clarity.two.framework.invocation.UsagePointType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@UsagePointMarker(UsagePointType.EVENT_LISTENER)
public @interface OnMessageContainer {
    Class<? extends GeneratedMessage> value() default GeneratedMessage.class;
}
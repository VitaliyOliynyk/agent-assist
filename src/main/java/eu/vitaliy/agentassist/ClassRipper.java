package eu.vitaliy.agentassist;

import org.hamcrest.Matcher;

import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;

public interface ClassRipper {

    String DEFAULT_NAME = "DEFAULT";

    Matcher<Class> getClassName();

    Matcher<Method> getMethodName();

    ClassFileTransformer getTransformer();

    default String getName() {
        return DEFAULT_NAME;
    }
}

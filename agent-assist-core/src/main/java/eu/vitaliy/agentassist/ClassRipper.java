package eu.vitaliy.agentassist;

import javassist.CtMethod;
import org.hamcrest.Matcher;

import java.lang.instrument.ClassFileTransformer;

public interface ClassRipper {

    String DEFAULT_NAME = "DEFAULT";

    Matcher<String> classMatcher();

    Matcher<String> methodMatcher();

    void ripTheMethod(CtMethod method);

    default ClassFileTransformer getTransformer() {
        return null;
    }

    default String getName() {
        return DEFAULT_NAME;
    }
}

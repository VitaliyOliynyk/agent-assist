package eu.vitaliy.agentassist;

import javassist.CtMethod;
import org.hamcrest.Matcher;

import java.lang.instrument.ClassFileTransformer;

public class ClassRipperTestImpl implements ClassRipper {
    @Override
    public Matcher<String> classMatcher() {
        return null;
    }

    @Override
    public Matcher<String> methodMatcher() {
        return null;
    }

    @Override
    public ClassFileTransformer getTransformer() {
        return null;
    }

    @Override
    public void ripTheMethod(CtMethod method) {
        /* empty */
    }
}

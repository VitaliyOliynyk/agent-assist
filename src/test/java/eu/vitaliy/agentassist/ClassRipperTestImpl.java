package eu.vitaliy.agentassist;

import org.hamcrest.Matcher;

import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;

public class ClassRipperTestImpl implements ClassRipper {
    @Override
    public Matcher<Class> getClassName() {
        return null;
    }

    @Override
    public Matcher<Method> getMethodName() {
        return null;
    }

    @Override
    public ClassFileTransformer getTransformer() {
        return null;
    }
}

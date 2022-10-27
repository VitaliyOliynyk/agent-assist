package eu.vitaliy.agentassist;

import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;

public interface ClassRipper {

    String DEFAULT_NAME = "DEFAULT";

    boolean classFilter(String className);

    boolean methodFilter(String methodName);

    void ripTheMethod(CtMethod method);

    default ClassFileTransformer getTransformer() {
        return null;
    }

    default String getName() {
        return DEFAULT_NAME;
    }
}

package eu.vitaliy.agentassist;

import javassist.CtMethod;
import org.hamcrest.Matcher;
import java.lang.instrument.ClassFileTransformer;

public class ClassRipperTestImpl implements ClassRipper {


    @Override
    public boolean classFilter(String className) {
        return true;
    }

    @Override
    public boolean methodFilter(String methodName) {
        return true;
    }

    @Override
    public void ripTheMethod(CtMethod method) {
        /* empty */
    }
}

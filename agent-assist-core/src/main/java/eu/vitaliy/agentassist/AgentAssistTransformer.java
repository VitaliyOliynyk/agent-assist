package eu.vitaliy.agentassist;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AgentAssistTransformer implements ClassFileTransformer {

    private ClassRipper classRipper;
    private String targetClassName;
    private ClassLoader targetClassLoader;

    public AgentAssistTransformer(ClassRipper classRipper, String targetClassName, ClassLoader targetClassLoader) {
        this.classRipper = classRipper;
        this.targetClassName = targetClassName;
        this.targetClassLoader = targetClassLoader;
    }

    public AgentAssistTransformer(ClassRipper classRipper) {
        this.classRipper = classRipper;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        byte[] byteCode = classfileBuffer;

        String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/"); //replace . with /
        if (!className.equals(finalTargetClassName)) {
            return byteCode;
        }

        if (loader.equals(targetClassLoader)) {
            System.out.println("[Agent] Transforming class MyAtm");
            try {
                ClassPool cp = ClassPool.getDefault();
                cp.insertClassPath(new ClassClassPath(classBeingRedefined));
                CtClass cc = cp.get(targetClassName);
                List<CtMethod> methods = Stream.of(cc.getDeclaredMethods())
                        .filter(ctMethod -> classRipper.methodMatcher().matches(ctMethod.getName()))
                        .collect(Collectors.toList());

                methods.forEach(classRipper::ripTheMethod);

                byteCode = cc.toBytecode();
                cc.detach();
            } catch (NotFoundException | CannotCompileException | IOException e) {
                e.printStackTrace();
            }
        }
        return byteCode;
    }
}

package eu.vitaliy.agentassist;

import java.lang.instrument.Instrumentation;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AgentMain {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("[Agent] In premain method");
        commonMain(agentArgs, inst);
    }



    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("[Agent] In agentmain method");
        commonMain(agentArgs, inst);
    }

    private static void commonMain(String agentArgs, Instrumentation inst) {
        Collection<ClassRipper> rippers = new ClassRipperLoader().loadRippers();
        rippers.forEach(ripper -> transformClass(ripper, inst));

    }

    private static void transformClass(ClassRipper ripper, Instrumentation instrumentation) {
        @SuppressWarnings("rawtypes")
        List<Class> classesToTransform = Stream.of(instrumentation.getAllLoadedClasses())
                .filter(c -> ripper.classMatcher().matches(c.getName()))
                .collect(Collectors.toList());

        if (classesToTransform.isEmpty()) {
            throw new RuntimeException("Failed to find any classes to transform");
        }

        classesToTransform.forEach(clazz -> {
            transform(clazz, instrumentation, ripper);
        });

    }

    private static void transform(Class<?> clazz, Instrumentation instrumentation, ClassRipper ripper) {
        AgentAssistTransformer dt = new AgentAssistTransformer(ripper, clazz.getName(), clazz.getClassLoader());
        instrumentation.addTransformer(dt, true);
        try {
            instrumentation.retransformClasses(clazz);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Transform failed for: [" + clazz.getName() + "]", ex);
        }
    }

}

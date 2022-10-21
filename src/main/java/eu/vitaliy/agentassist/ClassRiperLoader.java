package eu.vitaliy.agentassist;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClassRiperLoader {
    public Collection<ClassRipper> loadRippers() {
        ServiceLoader<ClassRipper> ripersLoader = ServiceLoader.load(ClassRipper.class);
        return StreamSupport.stream(ripersLoader.spliterator(), false)
                .collect(Collectors.toList());
    }
}

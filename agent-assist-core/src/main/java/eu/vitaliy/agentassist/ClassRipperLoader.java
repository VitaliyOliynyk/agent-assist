package eu.vitaliy.agentassist;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClassRipperLoader {
    public Collection<ClassRipper> loadRippers() {
        ServiceLoader<ClassRipper> rippersLoader = ServiceLoader.load(ClassRipper.class);
        return StreamSupport.stream(rippersLoader.spliterator(), false)
                .collect(Collectors.toList());
    }
}

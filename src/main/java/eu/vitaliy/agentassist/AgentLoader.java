package eu.vitaliy.agentassist;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class AgentLoader {
    public static void run(String[] args) {
        prepareAttach();
        attach(args);
    }

    private static void attach(String[] args) {
        AgentLoaderConfig config = new AgentLoaderConfig(args);
        File agentFile = new File(config.getAgentPath());
        String jvmPid = config.getPid();
        try {
            System.out.println("Attaching to target JVM with PID: " + jvmPid);
            VirtualMachine jvm = VirtualMachine.attach(jvmPid);
            jvm.loadAgent(agentFile.getAbsolutePath());
            jvm.detach();
            System.out.println("Attached to JVM and loaded Java agent succesfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void prepareAttach() {
        try {
            prepareAttachImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void prepareAttachImpl() throws NoSuchMethodException, MalformedURLException, InvocationTargetException, IllegalAccessException {
        try {
            Class.forName("com.sun.tools.attach.VirtualMachine");
            return;
        } catch (ClassNotFoundException e) {
            /* no class, load tools.jar */
        }

        String javaHome = System.getenv("JAVA_HOME");
        System.out.println("JAVA_HOME=" + javaHome);

        String toolsPath = Paths.get(javaHome).resolve("lib").resolve("tools.jar").toAbsolutePath().toString();
        System.out.println("toolsPath:" + toolsPath);

        URLClassLoader loader = (URLClassLoader) AgentLoader.class.getClassLoader();
        Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addUrlMethod.setAccessible(true);
        File toolsJar = new File(toolsPath);
        if (!toolsJar.exists()) {
            throw new RuntimeException(toolsJar.getAbsolutePath() + " does not exists");
        }

        addUrlMethod.invoke(loader, new File(toolsPath).toURI().toURL());
    }
}

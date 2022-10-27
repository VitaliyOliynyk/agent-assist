package eu.vitaliy.agentassist;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.net.URISyntaxException;

public class AgentLoaderConfig {
    private String pid;
    private String[] args;
    private String agentPath;

    public AgentLoaderConfig(String[] args) {
        this.args = args;
        validate();
        readPid();
        validatePid();
        initAgentPath();

    }

    private void initAgentPath() {
        try {
            agentPath = new File(AgentLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void readPid() {
        pid = args[0];
    }

    private void validatePid() {
        boolean processExists = VirtualMachine.list()
                .stream()
                .map(VirtualMachineDescriptor::id)
                .anyMatch(pid::equals);
        if (!processExists) {
            throw new RuntimeException("Process: " + pid + " is not exists");
        }
    }

    private void validate() {
        if (args == null || args.length != 1) {
           throw new RuntimeException("Valid parameters: agent-assist.jar <PID> -cp <jar with ClassRipperLoader implementation>");
        }
    }

    public String getPid() {
        return pid;
    }

    public String getAgentPath() {
        return agentPath;
    }
}

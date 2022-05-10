package com.example.client.e2e;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

public class ServerContainer extends GenericContainer<ServerContainer> {
    public ServerContainer() {
        super("serverimg");
        addExposedPorts(7000);
        withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(7000), new ExposedPort(7000)))));
        waitingFor(new HostPortWaitStrategy());

    }
}

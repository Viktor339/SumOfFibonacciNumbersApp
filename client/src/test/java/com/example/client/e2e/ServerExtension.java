package com.example.client.e2e;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ServerExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {
    private final ServerContainer serverContainer = new ServerContainer();

    @Override
    public void afterAll(ExtensionContext context) {
        serverContainer.stop();
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        serverContainer.start();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(ServerInfo.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new ServerInfo(serverContainer.getFirstMappedPort());
    }
}
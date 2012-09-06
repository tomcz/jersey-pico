package com.sun.jersey.spi.pico.container.servlet;

import org.picocontainer.MutablePicoContainer;

import java.util.Set;

/**
 * Wrapper class to register more than one {@link PicoConfiguration} with the {@link ScopedPicoContainerProvider}.
 */
public class PicoConfigurations implements PicoConfiguration {

    private final PicoConfiguration[] configurations;

    public PicoConfigurations(PicoConfiguration... configurations) {
        this.configurations = configurations;
    }

    @Override
    public void registerApplicationScope(MutablePicoContainer scope) {
        for (PicoConfiguration configuration : configurations) {
            configuration.registerApplicationScope(scope);
        }
    }

    @Override
    public void registerResourceScope(MutablePicoContainer scope) {
        for (PicoConfiguration configuration : configurations) {
            configuration.registerResourceScope(scope);
        }
    }

    @Override
    public void registerResources(Set<Class<?>> resources) {
        for (PicoConfiguration configuration : configurations) {
            configuration.registerResources(resources);
        }
    }
}

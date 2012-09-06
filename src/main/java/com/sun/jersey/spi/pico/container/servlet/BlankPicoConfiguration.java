package com.sun.jersey.spi.pico.container.servlet;

import org.picocontainer.MutablePicoContainer;

import java.util.Set;

/**
 * Provides no-op implementations of {@link PicoConfiguration} registration methods
 * so you may only override whichever one you want without having to implement them all.
 */
public class BlankPicoConfiguration implements PicoConfiguration {

    @Override
    public void registerApplicationScope(MutablePicoContainer scope) {
    }

    @Override
    public void registerResourceScope(MutablePicoContainer scope) {
    }

    @Override
    public void registerResources(Set<Class<?>> resources) {
    }
}

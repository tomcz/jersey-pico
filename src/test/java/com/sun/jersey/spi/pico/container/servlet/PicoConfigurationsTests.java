package com.sun.jersey.spi.pico.container.servlet;

import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PicoConfigurationsTests {

    private PicoConfiguration c1;
    private PicoConfiguration c2;
    private MutablePicoContainer scope;
    private PicoConfigurations configurations;

    @Before
    public void setUp() {
        c1 = mock(PicoConfiguration.class);
        c2 = mock(PicoConfiguration.class);
        scope = mock(MutablePicoContainer.class);
        configurations = new PicoConfigurations(c1, c2);
    }

    @Test
    public void shouldRegisterApplicationScopeForAllConfigurations() {
        configurations.registerApplicationScope(scope);
        verify(c1).registerApplicationScope(scope);
        verify(c2).registerApplicationScope(scope);
    }

    @Test
    public void shouldRegisterRequestScopeForAllConfigurations() {
        configurations.registerResourceScope(scope);
        verify(c1).registerResourceScope(scope);
        verify(c2).registerResourceScope(scope);
    }

    @Test
    public void shouldRegisterResourcesForAllConfigurations() {
        Set<Class<?>> resources = Collections.emptySet();
        configurations.registerResources(resources);
        verify(c1).registerResources(resources);
        verify(c2).registerResources(resources);
    }
}

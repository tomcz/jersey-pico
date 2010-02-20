/*
 * Copyright (c) 2010, Thomas Czarniecki
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  * Neither the name of Thomas Czarniecki, tomczarniecki.com nor
 *    the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.sun.jersey.spi.pico.container.servlet;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.spi.pico.container.PicoComponentProviderFactory;

import javax.servlet.ServletConfig;

/**
 * HttpServlet to provide Jersey resources/components using PicoContainer IoC.
 */
public class PicoServlet extends ServletContainer {

    /**
     * ServletConfig init-param for the name of a {@link com.sun.jersey.spi.pico.container.servlet.PicoConfiguration}
     * implementation for this servlet. The implementing class must have a no-arg constructor.
     */
    public static final String PICO_CONFIGURATION_CLASS = "pico.configuration.class";

    /**
     * ServletConfig init-param for the ServletContext attribute name that should be used to retrieve an instance
     * of a {@link com.sun.jersey.spi.pico.container.servlet.PicoContainerProvider} for this servlet.
     */
    public static final String PICO_PROVIDER_KEY = "pico.provider.key";

    private PicoContainerProvider provider;

    @Override
    protected void initiate(ResourceConfig rc, WebApplication wa) {
        provider = lookupProvider();
        rc.getClasses().addAll(provider.getResources());
        wa.initiate(rc, new PicoComponentProviderFactory(provider.getContainer(), provider.getResources()));
    }

    /**
     * Attempts to create an instance of a {@link com.sun.jersey.spi.pico.container.servlet.PicoConfiguration}
     * via the {@link #PICO_CONFIGURATION_CLASS} init-param first and then falls back to checking the ServletContext
     * via the {@link #PICO_PROVIDER_KEY} init-param attribute's value.
     */
    protected PicoContainerProvider lookupProvider() {
        ServletConfig servletConfig = getServletConfig();

        String configurationClassName = servletConfig.getInitParameter(PICO_CONFIGURATION_CLASS);
        if (configurationClassName != null) {
            return createProvider(configurationClassName);
        }

        String providerKey = servletConfig.getInitParameter(PICO_PROVIDER_KEY);
        if (providerKey != null) {
            PicoContainerProvider provider = (PicoContainerProvider) getServletContext().getAttribute(providerKey);
            if (provider == null) {
                throw new IllegalArgumentException(
                        "Cannot find PicoContainerProvider in servlet context using '" + providerKey + "'");
            }
            return provider;
        }

        throw new IllegalStateException("Cannot find PicoContainerProvider for servlet");
    }

    private PicoContainerProvider createProvider(String configurationClassName) {
        try {
            Class<?> configurationClass = Class.forName(configurationClassName);
            PicoConfiguration configuration = (PicoConfiguration) configurationClass.newInstance();
            return new ScopedPicoContainerProvider(configuration);

        } catch (RuntimeException e) {
            throw e; // no need to wrap

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        provider.destroyContainer();
        super.destroy();
    }
}

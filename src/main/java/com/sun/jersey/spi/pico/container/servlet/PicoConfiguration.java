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

import org.picocontainer.MutablePicoContainer;

import java.util.Set;

/**
 * Provides resources and components for a {@link PicoServlet}.
 * <p>
 * Application-scoped components are esentially singleton instances within the context of a single PicoServlet
 * instance. Each new instance of a Resource-scoped class will get the same instance of an Application-scoped
 * class.
 * <p>
 * Resource-scoped components are usually created per-request. This does depend on how each resource is actually
 * managed by the Jersey runtime, but by default it is per-request.
 *
 * @see com.sun.jersey.spi.pico.container.servlet.ScopedPicoContainerProvider
 */
public interface PicoConfiguration {

    /**
     * Register application-scoped components/resources.
     */
    void registerApplicationScope(MutablePicoContainer scope);

    /**
     * Register resource-scoped components/resources.
     */
    void registerResourceScope(MutablePicoContainer scope);

    /**
     * Register resource (or resource-provider) classes with the Jersey runtime. If these resources are to
     * be provided by PicoContainer then they should have been registered with either the Resource-scope
     * or the Application-scope.
     *
     * @see #registerApplicationScope(org.picocontainer.MutablePicoContainer)
     * @see #registerResourceScope(org.picocontainer.MutablePicoContainer)
     */
    void registerResources(Set<Class<?>> resources);
}

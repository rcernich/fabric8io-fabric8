/**
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// START SNIPPET: service
package org.jboss.quickstarts.fuse.soap;

import javax.jws.WebService;
import java.net.InetAddress;

/**
 * This is our web service implementation, which implements the web service interface.
 * We also add the @WebService annotation to it to mark this class an implementation for the endpoint interface.
 */
@WebService(endpointInterface = "org.jboss.quickstarts.fuse.soap.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    private static final String ADDRESS;

    /**
     * Just a simple implementation for a friendly message that says hello.
     */
    public String sayHi(String name) {
        return "Hello " + name + ", from " + ADDRESS;
    }

    static {
        try {
            InetAddress address = InetAddress.getLocalHost();
            ADDRESS = address.getHostAddress();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}

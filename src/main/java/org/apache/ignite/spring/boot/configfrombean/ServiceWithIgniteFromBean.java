/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.spring.boot.configfrombean;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Example component. */
@Component
public class ServiceWithIgniteFromBean implements CommandLineRunner {
    /** Ignite instance autoconfigured and injected. */
    @Autowired
    private Ignite ignite;

    /** This method executed after startup. */
    @Override public void run(String... args) throws Exception {
        IgniteCache<Integer, Integer> cache = ignite.createCache("my-cache1");

        System.out.println("ServiceWithIgnite.run:");
        //This property comes from configurer. See AutoConfigureExample.
        System.out.println("    IgniteConsistentId: " + ignite.configuration().getConsistentId());

        //Other properties are set via application-node.yml.
        System.out.println("    IgniteInstanceName: " + ignite.configuration().getIgniteInstanceName());
        System.out.println("    CommunicationSpi.localPort: " +
            ((TcpCommunicationSpi)ignite.configuration().getCommunicationSpi()).getLocalPort());
        System.out.println("    DefaultDataRegion initial size: " +
            ignite.configuration().getDataStorageConfiguration().getDefaultDataRegionConfiguration().getInitialSize());

        DataRegionConfiguration drc =
            ignite.configuration().getDataStorageConfiguration().getDataRegionConfigurations()[0];

        System.out.println("    " + drc.getName() + " initial size: " + drc.getInitialSize());

        System.out.println("Putting data to the my-cache1...");

        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);

        System.out.println("Done putting data to the my-cache1...");
    }
}

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

package org.apache.ignite.spring.boot.thinclientfromconfig;

import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Example component. */
@Component
public class ServiceWithIgniteClient implements CommandLineRunner {
    /** IgniteClient instance autoconfigured and injected. */
    @Autowired
    private IgniteClient client;

    /** This method executed after startup. */
    @Override public void run(String... args) throws Exception {
        System.out.println("ServiceWithIgniteClient.run");
        System.out.println("Cache names existing in cluster: " + client.cacheNames());

        ClientCache<Integer, Integer> cache = client.cache("my-cache1");

        System.out.println("Putting data to the my-cache1...");

        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);

        System.out.println("Done putting data to the my-cache1...");
    }
}

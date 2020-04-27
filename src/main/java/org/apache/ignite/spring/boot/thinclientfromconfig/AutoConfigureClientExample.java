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

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteAutoConfiguration;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteClientConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/** Example of Ignite client autoconfigurer. */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, IgniteAutoConfiguration.class})
public class AutoConfigureClientExample {
    /** Main method of the application. */
    public static void main(String[] args) {
        //Starting Ignite server node outside of Spring Boot application so client can connect to it.
        Ignite serverNode = Ignition.start(new IgniteConfiguration());

        //Creating caches.
        serverNode.createCache("my-cache1");
        serverNode.createCache("my-cache2");

        //Activating `thinclient` profile to get properties from application-thinclient.yml
        System.setProperty("spring.profiles.active", "thinclient");

        SpringApplication.run(AutoConfigureClientExample.class);
    }

    /** Providing configurer for the Ignite client. */
    @Bean
    IgniteClientConfigurer configurer() {
        //Setting some property.
        //Other will come from `application-thinclient.yml`
        return cfg -> cfg.setSendBufferSize(64*1024);
    }
}

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

import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.ignite.IgniteClientAutoConfiguration;
import org.springframework.boot.autoconfigure.ignite.IgniteConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/** Example of Ignite node autoconfigurer. */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, IgniteClientAutoConfiguration.class})
public class AutoConfigureExample {
    /** Main method of the application. */
    public static void main(String[] args) {
        //Activating `node` profile to get properties from application-node.yml
        System.setProperty("spring.profiles.active", "node");

        SpringApplication.run(AutoConfigureExample.class, args);
    }

    /** Providing configurer for the Ignite. */
    @Bean
    public IgniteConfigurer configurer() {
        return cfg -> {
            //Setting consistent id.
            //See `application-node.yml` for the additional properties.
            cfg.setConsistentId("consistent-id");
            cfg.setCommunicationSpi(new TcpCommunicationSpi());
        };
    }
}

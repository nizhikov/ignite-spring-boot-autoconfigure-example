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

package org.apache.ignite.spring.boot.thinclientfrombean;

import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AutoConfigureClientFromBeanExample {
    public static void main(String[] args) {
        System.out.println("Starting Ignite server node outside of Spring Boot application...");
        Ignition.start(new IgniteConfiguration());
        System.out.println("Ignite server node started...");

        SpringApplication.run(AutoConfigureClientFromBeanExample.class, args);
    }

    @Bean
    public ClientConfiguration clientConfig() {
        return new ClientConfiguration().setAddresses("127.0.0.1:10800");
    }
}

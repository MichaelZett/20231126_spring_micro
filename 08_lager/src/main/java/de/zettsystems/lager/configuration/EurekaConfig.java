package de.zettsystems.lager.configuration;


import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@ConditionalOnDiscoveryEnabled
public class EurekaConfig {
}

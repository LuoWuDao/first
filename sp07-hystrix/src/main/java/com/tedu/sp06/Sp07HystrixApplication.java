package com.tedu.sp06;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringCloudApplication
/*@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication*/
public class Sp07HystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp07HystrixApplication.class, args);
    }

    // 新建RestTemplate实例，放入Spring容器
    // 生成一个动态代理对象，切入负载均衡代码
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        //return new RestTemplate();
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(1000);
        return new RestTemplate(factory);
    }
}

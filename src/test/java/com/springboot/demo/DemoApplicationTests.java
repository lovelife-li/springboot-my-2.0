package com.springboot.demo;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PublicKey;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() throws Exception{

        String privateKey="MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAluppBnYUknGMxCFO9RZh6ZCw3qrBbi0YFfNrQcE4RMdKSzqaanca0ErEIPIRwHn5J7WhWyBnW0PQ5jgP9WY7sQIDAQABAkAHM/j/TfN9ad4GHnDeUcsqkrVea1gAIjbcFVoxMT+2SJnz0GGAX6UANS9starSnhwjmAvcVxpqJB2jbx3uBvABAiEA11aW07Rnull5kknL2h+Qaf/nQg926+gtLEb6CyDDmiECIQCzaaVOLcg52NkU4I20ARTfjErQ3B14upMPYU7uJhQPkQIhAKrLFt3bHNqE1RPeTD05A8XPHpEayuvZPtr7gJ8K4ZmBAiA4NIl/02HdlWx0WOCrmocifT3W7o4hCzoU4GQswSsZsQIgI2uizUaGfNExmYQs/IR+dzGB9tdYu3k4wi1sWIMyAbE=";
        String publicKey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJbqaQZ2FJJxjMQhTvUWYemQsN6qwW4tGBXza0HBOETHSks6mmp3GtBKxCDyEcB5+Se1oVsgZ1tD0OY4D/VmO7ECAwEAAQ==";
        String password="FlowV83jh03fDgFAahS1OJ/KMYSfZ6IfLkAjHJ1yrqTvsjBpHcmYIPDpDS+BX0YIHSEM3n+zI4UE2zRRmgmrUw==";
        System.out.println(ConfigTools.decrypt(publicKey, password));


        String[] arr = ConfigTools.genKeyPair(512);
        System.out.println("privateKey:" + arr[0]);
        System.out.println("publicKey:" + arr[1]);



    }

}

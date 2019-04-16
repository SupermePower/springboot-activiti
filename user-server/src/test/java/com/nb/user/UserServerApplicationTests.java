package com.nb.user;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServerApplicationTests {

    @Test
    public void contextLoads() {
        long id = IdWorker.getId();
        System.out.println(id);
    }

}

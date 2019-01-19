package api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityApplicationTests {

    @Test
    public void contextLoads() {

        String admin =
                new BCryptPasswordEncoder()
                        .encode("admin");
        System.out.println(admin);

    }

}


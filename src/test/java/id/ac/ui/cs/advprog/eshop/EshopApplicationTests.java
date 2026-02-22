package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main(){
        String[] args = {"--spring.main.web-application-type=none"};
        assertDoesNotThrow(() -> {
            EshopApplication.main(args);
        });
    }

}

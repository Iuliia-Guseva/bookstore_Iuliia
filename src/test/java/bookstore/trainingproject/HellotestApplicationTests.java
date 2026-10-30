package bookstore.trainingproject;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@SpringBootTest
class HellotestApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void allControllersAreAvailable() {
        // Collect every controller bean created when the full application starts.
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);

        // A smoke test should fail if there are no controllers or any controller is unavailable.
        assertThat(controllers).isNotEmpty();
        assertThat(controllers.values()).allSatisfy(controller -> assertThat(controller).isNotNull());
    }
}

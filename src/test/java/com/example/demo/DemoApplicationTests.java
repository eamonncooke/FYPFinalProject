package com.example.demo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.demo.controller.CoachController;
import com.example.demo.controller.HomeController;
import com.example.demo.model.AuthUser;
import com.example.demo.service.ServiceForCoach;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
//

//@RunWith(SpringRunner.class)
//@WebMvcTest(value = CoachController.class)
@SpringBootTest
class CoachControllerTests {
    
    @Autowired
    private HomeController controller;
//    
//    @MockBean
//    private ServiceForCoach coachService;
//    
//    AuthUser mockUser = new AuthUser(2,"Eamonn","Cooke","cooke.eamonn@gmail.com", "$$2a$10$NurZg178eh5lC8AuJT49wOU8plj/7YAf2Q8yAD0JDu4DGQXSBych2", "PLAYER");
//    
    @Test
    void testCheckForUser() {
        assertThat(controller).isNotNull();
//        String s = "Hello World";
//        assertEquals("Hello World", s);
        
//        AuthUser user = coachService.getUserByEmail("cooke.eamonn@gmail.com");
//        assertEquals(mockUser,user);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.example.demo.controller.HomeController;
import com.example.demo.model.AuthUser;
import com.example.demo.service.ServiceForCoach;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * @author cooke
 */
public class JunitTesting {
    
    public JunitTesting() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    
    
    @MockBean
    private ServiceForCoach coachService;
    
    AuthUser mockUser = new AuthUser(2,"Eamonn","Cooke","cooke.eamonn@gmail.com", "$$2a$10$NurZg178eh5lC8AuJT49wOU8plj/7YAf2Q8yAD0JDu4DGQXSBych2", "PLAYER");
    
    @Test
    public void testCheckForUser() {
        
        AuthUser user = coachService.getUserByEmail("cooke.eamonn@gmail.com");
        assertEquals(mockUser,user);
    }
}

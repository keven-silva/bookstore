// package com.example.tdd.bookstore.controller;

// import java.net.URI;

// import com.google.gson.Gson;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.TestInstance.Lifecycle;
// import org.mockito.Mock;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import com.example.tdd.bookstore.repository.UserRepository;
// import com.example.tdd.bookstore.model.User;

// import com.example.tdd.bookstore.controller.dto.UserCreateRequestDTO;

// @SpringBootTest
// @AutoConfigureMockMvc
// @TestInstance(Lifecycle.PER_CLASS)
// @ActiveProfiles("test")
// public class UserControllerTest {
    
//     private UserCreateRequestDTO userRequestDTO;
//     private  Gson gson;
//     private String json;

//     @Autowired
//     private MockMvc mockMvc;

//     @Mock
//     private UserRepository userRepository;

//     @BeforeAll
//     public void setup() {
//         gson = new Gson();
        
//         userRequestDTO = new UserCreateRequestDTO(
//             "John Doe",
//             "teste01",
//             "john.doe@example.com"
//         );
        
//        json = gson.toJson(userRequestDTO);
//     }

//     @Test
//     public void testError404PersonController() throws Exception {
//         URI uri = new URI("/");
 
//         this.mockMvc.perform(
//             MockMvcRequestBuilders
//             .post(uri)
//             .content(json)
//             .contentType(MediaType.APPLICATION_JSON))
//         .andExpect(
//             MockMvcResultMatchers
//             .status()
//             .is(404)
//         );
//     }

//     @Test
//     public void testRegisterPersonController() throws Exception {
//         URI uri = new URI("/user");

//         this.mockMvc.perform(
//             MockMvcRequestBuilders
//             .post(uri)
//             .content(json.toString())
//             .contentType(MediaType.APPLICATION_JSON))
//         .andExpect(
//             MockMvcResultMatchers
//             .status()
//             .is(201)
//         );
//     }

//     @Test
//     public void testGetAllPersonController() throws Exception {
//         URI uri = new URI("/persons");

//         mockMvc.perform(
//             MockMvcRequestBuilders
//             .get(uri))
//             .andExpect(
//               MockMvcResultMatchers
//               .status()
//               .is(200)
//             );
//     }

//     @Test
//     public void testUpdatePersonController() throws Exception {
//         URI uri = new URI("/user/1");
        
//         this.mockMvc.perform(
//             MockMvcRequestBuilders
//             .put(uri)
//             .content(json.toString())
//             .contentType(MediaType.APPLICATION_JSON))
//         .andExpect(
//             MockMvcResultMatchers
//             .status()
//             .is(200)
//         );
//     }

//     @Test
//     public void testDeletePersonController() throws Exception {
//         User user = new User(
//             "Alberto",
//             "14551518",
//             "@example.com"
//         );

//         this.userRepository.save(user);
        
//         URI uri = new URI("/user/1");
        
//         this.mockMvc.perform(
//             MockMvcRequestBuilders
//             .delete(uri)
//             .contentType(MediaType.APPLICATION_JSON))
//         .andExpect(
//             MockMvcResultMatchers
//             .status()
//             .is(200)
//         );
//     }
// }

//package com.template.authentication.service.impl;
//
//import com.template.authentication.domain.UsersDao;
//import com.template.authentication.model.Users;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class UserServiceImplTest {
//
//    @MockBean
//    private final UsersDao userDao;
//
//    @Autowired
//    private final UserServiceImpl userService;
//
//    UserServiceImplTest(UsersDao userDao, UserServiceImpl userService) {
//        this.userDao = userDao;
//        this.userService = userService;
//    }
//
//    @Test
//    void findUserById() {
//        Long userId = 1L;
//        Users user = new Users();
//        user.setUserId(userId);
//        Optional<Users> optionalUsers = Optional.of(user);
//        when(userDao.findUsersByUserId(userId)).thenReturn(optionalUsers);
//
//        Optional<Users> result = userService.findUserById(userId);
//        Assertions.assertEquals(optionalUsers.get(), result.get());
//    }
//
//    @Test
//    void findUserByEmail() {
//    }
//}
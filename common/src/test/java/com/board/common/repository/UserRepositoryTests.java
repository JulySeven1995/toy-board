package com.board.common.repository;

import com.board.common.entity.User;
import com.board.common.type.UserType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @Test
    public void saveUser() {

        final User userForSave = new User("Jaeho", "멋쟁이 재호", "1234", UserType.ADMIN);
        final User savedUser = repository.saveAndFlush(userForSave);

        assertEquals(userForSave.getUserId(), savedUser.getUserId());
        assertEquals(UserType.ADMIN, savedUser.getUserType());
    }

    @Test
    public void updateUserAndSelect() {

        final String ID = "Julius";
        final String NAME = "최재호";

        final User userForSave = new User(ID, "Julius Choi", "3145");
        final User userForUpdate = repository.saveAndFlush(userForSave);

        userForUpdate.setUserName(NAME);
        repository.saveAndFlush(userForUpdate);

        assertEquals(repository.findByUserId(ID).get().getUserName(), NAME);
    }

    @Test
    public void deleteUserAndSelect() {

        final String ID = "CJH";
        final User userForSave = new User(ID, "멋쟁이 재호", "3145");

        repository.saveAndFlush(userForSave);
        repository.deleteByUserId(userForSave.getUserId());

        assertFalse(repository.findByUserId(ID).isPresent());
    }

}

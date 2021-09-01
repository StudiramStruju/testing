package io.test.postgres.dao;

import io.test.postgres.AbstractDatabaseTest;
import io.test.postgres.domain.User;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


class UserDaoTest extends AbstractDatabaseTest {

    @Autowired
    private UserDao userDao;

    @Test
    @FlywayTest(locationsForMigrate = "UserDaoTest/testList")
    void testList() {
        final List<User> users = this.userDao.list();
        assertNotNull(users);
        assertEquals(8, users.size());
    }

    @Test
    @FlywayTest(locationsForMigrate = "UserDaoTest/testGetById")
    void testGetById() {
        final User user = this.userDao.getById(1);
        assertNotNull(user);
        assertEquals(user, new User(1, "Awesome Hacker", "email@hacker.you", "1234"));
    }

    @Test
    @FlywayTest
    void testCreate() {
        final int userId = this.userDao.create(new User("New User", "new@user.you", "hello123"));
        final User user = this.userDao.getById(userId);
        assertEquals(user, new User(userId, "New User", "new@user.you", "hello123"));
    }

    @Test
    @FlywayTest(locationsForMigrate = "UserDaoTest/testGetById")
    void testUpdate() {
        final User user = this.userDao.getById(1);
        assertNotNull(user);
        user.setFullName("I Updated my name").setPassword("hello123");
        final int id = this.userDao.update(user);
        final User updated = this.userDao.getById(id);
        assertEquals(updated, new User(id, "I Updated my name", "email@hacker.you", "hello123"));
    }
}
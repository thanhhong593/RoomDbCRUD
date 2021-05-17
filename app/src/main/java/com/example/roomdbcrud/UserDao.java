package com.example.roomdbcrud;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    User findByName(String name);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}

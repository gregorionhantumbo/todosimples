package com.softdeving.todosimples.repositories;

import com.softdeving.todosimples.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t JOIN FETCH t.user WHERE t.user.id = :userId")
    List<Task> findAllByUserId(@Param("userId") Long userId);

}

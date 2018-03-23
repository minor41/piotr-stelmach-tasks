package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Override
    List<Task> findAll();

    List<Task> findTaskById(final Long id);

    @Override
    Task save(Task task);

    Optional<Task> findById(final Long id);

    Task deleteById(final Long id);
}

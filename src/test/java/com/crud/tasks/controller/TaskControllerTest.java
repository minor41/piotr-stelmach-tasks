package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1L, "Title", "Content");
        tasks.add(task1);
        List<TaskDto> taskDtoList = new ArrayList<>();
        TaskDto taskDto1 = new TaskDto(1L, "Title", "Content");
        taskDtoList.add(taskDto1);

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Title")))
                .andExpect(jsonPath("$[0].content", is("Content")));


    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        Optional<Task> task = Optional.of(new Task(1L, "test title", " test content"));
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");

        when(dbService.getTask(anyLong())).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test title")))
                .andExpect(jsonPath("$.content", is("test content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        Optional<Task> task = Optional.of(new Task(1L, "Title", "Content"));
        List<TaskDto> taskDtoList = new ArrayList<>();
        TaskDto taskDto1 = new TaskDto(1L, "Title", "Content");
        taskDtoList.add(taskDto1);

        when(dbService.getTask(1L)).thenReturn(task);

        //When & Then
        mockMvc.perform(delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        // Given
        TaskDto taskDto1 = new TaskDto(1L, "updated test", "updated content");
        Task task1 = new Task(1L, "updated title", "updated Content");

        when(dbService.saveTask(task1)).thenReturn(task1);
        when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto1);

        // When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("updated test")))
                .andExpect(jsonPath("$.content", is("updated content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        // Given
        TaskDto taskDto1 = new TaskDto(1L, "new title", "new content");
        Task task1 = new Task(1L, "new title", "new content");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto1);

        // When & Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
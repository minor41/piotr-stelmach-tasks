package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void shouldMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "test",
                "test description",
                "top",
                "id");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("test", trelloCard.getName());
        assertEquals("test description", trelloCard.getDescription());
        assertEquals("top", trelloCard.getPos());
        assertEquals("id", trelloCard.getListId());
    }

    @Test
    public void shouldMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test",
                "test description",
                "top",
                "id");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("test", trelloCardDto.getName());
        assertEquals("test description", trelloCardDto.getDescription());
        assertEquals("top", trelloCardDto.getPos());
        assertEquals("id", trelloCardDto.getListId());
    }

    @Test
    public void shouldMapToList() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        TrelloList list1 = new TrelloList("id", "test", true);
        trelloList.add(list1);
        //When
        List<TrelloListDto> trelloListDto = trelloMapper.mapToListDto(trelloList);
        //Then
        assertEquals("id", trelloListDto.get(0).getId());
        assertEquals("test", trelloListDto.get(0).getName());
        assertTrue(trelloListDto.get(0).isClosed());
    }

    @Test
    public void shouldMapToListDto() {
        //
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        TrelloListDto list1 = new TrelloListDto("id", "test", true);
        trelloListDto.add(list1);
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDto);
        //Given
        assertEquals("id", trelloLists.get(0).getId());
        assertEquals("test", trelloLists.get(0).getName());
        assertTrue(trelloLists.get(0).isClosed());
    }

    @Test
    public void shouldMapToBoard() {
        //When
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        TrelloBoard board1 = new TrelloBoard("id", "test", new ArrayList<>());
        trelloBoards.add(board1);
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals("id", trelloBoardDtos.get(0).getId());
        assertEquals("test", trelloBoardDtos.get(0).getName());
        assertEquals(new ArrayList<>(), trelloBoardDtos.get(0).getLists());

    }

    @Test
    public void shouldMapToBoardDto(){
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        TrelloBoardDto board1 = new TrelloBoardDto("id", "test", new ArrayList<>());
        trelloBoardDtos.add(board1);
        //Then
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals("id", trelloBoards.get(0).getId());
        assertEquals("test", trelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), trelloBoards.get(0).getLists());
    }
}

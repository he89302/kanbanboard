package com.practice.cleankanban.usecase.kanbanboard.board;

import com.practice.cleankanban.domain.model.kanbanboard.board.Board;

import java.util.List;

public interface BoardRepository {
    public List<Board> findAll();
    public Board save(Board board);
    public Board findById(String id);
    public Board findFirstBoardByName(String name);
    public boolean remove(Board board);

}

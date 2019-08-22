package com.practice.cleankanban.adapter.gateway.kanbanboard;

import com.practice.cleankanban.domain.model.kanbanboard.board.Board;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryBoardRepository implements BoardRepository {
    private List<Board> boards;

    public InMemoryBoardRepository() {
        boards = new ArrayList<>();
    }

    @Override
    public List<Board> findAll() {
        return boards;
    }

    @Override
    public Board save(Board board) {
        if (boards.contains(board)) {
            return board;
        } else if (boards.add(board)) {
            return board;
        } else {
            return null;
        }
    }

    @Override
    public Board findById(String id) {
        for (Board each:boards
             ) {
            if (each.getId().equalsIgnoreCase(id)) {
                return each;
            }
        }
        throw new RuntimeException("Can't find Board by id : " + id);
    }

    @Override
    public Board findFirstBoardByName(String name) {
        for (Board each:boards
             ) {
            if (each.getName().equals(name)) {
                return each;
            }
        }
        throw new RuntimeException("Can't find Board by name : " + name);
    }

    @Override
    public boolean remove(Board board) {
        return boards.remove(board);
    }
}

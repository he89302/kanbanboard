package com.practice.cleankanban.domain.model.kanbanboard.board;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;

public class BoardTest {
    @Test
    public void when_add_a_stage_to_board_a_boardstage_is_created_and_its_ordering_is_increased_by_1(){

        Board board = new Board("Kanban Game Board");
        Stage stage1 = board.createStage("Ready");
        board.addStage(stage1);

        assertEquals(1, board.getBoardStages().size());
        assertEquals(1, board.getStageOrderingByStageId(stage1.getId()));

        Stage stage2 = board.createStage("Analysis");
        board.addStage(stage2);
        assertEquals(2, board.getBoardStages().size());
        assertEquals(2, board.getStageOrderingByStageId(stage2.getId()));

    }
}
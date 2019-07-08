package com.practice.cleankanban.usecase.kanbanboard.stage;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.usecase.Utility;
import org.junit.Test;

import static org.junit.Assert.*;


public class AddStageTest {

    @Test
    public void  add_two_stage_to_a_board() {
        Utility utility = new Utility();
        StageRepository stageRepository = utility.invoke();

        Stage stage = stageRepository.findByStageName("ToDo");
        assertNotNull(stage.getId());
        assertEquals(3, stageRepository.findAll().size());
        assertEquals("ToDo", stageRepository.findAll().get(0).getName());
        assertEquals("Doing", stageRepository.findAll().get(1).getName());
    }

}

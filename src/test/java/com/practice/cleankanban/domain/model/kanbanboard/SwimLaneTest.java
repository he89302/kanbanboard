package com.practice.cleankanban.domain.model.kanbanboard;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.SwimLane;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SwimLaneTest {
    @Test
    public void test_swim_lane_set_wip_limit_works() {
        Stage stage = new Stage("ToDo", "fake_board_id");
        SwimLane swimLane = stage.getDefaultSwimLaneOfMiniStage();
        swimLane.setWipLimit(5);
        assertEquals(5, swimLane.getWipLimit());
    }
}

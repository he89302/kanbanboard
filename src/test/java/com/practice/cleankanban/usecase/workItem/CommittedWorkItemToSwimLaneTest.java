package com.practice.cleankanban.usecase.workItem;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.SwimLane;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.Utility;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CommittedWorkItemToSwimLaneTest {
    private WorkItem ooad;
    private WorkItem stv;
    private Utility utility;

    @Before
    public void setUp() {
        ooad = new WorkItem("Implement ooad homework", "", "", "");
        stv = new WorkItem("Implement stv homework", "", "", "");
        utility = new Utility();
    }


//# Add a work item to the ScrumBoard
//    Given an kanbanboard named "ScrumBoard" with "To Do", "Doing", and "Done" stages
//    When I add a work item named "Implement Apple pay" to the "To Do" stage
//    Then the work item should be added to the default swim lane of the "To Do" stage
//    And a WorkItemAdded event should be fired
    @Test
    public void commit_a_work_item_to_todo_stage_of_a_scrum_board() throws WipLimitExceedException {
        Stage todo = utility.invoke().findByStageName("ToDo");
        SwimLane swimLane = todo.getDefaultSwimLaneOfMiniStage();

        todo.committedWorkItemToSwimLaneById(swimLane.getId(), ooad.getId());

        assertEquals(1, swimLane.getCommittedWorkItems().size());
        assertEquals(ooad.getId(), swimLane.getCommittedWorkItems().get(0));
    }

    @Test
    public  void commit_work_item_exceeds_wip_limit_1_should_be_throw_exception() throws WipLimitExceedException {
        Stage todo = utility.invoke().findByStageName("ToDo");
        SwimLane swimLane = todo.getDefaultSwimLaneOfMiniStage();

        todo.setSwimLaneWipLimit(swimLane.getId(), 1);
        todo.committedWorkItemToSwimLaneById(swimLane.getId(), ooad.getId());

        try {
            todo.committedWorkItemToSwimLaneById(swimLane.getId(), stv.getId());
            fail("Should throw a WipLimitExceedException but not.");
        } catch (WipLimitExceedException e) {
            assertEquals("Exceeds WIP Exception : 1", e.getMessage());
        }
    }
}

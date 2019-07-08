package com.practice.cleankanban.usecase.workItem;

import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.SwimLane;
import com.practice.cleankanban.domain.model.kanbanboard.WipLimitExceedException;
import com.practice.cleankanban.domain.model.workItem.WorkItem;
import com.practice.cleankanban.usecase.Utility;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UncommittedWorkItemFromSwimLaneTest {
    private WorkItem ooad;
    private WorkItem stv;
    private Stage todo;
    private Utility utility;

    @Before
    public void setUp() throws WipLimitExceedException {
        ooad = new WorkItem("Implement ooad homework", "", "", "");
        stv = new WorkItem("Implement stv homework", "", "", "");
        utility = new Utility();
        todo = utility.invoke().findByStageName("ToDo");
        SwimLane swimLane = todo.getDefaultSwimLaneOfMiniStage();

        todo.committedWorkItemToSwimLaneById(swimLane.getId(), ooad.getId());
        todo.committedWorkItemToSwimLaneById(swimLane.getId(), stv.getId());
    }

    @Test
    public void uncommitted_the_first_work_itme_from_swim_lane() throws WipLimitExceedException {
        assertEquals(2, todo.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());

        boolean result = todo.getDefaultSwimLaneOfMiniStage().uncommittedWorkItemById(ooad.getId());

        assertTrue(result);
        assertEquals(1, todo.getDefaultSwimLaneOfMiniStage().getCommittedWorkItems().size());
    }


}

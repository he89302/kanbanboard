package com.practice.cleankanban.domain.model.kanbanboard.stage;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class StageAggregateDomainEventTest extends AbstractDomainEventTest {

    private Stage stage;

    @Before
    public void mySetUp(){
        stage = new Stage("To Do", "BOARD_ID");
        creating_a_stage_publishes_a_StageCreated_a_MiniStageCreated_a_SwimLaneCreated_events();
    }


    private void creating_a_stage_publishes_a_StageCreated_a_MiniStageCreated_a_SwimLaneCreated_events() {
        assertThat(storedSubscriber.expectedResults.size()).isEqualTo(3);
        assertThat(storedSubscriber.expectedResults.get(0)).startsWith("StageCreated");
        assertThat(storedSubscriber.expectedResults.get(1)).startsWith("MiniStageCreated[name");
        assertThat(storedSubscriber.expectedResults.get(2)).startsWith("SwimLaneCreated");
    }

    @Test
    public void creating_ministage_publishes_a_MiniStageCreated_and_a_SwimLaneCreated_events() {
        storedSubscriber.expectedResults.clear();
        stage.createMiniStage("Verifying");

        assertThat(storedSubscriber.expectedResults.size()).isEqualTo(2);
        assertThat(storedSubscriber.expectedResults.get(0)).startsWith("MiniStageCreated[name");
        assertThat(storedSubscriber.expectedResults.get(1)).startsWith("SwimLaneCreated");
    }

//    @Test
//    public void deleting_a_ministage_publishes_a_SwimLaneDeleted_and_a_MiniStageDeleted_event() {
//
//        MiniStage miniStage = stage.createMiniStage("Verifying");
//        storedSubscriber.expectedResults.clear();
//        stage.deleteMiniStage(miniStage);
//
//        assertThat(storedSubscriber.expectedResults.size()).isEqualTo(2);
//        assertThat(storedSubscriber.expectedResults.get(0)).startsWith("SwimLaneDeleted");
//        assertThat(storedSubscriber.expectedResults.get(1)).startsWith("MiniStageDeleted[name='Verifying'");
//    }


//    @Test
//    public void creating_swimlane_publishes_a_SwimLaneCreated_event() {
//        stage.getDefaultMiniStage().createSwimLaneForMiniStage();
//        assertThat(subscriber.expectedResult).startsWith("SwimLaneCreated[name='', id=");
//    }
}

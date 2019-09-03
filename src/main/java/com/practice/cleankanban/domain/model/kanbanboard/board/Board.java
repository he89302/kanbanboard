package com.practice.cleankanban.domain.model.kanbanboard.board;

import com.practice.cleankanban.domain.Entity;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Board extends Entity {

    Set<BoardStage> boardStages;

    public Board(String name) {
        super(name);

        boardStages = new HashSet<BoardStage>();
    }

    public Stage createStage(String stageName) {
        Stage stage = new Stage(stageName, this.getId());
        return stage;
    }

    public Set<BoardStage> getBoardStages() {
        return Collections.unmodifiableSet(boardStages);
    }

    public void addStage(Stage stage) {
        BoardStage boardStage = new BoardStage(this.getId(), stage.getId());
        boardStage.setOrdering(boardStages.size() + 1);
        boardStages.add(boardStage);
    }

    public int getStageOrderingByStageId(String id) {
        for (BoardStage each:boardStages
             ) {
            if (each.getStageId().equalsIgnoreCase(id)) {
                return each.getOrdering();
            }
        }

        throw new RuntimeException("Can't find stage : " + id + " on the board.");
    }

    public boolean isContainStage(String stageId) {
        boolean result = false;
        for (BoardStage each:boardStages
             ) {
            if (each.getStageId().equalsIgnoreCase(stageId)) {
                result = true;
            }
        }

        return result;
    }

	public void reorderBoardStage(String stageId, int oldPosition, int newPosition) {
        for (BoardStage each:boardStages) {
            if (each.getOrdering() == oldPosition) {
                each.setOrdering(newPosition);
                continue;
            }
            if (this.isMoveForward(oldPosition, newPosition)) {
                if (isMoveForwardForNotNeededReorderPosition(oldPosition, newPosition, each)) {
                    continue;
                }
                if (isMoveForwardForNeededReorderPosition(oldPosition, newPosition, each)) {
                    each.moveBackward();
                }
                
            } else {//move backward
                if (isMoveBackwardForNotNeededReorderingPosition(oldPosition, newPosition, each)) {
                    continue;
                }
                if (isMoveBackwardForNeededReorderingPosition(oldPosition, newPosition, each)) {
                    each.moveForward();
                }
            }
        }

	}

    private boolean isMoveForwardForNeededReorderPosition(int oldPosition, int newPosition, BoardStage each) {
        return each.getOrdering() < oldPosition && each.getOrdering() >= newPosition;
    }

    private boolean isMoveForwardForNotNeededReorderPosition(int oldPosition, int newPosition, BoardStage each) {
        return each.getOrdering() > oldPosition || each.getOrdering() < newPosition;
    }

    private boolean isMoveBackwardForNeededReorderingPosition(int oldPosition, int newPosition, BoardStage each) {
        return each.getOrdering() > oldPosition && each.getOrdering() <= newPosition;
    }

    private boolean isMoveBackwardForNotNeededReorderingPosition(int oldPosition, int newPosition, BoardStage each) {
        return each.getOrdering() < oldPosition || each.getOrdering() > newPosition;
    }

    private boolean isMoveForward(int oldPosition, int newPosition) {
        return oldPosition > newPosition ? true:false;
    }

	public void removeStageOnBoard(Stage stage) {
        int removeStageOrder = -1;
        int lastStageOrder = boardStages.size();
        for (BoardStage each:boardStages) {
            if (each.getStageId().equalsIgnoreCase(stage.getId())) {
                removeStageOrder = each.getOrdering();
                reorderBoardStage(stage.getId(), removeStageOrder, lastStageOrder);
                boardStages.remove(each);
                break;
            }
        }
	}
}

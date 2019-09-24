package com.practice.cleankanban.usecase.kanbanboard;

import com.practice.cleankanban.domain.model.kanbanboard.stage.MiniStage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.Stage;
import com.practice.cleankanban.domain.model.kanbanboard.stage.SwimLane;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.MiniStageDto;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageDto;
import com.practice.cleankanban.usecase.kanbanboard.stage.SwimLaneDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity data transfer to Object
 */
public class DtpConverter {

    public static List<StageDto> convertDtoList(List<Stage> stages, BoardRepository boardRepository) {
        List<StageDto> stageDtoList = new ArrayList<>();
        for (Stage each:stages) {
            if (boardRepository.findById(each.getBoardId()).isContainStage(each.getId())) {
                stageDtoList.add(DtpConverter.covertStageDto(each, boardRepository.findById(each.getBoardId()).getStageOrderingByStageId(each.getId())));
            }
        }
        return stageDtoList;
    }

    private static StageDto covertStageDto(Stage stage, int ordering) {
        List<MiniStageDto> miniStageDtoList = new ArrayList<>();
        for (MiniStage each:stage.getMiniStages()
             ) {
            miniStageDtoList.add(new MiniStageDto(each.getStageId(),
                    each.getId(),
                    each.getName(),
                    createSwimLaneDtoList(each.getSwimLanes())));
        }
        return new StageDto(stage.getName(), stage.getId(), ordering, stage.getBoardId(), miniStageDtoList);
    }

    private static List<SwimLaneDto> createSwimLaneDtoList(List<SwimLane> swimLanes) {
        List<SwimLaneDto> swimLaneDtoList = new ArrayList<>();
        for (SwimLane each:swimLanes) {
            swimLaneDtoList.add(createSwimLaneDto(each));
        }

        return swimLaneDtoList;
    }

    private static SwimLaneDto createSwimLaneDto(SwimLane each) {
        return new SwimLaneDto(each.getId(), each.getName());
    }
}

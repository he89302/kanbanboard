package com.practice.cleankanban.adapter.resource;

import com.practice.cleankanban.adapter.presenter.kanbanboard.MiniStageInfo;
import com.practice.cleankanban.adapter.presenter.kanbanboard.StageInfo;
import com.practice.cleankanban.adapter.presenter.kanbanboard.SwimLaneInfo;
import com.practice.cleankanban.usecase.kanbanboard.stage.MiniStageDto;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageDto;
import com.practice.cleankanban.usecase.kanbanboard.stage.SwimLaneDto;

import java.util.ArrayList;
import java.util.List;

public class StageInfoConverter {
    public static List<StageInfo> convertStageInfo(List<StageDto> stages) {
        List<StageInfo> stageInfoList = new ArrayList<>();
        for (StageDto each:stages) {
            stageInfoList.add(StageInfoConverter.covertStageInfo(each));
        }
        return stageInfoList;
    }

    private static StageInfo covertStageInfo(StageDto stage) {
        List<MiniStageInfo> miniStageInfoList = new ArrayList<>();
        for (MiniStageDto each:stage.getMiniStageDtos()
        ) {
            miniStageInfoList.add(new MiniStageInfo(each.getStageId(),
                    each.getId(),
                    each.getMiniStageName(),
                    createSwimLaneInfo(each.getSwimLanes())));
        }
        return new StageInfo(stage.getName(), stage.getId(), stage.getOrdering(), stage.getBoardId(), miniStageInfoList);
    }

    private static List<SwimLaneInfo> createSwimLaneInfo(List<SwimLaneDto> swimLanes) {
        List<SwimLaneInfo> swimLaneInfoList = new ArrayList<>();
        for (SwimLaneDto each:swimLanes) {
            swimLaneInfoList.add(createSwimLaneInfo(each));
        }

        return swimLaneInfoList;
    }

    private static SwimLaneInfo createSwimLaneInfo(SwimLaneDto each) {
        return new SwimLaneInfo(each.getSwimLaneId(), each.getSwimLaneName());
    }
}

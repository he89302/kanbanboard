//package com.practice.cleankanban.application.console.spring.config;
//
//
//import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
//import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//@Configuration
//@Order(1)
//public class RepositoryConfiguration {
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Bean(name="stageRepository")
//    public StageRepository stageRepository() {
//
//    StageRepository stageRepository = new InMemoryStageRepository();
////        StageRepository stageRepository = new SerializableStageRepository();
//
//        DefaultBoard defaultBoard = new DefaultBoard(boardRepository, stageRepository);
//        defaultBoard.createKanbanBoardGameStage();
//        defaultBoard.createScrumBoardStage();
//
//        return stageRepository;
//    return new InMemoryStageRepository();
//    }
//
//
//    @Bean(name="boardRepository")
//    public BoardRepository boardRepository() {
//        BoardRepository repository = new InMemoryBoardRepository();
////        BoardRepository repository = new SerializableBoardRepository();
//        return repository;
//    }
//
//}

package com.practice.cleankanban.application.console.spring.config;


import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryBoardRepository;
import com.practice.cleankanban.adapter.gateway.kanbanboard.InMemoryStageRepository;
import com.practice.cleankanban.usecase.kanbanboard.board.BoardRepository;
import com.practice.cleankanban.usecase.kanbanboard.stage.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class RepositoryConfiguration {
    @Autowired
    private BoardRepository boardRepository;

    @Bean(name="stageRepository")
    public StageRepository stageRepository() {

    StageRepository stageRepository = new InMemoryStageRepository();
//        StageRepository stageRepository = new SerializableStageRepository();
        return stageRepository;
    }


    @Bean(name="boardRepository")
    public BoardRepository boardRepository() {
        BoardRepository repository = new InMemoryBoardRepository();
//        BoardRepository repository = new SerializableBoardRepository();
        return repository;
    }

}

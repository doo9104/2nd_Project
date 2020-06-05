package com.doo9104.project.Board.service;

import com.doo9104.project.Board.domain.entity.Board;
import com.doo9104.project.Board.domain.entity.TopView;
import com.doo9104.project.Board.domain.repository.Board_Repository;

import com.doo9104.project.Board.domain.repository.TopViewRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;

@RequiredArgsConstructor
@Component
public class TopViewScheduler {

    private final Board_Repository boardRepository;
    private final TopViewRepository topViewRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 24시에 실행
    //@Scheduled(fixedDelay=1000)
    public void scheduleFixedRateTask() {
        // 우선 리스트를 다 비운다
        topViewRepository.deleteAll();

        for(Board board : boardRepository.findTop6ByOrderByHitcountDesc()) {
            TopView topView = new TopView();
            topView.setBoard(board);
            topView.setTitle(board.getTitle());
            topView.setThumbnailsrc(board.getThumbnailsrc());
            topView.setBoardtype(board.getBoardtype());
            topViewRepository.saveAndFlush(topView);
        }



    }
}

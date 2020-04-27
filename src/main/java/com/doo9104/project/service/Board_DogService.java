package com.doo9104.project.service;

import com.doo9104.project.domain.entity.Board_DogRepository;
import com.doo9104.project.domain.entity.Board_Dog;
import com.doo9104.project.web.dto.DogDto;
import com.doo9104.project.web.dto.DogListResponseDto;
import com.doo9104.project.web.dto.DogPostResponseDto;
import com.doo9104.project.web.dto.DogUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class Board_DogService {

    private final Board_DogRepository boardDogRepository;


    @Transactional
    public Long save(DogDto dogDto) {
        return boardDogRepository.save(dogDto.toEntity()).getId();
    }


    public Page<Board_Dog> findBoardList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(),new Sort(Sort.Direction.DESC, "id"));

        return boardDogRepository.findAll(pageable);
    }


    @Transactional
    public Long update(Long id, DogUpdateRequestDto dogUpdateRequestDto) {
        Board_Dog boardDog = boardDogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("해당 게시물이 없습니다.")));

        boardDog.update(dogUpdateRequestDto.getTitle(), dogUpdateRequestDto.getContent());
        return id;
    }


    public DogPostResponseDto findById(Long id) {
        Board_Dog entity = boardDogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new DogPostResponseDto(entity);
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유효하되 조회기능만 남겨둠(조회속도 향상)
    public List<DogListResponseDto> findAllDesc() {
        return boardDogRepository.findAllDesc().stream()
                .map(DogListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void delete(Long id) {
        Board_Dog boardDog = boardDogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        boardDogRepository.delete(boardDog);
    }













}

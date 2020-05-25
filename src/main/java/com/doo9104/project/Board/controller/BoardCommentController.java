package com.doo9104.project.Board.controller;

import com.doo9104.project.Board.domain.entity.Board;
import com.doo9104.project.Board.domain.entity.BoardType;
import com.doo9104.project.Board.domain.entity.Board_Comment;
import com.doo9104.project.Board.domain.repository.Board_CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class BoardCommentController {

    @Autowired
    private Board_CommentRepository boardCommentRepository;


    private List<Board_Comment> getListByBoard(Board board) throws RuntimeException{
        System.out.println("getListByBoard ID : "+ board.getId());
        return boardCommentRepository.getCommentsOfBoard(board);
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<List<Board_Comment>> getComment(@PathVariable("id") Long id, @PathVariable BoardType type) {
        Board board = new Board();
        board.SetBoardId(id);
        return ResponseEntity.ok(getListByBoard(board));
    }

    @Transactional
    @PostMapping("/{type}/{id}")
    public ResponseEntity<List<Board_Comment>> addComment(@PathVariable("id") Long id, @RequestBody Board_Comment board_comment, @PathVariable BoardType type) {
        Board board = new Board();
        board.SetBoardId(id);

        // comment엔티티의 Board에 위에 SetBoardId 값을 넣어준다.
        board_comment.SetCommentId(board);
        boardCommentRepository.save(board_comment);
        boardCommentRepository.CommentCountUp(id);

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{type}/{bid}/{cid}")
    public ResponseEntity<List<Board_Comment>> deleteComment(@PathVariable("bid") Long bid, @PathVariable("cid") Long cid, @PathVariable BoardType type) {

        boardCommentRepository.deleteById(cid);
        boardCommentRepository.CommentCountDown(bid);

        Board board = new Board();
        board.SetBoardId(bid);

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
    }


    @Transactional
    @PutMapping("/{type}/{bid}") /*게시물 번호*/
    public ResponseEntity<List<Board_Comment>> modifyComment(@PathVariable("bid") Long bid, @RequestBody Board_Comment board_comment, @PathVariable BoardType type) {
        //값이 있으면 코드 블록을 실행 함
        boardCommentRepository.findById(board_comment.getId()).ifPresent(origin -> {
            origin.SetCommentContent(board_comment.getContent());
            boardCommentRepository.save(origin);
        });

        Board board = new Board();
        board.SetBoardId(bid);

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
    }





}

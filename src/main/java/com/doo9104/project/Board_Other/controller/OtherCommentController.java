package com.doo9104.project.Board_Other.controller;

import com.doo9104.project.Board_Other.domain.entity.Board_Other;
import com.doo9104.project.Board_Other.domain.entity.Comment_Other;
import com.doo9104.project.Board_Other.domain.entity.Comment_OtherRepository;
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
public class OtherCommentController {

    @Autowired
    private Comment_OtherRepository comment_otherRepository;


    private List<Comment_Other> getListByBoard(Board_Other boardOther) throws RuntimeException{
        System.out.println("getListByBoard ID : "+ boardOther.getId());
        return comment_otherRepository.getCommentsOfBoard(boardOther);
    }

    @GetMapping("/other/{id}")
    public ResponseEntity<List<Comment_Other>> getComment(@PathVariable("id") Long id) {
        Board_Other boardOther = new Board_Other();
        boardOther.SetBoardId(id);
        return ResponseEntity.ok(getListByBoard(boardOther));
    }

    @Transactional
    @PostMapping("/other/{id}")
    public ResponseEntity<List<Comment_Other>> addComment(@PathVariable("id") Long id, @RequestBody Comment_Other comment_other) {
        Board_Other boardOther = new Board_Other();
        boardOther.SetBoardId(id);

        // comment_other엔티티의 Board_Other에 위에 SetBoardId 값을 넣어준다.
        comment_other.SetCommentId(boardOther);
        comment_otherRepository.save(comment_other);
        comment_otherRepository.CommentCountUp(id);

        return new ResponseEntity<>(getListByBoard(boardOther), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/other/{bid}/{cid}")
    public ResponseEntity<List<Comment_Other>> deleteComment(@PathVariable("bid") Long bid,@PathVariable("cid") Long cid) {

        comment_otherRepository.deleteById(cid);
        comment_otherRepository.CommentCountDown(bid);

        Board_Other boardOther = new Board_Other();
        boardOther.SetBoardId(bid);

        return new ResponseEntity<>(getListByBoard(boardOther), HttpStatus.OK);
    }


    @Transactional
    @PutMapping("/other/{bid}") /*게시물 번호*/
    public ResponseEntity<List<Comment_Other>> modifyComment(@PathVariable("bid") Long bid, @RequestBody Comment_Other comment_other) {
        //값이 있으면 코드 블록을 실행 함
        comment_otherRepository.findById(comment_other.getId()).ifPresent(origin -> {
            origin.SetCommentContent(comment_other.getContent());
            comment_otherRepository.save(origin);
        });

        Board_Other boardOther = new Board_Other();
        boardOther.SetBoardId(bid);

        return new ResponseEntity<>(getListByBoard(boardOther), HttpStatus.CREATED);
    }





}

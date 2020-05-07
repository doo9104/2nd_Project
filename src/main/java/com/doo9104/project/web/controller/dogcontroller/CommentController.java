package com.doo9104.project.web.controller.dogcontroller;

import com.doo9104.project.domain.entity.Board_Dog;
import com.doo9104.project.domain.entity.Comment_Dog;
import com.doo9104.project.domain.entity.Comment_DogRepository;
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
public class CommentController {

    @Autowired
    private Comment_DogRepository comment_dogRepository;

    private List<Comment_Dog> getListByBoard(Board_Dog boardDog) throws RuntimeException{
        System.out.println("getListByBoard ID : "+ boardDog.getId());
        return comment_dogRepository.getCommentsOfBoard(boardDog);
    }

    @GetMapping("/dog/{id}")
    public ResponseEntity<List<Comment_Dog>> getComment(@PathVariable("id") Long id) {
        Board_Dog boardDog = new Board_Dog();
        boardDog.SetBoardId(id);
        return ResponseEntity.ok(getListByBoard(boardDog));
    }

    @Transactional
    @PostMapping("/dog/{id}")
    public ResponseEntity<List<Comment_Dog>> addComment(@PathVariable("id") Long id, @RequestBody Comment_Dog comment_dog) {
        Board_Dog boardDog = new Board_Dog();
        boardDog.SetBoardId(id);

        // comment_dog엔티티의 Board_Dog에 위에 SetBoardId 값을 넣어준다.
        comment_dog.SetCommentId(boardDog);
        comment_dogRepository.save(comment_dog);


        return new ResponseEntity<>(getListByBoard(boardDog), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/dog/{bid}/{cid}")
    public ResponseEntity<List<Comment_Dog>> deleteComment(@PathVariable("bid") Long bid,@PathVariable("cid") Long cid) {

        comment_dogRepository.deleteById(cid);

        Board_Dog boardDog = new Board_Dog();
        boardDog.SetBoardId(bid);

        return new ResponseEntity<>(getListByBoard(boardDog), HttpStatus.OK);
    }


    @Transactional
    @PutMapping("/dog/{id}")
    public ResponseEntity<List<Comment_Dog>> modifyComment(@PathVariable("id") Long id, @RequestBody Comment_Dog comment_dog) {
        //값이 있으면 코드 블록을 실행 함
        comment_dogRepository.findById(comment_dog.getId()).ifPresent(origin -> {
            origin.SetCommentContent(comment_dog.getContent());
            comment_dogRepository.save(origin);
        });

        Board_Dog boardDog = new Board_Dog();
        boardDog.SetBoardId(id);

        return new ResponseEntity<>(getListByBoard(boardDog), HttpStatus.CREATED);
    }

}
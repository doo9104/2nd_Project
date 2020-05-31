package com.doo9104.project.web;

import com.doo9104.project.Board.domain.entity.Board;
import com.doo9104.project.Board.domain.repository.Board_Repository;
import com.doo9104.project.Board.dto.BoardDto;
import com.doo9104.project.Board.dto.BoardUpdateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Board_Repository boardDogRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @After
    public void tearDown() throws Exception {
        boardDogRepository.deleteAll();
    }

    @Test
    public void 등록테스트() throws Exception {
        //given

        String title = "title";
        String content = "content";
        BoardDto boardDto = BoardDto.builder()
                .title(title)
                .content(content)
                .writer("author")
                .build();

        String url = "http://localhost:" + port + "/post";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, boardDto,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Board> all = boardDogRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void 수정() throws Exception {
        //given
        Board savedPosts = boardDogRepository.save(Board.builder()
                .title("title")
                .content("content")
                .writer("writer")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "modifiedTitle";
        String expectedContent = "modifiedContent";

        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/post/" + updateId;

        HttpEntity<BoardUpdateRequestDto> requestEntity = new HttpEntity<>(boardUpdateRequestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Board> all = boardDogRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

}

package com.doo9104.project.web;

import com.doo9104.project.domain.entity.Board_Dog;
import com.doo9104.project.domain.entity.Board_DogRepository;
import com.doo9104.project.web.dto.DogDto;
import com.doo9104.project.web.dto.DogUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Board_DogRepository boardDogRepository;

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
        DogDto dogDto = DogDto.builder()
                .title(title)
                .content(content)
                .writer("author")
                .build();

        String url = "http://localhost:" + port + "/post";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,dogDto,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Board_Dog> all = boardDogRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void 수정() throws Exception {
        //given
        Board_Dog savedPosts = boardDogRepository.save(Board_Dog.builder()
                .title("title")
                .content("content")
                .writer("writer")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "modifiedTitle";
        String expectedContent = "modifiedContent";

        DogUpdateRequestDto dogUpdateRequestDto = DogUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/post/" + updateId;

        HttpEntity<DogUpdateRequestDto> requestEntity = new HttpEntity<>(dogUpdateRequestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Board_Dog> all = boardDogRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

}

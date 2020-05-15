package com.doo9104.project.web.controller.filecontroller;


import com.google.gson.JsonObject;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class FileManageController {

    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }


    @PostMapping(value = "/uploadSummernoteImageFile")
    @ResponseBody
    public Map uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        Map result = new HashMap<String, Object>();

        String fileRoot = "C:\\summernote_image\\";    //저장될 파일 경로

        // Make Folder
        File uploadPath = new File(fileRoot, getFolder());
        if (uploadPath.exists() == false) {
            uploadPath.mkdirs();
            //uploadPath : C:\summernote_image\2020\05\15
        }

        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명

        // 랜덤 UUID+확장자로 저장될 savedFileName
        String savedFileName = UUID.randomUUID() + "_" + originalFileName;
        // savedFileName : 968832b0-6341-4093-abb4-decc27cb2136_dog2.png

        File targetFile = new File(uploadPath + "/" + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);    //파일 저장

            /*FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath + "/" + "s_" + savedFileName));
            Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
            thumbnail.close();*/
            result.put("url","/summernoteImage/" + getFolder() + "/" + savedFileName);
            return result;
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);    // 실패시 저장된 파일 삭제
            e.printStackTrace();
            result.put("url","fail");
            return result;
        }
    }


    @PostMapping(value = "/deleteSummernoteImageFile")
    @ResponseBody
    public ResponseEntity<String> deleteSummernoteImageFile(String src) throws UnsupportedEncodingException{
       System.out.println("호출되어땅! : " + src);

        File file;


        String path = src.replace("http://localhost:8080/summernoteImage","c:\\summernote_image");
        // 한글 파일이 있을수도 있으니 디코딩 한다음 처리.
        path = URLDecoder.decode(path,"UTF-8");
        file = new File(path);


        System.out.println("url인코딩 결과 : " + path);

        try {
            if( file.exists() ){
                if(file.delete()){
                    System.out.println("파일삭제 성공");
                }else {
                    System.out.println("파일삭제 실패");
                }
            }else{
                System.out.println("파일이 존재하지 않습니다.");
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("deleted",HttpStatus.OK);
    }


    /*@PostMapping(value = "/makeThumbnail", produces = "application/json")
    public String makeThumbnail(@RequestBody String src) {
        // http://localhost:8080/summernoteImage/2020\05\15/265fe77a-2e2b-4358-8264-3de8325d54c8_dog.png
        // 이렇게 들어옴
        String f = "http://localhost:8080";
        //src = f+"/summernoteImage/2020\\05\\15/265fe77a-2e2b-4358-8264-3de8325d54c8_dog.png";
        //src = "C:\\Users\\devil\\Downloads\\attach.png";
        System.out.println(src);
        File file = new File(src);
        String path = file.getParentFile().toString();
        String fileName = file.getName();

        System.out.println(path); // \summernoteImage\2020\05\15
        System.out.println(fileName); // 265fe77a-2e2b-4358-8264-3de8325d54c8_dog.png



        try {
            FileOutputStream thumbnail = new FileOutputStream(new File(path + "/"  + fileName));
            //Thumbnailator.createThumbnail(file, thumbnail, 100, 100);
            Thumbnails.of(new File(src))
                    .size(160, 160)
                    .toFile(new File(path + "/" + "thumbnail123_" + fileName));
            thumbnail.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "gello";
    }
*/




}







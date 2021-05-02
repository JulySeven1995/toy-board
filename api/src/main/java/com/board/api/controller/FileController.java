package com.board.api.controller;

import com.board.api.service.PostFileService;
import com.board.common.entity.PostFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/file")
public class FileController {

    private final PostFileService postFileService;

    public FileController(PostFileService postFileService) {

        this.postFileService = postFileService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getFile", params = "postFileUid")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getFile(@RequestParam("postFileUid") Long postFileUid) throws IOException {

        PostFile postFile = postFileService.getItemByUid(postFileUid)
                .orElseThrow(() -> new IllegalArgumentException(String.format("File Uid Not Exists [%s]", postFileUid)));

        FileSystemResource resource = new FileSystemResource(postFile.getFilePath());

        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.setContentType(new MediaType("application", "download", StandardCharsets.UTF_8));
        responseHeaders.set("Content-Disposition", "attachment;filename=\"" + postFile.getOriginalFileName() + "\";");
        responseHeaders.set("Content-Transfer-Encoding", "binary");
        String encodedFilename = URLEncoder.encode(postFile.getOriginalFileName(),"UTF-8").replace("+", "%20");

        responseHeaders.set("Content-Disposition",
                "attachment;filename=" + encodedFilename + ";filename*= UTF-8''" + encodedFilename);

        return new ResponseEntity<>(new InputStreamResource(resource.getInputStream()), responseHeaders, HttpStatus.OK);
    }

}

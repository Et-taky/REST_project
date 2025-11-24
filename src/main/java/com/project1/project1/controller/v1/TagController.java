package com.project1.project1.controller.v1;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.project1.services.TagService;
import com.project1.project1.util.GenerateEtag;

@RestController
@RequestMapping(path = "/tags",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },headers = "X-API-VERSION=1")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping()
    public ResponseEntity<List<String>> getTags(
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        List<String> tags = tagService.getAllTags();
        String eTag = GenerateEtag.generate(tags);

        if (ifNoneMatch != null && ifNoneMatch.equals(eTag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(eTag)
                    .build();
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(tags);
    }


}

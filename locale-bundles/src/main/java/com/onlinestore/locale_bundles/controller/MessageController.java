package com.onlinestore.locale_bundles.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Locale Bundles Service")
@RequiredArgsConstructor
@RestController
public class MessageController {

    private final ResourceLoader resourceLoader;

    @ApiOperation(value = "Get all messages based on localization", notes = "Retrieves a resources bundles")
    @GetMapping("/api/v1/messages")
    public ResponseEntity<Resource> getMessages(@RequestParam("lang") String lang) {
        /** If the i18n is in the multi module project root, and not in module resources folder*/
        //Resource resource = resourceLoader.getResource("file:" + System.getenv("PROJECT_ROOT") + "/i18n/messages_" + lang + ".json");
        Resource resource = resourceLoader.getResource("classpath:i18n/messages_" + lang + ".json");
        return ResponseEntity.ok(resource);
    }
}

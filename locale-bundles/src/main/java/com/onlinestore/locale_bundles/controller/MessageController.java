package com.onlinestore.locale_bundles.controller;

import com.onlinestore.locale_bundles.service.MessageService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Locale;

@Validated
@OpenAPIDefinition(info = @Info(title = "Locale Bundles Service", version = "v1"))
@RequiredArgsConstructor
@CrossOrigin(origins = "${angular.client.url}")
@RequestMapping("/api/i18n")
@RestController
public class MessageController {

    private final MessageService messageService;
    @Operation(summary = "Get all messages based on localization", description = "Retrieves a resources bundles")
    @GetMapping(value = "/v1/{lang}.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> getMessages(@NotBlank @PathVariable("lang") String lang) throws IOException {
        var resource = messageService.getMessagesByLanguage(lang.toLowerCase(Locale.ROOT));
        if (!resource.exists() || resource.contentLength() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resource);
    }
}

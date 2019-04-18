package org.starrier.imperator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Starrier
 * @date 2018/12/21.
 */
@RestController
public class FallbackController {

    @GetMapping("/articleFallback")
    public String fallback() {
        return "article-service server is available ";
    }
}

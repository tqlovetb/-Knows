package tq.tyd.knows.faq.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tq.tyd.knows.commons.model.Tag;
import tq.tyd.knows.faq.service.ITagService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/v2/tags")
public class TagController {
    @Autowired
    private ITagService tagService;
    @GetMapping("")
    public List<Tag> tags(){
        List<Tag> tags = tagService.getTags();
        return tags;
    }

}

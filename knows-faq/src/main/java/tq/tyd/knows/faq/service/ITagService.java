package tq.tyd.knows.faq.service;


import com.baomidou.mybatisplus.extension.service.IService;
import tq.tyd.knows.commons.model.Tag;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
public interface ITagService extends IService<Tag> {

    List<Tag> getTags();

    Map<String,Tag> getTagMap();

}

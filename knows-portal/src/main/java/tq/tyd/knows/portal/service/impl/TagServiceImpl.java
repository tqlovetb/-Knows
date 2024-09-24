package tq.tyd.knows.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import tq.tyd.knows.portal.model.Tag;
import tq.tyd.knows.portal.mapper.TagMapper;
import tq.tyd.knows.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 糖球
 * @since 2024-06-26
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
/*CopyOnWriteArrayList 和 ConcurrentHashMap 的区别：

CopyOnWriteArrayList 是一个线程安全的 List 实现，适合于读多写少的场景。它通过在写操作（添加、修改、删除元素）时复制整个数组来实现线程安全，读操作则无需加锁，因此适合于读操作频繁的场景。

ConcurrentHashMap 是一个线程安全的 Map 实现，适合于高并发读写的场景。它通过分段锁（锁分段技术）来实现高效的并发访问，不同的段可以被不同的线程同时访问，因此适合于读写操作都频繁的场景。

什么时候使用哪一个：

CopyOnWriteArrayList 适合于以下情况：

当需要一个线程安全的 List，并且读操作远远多于写操作时。
当对 List 的迭代操作非常频繁，且对实时性要求不高时。
例如事件监听器列表，在这种情况下，读操作非常频繁，但添加或删除监听器的操作较少发生。
ConcurrentHashMap 适合于以下情况：

当需要一个高度并发访问的线程安全的 Map 结构时。
当 Map 的大小较大，并且有多个线程需要同时读取和修改 Map 时。
例如缓存系统、Web 应用程序中的会话管理等场景，这些场景下，对 Map 的并发读写操作非常频繁。*/
    private List<Tag> tags = new CopyOnWriteArrayList<>();
    private Map<String,Tag> tagMap= new ConcurrentHashMap<>();
    @Autowired
    TagMapper tagMapper;
    public List<Tag> getTags(){
        if(tags.isEmpty()){
            synchronized (tags){
//                防止多线程，上面虽然判断过了，但可能到这里又变了
                if(tags.isEmpty()){
                    List<Tag> list = tagMapper.selectList(null);
//                    与使用 addAll 相比，使用 add 需要显式地编写循环代码，虽然功能上是等价的，但不如 addAll 简洁和直观。
                    tags.addAll(list);
                    for(Tag t : tags){
                        tagMap.put(t.getName(), t);
                    }
                    System.out.println("tags加载数据完成");
                }
            }
        }

        return tags;
    }

    @Override
    public Map<String, Tag> getTagMap() {
//        isEmpty() 方法：
//
//适用场景： 主要用于检查集合类（如 List、Map、Set 等）是否为空，即其中是否没有任何元素。
//        != null 检查：
//
//适用场景： 用于检查一个对象是否被实例化（即不为 null）。这通常用于单个对象的引用，而不是集合。
        if(tagMap.isEmpty()){
            getTags();
        }
        return tagMap;
    }


}

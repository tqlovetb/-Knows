package tq.tyd.knows.serach;

import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import tq.tyd.knows.commons.model.Question;
import tq.tyd.knows.serach.repository.ItemRepository;
import tq.tyd.knows.serach.repository.QuestionRepository;
import tq.tyd.knows.serach.service.IQuestionService;
import tq.tyd.knows.serach.vo.Item;
import tq.tyd.knows.serach.vo.QuestionVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class KnowsSerachApplicationTests {

    @Resource
    ItemRepository itemRepository;
    @Test
    void addOne(){
        Item item = new Item()
                .setId(1L)
                .setTitle("罗技激光无线游戏鼠标")
                .setCategory("鼠标")
                .setBrand("罗技")
                .setPrice(160.0)
                .setImages("/1.jpg");
        itemRepository.save(item);
        System.out.println("ok");
    }
    @Test
    void getOne(){
        Optional<Item> optional = itemRepository.findById(1L);
        System.out.println(optional);
    }
    @Test
    void addList(){
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L,"罗技激光有线办公鼠标","鼠标",
                "罗技",102.5,"/2.jpg"));
        list.add(new Item(3L,"雷蛇机械无线游戏键盘","键盘",
                "雷蛇",315.0,"/3.jpg"));
        list.add(new Item(4L,"微软有线静音办公鼠标","鼠标",
                "微软",238.0,"/4.jpg"));
        list.add(new Item(5L,"罗技有线机械背光键盘","键盘",
                "罗技",286.0,"/5.jpg"));
        itemRepository.saveAll(list);
        System.out.println("ok");
    }
    @Test
    void getAll(){
        Iterable<Item> itemIterator = itemRepository.findAll();
        for(Item t : itemIterator){
            System.out.println(t);
        }
    }
    @Test
    void query1(){
        Iterable<Item> items = itemRepository.queryItemsByTitleMatches("游戏");
        for(Item item : items){
            System.out.println(item);
        }
    }

    @Test
    void query2(){
        Iterable<Item> items = itemRepository.queryItemsByTitleMatchesAndBrandMatches("游戏", "罗技");
        for(Item item : items){
            System.out.println(item);
        }
    }
    @Test
    void query3(){
        Iterable<Item> items = itemRepository.queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc("游戏", "罗技");
        for(Item item : items){
            System.out.println(item);
        }
    }
    @Test
    void query4(){
        int pageNum = 1;
        int pageSize = 2;
        Page<Item> page = itemRepository.queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc("游戏", "罗技", PageRequest.of(pageNum-1, pageSize));
        for(Item item : page){
            System.out.println(item);
        }
        System.out.println("总页数:"+page.getTotalPages());
        System.out.println("当前页:"+page.getNumber());
        System.out.println("每页条数:"+page.getSize());
        System.out.println("是不是首页:"+page.isFirst());
        System.out.println("是不是末页:"+page.isLast());
    }
    @Resource
    IQuestionService questionService;
    @Test
    void addEs() {
        questionService.synData();
    }
    @Resource
    QuestionRepository questionRepository;
    @Test
    void search(){
        Page<QuestionVo> page = questionRepository.queryAllByParams("java", "java", 11, PageRequest.of(0,8));
        for(QuestionVo q : page){
            System.out.println(q);
        }
    }
    @Test
    void testService(){
        PageInfo<QuestionVo> pageInfo = questionService.search("java", "st2", 1,8);
        for(QuestionVo q:pageInfo.getList()){
            System.out.println(q);
        }

    }

}

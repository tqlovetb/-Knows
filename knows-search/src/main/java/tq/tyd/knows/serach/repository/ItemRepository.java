package tq.tyd.knows.serach.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import tq.tyd.knows.serach.vo.Item;

// Spring家族框架都将数据访问层称之为Repository
@Repository
public interface ItemRepository extends
        ElasticsearchRepository<Item,Long> {
    // 自定义查询方法
// SpringData框架都支持使用方法名称来表示查询逻辑
//              所以方法名称必须严格按照格式要求,不能有任何错误
//   query:查询 Item是查询索引 Items表示返回多个
//   By:类似于sql的where后面跟条件   Title:条件属性名称 Matches:模糊匹配
    Iterable<Item> queryItemsByTitleMatches(String title);

    Iterable<Item> queryItemsByTitleMatchesAndBrandMatches(String title, String brand);
    Iterable<Item> queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc(String title, String brand);
    Page<Item> queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc(String title, String brand, Pageable pageable);

}

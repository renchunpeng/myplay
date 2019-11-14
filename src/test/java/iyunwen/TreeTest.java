package iyunwen;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cdxu@iyunwen.com on 2019/7/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeTest {

//    @Autowired
//    private LabelMapper labelMapper;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void init() throws JsonProcessingException {
//        List<Label> byWebId = labelMapper.findByWebId(111L);
//        List<LabelTree> source = new ArrayList<>();
//        Iterator<Label> iterator = byWebId.iterator();
//        while (iterator.hasNext()){
//            Label next = iterator.next();
//            LabelTree item = new LabelTree();
//            BeanUtils.copyProperties(next, item);
//            source.add(item);
//        }
//
//        YWTreeUtils treeUtils = new YWTreeUtils(source);
//        List<LabelTree> trees = (List<LabelTree>)treeUtils.buildTree(0L);
//        String string = objectMapper.writeValueAsString(trees);
//        System.out.println(string);
//        List<Long> parentId = treeUtils.findParentId(122L);
//        for(Long l : parentId){
//            System.out.println("父节点:"+l);
//        }
//        List<Long> childId = treeUtils.findChildId(122L);
//        for(Long l : childId){
//            System.out.println("子节点:"+l);
//        }
//    }
}

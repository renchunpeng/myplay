package iyunwen;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class YWTreeUtils {

    private List<? extends Tree> list;

    /** 将节点转化为parentId-List<value>的方式存储</> */
    private Map<Long, List<Tree>> labelListMap;

    /** 将所有节点简单转化为key-value存储 */
    private Map<Long, Tree> labelIdMap;

    YWTreeUtils(List<? extends Tree> list){
        this.list = list;

        labelListMap = new HashMap<>();
        labelIdMap = new HashMap<>();
        for (Tree label : list) {
            labelIdMap.put(label.getId(), label);
            if (labelListMap.containsKey(label.getParentId())) {
                labelListMap.get(label.getParentId()).add(label);
            } else {
                List<Tree> tempLabelList = new ArrayList<>();
                tempLabelList.add(label);
                labelListMap.put(label.getParentId(), tempLabelList);
            }
        }
    }

    /** 递归创建树形结构 */
    public List<? extends Tree> buildTree(Long parentId) {
        List<Tree> resultList = new ArrayList<>();
        if (list == null || list.size() == 0 || parentId < 0L) {
            return null;
        }
        for (Tree tree : list) {
            if (tree.getParentId().equals(parentId)) {
                resultList.add(tree);
                tree.setChildren((List<Tree>) buildTree(tree.getId()));
            }
        }
        return resultList;
    }

    /** 查询传入节点的所有父级节点 */
    public List<Long> findParentId(Long node) {
        List<Long> labelIdList = new ArrayList<>();
        searchParentId(labelIdMap.get(node), labelIdList);
        return labelIdList;
    }

    /** 查询传入节点的所有子级节点 */
    public List<Long> findChildId(Long node) {
        List<Long> labelIdList = new ArrayList<>();
        List<Tree> labelList = new ArrayList<>();
        labelList.add(labelIdMap.get(node));
        searchChildId(labelList, labelIdList);
        return labelIdList;
    }


    private void searchChildId(List<Tree> labelList, List<Long> labelIdList){
        for (Tree label : labelList) {
            if (labelListMap.containsKey(label.getId())){
                this.searchChildId(labelListMap.get(label.getId()), labelIdList);
            }
            labelIdList.add(label.getId());
        }
    }

    private void searchParentId(Tree label, List<Long> labelIdList) {
        if (new Long(0L).equals(label.getParentId())){
            return;
        }
        // 如果父id不为0，则递归查询
        labelIdList.add(label.getParentId());
        this.searchParentId(labelIdMap.get(label.getParentId()), labelIdList);
    }
}




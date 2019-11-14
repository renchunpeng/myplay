package iyunwen;

import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/7/29
 */
public class Tree {
    private Long id;
    private String name;
    private Long parentId;
    private List<Tree> children;

    public Tree(Long id, String name, Long parentId) {
        super();
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Tree() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

}

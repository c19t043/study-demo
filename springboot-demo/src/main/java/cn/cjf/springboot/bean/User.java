package cn.cjf.springboot.bean;

import cn.cjf.springboot.api.ListNotHasNull;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class User {

    //其他参数 .......

    /**
     * 所拥有的书籍列表
     */
    @NotEmpty(message = "所拥有书籍不能为空")
    @ListNotHasNull(message = "List 中不能含有null元素")
    @Valid
    private List<Book> books;
    //getter setter 方法.......
}
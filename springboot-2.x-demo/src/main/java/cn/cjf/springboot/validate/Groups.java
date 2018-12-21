package cn.cjf.springboot.validate;

/**
 * 验证组
 * <p>
 * 有的时候，我们对一个实体类需要有多中验证方式，在不同的情况下使用不同验证方式，
 * 比如说对于一个实体类来的 id 来说，新增的时候是不需要的，对于更新时是必须的，这个时候你是选择写一个实体类呢还是写两个呢？
 *
 * @author Levin
 * @since 2018/6/7 0007
 */
public class Groups {

    public interface Update {

    }

    public interface Default {

    }
}
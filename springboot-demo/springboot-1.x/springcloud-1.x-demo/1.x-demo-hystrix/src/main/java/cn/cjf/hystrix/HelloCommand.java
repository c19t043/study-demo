package cn.cjf.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HelloCommand extends HystrixCommand<String> {

    private String url;

    CloseableHttpClient httpClient;

    public HelloCommand(String url) {
        //调用父类的构造器，设置命令组的key，默认用来作为线程池的key
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        //创建httpClient客户端
        httpClient = HttpClients.createDefault();
        this.url = url;
    }

    protected HelloCommand(HystrixCommandGroupKey group) {
        super(group);
    }

    @Override
    protected String run() throws Exception {
        try {
            HttpGet httpGet = new HttpGet(url);

            CloseableHttpResponse response = httpClient.execute(httpGet);

            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * run执行异常执行的逻辑
     *
     * @return
     */
    @Override
    protected String getFallback() {
        System.out.println("执行 HelloCommand 的回退方法");
        return "error";
    }

    public static void main(String[] args) {
        String normalUrl = "http://localhost:8080/normalHello";
        HelloCommand command = new HelloCommand(normalUrl);
        String result = command.execute();
        System.out.println("请求正常的服务,结果：" + result);
    }
}

package cn.cjf.hystrix;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.apache.commons.lang.math.RandomUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MyHystrixCollapser extends HystrixCollapser<Map<String, Person>, Person, String> {

    static class CollapserCommand extends HystrixCommand<Map<String, Person>> {
        Collection<CollapsedRequest<Person, String>> requests;

        private CollapserCommand(Collection<CollapsedRequest<Person, String>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")));
            this.requests = requests;
        }


        @Override
        protected Map<String, Person> run() throws Exception {

            List<String> personNames = new ArrayList<String>();
            for (CollapsedRequest<Person, String> request : requests) {
                personNames.add(request.getArgument());
            }

            Map<String, Person> result = callService(personNames);

            return result;
        }

        private Map<String, Person> callService(List<String> personNames) {
            Map<String, Person> result = new HashMap<>();
            for (String personName : personNames) {
                Person p = new Person();
                p.setId(RandomUtils.nextInt());
                p.setName(personName);
                p.setAge(new Random().nextInt(30));
                result.put(personName, p);
            }
            return result;
        }
    }

    String personName;

    public MyHystrixCollapser(String personName) {
        this.personName = personName;
    }

    @Override
    public String getRequestArgument() {
        return personName;
    }

    @Override
    protected HystrixCommand<Map<String, Person>> createCommand(Collection<CollapsedRequest<Person, String>> requests) {
        return new CollapserCommand(requests);
    }

    @Override
    protected void mapResponseToRequests(Map<String, Person> batchResponse, Collection<CollapsedRequest<Person, String>> requests) {
        for (CollapsedRequest<Person, String> request : requests) {
            Person person = batchResponse.get(request.getArgument());
            request.setResponse(person);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ConfigurationManager.getConfigInstance().setProperty("hystrix.collapser.default.timerDelayInMilliseconds", 1000);

        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        MyHystrixCollapser c1 = new MyHystrixCollapser("angus");
        MyHystrixCollapser c2 = new MyHystrixCollapser("Crazyit");
        MyHystrixCollapser c3 = new MyHystrixCollapser("Sune");
        MyHystrixCollapser c4 = new MyHystrixCollapser("Paris");

        Future<Person> f1 = c1.queue();
        Future<Person> f2 = c2.queue();
        Future<Person> f3 = c3.queue();
        Future<Person> f4 = c4.queue();

        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        System.out.println(f4.get());


        context.shutdown();
    }
}

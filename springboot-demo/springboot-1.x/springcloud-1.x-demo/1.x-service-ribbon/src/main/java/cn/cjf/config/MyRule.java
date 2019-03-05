package cn.cjf.config2;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class MyRule implements IRule {
    ILoadBalancer loadBalancer;

    /**
     * 负载均衡方法
     */
    @Override
    public Server choose(Object o) {
        List<Server> allServers = loadBalancer.getAllServers();
        return allServers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.loadBalancer;
    }
}

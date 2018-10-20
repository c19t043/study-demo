package cn.cjf.mybatis.dynamic_switch_datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return CustomerContextHolder.getCustomerType();
    }
}
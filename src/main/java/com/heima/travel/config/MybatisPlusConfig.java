package com.heima.travel.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //注册分页插件 使用mybatisplu内部提供的分页插件
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor();
        //-1表示pageSize不设限，默认500
        innerInterceptor.setMaxLimit(-1l);
        innerInterceptor.setOverflow(true);
        //     * 生成 countSql 优化掉 join
        //     * 现在只支持 left join
        innerInterceptor.setOptimizeJoin(true);
        //指定操纵的数据库类型
        innerInterceptor.setDbType(DbType.MYSQL);
        interceptor.addInnerInterceptor(innerInterceptor);
        //继续注册其他的插件
        return interceptor;
    }
}

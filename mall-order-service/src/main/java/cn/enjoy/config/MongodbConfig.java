package cn.enjoy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongodbConfig{
    @Bean
    public CustomConversions customConversions() {
        List<Object> list = new ArrayList<>();
        list.add(new BigDecimal2DoubleConverter());//自定义的类型转换器
        return new CustomConversions(list);
    }
}
class BigDecimal2DoubleConverter implements Converter<BigDecimal,Double> {
    @Override
    public Double convert(BigDecimal bigDecimal) {
        return bigDecimal.doubleValue();
    }
}

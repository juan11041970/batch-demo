package com.example.batch.batchdemo

import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.InitializingBean

class ProductWriter<Product> implements ItemWriter<Product>, InitializingBean {
    @Override
    void write(List<? extends Product> items) throws Exception {
        println "Writing items: ${items}"
    }

    @Override
    void afterPropertiesSet() throws Exception {
        println "afterPropertiesSet()"
    }
}

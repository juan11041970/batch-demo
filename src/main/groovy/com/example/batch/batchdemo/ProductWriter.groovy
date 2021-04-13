package com.example.batch.batchdemo

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
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

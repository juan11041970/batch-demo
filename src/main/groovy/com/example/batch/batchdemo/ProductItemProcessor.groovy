package com.example.batch.batchdemo

import org.springframework.batch.item.ItemProcessor
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component

@Component
class ProductItemProcessor implements ItemProcessor<Product, Product> {

    @Override
    Product process(@NonNull Product item) throws Exception {
        println "Entered write(...) method in ProductWriterProcessor"
        item
    }
}

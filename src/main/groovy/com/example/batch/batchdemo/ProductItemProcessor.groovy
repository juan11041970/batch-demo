package com.example.batch.batchdemo

import org.springframework.batch.item.ItemWriter

import org.springframework.batch.item.ItemProcessor
import org.springframework.lang.NonNull

class ProductItemProcessor implements ItemProcessor<Product, Product> {

//    @Override
//    void write(List<? extends Product> items) throws Exception {
//        println "Entered write(...) method in ProductWriterProcessor"
//
//        items.each {
//            println it
//        }
//    }

//    @Override
//    Product process(@NonNull Product item) throws Exception {
//        println "Entered write(...) method in ProductWriterProcessor"
//        return item
//    }
//    @Override
//    Object process(@NonNull Object item) throws Exception {
//        println "Entered write(...) method in ProductWriterProcessor"
//        return null
//    }

    @Override
    Product process(@NonNull Product item) throws Exception {
        println "Entered write(...) method in ProductWriterProcessor"
        return item
    }
}

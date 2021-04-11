package com.example.batch.batchdemo

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource

@Configuration
@EnableBatchProcessing
class BatchConfig {

    @Autowired
    JobBuilderFactory jobBuilders

    @Autowired
    StepBuilderFactory stepBuilders

    @Bean
    public Job productReaderJob() {
        return jobBuilders.get("productReaderJob")
                .incrementer(new RunIdIncrementer())
                .flow(productReaderTask())
                .end()
                .build()
    }

    @Bean
    public Step productReaderTask(ProductWriter writer) {
        return stepBuilders.get("productReaderTask")
                .<Product, Product> chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build()
    }

    @Bean
    public FlatFileItemReader<Product> reader() {
        //Create reader instance
        FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>()

        //Set input file location
        reader.setResource(new FileSystemResource("/Users/juaalvarado/Documents/GitHub-2021/batch-demo/src/main/resources/input-files/products.txt"))
        reader.setLinesToSkip(1)
        reader.setName("batch-reader")

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        String[] names = [ "productid", "name", "description", "price"]
                        setNames(names)
                    }
                })
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {
                    {
                        setTargetType(Product.class);
                    }
                })
            }
        })
        return reader
    }

    @Bean
    ProductWriter writer() {
        return new ProductWriter()
    }

    @Bean
    ProductItemProcessor processor() {
        return new ProductItemProcessor()
    }
}

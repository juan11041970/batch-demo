package com.example.batch.batchdemo

import org.aspectj.lang.annotation.DeclareAnnotation
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.core.io.FileSystemResource

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = ["com.example.batch"])
class BatchConfig {

    @Autowired
    JobBuilderFactory jobBuilders

    @Autowired
    StepBuilderFactory stepBuilders

    @Bean
    public Job productReaderJob() {
        return jobBuilders.get("productReaderJob")
            .start(decompressTask())
            .build()
//        return jobBuilders.get("productReaderJob")
//                .incrementer(new RunIdIncrementer())
//                .flow(readerWriterTask())
//                .end()
//                .build()
    }

    @Bean
    public Step decompressTask() {
        return stepBuilders.get("decompressTask")
                .tasklet(decompressTasklet())
//                .tasklet(readerWriterTask())
                .build()
    }

    @Bean
    public Step readerWriterTask() {
        return stepBuilders.get("readerWriterTask")
            .<Product, Product> chunk(3)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build()
    }


    @Bean
    @StepScope
    public FlatFileItemReader<Product> reader() {
        //Create reader instance
        FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>()

        //Set input file location
        reader.setResource(new FileSystemResource("/Users/juaalvarado/Documents/GitHub-2021/batch-demo/src/main/resources/input-files/products.csv"))
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

    @Bean
    @StepScope
    Tasklet decompressTasklet() {
        new DecompressTasklet()
    }

//    @Bean
//    public JobBuilderFactory jobBuilderFactory() throws Exception {
//        JobBuilderFactory jobBuilderFactory = new JobBuilderFactory();
//        return jobBuilderFactory;
//    }

//    @Bean
//    public StepScope stepScope() {
//        final org.springframework.batch.core.scope.StepScope stepScope =
//                new org.springframework.batch.core.scope.StepScope();
//        stepScope.setAutoProxy(true);
//        return stepScope;
//    }

    @Bean
    org.springframework.batch.test.JobLauncherTestUtils jobLauncherTestUtils() {
        return new org.springframework.batch.test.JobLauncherTestUtils()
    }
}

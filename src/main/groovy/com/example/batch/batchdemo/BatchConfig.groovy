package com.example.batch.batchdemo


import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.LineMapper
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.file.transform.LineTokenizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = ["com.example.batch"])
class BatchConfig {

    @Autowired
    JobBuilderFactory jobBuilders

    @Autowired
    StepBuilderFactory stepBuilders

    static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(BatchConfig.class);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean(Job.class);
        jobLauncher.run(job, new JobParameters());
    }

    @Bean
    public Job productReaderJob() {
//        return jobBuilders.get("productReaderJob")
//            .incrementer(new RunIdIncrementer())
//            .flow(readProcessWriteFile())
//            .end()
//            .build()

        return jobBuilders.get("productReaderJob")
                .start(readProcessWriteFile())
                .build()
    }

    @Bean
    public Step readProcessWriteFile() {
        def buildStep = stepBuilders.get("readProcessWriteFile")
                .<Product, Product> chunk(3)
                .reader(itemReader())
                .processor(processor())
                .writer(writer())
                .build()
        buildStep
    }

    private LineTokenizer createProductLineTokenizer() {
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer()
        studentLineTokenizer.setDelimiter(",")
        String[] fields = ["productid", "name", "description", "price"]

        studentLineTokenizer.setNames(fields)
        return studentLineTokenizer
    }

    @Bean
    public FlatFileItemReader<Product> itemReader() {
        LineMapper<Product> studentLineMapper = createProductLineMapper()

        FlatFileItemReader reader = new FlatFileItemReader<Product>()
        reader.setName("itemReader")
//        reader.setResource(new ClassPathResource("input-files/products.csv"))
        reader.setResource(new FileSystemResource("/Users/juaalvarado/Documents/GitHub-2021/batch-demo/src/main/resources/input-files/products.csv"))
        reader.setLinesToSkip(1)
        reader.setLineMapper(studentLineMapper)

        return reader
    }

    private LineMapper<Product> createProductLineMapper() {
        DefaultLineMapper<Product> productLineMapper = new DefaultLineMapper<>();

        LineTokenizer productLineTokenizer = createProductLineTokenizer();
        productLineMapper.setLineTokenizer(productLineTokenizer);

        FieldSetMapper<Product> productInformationMapper = createProductInformationMapper()
        productLineMapper.setFieldSetMapper(productInformationMapper);

        return productLineMapper;
    }

    private FieldSetMapper<Product> createProductInformationMapper() {
        FieldSetMapper<Product> mapper = new ProductFieldMapper()
        mapper
    }

    @Bean
    ItemWriter<Product> writer() {
        return new ProductWriter()

    }

    @Bean
    ProductItemProcessor processor() {
        return new ProductItemProcessor()
    }


//    @Bean
//    org.springframework.batch.test.JobLauncherTestUtils jobLauncherTestUtils() {
//        return new org.springframework.batch.test.JobLauncherTestUtils()
//    }
        Step stepTwo() {
            def writerTask = stepBuilders.get("writerTask")
                    .tasklet(new DecompressTasklet())
                    .build()
            writerTask
        }
}

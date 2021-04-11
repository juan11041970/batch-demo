package com.example.batch.batchdemo

import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.junit.jupiter.api.Assertions

@SpringBootTest
@ContextConfiguration(classes=[BatchConfig.class])
public class BatchDemoApplicationTest {

	@Autowired
	Job productReaderJob

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	JobLauncherTestUtils jobLauncherTestUtils

	@Mock
	JobParameters jobParameters

	void init(Job job) {
		this.jobLauncherTestUtils = new JobLauncherTestUtils();
		this.jobLauncherTestUtils.setJobLauncher(jobLauncher);
		this.jobLauncherTestUtils.setJobRepository(jobRepository);
		this.jobLauncherTestUtils.setJob(job);
	}

	void contextLoads() {
		println "Groovy works!!!!"
	}

	@Test
	void testDecompressTask() {
		init(productReaderJob)

		JobParameters jobParams = new JobParametersBuilder()
			.addString("host", "9")
			.toJobParameters()
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("decompressTask", jobParams)

		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus())
	}

	JobParameters generateJobParametersForFeeNettingJob(){
		Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
		parameters.put("serverId", new JobParameter(9));

		return new JobParameters(parameters);
	}
}

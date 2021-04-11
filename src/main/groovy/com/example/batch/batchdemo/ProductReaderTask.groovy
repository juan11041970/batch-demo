package com.example.batch.batchdemo

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class ProductReaderTask implements Tasklet , StepExecutionListener {
    @Override
    void beforeStep(StepExecution stepExecution) {
        println ("In Task beforeStep(...) method")
    }

    @Override
    ExitStatus afterStep(StepExecution stepExecution) {
        println ("In Task afterStep(...) method")
        return ExitStatus.COMPLETED
    }

    @Override
    RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        println ("In Task execute(...) method")

        return RepeatStatus.FINISHED
    }
}

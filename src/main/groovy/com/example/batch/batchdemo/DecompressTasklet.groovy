package com.example.batch.batchdemo

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.annotation.BeforeStep
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

class DecompressTasklet implements Tasklet, StepExecutionListener {

    @Value("#{jobParameters['host'] ?: -1}")
    public int host

    StepExecution stepExecution

    @Override
    void beforeStep(StepExecution stepExecution) {
        println ("In beforeStep(...) of DecompressTasklet")
        this.stepExecution = stepExecution;
    }

    @Override
    RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        println ("In execute(...) of DecompressTasklet")
        return RepeatStatus.FINISHED
    }

    @Override
    ExitStatus afterStep(StepExecution stepExecution) {
        println ("In afterStep(...) of DecompressTasklet")
        return ExitStatus.COMPLETED
    }
}

package de.onestydirect.openiban.data.loader.common;

import de.onestydirect.openiban.data.loader.models.BankData;
import de.onestydirect.openiban.data.loader.services.BankDataService;
import de.onestydirect.openiban.data.loader.services.ExcelLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReadExcelJobLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadExcelJobLauncher.class);

	private final Job job;

	private final JobLauncher jobLauncher;

	@Autowired
	public ReadExcelJobLauncher(@Qualifier("excelFileToDatabaseJob") Job job, JobLauncher jobLauncher) {
		this.job = job;
		this.jobLauncher = jobLauncher;
	}

	@Scheduled(cron = "${excel.to.database.job.cron}")
	public void updateBankData() {
		LOGGER.info("Starting excelFileToDatabase job");
		try {
			jobLauncher.run(job, newExecution());
		} catch (JobExecutionAlreadyRunningException e) {
			LOGGER.info(e.toString());
		} catch (JobRestartException e) {
			LOGGER.info(e.toString());
		} catch (JobInstanceAlreadyCompleteException e) {
			LOGGER.info(e.toString());
		} catch (JobParametersInvalidException e) {
			LOGGER.info(e.toString());
		}
		LOGGER.info("Stopping excelFileToDatabase job");
	}

	private JobParameters newExecution() {
		Map<String, JobParameter> parameters = new HashMap<>();

		JobParameter parameter = new JobParameter(new Date());
		parameters.put("currentTime", parameter);

		return new JobParameters(parameters);
	}
}

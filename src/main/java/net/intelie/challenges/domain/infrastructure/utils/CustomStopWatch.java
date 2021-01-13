package net.intelie.challenges.domain.infrastructure.utils;

import org.springframework.util.StopWatch;

public class CustomStopWatch extends StopWatch {

	@Override
	public void stop() {
		if (this.isRunning()) {
			super.stop();
		}
	}
	
	@Override
	public long getTotalTimeMillis() {
		stop();
		return super.getTotalTimeMillis();
	}
}

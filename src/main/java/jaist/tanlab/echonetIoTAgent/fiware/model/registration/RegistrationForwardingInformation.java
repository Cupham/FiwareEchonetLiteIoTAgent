package jaist.tanlab.echonetIoTAgent.fiware.model.registration;

import java.time.Instant;

public class RegistrationForwardingInformation {
	public RegistrationForwardingInformation() {
		
	}
	Instant timeSent;
	Instant lastForwarding;
	Instant lastFailure;
	Instant lastSuccess;
	public Instant getTimeSent() {
		return timeSent;
	}
	public void setTimeSent(Instant timeSent) {
		this.timeSent = timeSent;
	}
	public Instant getLastForwarding() {
		return lastForwarding;
	}
	public void setLastForwarding(Instant lastForwarding) {
		this.lastForwarding = lastForwarding;
	}
	public Instant getLastFailure() {
		return lastFailure;
	}
	public void setLastFailure(Instant lastFailure) {
		this.lastFailure = lastFailure;
	}
	public Instant getLastSuccess() {
		return lastSuccess;
	}
	public void setLastSuccess(Instant lastSuccess) {
		this.lastSuccess = lastSuccess;
	}

	
}

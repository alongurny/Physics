package physics.graphics;

public class DrawingEvent {

	private long prev, now;

	public DrawingEvent(long prev, long now) {
		this.prev = prev;
		this.now = now;
	}

	public long getNanoTimePassed() {
		return now - prev;
	}

}

package gr.hua.it21774.requests;

import java.time.Instant;

public class DateRequest {
    private Instant from;
    private Instant to;

    public DateRequest() {
    }

    public DateRequest(Instant from, Instant to) {
        this.from = from;
        this.to = to;
    }

    public Instant getFrom() {
        return from;
    }

    public Instant getTo() {
        return to;
    }

    public void setFrom(Instant from) {
        this.from = from;
    }

    public void setTo(Instant to) {
        this.to = to;
    }
}

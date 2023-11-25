package de.zettsystems.timeutil.adapter.to;

import java.time.LocalDateTime;

public class TimeChangeMessage {

    private final Header header = new Header();
    private final Body body = new Body();

    public Header getHeader() {
        return this.header;
    }

    public Body getBody() {
        return this.body;
    }

    @Override
    public String toString() {
        return "TimeChangeMessage(header=" + this.getHeader() + ", body=" + this.getBody() + ")";
    }

    public static class Header {
        private String actionType;

        public String getActionType() {
            return this.actionType;
        }

        public void setActionType(String actionType) {
            this.actionType = actionType;
        }

        @Override
        public String toString() {
            return "TimeChangeMessage.Header(actionType=" + this.getActionType() + ")";
        }
    }

    public static class Body {
        private String duration;
        private LocalDateTime localDateTime;

        public String getDuration() {
            return this.duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public LocalDateTime getLocalDateTime() {
            return this.localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public String toString() {
            return "TimeChangeMessage.Body(duration=" + this.getDuration() + ")";
        }
    }
}

package pl.sda.partyka.dto;

public enum EventsSearchOptions {
    PRESENT_AND_FUTURE("Present and Future Events", 1),
    ONLY_FUTURE("Future Events", 0),
    ALL_EVENTS("All Events (including Past Events)", 2);

    private String displayText;
    private Integer value;

    EventsSearchOptions(String displayText, Integer value) {
        this.displayText = displayText;
        this.value = value;
    }

    public String getDisplayText() {
        return displayText;
    }

    public Integer getValue() {
        return value;
    }
}

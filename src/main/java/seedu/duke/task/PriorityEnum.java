package seedu.duke.task;

import seedu.duke.exception.InvalidPriorityException;

public enum PriorityEnum {
    HIGH(2),
    MEDIUM(1),
    LOW(0);

    private final int priorityNumber;

    PriorityEnum(final int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public int getValue() {
        return this.priorityNumber;
    }

    /**
     * Returns the name of enum in lowercase.
     *
     * @return <code>String</code> of {@link PriorityEnum} enum name in lowercase.
     */
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    /**
     * Returns the Priority enum corresponding to int argument.
     *
     * @return {@link PriorityEnum} enum corresponding to <code>int priorityNumber</code> argument.
     */
    public static PriorityEnum getPriority(int priorityNumber) throws InvalidPriorityException {
        for (PriorityEnum priority : PriorityEnum.values()) {
            if (priorityNumber == priority.getValue()) {
                return priority;
            }
        }
        throw new InvalidPriorityException(Integer.toString(priorityNumber));
    }

    public static PriorityEnum getPriority(String priority) throws InvalidPriorityException {
        for (PriorityEnum priorityEnum : values()) {
            if (priority.equalsIgnoreCase(priorityEnum.name())) {
                return priorityEnum;
            }
        }
        throw new InvalidPriorityException(priority);
    }

    public static String getPrioritiesListString(String argumentSplit) {
        String listString = "%s";
        for (PriorityEnum priority : PriorityEnum.values()) {
            listString = String.format(listString, priority.toString() + argumentSplit + "%s");
        }
        listString = listString.replaceAll("%s", "");
        return listString.substring(0, listString.length() - 1);
    }
}

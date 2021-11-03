package seedu.duke.parser;

import seedu.duke.command.BrowseCommand;
import seedu.duke.command.ByeCommand;
import seedu.duke.command.Command;
import seedu.duke.command.CommandEnum;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.EditCommand;
import seedu.duke.command.HelpCommand;
import seedu.duke.command.InvalidCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.command.SortCommand;
import seedu.duke.command.addtask.DeadlineCommand;
import seedu.duke.command.addtask.EventCommand;
import seedu.duke.command.addtask.ModuleCommand;
import seedu.duke.command.addtask.TodoCommand;
import seedu.duke.log.Log;
import java.util.HashMap;
import java.util.Map;

import seedu.duke.task.taskmanager.TaskManager;
import seedu.duke.utility.Utility;

//@@author APZH
/**
 * Parses user input.
 */
public class CommandParser {

    private static final String FLAG_REGEX = "^--\\w+";
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String WHITESPACE = " ";
    private static final String SPLIT_INPUT_REGEX = "\\s+";
    private static final String INVALID_TASK_INDEX = "%s is not an integer!";

    //@@author APZH
    /**
     * Returns a {@code Map} mapping command flags to the arguments entered by the user.
     *
     * @param commandArguments The command entered by the user.
     * @return {@code Map} mapping command flags to the arguments.
     */
    public static Map<String, String> getCommandOptions(String commandArguments) {

        Map<String, String> flagsToArguments = new HashMap<>();
        String[] tokens = commandArguments.split(WHITESPACE_REGEX);
        String mainArgument = "";

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].matches(FLAG_REGEX)) {
                String flag = tokens[i];
                String flagArguments = "";
                try {
                    while (tokens[i + 1].matches(FLAG_REGEX) == false) {
                        flagArguments += tokens[i + 1] + WHITESPACE;
                        i++;
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.warning(e.getMessage());
                }
                flagsToArguments.put(flag.substring(2), flagArguments.trim());
            } else {
                mainArgument += tokens[i] + WHITESPACE;
            }
        }

        flagsToArguments.put(Command.MAIN_ARGUMENT, mainArgument.trim());
        return flagsToArguments;
    }

    //@@author APZH
    /**
     * Creates a {@code Command} object based on the {@code commandSyntax}.
     *
     * @param taskManager    {@code TaskManager} object that contains the tasklist to be executed upon.
     * @param commandSyntax  Contains the type of command entered by the user.
     * @param commandOptions Contains any flags and arguments associated with the command.
     * @return The {@code commandSyntax} specified {@code Command} object.
     */
    public static Command createCommand(TaskManager taskManager, CommandEnum commandSyntax,
                                        Map<String, String> commandOptions) {
        switch (commandSyntax) {
        case BYE:
            return new ByeCommand();
        case HELP:
            return new HelpCommand();
        case TODO:
            return new TodoCommand(taskManager, commandOptions);
        case DEADLINE:
            return new DeadlineCommand(taskManager, commandOptions);
        case EVENT:
            return new EventCommand(taskManager, commandOptions);
        case LIST:
            return new ListCommand(taskManager, commandOptions);
        case DELETE:
            return new DeleteCommand(taskManager, commandOptions);
        case SORT:
            return new SortCommand(taskManager, commandOptions);
        case MODULE:
            return new ModuleCommand(taskManager, commandOptions);
        case EDIT:
            return new EditCommand(taskManager, commandOptions);
        case BROWSE:
            return new BrowseCommand(taskManager, commandOptions);
        default:
            return new InvalidCommand();
        }
    }

    //@@author APZH
    /**
     * Returns the {@code Command} to be executed based on the {@code userInput}.
     *
     * @param taskManager {@code TaskManager} object that contains the tasklist to be executed upon.
     * @param userInput   User input to determine the {@code Command} to be executed.
     * @return {@code Command} to be executed.
     */
    public static Command parseCommand(TaskManager taskManager, String userInput) {
        String[] inputArguments = userInput.split(SPLIT_INPUT_REGEX, 2);
        String command = inputArguments[0];

        CommandEnum commandSyntax = CommandEnum.getCommand(command);
        Map<String, String> commandOptions = new HashMap<>();

        if (inputArguments.length == 2) {
            commandOptions = getCommandOptions(inputArguments[1]);
        }

        return createCommand(taskManager, commandSyntax, commandOptions);
    }

    //@@author SeanRobertDH
    public static Integer parseTaskIndex(String index) throws NumberFormatException {
        if (!Utility.isInteger(index)) {
            throw new NumberFormatException(String.format(INVALID_TASK_INDEX, index));
        }
        return Integer.parseInt(index);
    }
}

package tracker.commands;

import tracker.LearningProgressTracker;

public interface TrackerCommand {
    String STOP_MESSAGE = "back";
    void executeOnTracker(LearningProgressTracker tracker);
}

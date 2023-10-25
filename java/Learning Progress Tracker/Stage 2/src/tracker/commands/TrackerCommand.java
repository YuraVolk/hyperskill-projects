package tracker.commands;

import tracker.LearningProgressTracker;

public interface TrackerCommand {
    void executeOnTracker(LearningProgressTracker tracker);
}

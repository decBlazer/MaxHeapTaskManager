package com.varughese.maxheaptaskmanager

import java.io.Serializable

/**
 * Instantiable class for Task objects. These Tasks can be given different priority levels and
 * compared based on different criteria.
 *
 * @author Blaise
 */
class Task (
    /** the title of this Task  */
    val title: String,
    /** the description of this Task  */
    val description: String,
    /** the amount of estimated time to complete this Task  */
    val time: Int,
    /** the priority of this Task  */
    val priorityLevel: PriorityLevel,
    /** denotes whether or not this task has been completed */
    var isCompleted: Boolean = false

) : Serializable {

    /**
     * Reports if a Task has been completed.
     *
     * @return true if this Task is completed, false otherwise
     */
    fun checkIfCompleted(): Boolean {
        return isCompleted
    }


    /**
     * Marks this Task as completed.
     */
    fun markCompleted() {
        isCompleted = true
    }


    /**
     * Returns a String representation of a Task.
     *
     * @return this Task as a String
     */
    override fun toString(): String {
        return (title + ": " + description + "(" + this.priorityLevel + "), ETA " + this.time
                + " minutes")
    }

    /**
     * Determines if another object is equal to a Task.
     *
     * @param other the object to check is equal to this Task
     * @return true if other is a Task and has the same title, time, description and priority level,
     * false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other is Task) {
            return this.title == other.title && (this.description == other.description) && (this.priorityLevel === other.priorityLevel) && (this.time == other.time)
        }

        return false
    }

    /**
     * Compares the other Task to this Task based on the given PrioritizationMode.
     *
     * @param other    the Task to compare to this
     * @param criteria the criteria used to determine how to compare them
     * @return < 0 if this is smaller than other, > 0 if this is larger than other, and 0 if they are
     * equal
     *
     * @author Blaise
     */
    fun compareTo(other: Task, criteria: CompareCriteria?): Int {
        when (criteria) {
            CompareCriteria.TIME -> {
                // Task that takes more time to complete gets a greater priority
                return if (this == other) {
                    0 // If both tasks equal each other, return 0 as they are the same
                } else {
                    /*
                   * Example: this.getTime() is 5, and other.getTime() is 6, so returns -1. This means that
                   * this task has less priority than the other task.
                   */
                    time - other.time
                }
            }

            CompareCriteria.TITLE -> {
                // Task that is greater in reverse lexicographic order gets a greater priority
                return if (this == other) {
                    0 // If both tasks equal each other, return 0 as they are the same
                } else {
                    /*
                       * Use string .compareTo() for normal lexicographic order, and then multiply by -1 for
                       * reverse lexicographic order Example: this.getTitle() is a, and other.getTitle() is b.
                       * string .compareTo() returns -1, but multiplying by -1 for reverse lexicographic order
                       * returns 1. This means that this task has greater priority than the other task.
                       */
                    title.compareTo(other.title) * -1
                }
            }

            CompareCriteria.LEVEL -> {
                // Task with a higher level has the greater priority
                if (this == other) {
                    return 0 // If both tasks equal each other, return 0 as they are the same
                } else {
                    val thisTaskOrdinalValue: Int =
                        priorityLevel.ordinal // Get ordinal value for this
                    val otherTaskOrdinalValue: Int =
                        other.priorityLevel.ordinal // Get ordinal value for other
                    /*
           * Example: this's ordinal value is 3 because it has a priority level of HIGH, and other's
           * ordinal value is 1 because it has a priority level of LOW. 3 - 1 = 2, so that means
           * this has a greater priority than other, as a positive number was returned
           */
                    return thisTaskOrdinalValue - otherTaskOrdinalValue
                }
            }

            else -> {
                return 0 // Default case just returns 0.
            }
        }
    }
}

package com.varughese.maxheaptaskmanager
import java.lang.IllegalArgumentException

/**
 * Class that creates the queue of objects according to priority level
 */
class TaskQueue(capacity: Int, priorityCriteria: CompareCriteria?) : Any() {
    /** oversized array that holds all of Tasks in the heap  */
    private val heapData: Array<Task?>

    /** the number of items in the TaskQueue  */
    var size = 0// Return the priority criteria

    /**
     * Gets the criteria used to prioritize tasks in this TaskQueue.
     *
     * @return the prioritization criteria of this TaskQueue
     */
    /** the criteria used to determine how to prioritize Tasks in the queue  */
    private var priorityCriteria: CompareCriteria? = null

    /**
     * Creates an empty TaskQueue with the given capacity and priority criteria.
     *
     * @param capacity         the max number of Tasks this priority queue can hold
     * @param priorityCriteria the criteria for the queue to use to determine a Task's priority
     * @throws IllegalArgumentException with a descriptive message if the capacity is non-positive
     */
    init {
        try {
            require(capacity > 0) {  // Capacity is non-positive, so throw IllegalArgumentException
                "Invalid capacity"
            }

            heapData = arrayOfNulls(capacity) // Create a new array with the capacity parameter

            this.priorityCriteria = priorityCriteria // Set the priority criteria
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw IllegalArgumentException("The capacity: $capacity, is non-positive")
        }
    }

    val isEmpty: Boolean
        /**
         * Reports if a TaskQueue is empty.
         *
         * @return true if this TaskQueue is empty, false otherwise
         */
        get() = if (size() == 0) {
            true // There are no tasks, so return true
        } else {
            false // There is at least one task
        }

    /**
     * Reports the size of a TaskQueue.
     *
     * @return the number of Tasks in this TaskQueue
     */
    fun size(): Int {
        return size // Return size
    }

    /**
     * Gets the Task in a TaskQueue that has the highest priority WITHOUT removing it. The Task that
     * has the highest priority may differ based on the current priority criteria.
     *
     * @return the Task in this queue with the highest priority
     * @throws NoSuchElementException with descriptive message if this TaskQueue is empty
     */
    fun peekBest(): Task? {
        try {
            if (isEmpty) { // There are no tasks, so there is nothing to peek
                throw NoSuchElementException("The TaskQueue is empty, as the size is 0.")
            }

            return heapData[0] // The highest priority item is the first item in the array
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
            throw NoSuchElementException("The TaskQueue is empty, as the size is 0.")
        }
    }

    /**
     * Adds the newTask to this priority queue.
     *
     * @param newTask the task to add to the queue
     * @throws IllegalArgumentException with a descriptive message if the Task is already completed
     * @throws IllegalStateException    with a descriptive message if the priority queue is full
     */
    fun enqueue(newTask: Task) {
        try {
            require(!newTask.checkIfCompleted()) {  // Can't add a task if it's already completed
                "The Task: $newTask is already completed."
            }

            check(heapData.size != size) {  // The queue is full, so can't add another item
                "The priority queue is full. The size is : " + heapData.size
            }

            heapData[size] = newTask // Add the new task to the end of the queue
            size++ // Increment size

            /*
       * Don't percolate up if there is only one element in the heap
       */
            if (size == 1) {
                return
            }

            percolateUp(size - 1) // Call the percolate up method to fix heap violations
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw IllegalArgumentException("The Task: $newTask is already completed.")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            throw IllegalStateException(
                "The priority queue is full. The size is : " + heapData.size
            )
        }
    }

    /**
     * Fixes one heap violation by moving it up the heap
     *
     * @param index the index of the element where the violation may be
     */
    private fun percolateUp(index: Int) {
        var currentIndex = index
        while (currentIndex > 0) { // When index becomes 0, that means the heap violations are fixed
            val parentIndex = (index - 1) / 2 // Get the parent index of a max-heap
            /*
       * Repeat the comparison until the parent is larger than or equal to the percolating element
       */
            if (heapData[index]!!.compareTo(heapData[parentIndex]!!, priorityCriteria) < 0) {
                return
            } else {
                /*
         * Swap the data at the parameter index and parent index
         */
                val tempTask = heapData[index]
                heapData[index] = heapData[parentIndex]
                heapData[parentIndex] = tempTask

                currentIndex = parentIndex // Update index to be its parent, as they have been swapped
            }
        }
    }


    /**
     * Gets and removes the Task that has the highest priority. The Task that has the highest priority
     * may differ based on the current priority criteria.
     *
     * @return the Task in this queue with the highest priority
     * @throws NoSuchElementException with descriptive message if this TaskQueue is empty
     */
    fun dequeue(): Task? {
        try {
            if (isEmpty) { // Nothing to dequeue if the size is 0
                throw NoSuchElementException(
                    "This TaskQueue is empty because the size is 0. No more tasks to dequeue."
                )
            }

            val highestPriorityTask = heapData[0] // The first item in the max-heap has the highest
            // priority
            heapData[0] = heapData[size - 1] // Set the last element to be the first element (thus
            // removing the first element)
            heapData[size - 1] = null // Remove the last element, as it is in the first index now.
            size-- // Decrement size

            /*
       * Don't percolate down if there is only one element in the heap
       */
            if (size == 1) {
                return highestPriorityTask
            }

            percolateDown(0) // Call the percolate down method to fix heap violations


            return highestPriorityTask // Return the removed task
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(
                "This TaskQueue is empty because the size is 0. No more tasks to dequeue."
            )
        }
    }

    /**
     * Fixes one heap violation by moving it down the heap.
     *
     * @param index the index of the element where the violation may be
     */
    private fun percolateDown(index: Int) {
        var currentIndex = index
        while (index < size()) { // When index becomes the size, that means the heap violations have
            // been fixed
            val childIndex = (2 * index) + 1 // Find the child index
            val currentTask = heapData[index] // The current task is at the parameter index

            var maxPriorityTask =
                currentTask // Create a new task that gets the current task and tries
            // to find the highest priority task
            var maxPriorityTaskIndex = 0
            /*
       * Iterate through both of the child indexes
       */
            var i = 0
            while (i < 2 && i + childIndex < size()) {
                /*
                        * If one of the child tasks is greater than the current task, set the max priority task to
                        * be the child
                        */
                if (heapData[i + childIndex]!!.compareTo(maxPriorityTask!!, priorityCriteria) > 0) {
                    maxPriorityTask = heapData[i + childIndex]
                    maxPriorityTaskIndex = i + childIndex
                }
                i++
            }

            /*
       * If max priority task wasn't updated, return, as the heap violations have been fixed
       */
            if (maxPriorityTask!! == currentTask) {
                return  // Break out of the loop
            } else {
                /*
         * Swap the data at the parameter index and max index
         */
                val tempTask = heapData[index]
                heapData[index] = heapData[maxPriorityTaskIndex]
                heapData[maxPriorityTaskIndex] = tempTask
                currentIndex = (2 * currentIndex) + 1 // Update the index to be the child
            }
        }
    }

    /**
     * Changes the priority criteria of this priority queue and fixes it so that is is a proper
     * priority queue based on the new criteria.
     *
     * @param priorityCriteria the (new) criteria that should be used to prioritze the Tasks in this
     * queue
     */
    fun reprioritize(priorityCriteria: CompareCriteria?) {
        this.priorityCriteria = priorityCriteria
        for (i in ((size() - 1) / 2) downTo 0) {
            percolateDown(i)
        }
    }

    /**
     * Creates and returns a deep copy of the heap's array of data.
     *
     * @return the deep copy of the array holding the heap's data
     */
    fun getHeapData(): Array<Task?> {
        val cloneArray = heapData.clone()
        return cloneArray // return the cloned array
    }
}

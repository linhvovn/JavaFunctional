package com.java.functional.pattern.chain;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * 
 * @author linh
 *
 */
public interface ChainProcessor<T extends ChainData> {
	
	/**
	 * The starting point
	 * @param data
	 * @return
	 */
	ChainStep<T> startWith(UnaryOperator<T> start);
	
	/**
	 * Start with group actions
	 * @param start
	 * @return
	 */
	ChainStep<T> startWithGroup(UnaryOperator<T> start);
	
	/**
	 * Apply this one after another one
	 * @param function
	 * @return
	 */
	ChainStep<T> then(UnaryOperator<T> function);
	
	/**
	 * Indicate that need to group list of functions to one group
	 * @param function
	 * @return
	 */
	ChainStep<T> thenGroup(UnaryOperator<T> function);
	
	/**
	 * Execute current chain on input data
	 * @return
	 */
	ChainProcessor<T> run(T data);
	
	/**
	 * Execute the action when Exception happens at a step
	 * @return
	 */
	ChainProcessor<T> onStepException(BiConsumer<T,Exception> action);
	
	/**
	 * Default condition to stop each step execution
	 * @param condition
	 * @return
	 */
	ChainProcessor<T> skipStepWhen(Predicate<T> condition);
}

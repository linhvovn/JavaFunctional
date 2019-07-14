package com.java.functional.pattern.chain;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * 
 * @author linh
 *
 */
public interface ChainStep<T extends ChainData> {
	
	/**
	 * Decide when to skip this step
	 * @param skipCondition
	 * @return
	 */
	ChainStep<T> skipWhen(Predicate<T> skipCondition);
	
	/**
	 * Check if this step is skipped at runtime
	 * @return
	 */
	boolean isSkipped(T data);
	
	/**
	 * Execute current step
	 * @return
	 */
	ChainStep<T> execute(T data);
	
	/**
	 * Assign a name for current step
	 * @param name
	 * @return
	 */
	ChainStep<T> withName(String name);
	
	/**
	 * Return the current processor
	 * @return
	 */
	ChainProcessor<T> processor();
	
	/**
	 * Equal to ChainProcessor.then
	 * @param function
	 * @return
	 */
	ChainStep<T> next(UnaryOperator<T> function);
	
	/**
	 * 
	 * @return
	 */
	ChainStep<T> onExecutionException(BiConsumer<T,Exception> action);
	
	/**
	 * If either one of condition satisfy, the Skip condition will be ignore
	 * @param condition
	 * @return
	 */
	ChainStep<T> ignoreAllSkipConditionWhen(Predicate<T> condition);
}

package com.java.functional.pattern.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

/**
 * Basic chain processor implementation which use a LinkedList
 * @author linh
 *
 */
public class SimpleChainProcessor<T extends ChainData> implements ChainProcessor<T> {
	
	private static final Logger LOGGER = Logger.getAnonymousLogger();
	
	private List<ChainStep<T>> steps;
	
	private BiConsumer<T,Exception> onStepException;
	
	private Predicate<T> skipStepCondition = d -> false;
	
	public SimpleChainProcessor() {
		steps = new ArrayList<>();
	}

	@Override
	public ChainStep<T> startWith(UnaryOperator<T> start) {
		return this.then(start);
	}

	@Override
	public ChainStep<T> then(UnaryOperator<T> function) {
		ChainStep<T> newStep = new SimpleChainStep<>(function,this)
				.skipWhen(skipStepCondition)
				.onExecutionException(onStepException);
		
		steps.add(newStep);
		
		return newStep;
	}
	
	@Override
	public ChainStep<T> thenGroup(UnaryOperator<T> function) {
		ChainStep<T> newStep = new CompositeChainStep<>(function,this)
				.skipWhen(skipStepCondition)
				.onExecutionException(onStepException);
		
		steps.add(newStep);
		
		return newStep;
	}

	@Override
	public ChainProcessor<T> run(T data) {
		LOGGER.info("INTO SimpleChainProcessor.run");
		steps
			.stream()
			.forEach( step -> step.execute(data));
		return this;
	}

	@Override
	public ChainProcessor<T> onStepException(BiConsumer<T, Exception> action) {
		this.onStepException = action;
		return this;
	}

	@Override
	public ChainProcessor<T> skipStepWhen(Predicate<T> condition) {
		this.skipStepCondition = condition;
		return this;
	}

	@Override
	public ChainStep<T> startWithGroup(UnaryOperator<T> start) {
		return this.thenGroup(start);
	}

}

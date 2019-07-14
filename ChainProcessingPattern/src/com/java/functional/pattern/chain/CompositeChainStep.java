package com.java.functional.pattern.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * CompositeChainStep contains a list of functions to be executed
 * @author linh
 *
 * @param <T>
 */
class CompositeChainStep <T extends ChainData>  extends BaseChainStep<T> {
	
	private List<UnaryOperator<T>> steps;

	public CompositeChainStep(UnaryOperator<T> func, ChainProcessor<T> processor) {
		super(processor);
		steps = new ArrayList<>();
		steps.add(func);
	}
	
	@Override
	public ChainStep<T> next(UnaryOperator<T> function) {
		steps.add(function);
		return this;
	}

	@Override
	protected void excuteStep(T data) {
		steps
			.stream()
			.forEach(f -> f.apply(data));
	}

}

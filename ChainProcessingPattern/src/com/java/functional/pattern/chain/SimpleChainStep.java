package com.java.functional.pattern.chain;

import java.util.function.UnaryOperator;

/**
 * Simple chain step with only one executable function <br/>
 * ChainStep is transparent and won't be exposed to outside
 * 
 * @author linh
 *
 */
class SimpleChainStep<T extends ChainData> extends BaseChainStep<T> {

	private UnaryOperator<T> stepFunction;

	public SimpleChainStep(UnaryOperator<T> func, ChainProcessor<T> processor) {
		super(processor);
		this.stepFunction = func;
	}

	@Override
	protected void excuteStep(T data) {
		stepFunction.apply(data);
	}

}

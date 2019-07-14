package com.java.functional.pattern.chain;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

/**
 * BaseChainStep<br/>
 * ChainStep is transparent and won't be exposed to outside
 * 
 * @author linh
 *
 */
abstract class BaseChainStep<T extends ChainData> implements ChainStep<T> {

	private static final Logger LOGGER = Logger.getAnonymousLogger();

	private Predicate<T> skipCondition;
	private String stepName = "";
	private ChainProcessor<T> processor;
	private BiConsumer<T, Exception> onExecutionException;

	private Predicate<T> ignoreSkipConditionWhen = d -> false;

	public BaseChainStep(ChainProcessor<T> processor) {
		this.skipCondition = d -> false;
		this.processor = processor;
	}

	@Override
	public ChainStep<T> skipWhen(Predicate<T> skipCondition) {
		if (this.skipCondition == null) {
			this.skipCondition = skipCondition;
		} else {
			this.skipCondition = this.skipCondition.or(skipCondition);
		}

		return this;
	}

	@Override
	public boolean isSkipped(T data) {
		return skipCondition.test(data);
	}

	@Override
	public ChainStep<T> execute(T data) {
		if (ignoreSkipConditionWhen.test(data) || !this.isSkipped(data)) {
			LOGGER.info("EXECUTE STEP " + stepName);
			try {
				excuteStep(data);
			} catch (Exception e) {
				if (onExecutionException != null) {
					onExecutionException.accept(data, e);
				} else {
					throw e;
				}
			}

		} else {
			LOGGER.info("IGNORE current step " + stepName);
		}
		return this;
	}
	
	protected abstract void excuteStep(T data);

	@Override
	public ChainStep<T> withName(String name) {
		this.stepName = name;
		return this;
	}

	@Override
	public ChainProcessor<T> processor() {
		return processor;
	}

	@Override
	public ChainStep<T> next(UnaryOperator<T> function) {
		return processor.then(function);
	}

	@Override
	public ChainStep<T> onExecutionException(BiConsumer<T, Exception> action) {
		this.onExecutionException = action;
		return this;
	}

	@Override
	public ChainStep<T> ignoreAllSkipConditionWhen(Predicate<T> condition) {
		ignoreSkipConditionWhen = ignoreSkipConditionWhen.or(condition);
		return this;
	}

}

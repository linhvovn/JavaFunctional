package com.java.functional.pattern.chain;

interface CommonFunction {
	
	@FunctionalInterface
	interface NoArgumentAction {
		void run();
	}

}

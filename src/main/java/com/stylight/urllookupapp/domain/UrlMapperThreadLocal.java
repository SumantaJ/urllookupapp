package com.stylight.urllookupapp.domain;

public class UrlMapperThreadLocal {

	private static ThreadLocal<UrlLookupAppContext> context = new ThreadLocal<UrlLookupAppContext>() {
		protected UrlLookupAppContext initialValue() {
			return new UrlLookupAppContext();
		}
	};

	public static UrlLookupAppContext getContext() {
		return context.get();
	}

	public static void clear() {
		context.remove();
	}
}

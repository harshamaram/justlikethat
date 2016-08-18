package com.sample.annotations;

public @interface Documentation {
	public String author() default "hmaram";
	public String version() default "1.0";
}

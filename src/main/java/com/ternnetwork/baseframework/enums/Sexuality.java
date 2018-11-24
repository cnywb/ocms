package com.ternnetwork.baseframework.enums;

public enum Sexuality {
	heterosex{public String getName(){return "异性";}},
	homosex{public String getName(){return "同性";}},
	bisexuality{public String getName(){return "双性";}};
	public abstract String getName();
}

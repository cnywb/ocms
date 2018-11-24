package com.ternnetwork.toolkit.enums;

public enum DealerBigArea {
    NORTH{public String getName(){return "北区";}},
	NORTH_EAST{public String getName(){return "东北区";}},
	EAST_REGION{public String getName(){return "东区";}},
	MIDDLE_REGION{public String getName(){return "华中区";}},
	SOUTH_REGION{public String getName(){return "南区";}},
	WEST_REGION{public String getName(){return "西区";}};
	public abstract String getName();
}

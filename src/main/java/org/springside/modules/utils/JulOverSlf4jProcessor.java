/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package org.springside.modules.utils;

import javax.annotation.PostConstruct;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class JulOverSlf4jProcessor {
	@PostConstruct
	public void init() {
		SLF4JBridgeHandler.install();
	}
}
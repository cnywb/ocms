package com.ternnetwork.baseframework.model.ui;

import java.util.Comparator;

public class KindEditorFileTypeComparator implements Comparator {
	@SuppressWarnings("rawtypes")
	public int compare(Object a, Object b) {
		KindEditorFile hashA = (KindEditorFile)a;
		KindEditorFile hashB = (KindEditorFile)b;
		if (((Boolean)hashA.getIs_dir()) && !((Boolean)hashB.getIs_dir())) {
			return -1;
		} else if (!((Boolean)hashA.getIs_dir()) && ((Boolean)hashB.getIs_dir())) {
			return 1;
		} else {
			return ((String)hashA.getFiletype()).compareTo((String)hashB.getFiletype());
		}
	}
}

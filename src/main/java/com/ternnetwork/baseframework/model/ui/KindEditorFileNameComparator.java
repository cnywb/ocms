package com.ternnetwork.baseframework.model.ui;

import java.util.Comparator;

public class KindEditorFileNameComparator implements Comparator {
	@SuppressWarnings("rawtypes")
	public int compare(Object a, Object b) {
		KindEditorFile hashA = (KindEditorFile)a;
		KindEditorFile hashB = (KindEditorFile)b;
		if (((Boolean)hashA.getIs_dir()) && !((Boolean)hashB.getIs_dir())) {
			return -1;
		} else if (!((Boolean)hashA.getIs_dir()) && ((Boolean)hashB.getIs_dir())) {
			return 1;
		} else {
			return ((String)hashA.getFilename()).compareTo((String)hashB.getFilename());
		}
	}
}

/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package org.springside.modules.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class CollectionUtils {
	public static List fetchPropertyToList(Collection collection, String propertyName) throws Exception {
		List list = new ArrayList();

		for (Iterator i$ = collection.iterator(); i$.hasNext();) {
			Object obj = i$.next();
			list.add(PropertyUtils.getProperty(obj, propertyName));
		}

		return list;
	}

	public static String fetchPropertyToString(Collection collection, String propertyName, String separator)
			throws Exception {
		List list = fetchPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	public static <T, ID> void mergeByCheckedIds(Collection<T> collection, Collection<ID> checkedIds, Class<T> clazz)
			throws Exception {
		mergeByCheckedIds(collection, checkedIds, "id", clazz);
	}

	public static <T, ID> void mergeByCheckedIds(Collection<T> collection, Collection<ID> checkedIds, String idName,
			Class<T> clazz) throws Exception {
		if (checkedIds == null) {
			collection.clear();
			return;
		}

		Iterator it = collection.iterator();

		while (it.hasNext()) {
			Object obj = it.next();
			if (checkedIds.contains(PropertyUtils.getProperty(obj, idName)))
				checkedIds.remove(PropertyUtils.getProperty(obj, idName));
			else {
				it.remove();
			}
		}

		for (Iterator i$ = checkedIds.iterator(); i$.hasNext();) {
			Object id = i$.next();
			Object obj = clazz.newInstance();
			PropertyUtils.setProperty(obj, idName, id);
			collection.add((T) obj);
		}
	}
}
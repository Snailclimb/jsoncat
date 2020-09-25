package com.github.jsoncat.common.utils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * @author shuang.kou
 * @createTime 2020年09月25日 15:52:00
 **/
public class ObjectUtils {
    /**
     * convert from String to a target type
     */
    public static Object convert(Class<?> targetType, String text) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(text);
        return editor.getValue();
    }
}

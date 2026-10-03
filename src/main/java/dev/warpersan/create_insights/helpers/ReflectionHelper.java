package dev.warpersan.create_insights.helpers;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/// Class responsible to offer methods for reflection
public class ReflectionHelper
{
	private static final Map<String, Field> FIELD_CACHE = new ConcurrentHashMap<>();

	/**
	 * Gets the instance value of the given field
	 */
	@Nullable
	public static Object getValue(@NotNull Object object, @NotNull String className, @NotNull String fieldName)
	{
		var parts = fieldName.split("\\.", 2);
		var field = getField(className, parts[0]);

		if (field == null)
			return null;

		try
		{
			var value = field.get(object);

			if (parts.length == 1)
				return value;

			var subClassName = field.getType().getName();

			return getValue(value, subClassName, parts[1]);
		} catch (IllegalAccessException e)
		{
			return null;
		}
	}

	/**
	 * Gets the field in the given class with the given name
	 */
	@Nullable
	private static Field getField(String className, String fieldName)
	{
		var key = className + "#" + fieldName;

		if (FIELD_CACHE.containsKey(key))
			return FIELD_CACHE.get(key);

		try
		{
			var clazz = Class.forName(className);
			var field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			FIELD_CACHE.put(key, field);
			return field;
		} catch (ClassNotFoundException | NoSuchFieldException e)
		{
			return null;
		}
	}
}

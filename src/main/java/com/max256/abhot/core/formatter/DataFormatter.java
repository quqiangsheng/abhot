package com.max256.abhot.core.formatter;

import com.max256.abhot.core.datastore.DataPointGroup;

import java.io.Writer;
import java.util.List;

public interface DataFormatter
{
	void format(Writer writer, List<List<DataPointGroup>> data) throws FormatterException;

	void format(Writer writer, Iterable<String> iterable) throws FormatterException;
}
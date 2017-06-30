package com.max256.abhot.core.formatter;


import org.json.JSONException;
import org.json.JSONWriter;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.datastore.DataPointGroup;
import com.max256.abhot.core.groupby.GroupByResult;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class JsonFormatter implements DataFormatter
{
	@Override
	public void format(Writer writer, Iterable<String> iterable) throws FormatterException
	{
		checkNotNull(writer);
		checkNotNull(iterable);

		try
		{
			JSONWriter jsonWriter = new JSONWriter(writer);
			jsonWriter.object().key("results").array();
			for (String string : iterable)
			{
				jsonWriter.value(string);
			}
			jsonWriter.endArray().endObject();
		}
		catch (JSONException e)
		{
			throw new FormatterException(e);
		}
	}

	@Override
	public void format(Writer writer, List<List<DataPointGroup>> data) throws FormatterException
	{

		checkNotNull(writer);
		checkNotNull(data);
		try
		{
			JSONWriter jsonWriter = new JSONWriter(writer);

			jsonWriter.object().key("queries").array();

			for (List<DataPointGroup> groups : data)
			{
				jsonWriter.object().key("results").array();

				for (DataPointGroup group : groups)
				{
					final String metric = group.getName();

					jsonWriter.object();
					jsonWriter.key("name").value(metric);

					if (!group.getGroupByResult().isEmpty())
					{
						jsonWriter.key("group_by");
						jsonWriter.array();
						boolean first = true;
						for (GroupByResult groupByResult : group.getGroupByResult())
						{
							if (!first)
								writer.write(",");
							writer.write(groupByResult.toJson());
							first = false;
						}
						jsonWriter.endArray();
					}

					jsonWriter.key("tags").object();

					for (String tagName : group.getTagNames())
					{
						jsonWriter.key(tagName);
						jsonWriter.value(group.getTagValues(tagName));
					}
					jsonWriter.endObject();

					jsonWriter.key("values").array();
					while (group.hasNext())
					{
						DataPoint dataPoint = group.next();

						jsonWriter.array().value(dataPoint.getTimestamp());

						dataPoint.writeValueToJson(jsonWriter);

						/*
						if (dataPoint.isInteger())
						{
							jsonWriter.value(dataPoint.getLongValue());
						}
						else
						{
							final double value = dataPoint.getDoubleValue();
							if (value != value || Double.isInfinite(value))
							{
								throw new IllegalStateException("NaN or Infinity:" + value + " data point=" + dataPoint);
							}
							jsonWriter.value(value);
						}*/

						//jsonWriter.value(dataPoint.getApiDataType());
						jsonWriter.endArray();
					}
					jsonWriter.endArray();
					jsonWriter.endObject();

					group.close();
				}

				jsonWriter.endArray().endObject();
			}

			jsonWriter.endArray().endObject();
		}
		catch (JSONException e)
		{
			throw new FormatterException(e);
		}
		catch (IOException e)
		{
			throw new FormatterException(e);
		}
	}
}
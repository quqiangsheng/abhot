/**
This file is automatically generated.  Do not modify
*/
package com.max256.abhot.datastore.h2.orm;

import java.util.Locale;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.Timestamp;
import org.xml.sax.ContentHandler;
import org.xml.sax.Attributes;
import org.agileclick.genorm.runtime.*;


/**
	Inserts or updates data point
*/
public class InsertDataPointQuery extends SQLQuery
	{
	private static final Logger s_logger = LoggerFactory.getLogger(InsertDataPointQuery.class.getName());
	
	public static final String QUERY_NAME = "insert_data_point";
	public static final String QUERY = "MERGE INTO data_point (\"metric_id\", \"timestamp\", \"value\") VALUES(?, ?, ?)";
	private static final int ATTRIBUTE_COUNT = 0;
	private static Map<String, Integer> s_attributeIndex;
	private static String[] s_attributeNames = {
 };
			
	static
		{
		s_attributeIndex = new HashMap<String, Integer>();
		for (int I = 0; I < ATTRIBUTE_COUNT; I++)
			s_attributeIndex.put(s_attributeNames[I], I);
		}
	
	private boolean m_serializable;
	
	private String m_metricId;
	private Timestamp m_timestamp;
	private byte[] m_value;

	//Deprecated
	public InsertDataPointQuery()
		{
		super();
		}		
	//---------------------------------------------------------------------------
	public InsertDataPointQuery(String metricId, Timestamp timestamp, byte[] value)
		{
		super();
		m_metricId = metricId;
		m_timestamp = timestamp;
		m_value = value;
		}
		
	//---------------------------------------------------------------------------
	public String getQueryName() { return (QUERY_NAME); }
	
	//---------------------------------------------------------------------------
	public String getQuery() { return (QUERY); }
		
	//---------------------------------------------------------------------------
	public void setSerializable(boolean serializable)
		{
		m_serializable = serializable;
		}
	
	//---------------------------------------------------------------------------
	public String toString()
		{
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName());
		sb.append(" metricId=").append(String.valueOf(m_metricId));
		sb.append(" timestamp=").append(String.valueOf(m_timestamp));
		sb.append(" value=").append(String.valueOf(m_value));
		
		return (sb.toString());
		}
		
	
	//---------------------------------------------------------------------------
	//Deprecated
	public int runUpdate(String metricId, Timestamp timestamp, byte[] value)
		{
		int ret = 0;
		java.sql.PreparedStatement genorm_statement = null;
		
		try
			{
			String genorm_query = QUERY;
			
			genorm_statement = com.max256.abhot.datastore.h2.orm.GenOrmDataSource.prepareStatement(genorm_query);
			genorm_statement.setString(1, metricId);
			genorm_statement.setTimestamp(2, timestamp);
			genorm_statement.setBytes(3, value);

			
			ret = genorm_statement.executeUpdate();
			}
		catch (java.sql.SQLException sqle)
			{
			throw new GenOrmException(sqle);
			}
		finally
			{
			try
				{
				if (genorm_statement != null)
					genorm_statement.close();
				}
			catch (java.sql.SQLException sqle2) { }
			}
			
		return (ret);
		}	
	//---------------------------------------------------------------------------
	public int runUpdate()
		{
		int ret = 0;
		java.sql.PreparedStatement genorm_statement = null;
		try
			{
			String genorm_query = QUERY;
			
			genorm_statement = com.max256.abhot.datastore.h2.orm.GenOrmDataSource.prepareStatement(genorm_query);
			genorm_statement.setString(1, m_metricId);
			genorm_statement.setTimestamp(2, m_timestamp);
			genorm_statement.setBytes(3, m_value);

			
			ret = genorm_statement.executeUpdate();
			}
		catch (java.sql.SQLException sqle)
			{
			throw new GenOrmException(sqle);
			}
		finally
			{
			try
				{
				genorm_statement.close();
				}
			catch (java.sql.SQLException sqle2) { }
			}
			
		return (ret);
		}
	}
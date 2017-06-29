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
	Returns the metric ids for a specified query. In essence this the initial rows returned.
*/
public class MetricIdsQuery extends SQLQuery
	{
	private static final Logger s_logger = LoggerFactory.getLogger(MetricIdsQuery.class.getName());
	
	public static final String QUERY_NAME = "metric_ids";
	public static final String QUERY = "select m.\"id\" as metric_id, m.\"type\" as metric_type\n				from metric m, metric_tag mt\n				where\n				mt.\"metric_id\" = m.\"id\"\n				and m.\"name\" = ?\n				group by m.\"id\"";
	private static final int ATTRIBUTE_COUNT = 2;
	private static Map<String, Integer> s_attributeIndex;
	private static String[] s_attributeNames = {
			"metricId",
			"metricType" };
			
	static
		{
		s_attributeIndex = new HashMap<String, Integer>();
		for (int I = 0; I < ATTRIBUTE_COUNT; I++)
			s_attributeIndex.put(s_attributeNames[I], I);
		}
	
	private boolean m_serializable;
	
	private String m_metricName;

	//Deprecated
	public MetricIdsQuery()
		{
		super();
		}		
	//---------------------------------------------------------------------------
	public MetricIdsQuery(String metricName)
		{
		super();
		m_metricName = metricName;
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
		sb.append(" metricName=").append(String.valueOf(m_metricName));
		
		return (sb.toString());
		}
		
	

	//---------------------------------------------------------------------------
	public void serializeQuery(ContentHandler ch, String tagName)
			throws org.xml.sax.SAXException
		{
		boolean prevSerializeState = m_serializable;
		m_serializable = true;
		ResultSet rs = runQuery();
		
		while (rs.next())
			{
			MetricIdsData rec = rs.getRecord();
			ch.startElement("", tagName, tagName, rec);
			ch.endElement("", tagName, tagName);
			}
			
		rs.close();
		m_serializable = prevSerializeState;
		}
	
	//---------------------------------------------------------------------------
	//Deprecated
	public ResultSet runQuery(String metricName)
		{
		java.sql.PreparedStatement genorm_statement = null;
		try
			{
			String genorm_query = QUERY;
			
			genorm_statement = com.max256.abhot.datastore.h2.orm.GenOrmDataSource.prepareStatement(genorm_query);
			genorm_statement.setString(1, metricName);

			long genorm_queryTimeStart = 0L;
			if (s_logger.isInfo())
				{
				genorm_queryTimeStart = System.currentTimeMillis();
				}
				
			java.sql.ResultSet genorm_resultSet = genorm_statement.executeQuery();
			
			if (genorm_queryTimeStart != 0L)
				{
				long genorm_quryTime = System.currentTimeMillis() - genorm_queryTimeStart;
				s_logger.info(genorm_quryTime);
				}
			
			ResultSet genorm_ret = new SQLResultSet(genorm_resultSet, genorm_statement, genorm_query);
			
			return (genorm_ret);
			}
		catch (java.sql.SQLException sqle)
			{
			try
				{
				if (genorm_statement != null)
					genorm_statement.close();
				}
			catch (java.sql.SQLException sqle2) { }
				
			throw new GenOrmException(sqle);
			}
		}		
	//---------------------------------------------------------------------------
	public ResultSet runQuery()
		{
		java.sql.PreparedStatement genorm_statement = null;
		try
			{
			String genorm_query = QUERY;
			
			genorm_statement = com.max256.abhot.datastore.h2.orm.GenOrmDataSource.prepareStatement(genorm_query);
			genorm_statement.setString(1, m_metricName);

			long genorm_queryTimeStart = 0L;
			if (s_logger.isInfo())
				{
				genorm_queryTimeStart = System.currentTimeMillis();
				}
				
			java.sql.ResultSet genorm_resultSet = genorm_statement.executeQuery();
			
			if (genorm_queryTimeStart != 0L)
				{
				long genorm_quryTime = System.currentTimeMillis() - genorm_queryTimeStart;
				s_logger.info(genorm_quryTime);
				}
			
			ResultSet genorm_ret = new SQLResultSet(genorm_resultSet, genorm_statement, genorm_query);
			
			return (genorm_ret);
			}
		catch (java.sql.SQLException sqle)
			{
			try
				{
				if (genorm_statement != null)
					genorm_statement.close();
				}
			catch (java.sql.SQLException sqle2) { }
				
			throw new GenOrmException(sqle);
			}
		}
		
	//===========================================================================
	//Plugin Classes and Methods
		
	//===========================================================================
	public interface ResultSet extends GenOrmQueryResultSet<MetricIdsData>
		{
		public List<MetricIdsData> getArrayList(int maxRows);
		public List<MetricIdsData> getArrayList();
		public MetricIdsData getRecord();
		public MetricIdsData getOnlyRecord();
		}
		
	//===========================================================================
	private class SQLResultSet 
			implements ResultSet
		{
		private java.sql.ResultSet m_resultSet;
		private java.sql.Statement m_statement;
		private String m_query;
		private boolean m_onFirstResult;
		
		//------------------------------------------------------------------------
		//need to pass in the statement so it can be closed after the result set
		protected SQLResultSet(java.sql.ResultSet resultSet, java.sql.Statement statement, 
				String query)
			{
			m_resultSet = resultSet;
			m_statement = statement;
			m_query = query;
			m_onFirstResult = false;
			}
		
		//------------------------------------------------------------------------
		/**
			Closes any underlying java.sql.Result set and java.sql.Statement 
			that was used to create this results set.
		*/
		public void close()
			{
			try
				{
				m_resultSet.close();
				m_statement.close();
				}
			catch (java.sql.SQLException sqle)
				{
				throw new GenOrmException(sqle);
				}
			}
			
		//------------------------------------------------------------------------
		/**
			Returns the reults as an ArrayList of Record objects.
			The Result set is closed within this call
		*/
		public List<MetricIdsData> getArrayList(int maxRows)
			{
			ArrayList<MetricIdsData> results = new ArrayList<MetricIdsData>();
			int count = 0;
			
			try
				{
				if (m_onFirstResult)
					{
					count ++;
					results.add(new MetricIdsData(MetricIdsQuery.this, m_resultSet));
					}
					
				while (m_resultSet.next() && (count < maxRows))
					{
					count ++;
					results.add(new MetricIdsData(MetricIdsQuery.this, m_resultSet));
					}
					
				if (m_resultSet.next())
					throw new GenOrmException("Bound of "+maxRows+" is too small for query ["+m_query+"]");
				}
			catch (java.sql.SQLException sqle)
				{
				throw new GenOrmException(sqle);
				}
				
			close();
			return (results);
			}
			
		//------------------------------------------------------------------------
		/**
			Returns the reults as an ArrayList of Record objects.
			The Result set is closed within this call
		*/
		public List<MetricIdsData> getArrayList()
			{
			ArrayList<MetricIdsData> results = new ArrayList<MetricIdsData>();
			
			try
				{
				if (m_onFirstResult)
					results.add(new MetricIdsData(MetricIdsQuery.this, m_resultSet));
					
				while (m_resultSet.next())
					results.add(new MetricIdsData(MetricIdsQuery.this, m_resultSet));
				}
			catch (java.sql.SQLException sqle)
				{
				throw new GenOrmException(sqle);
				}
				
			close();
			return (results);
			}
			
		//------------------------------------------------------------------------
		/**
			Returns the underlying java.sql.ResultSet object
		*/
		public java.sql.ResultSet getResultSet()
			{
			return (m_resultSet);
			}
			
		//------------------------------------------------------------------------
		/**
			Returns the current record in the result set
		*/
		public MetricIdsData getRecord()
			{
			MetricIdsData ret = null;
			try
				{
				ret = new MetricIdsData(MetricIdsQuery.this, m_resultSet);
				}
			catch (java.sql.SQLException sqle)
				{
				throw new GenOrmException(sqle);
				}
				
			return (ret);
			}
			
		//------------------------------------------------------------------------
		/**
			This call expects only one record in the result set.  If multiple records
			are found an excpetion is thrown.
			The ResultSet object is automatically closed by this call.
		*/
		public MetricIdsData getOnlyRecord()
			{
			MetricIdsData ret = null;
			
			try
				{
				if (m_resultSet.next())
					ret = new MetricIdsData(MetricIdsQuery.this, m_resultSet);
					
				if (m_resultSet.next())
					throw new GenOrmException("Multiple rows returned in call from MetricIdsQuery.ResultSet.getOnlyRecord");
				}
			catch (java.sql.SQLException sqle)
				{
				throw new GenOrmException(sqle);
				}
				
			close();
			return (ret);
			}
			
		//------------------------------------------------------------------------
		/**
			Returns true if there is another record in the result set.
		*/
		public boolean next()
			{
			boolean ret = false;
			m_onFirstResult = true;
			try
				{
				ret = m_resultSet.next();
				}
			catch (java.sql.SQLException sqle)
				{
				throw new GenOrmException(sqle);
				}
			
			return (ret);
			}
		}
		
	//===========================================================================
	public class Record implements GenOrmQueryRecord, Attributes
		{
		protected String m_metricId;
		protected String m_metricType;

		protected String[] m_attrValues;
		
		protected Record(java.sql.ResultSet rs)
				throws java.sql.SQLException
			{
			m_metricId = (String)rs.getString(1);
			m_metricType = (String)rs.getString(2);

			if (m_serializable)
				{
				m_attrValues = new String[ATTRIBUTE_COUNT];
				
				m_attrValues[0] = MetricIdsQuery.this.m_formatter.toString(s_attributeNames[0], m_metricId);
				m_attrValues[1] = MetricIdsQuery.this.m_formatter.toString(s_attributeNames[1], m_metricType);

				}
			}
			
		public String getMetricId() { return (m_metricId); }
		public String getMetricType() { return (m_metricType); }
 
		
		//------------------------------------------------------------------------
		public String toString()
			{
			StringBuilder sb = new StringBuilder();
			sb.append(" metric_id=\"");
			sb.append(m_metricId);
			sb.append("\"");
			sb.append(" metric_type=\"");
			sb.append(m_metricType);
			sb.append("\"");

			return (sb.toString().trim());
			}
			
		//------------------------------------------------------------------------
		/*Attributes*/
		public int getIndex(String qName)
			{
			Integer index = s_attributeIndex.get(qName);
			if (index == null)
				return (-1);
			else
				return (index);
			}
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public int getIndex(String uri, String localName)
			{
			if (uri == null || uri.equals(""))
				return (getIndex(localName));
			else
				return (-1);
			}
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public int getLength() { return (ATTRIBUTE_COUNT); }
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public String getLocalName(int index)
			{
			if (index < 0 || index >= ATTRIBUTE_COUNT)
				return (null);
			else
				return (s_attributeNames[index]);
			}
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public String getQName(int index)
			{
			if (index < 0 || index >= ATTRIBUTE_COUNT)
				return (null);
			else
				return (s_attributeNames[index]);
			}
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public String getType(int index) { return ("CDATA"); }
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public String getType(String qName) { return ("CDATA"); }
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public String getType(String uri, String localName) { return ("CDATA"); }
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public String getURI(int index)
			{
			if (index < 0 || index >= ATTRIBUTE_COUNT)
				return (null);
			else
				return ("");
			}
			
		//------------------------------------------------------------------------
		/*Attributes*/
		public String getValue(int index)
			{
			if (index < 0 || index >= ATTRIBUTE_COUNT)
				return (null);
			else
				return (m_attrValues[index]);
			}
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public String getValue(String qName)
			{
			return (getValue(getIndex(qName)));
			}
		
		//------------------------------------------------------------------------
		/*Attributes*/
		public String getValue(String uri, String localName)
			{
			return (getValue(getIndex(uri, localName)));
			}
			
		}
		
	}
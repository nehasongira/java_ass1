package com.stackroute.datamunger;

/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMunger {

	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */

	public String[] getSplitStrings(String queryString) {

		queryString = queryString.toLowerCase();
		String[] SplitedString = queryString.split(" ");
		return SplitedString;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 *
	 * Please consider this while extracting the file name in this method.
	 */

	public String getFileName(String queryString) {
		int input1 = queryString.indexOf("from") + 5;
		int input2 = queryString.indexOf("csv") + 3;
		queryString = queryString.substring(input1, input2);
		return queryString;

	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 *
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */

	public String getBaseQuery(String queryString) {


		if (queryString.contains("where")) {

			int input2 = queryString.indexOf("where");
			input2 = input2 - 1;
			queryString = queryString.substring(0, input2);
		}else if(queryString.contains("group by")) {
			int input2 = queryString.indexOf("group by");
			input2 = input2 - 1;
			queryString = queryString.substring(0, input2);
		}else if(queryString.contains("order by"))
		{
			int input2 = queryString.indexOf("order by");
			input2 = input2 - 1;
			queryString = queryString.substring(0, input2);
		}
		else{
			return null;
		}

		return queryString;
	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 *
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 *
	 */

	public String[] getFields(String queryString) {

		int input1 = queryString.indexOf("select") + 7;
		int input2 = queryString.indexOf("from");
		input2 = input2 - 1;
		queryString = queryString.substring(input1, input2);
		String[] results = queryString.split(",");
		return results;
	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String getConditionsPartQuery(String queryString) {
		int input1 = queryString.indexOf("where") + 6;
		if (queryString.matches("order by")) {
			int input2 = queryString.indexOf("order by");
			input2 = input2 - 1;
			queryString = queryString.substring(input1, input2);
			return queryString.toLowerCase();
		}
		if (queryString.matches("group by")) {
			int input2 = queryString.indexOf("group by");
			input2 = input2 - 1;
			queryString = queryString.substring(input1, input2);
			return queryString.toLowerCase();
		}
		return queryString.substring(input1).toLowerCase();


	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 *
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 *
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String[] getConditions(String queryString) {
		queryString = queryString.toLowerCase();
		String result1[] = null;
		if (queryString.contains("where")) {
			int input1 = queryString.indexOf("where");
			input1 = input1 + 6;

			if (queryString.contains("order by")) {
				int input2 = queryString.indexOf("order by");
				input2 = input2 - 1;
				queryString = queryString.substring(input1, input2);

				String[] result2 = queryString.split(" and | or ");
				return result2;
			}
			if (queryString.contains("group by")) {
				int input2 = queryString.indexOf("group by");
				input2 = input2 - 1;
				queryString = queryString.substring(input1, input2);

				String[] result3 = queryString.split(" and | or ");
				return result3;
			}

			queryString = queryString.substring(input1);

			String[] result4 = queryString.split(" and | or ");
			return result4;
		}
		return null;

	}

	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 *
	 */

	public String[] getLogicalOperators(String queryString) {
		queryString = queryString.toLowerCase();
		String[] query = queryString.split(" ");
		String getLogical = "";
		if (queryString.contains("where ")) {
			for (int i = 0; i < query.length; i++) {
				if (query[i].matches("and|or|not")) {

					getLogical += query[i] + " ";
				}
			}
			return getLogical.toString().split(" ");
		}
		return null;
	}

	/*
	 * This method extracts the order by fields from the query string. Note:
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public String[] getOrderByFields(String queryString) {
		String[] result2 = null;
		if (queryString.contains("order by")) {
			int input2 = queryString.indexOf("order by");
			input2 = input2 + 9;
			queryString = queryString.substring(input2);
			result2 = queryString.split(" ,");

		}
		return result2;
		//return "error";
	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 *
	 * Consider this while extracting the group by fields
	 */

	public String[] getGroupByFields(String queryString) {
		String[] result2 = null;
		if (queryString.contains("group by")) {
			int input2 = queryString.indexOf("group by");
			input2 = input2 + 9;
			queryString = queryString.substring(input2);
			result2 = queryString.split(" ,");

		}
		return result2;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 *
	 * Consider this while extracting the aggregate functions
	 */

	public String[] getAggregateFunctions(String queryString) {

		queryString = queryString.toLowerCase();
		boolean state = false;
		String getAggregate = "";
		String[] query = queryString.split(" ");
		String[] aggQuery = query[1].split(",");
		for (int i = 0; i < aggQuery.length; i++) {
			if ((aggQuery[i].startsWith("max(") && aggQuery[i].endsWith(")"))
					|| (aggQuery[i].startsWith("min(") && aggQuery[i].endsWith(")"))
					|| (aggQuery[i].startsWith("count(") && aggQuery[i].endsWith(")"))
					|| (aggQuery[i].startsWith("avg(") && aggQuery[i].endsWith(")"))
					|| (aggQuery[i].startsWith("sum") && aggQuery[i].endsWith(")"))) {
				getAggregate += aggQuery[i] + " ";
				state = true;
			}
		}
		if (state == true)
			return getAggregate.trim().split(" ");
		else
			return null;

	}
}
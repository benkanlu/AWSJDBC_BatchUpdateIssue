# AWSJDBC_BatchUpdateIssue
This repository recreates the bug when the AWS JDBC Driver tries to parse the standard single line comment '-- ' and produces an error of "Statement.executeUpdate() or Statement.executeLargeUpdate() cannot issue statements that produce result sets."
This bug does not exist in MariaDB drivers from what has been tested.

This appears to have been introduced in release 1.0.0 of the AWS JDBC Driver around this part of the code that checks to see if the statements return a results set.


  QueryInfo.getQueryReturnType
  
  StringInspector 
  
    SKIP_BLOCK_COMMENTS

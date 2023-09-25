package com.example.BatchUpdateIssue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@SpringBootApplication
public class BatchUpdateIssueApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BatchUpdateIssueApplication.class, args);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public void run(String... args) throws Exception {

		try {

			// Example 1: Last statement ends in a comment of '-- ', causes error : "Statement.executeUpdate() or Statement.executeLargeUpdate() cannot issue statements that produce result sets."

			String[] statements = new String[]{
					"create table yeehaw2 (id bigint not null)",
					"create table howdy (id bigint not null)",
					"insert into yeehaw2 (id) values(555)\n",
					"insert into yeehaw2 (id) values(2323)\n",
					"insert into howdy (id) values(88)\n",
					"insert into howdy (id) values(77)\n",
					"\n-- "};

			int[] result = jdbcTemplate.batchUpdate(statements);

		} catch(Exception ex){

			System.out.println(MessageFormat.format("Error using batchUpdate statement: {0}", ex.getMessage()));

		}

		try {

			// Example 2: Comment of '-- ' in the middle of other statement, causes error : "Statement.executeUpdate() or Statement.executeLargeUpdate() cannot issue statements that produce result sets."
			String[] statements = new String[]{
					"insert into yeehaw2 (id) values(4444)\n",
					"insert into yeehaw2 (id) values(333)\n",
					"\ninsert into howdy (id) values(22)",
					"\n-- ",
					"insert into howdy (id) values(999999)\n"};

			int[] result = jdbcTemplate.batchUpdate(statements);

		} catch(Exception ex){

			System.out.println(MessageFormat.format("Error using batchUpdate statement: {0}", ex.getMessage()));

		}

		try {

			// Example 3: Script has been fully commented out, causes error : "Statement.executeUpdate() or Statement.executeLargeUpdate() cannot issue statements that produce result sets."
			String[] statements = new String[]{
					"\n-- Blanked out script",
					"-- This should work in case I want to blank out a script",
					"\n-- ABCDEFG",
					"\n-- HIJKLMNOP"};

			int[] result = jdbcTemplate.batchUpdate(statements);

		} catch(Exception ex){

			System.out.println(MessageFormat.format("Error using batchUpdate statement: {0}", ex.getMessage()));

		}


	}

}

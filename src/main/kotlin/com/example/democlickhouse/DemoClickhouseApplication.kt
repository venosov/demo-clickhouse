package com.example.democlickhouse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.Timestamp


data class MyFirstTable(
    val userId: Int,
    val message: String,
    val timestamp: Timestamp,
    val metric: Double
)

@Repository
class MyFirstRepository(private val jdbcTemplate: JdbcTemplate) {
    fun findAll(): List<MyFirstTable> {
        return jdbcTemplate.query<MyFirstTable>("SELECT user_id,message,timestamp,metric FROM my_first_table") { rs: ResultSet, _: Int ->
            MyFirstTable(
                rs.getInt("user_id"),
                rs.getString("message"),
                rs.getTimestamp("timestamp"),
                rs.getDouble("metric")
            )
        }
    }
}

@SpringBootApplication
class DemoClickhouseApplication : CommandLineRunner {
    @Autowired
    lateinit var repository: MyFirstRepository

    override fun run(vararg args: String?) {
        val r = repository.findAll()
        r.forEach(::println)
    }
}

fun main(args: Array<String>) {
    runApplication<DemoClickhouseApplication>(*args)
}

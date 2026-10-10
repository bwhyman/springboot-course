package org.example.mybatisflexexamples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SQLScriptWrite {

    public static void main(String[] args) throws IOException {
        createPostgreSQL();
    }

    private static void createPostgreSQL() throws IOException {
        List<String> numbers = Files.readAllLines(Path.of("D:/stus.txt"));
        StringBuilder builder = new StringBuilder();
        builder.append("-- 复制到console运行时，当出现账号存在等错误，选择忽略全部ignore all");
        var sql = """
                CREATE USER "%s" WITH PASSWORD '%s';
                CREATE DATABASE "%s" WITH OWNER = "%s";
                \\c "%s"
                ALTER SCHEMA public OWNER TO "%s";
                """;

        numbers.forEach(number -> {
            builder.append(sql.formatted(number, number, number, number, number, number));

        });
        Files.writeString(Path.of("D:/result.sql"), builder.toString());
    }

    private static void createMySQL() throws IOException {
        List<String> numbers = Files.readAllLines(Path.of("D:/stus.txt"));
        StringBuilder builder = new StringBuilder();
        var userSt = """
                create user if not exists '%s'@'%%' identified by '%s';
                """;
        var databaseSt = """
                create database if not exists `%s`;
                """;
        var privilegesSt = """
                grant all privileges on `%s`.* to '%s'@'%%' with grant option;
                """;
        var loadSt = """
                grant reload on *.* to '%s'@'%%';
                """;
        numbers.forEach(number -> {
            builder.append(userSt.formatted(number, number));
            builder.append(databaseSt.formatted(number));
            builder.append(privilegesSt.formatted(number, number));
            builder.append(loadSt.formatted(number));
        });
        builder.append("flush privileges;");
        Files.writeString(Path.of("D:/result.sql"), builder.toString());
    }
}

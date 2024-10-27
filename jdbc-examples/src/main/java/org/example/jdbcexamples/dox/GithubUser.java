package org.example.jdbcexamples.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubUser {
    @Id
    @CreatedBy
    private String id;
    private String name;
    private Integer followers;
    private Integer stars;
    private String gender;
    private  Integer repos;
}

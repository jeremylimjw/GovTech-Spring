## GovTech Mini Project
Documented below are some of the proofs for passing the acceptance criterias

## Acceptance Criteria 1
- [x] When the application starts up, it should be pre-loaded with testable data.  
- [x] Expose /users endpoint that returns users with valid salary (0 <= salary <= 4000)  
### Json response from seed data:
```json
{
    "results": [
        {
            "id": 1,
            "name": "Alice",
            "salary": 1000.00
        },
        {
            "id": 2,
            "name": "Bob",
            "salary": 2000.00
        }
    ]
}
```
### Additional sub-criterias:
- [x] 1.1: min and max  
- [x] 1.2: order. Also include illegal order parameters.  
- [x] 1.3: offset and limit.  

## Acceptance Criteria 2
- [x] Upload with a properly structured CSV file. You may include any data in the csv file.  
- [x] File should include some new data that is not in the database, and some that overwrites the database.  
- [x] File should include rows with negative and 0.00 salary.  
- [x] /users should work as expected after the upload and that there are new results returned as well as
previous results that have been overwritten. Negative rows should be ignored in the input and 0.0
should be updated and returned.  

### Test cases (executed on top of seed data)
<table>
<tr>
<td>
  
`POST /upload` request
  
</td>
<td>
  
`POST /upload` response

</td>
<td>
  
`GET /users` response
  
</td>
<td>

Notes

</td>
</tr>

<tr>
<td>

```
name,salary
Alice,0
Bob,-2000
Charlie,3000
```

</td>
<td>
  
```json
{
    "success": 1
}
```

</td>
<td>

```json
{
    "results": [
        {
            "id": 1,
            "name": "Alice",
            "salary": 0.00
        },
        {
            "id": 2,
            "name": "Bob",
            "salary": 2000.00
        },
        {
            "id": 3,
            "name": "Charlie",
            "salary": 3000.00
        }
    ]
}
```

</td>

<td>

- Alice's salary gets overwritten  
- Bob's new salary is ignored (<0 requirement)  
- Charlie gets registered as a new record

</td>
</tr>

</table>

### Acceptance Criteria 3
- [x] Upload with an improperly structured CSV file that should contain at least some good rows.
- [x] File should be rejected and none of the good rows should have been applied.

### Test cases (executed on top of seed data)
<table>
<tr>
<td>
  
Uploaded CSV
  
</td>
<td>
  
`POST /upload` response

</td>
<td>
  
`GET /users` response
  
</td>
<td>

Notes

</td>
</tr>

<tr>
<td>

```
name,salary,age
Alice,1111,21
Bob,2222,22
```

</td>
<td>
  
```json
{
    "message": "CsvInvalidFormatException: Csv must have exactly 2 columns.",
    "success": 0
}
```

</td>
<td>

```json
{
    "results": [
        {
            "id": 1,
            "name": "Alice",
            "salary": 1000.00
        },
        {
            "id": 2,
            "name": "Bob",
            "salary": 2000.00
        }
    ]
}
```

</td>

<td>

- CSV rejected due to too many columns
- Database state is unchanged

</td>
</tr>
<tr>
<td>

```
name,salary
Alice,1111
Bob,abcdef
```

</td>
<td>
  
```json
{
    "message": "CsvInvalidFormatException: abcdef is not a number.",
    "success": 0
}
```

</td>
<td>

```json
{
    "results": [
        {
            "id": 1,
            "name": "Alice",
            "salary": 1000.00
        },
        {
            "id": 2,
            "name": "Bob",
            "salary": 2000.00
        }
    ]
}
```

</td>

<td>

- CSV rejected due to invalid salary value
- Although Alice's new entry is valid, its record is not updated 
- Database state is unchanged

</td>
</tr>

</table>

## Configuration
- Maven Project
- Spring Boot v2.7.0
- Java 11

## Dependencies
- Spring Web
- Spring JPA & Hibernate
- Postgres Driver

## References
- Setting up Postgres connection - https://dzone.com/articles/bounty-spring-boot-and-postgresql-database
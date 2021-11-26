## Documentation
### Configuration file
The configuration data is kept in a configuration file in the same directory as the `.jar` under the name of `.data.json`. This file is in JSON format and contains the message database connection information as well as the user's UUID. This file can be distributed by the administrator to clients and imported from the program. 

### Database

| messageID (int) | messagePart (int \| null) | sender (char [36]) | receiver (char [36]) | sendDate (int) | messageType (int) |
|-----------------|---------------------------|--------------------|----------------------|----------------|-------------------|
| ...             | ...                       | ...                | ...                  | ...            | ...               |
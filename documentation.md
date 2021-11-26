## Documentation
### Configuration file
The configuration data is kept in a configuration file in the same directory as the `.jar` under the name of `.data.json`. This file is in JSON format and contains the message database connection information as well as the user's UUID. This file can be distributed by the administrator to clients and imported from the program. 

### Database
#### Main table
* Table name: `messages`

| messageID (int) | sender (char [36]) | receiver (char [36]) | sendDate (int) | contentID (int) | messageType (int) |
|-----------------|--------------------|----------------------|----------------|-----------------|-------------------|
| ...             | ...                | ...                  | ...            |                 |                   |

#### Message table
* Table name: `text_messages`

| messageID (int) | messagePart (int) | content (var char [512]) |
|-----------------|-------------------|--------------------------|
| ...             | ...               | ...                      |

#### File message table
* Table name: `file_messages`

| messageID (int) | fileName (var char [128]) | fileID (char [36]) | size (int) |
|-----------------|---------------------------|------------------------|------------|
| ...             | ...                       | ...                    | ...        |
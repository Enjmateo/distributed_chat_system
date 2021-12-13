## Documentation
### Configuration file
The configuration data is kept in a configuration file in the same directory as the `.jar` under the name of `.data.json`. This file is in JSON format and contains the message database connection information as well as the user's UUID. This file can be distributed by the administrator to clients and imported from the program. 

### Database
#### Main table
* Table name: `messages`

| messageID (int) | sender (char [36]) | receiver (char [36]) | sendDate (int) | contentID (int) | messageType (int) |
|-----------------|--------------------|----------------------|----------------|-----------------|-------------------|
| ...             | ...                | ...                  | ...            |                 |                   |

`messageType` inform data type:
* 0 => Debug message
* 1 => Text message
* 2 => Image message
* 3 => File message

#### Message table
* Table name: `text_message`

| messageID (int) | messagePart (int) | content (var char [512]) |
|-----------------|-------------------|--------------------------|
| ...             | ...               | ...                      |

#### File message table
* Table name: `file_message`

| messageID (int) | fileName (var char [128]) | fileID (char [36]) | size (int) |
|-----------------|---------------------------|------------------------|------------|
| ...             | ...                       | ...                    | ...        |

#### Init. database
```SQL
create table messages(messageID integer not null, sender char(36) not null, receiver char(36) not null, sendDate bigint not null, contentID integer not null, messageType integer);
create table text_message(messageID integer not null, messagePart integer, content varchar(512));
create table file_message(messageID integer not null, fileName varchar(128) not null, fileID char(36), size integer);
```


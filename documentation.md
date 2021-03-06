## Documentation
***Disclamer:*** **This documentation is a technical documentation about the messages database. It is recommended to refer to the administrator manual for more general documentation (See [report.pdf](rapport.pdf)).**

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
create table messages(messageID integer not null auto_increment, sender char(36) not null, receiver char(36) not null, sendDate bigint not null, contentID integer not null, messageType integer, PRIMARY KEY (messageID));
create table text_message(messageID integer not null, messagePart integer, content varchar(512));
create table file_message(messageID integer not null, fileName varchar(128) not null, fileID char(36), size integer);
```


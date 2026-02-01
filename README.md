# GEPrice

This is the service layer for the GEPrice application.  This code allows for the
querying of ge prices, as well as the uploading and maintenance of GE submissions.

## Endpoints

### Health
The health endpoint is a heartbeat endpoint, allowing verification that the server
is up

Example Endpoint
```
GET <api_endpoint>/api/health
```

Example Response 
```
{
    "status": "ok",
    "timestamp": "2026-02-01T20:46:17.895664100Z"
}
```

### Bosses

#### All Bosses

This endpoint returns information about all bosses

Example Endpoint

```
GET <api_endpoint>/api/bosses/all
```

Example Response
```json
[
    {
        "id": 1,
        "name": "Arch-Glacor",
        "wikiUrl": "https://runescape.wiki/w/Arch-Glacor",
        "icon": "Arch-Glacor.png",
        "createdAt": "2026-02-01T20:46:17.895664100Z"
    },
    {
        "id": 2,
        "name": "Amascut, the Devourer",
        "wikiUrl": "https://runescape.wiki/w/Amascut,_the_Devourer",
        "icon": "Amascut.png",
        "createdAt": "2026-02-01T20:46:17.895664100Z"
    }
]
```

#### Boss Information

This endpoint returns the information about a specific boss

Example Endpoint

```
GET <api_endpoint>/api/bosses/1
```

Example Response
```json
{
    "id": 1,
    "name": "Arch-Glacor",
    "wikiUrl": "https://runescape.wiki/w/Arch-Glacor",
    "icon": "Arch-Glacor.png"
}
```

Error Response
```json
{
    "error": "Boss not found"
}
```

### Submissions

### Items

#### Item Information

This endpoint returns the information about a specific item, with
optional query parameters to handle paging (default page=0, pageSize=20)

Example Endpoint

``` 
GET <api_endpoint>/api/items/20266
```

Example Response
```json
{
    "id": 20266,
    "name": "Accursed ashes",
    "description": "A heap of ashes from a demon footsoldier. Scatter them for Prayer XP or use them to create incense sticks.",
    "type": "Prayer materials",
    "icon": "20266_icon.gif",
    "iconLarge": "20266_icon_large.gif",
    "wikiUrl": "https://runescape.wiki/w/Accursed_ashes",
    "members": false
}
```

#### Item Search

This endpoint returns the information about a collection of items containing
the search query, with optional query parameters to handle paging 
(default page=0, pageSize=20)

Example Endpoint

``` 
GET <api_endpoint>/api/items/search/ash?pageNumber=0&pageSize=2
```

Example Response
```json
{
    "items": [
        {
            "id": 20266,
            "name": "Accursed ashes",
            "description": "A heap of ashes from a demon footsoldier. Scatter them for Prayer XP or use them to create incense sticks.",
            "type": "Prayer materials",
            "icon": "20266_icon.gif",
            "iconLarge": "20266_icon_large.gif",
            "wikiUrl": "https://runescape.wiki/w/Accursed_ashes",
            "members": false
        },
        {
            "id": 592,
            "name": "Ashes",
            "description": "A heap of ashes.",
            "type": "Herblore materials",
            "icon": "592_icon.gif",
            "iconLarge": "592_icon_large.gif",
            "wikiUrl": "https://runescape.wiki/w/Ashes",
            "members": false
        }
    ],
    "query": "ash",
    "pageNumber": 0,
    "pageSize": 2
}
```

#### Items By Boss

Ths endpoing allows you to retrieve all items which are dropped from a given boss

Example Endpoint

```
GET <api_endpoint>/api/items/boss/1
```

Example Response

```json
{
    "boss": {
        "id": 1,
        "name": "Arch-Glacor",
        "wikiUrl": "https://runescape.wiki/w/Arch-Glacor",
        "icon": "Arch-Glacor.png"
    },
    "items": [
        {
            "id": 52115,
            "name": "Scripture of Wen",
            "description": "A collection of manuscripts detailing as much as is known about the Elder God, Wen.",
            "type": "Miscellaneous",
            "icon": "https://secure.runescape.com/m=itemdb_rs/1769426190227_obj_sprite.gif?id=52115",
            "iconLarge": "https://secure.runescape.com/m=itemdb_rs/1769426190227_obj_big.gif?id=52115",
            "wikiUrl": "https://runescape.wiki/w/Scripture_of_Wen",
            "members": true
        },
        {
            "id": 51817,
            "name": "Manuscript of Wen",
            "description": "Adds 45 minutes of charge to a Scripture of Wen.",
            "type": "Miscellaneous",
            "icon": "https://secure.runescape.com/m=itemdb_rs/1769426190227_obj_sprite.gif?id=51817",
            "iconLarge": "https://secure.runescape.com/m=itemdb_rs/1769426190227_obj_big.gif?id=51817",
            "wikiUrl": "https://runescape.wiki/w/Scripture_of_Wen",
            "members": true
        }
    ]
}
```

Example Error

```json
{
    "error": "Boss not found"
}
```
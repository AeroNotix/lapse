# lapse

A Clojure library designed to generate clojure.test.check generators
from [JSON schemas](http://json-schema.org/).

## Usage

Given:

```json

{
    "$schema": "http://json-schema.org/draft-04/schema#",
    "title": "Item schema",
    "type": "object",
    "required": true,
    "properties": {
        "id": {
            "type": "string",
            "pattern": "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$",
            "required": true
        },
        "version": {
            "type": "string",
            "pattern": "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$",
            "required": true
        },
        "name":     { "type": "string", "required": true },
        "price":    { "type": "integer", "required": true },
        "quantity": {
            "type": "integer",
            "minimum": 1,
            "required": true
        },
        "taxes": {
            "type": ["array", "null"],
            "items": { "$ref": "tax.json", "required": true },
            "description": { "type": "string", "required": true },
            "active": { "type": "boolean", "required": true }
        },
        "photo": {
            "type": "string"
        },
        "active": {
            "type": "boolean",
            "required": true
            }
        }
    }
}
```

You can generate instances of this schema as such:

```clojure

lapse.core> (clojure.pprint/pprint (take 5 (gen/sample (schema->generator "item.json"))))

({:active true,
  :photo "",
  :taxes nil,
  :quantity 0,
  :price 0,
  :name "",
  :version "",
  :id ""}
 {:active false,
  :photo "Q",
  :taxes [],
  :quantity -1,
  :price -1,
  :name "",
  :version "",
  :id "g"}
 {:active false,
  :photo "4",
  :taxes nil,
  :quantity 2,
  :price -2,
  :name "N",
  :version "S$",
  :id "u/"}
 {:active true,
  :photo "O",
  :taxes [{:value 1, :name "", :version "V&3", :id "N`"}],
  :quantity -3,
  :price -2,
  :name "!MR",
  :version "@34",
  :id ""}
 {:active true,
  :photo "%",
  :taxes nil,
  :quantity -4,
  :price 2,
  :name "VuL",
  :version "",
  :id ""})
nil
```

TODO
----


* Patterns
* Extra options
  * e.g. minItems for array types, uniqueItems for array types. etc


## License

Copyright © 2014 Aaron France

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

# lapse

A Clojure library designed to generate clojure.test.check generators
from [JSON schemas](http://json-schema.org/).

## Usage

```clojure

lapse.core> (clojure.pprint/pprint (take 5 (gen/sample (schema->generator "item.json"))))
nil
nil
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

## License

Copyright © 2014 Aaron France

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

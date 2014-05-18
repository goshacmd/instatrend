# instatrend

A simple service to store historical instagram popular shots data and
retrieve it later (Instagram's API only allows to see currently popular
shots).

## Usage

```bash
$ lein trampoline run
```

```bash
$ curl instatrends.url/popular
[{"link":"http://instagram.com/p/oJO_zvCWJr/","created_time":"2014-05-18T16:07:00Z","filter":"Amaro","location":{"name":null,"lat":-23.525084252,"lng":-46.662554937}, ...]
```

## License

Copyright © 2013 Gosha Arinich

Distributed under the Eclipse Public License, the same as Clojure.

# clj-archaius

A Clojure library designed use [Netflix/archaius](https://github.com/Netflix/archaius/) for configuration management.

## Usage

Leiningen dependency:

```clj
[clj-archaius "0.1.0-RC"]
```

Require clj-archaius in your namespace:

```
(require '[clj-archaius.core :refer :all])
```

Asume that you have a `config.properties` file in your project resource path:

```properties
a=1
b=2.0
c=hello\ world
```

You can get these properties by:

```clj
(int-env :a)
(double-env :b)
(env :c) //or (string-env :c)
```

With a default value if the property is not present:

```clj
(int-env :not-exists 100)
```

Add a callback listener to be notified when the property is changed:

```clj
(on-int-env :a (fn [] (println "The new :a is " (int-env :a)))
```

Supported types and their default values as below:

```
                          'int 0
                          'string nil
                          'boolean false
                          'double 0
                          'float 0
                          'long 0
```

All these types have `{type}-env` function to retrieve property value and `on-{type}-env`
function to add a callback listener to be notified when the property is changed.

## License

Copyright © 2015 dennis zhuang

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

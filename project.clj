(defproject cn.leancloud/clj-archaius "0.4.1"
  :description "A Clojure library designed to use Netflix/archaius for configuration management."
  :url "https://github.com/leancloud/clj-archaius"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cn.leancloud/archaius-core "0.7.8-b"]]
  :profiles {:dev {:resource-paths ["dev"]}})

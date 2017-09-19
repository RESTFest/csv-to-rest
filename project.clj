(defproject csv-to-rest "0.1.0-SNAPSHOT"
  :description "Parse CSV and determine the hierarchy"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure   "1.8.0"]
                 [org.clojure/data.csv  "0.1.4"]
                 [semantic-csv          "0.2.0"]]

  :main ^:skip-aot csv-to-rest.core

  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

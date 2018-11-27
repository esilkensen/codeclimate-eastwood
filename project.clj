(defproject codeclimate-eastwood "0.1.0-SNAPSHOT"
  :description "Code Climate Analysis Engine for Eastwood."
  :url "https://github.com/esilkensen/codeclimate-eastwood"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.4.1"]
                 [jonase/eastwood "0.3.3"]
                 [cheshire "5.8.1"]]

  :profiles {:uberjar {:aot :all}}

  :plugins [[lein-cloverage "1.0.13" :exclusions [org.clojure/clojure]]]
  :main main
  :uberjar-name "codeclimate-eastwood.jar")

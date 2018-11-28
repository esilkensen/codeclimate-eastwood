(ns eastwood
  (:require
   [clojure.stacktrace :refer [print-stack-trace]]
   [cheshire.core :as json]
   [clojure.string :as str]
   [eastwood.lint :as e]
   [medley.core :as m]))

(defn- >errf
  "Like printf but prints to STDERR"
  [fmt & args]
  (.println *err* (apply format (apply conj [fmt] args))))

(def default-opts
  {:source-paths ["src"]
   :test-paths ["test"]})

(def opt-keys
  [:source-paths
   :test-paths
   :exclude-namespaces
   :linters
   :exclude-linters
   :add-linters
   :builtin-config-files
   :config-files])

(declare parse-config-opts)

(defn lint
  "Runs the Eastwood lint tool given a parsed Code Climate config.json file."
  [config]
  (let [opts (merge default-opts (parse-config-opts config))]
    ;;(def catch-this-error 1)
    (>errf "[cc-eastwood] analyzing")
    (try
      (let [{:keys [warnings err err-data]} (e/lint opts)]
        (>errf "[cc-eastwood] warnings count %d" (count warnings)))
      (catch Exception e
        (>errf "[cc-eastwood] failed: %s" (.getMessage e))
        (print-stack-trace e)))))

(defn parse-keyword
  "Parses strings that start with ':' as keywords."
  [s]
  (if (and s (str/starts-with? s ":"))
    (keyword (subs s 1))
    s))

(defn parse-config-opts
  "Parses the Eastwood config map from a parsed Code Climate config.json file."
  [config]
  (let [opts (m/map-vals #(map parse-keyword %) (:config config))]
    (select-keys opts opt-keys)))

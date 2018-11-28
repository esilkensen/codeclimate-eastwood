(ns main
  (:require
   [cheshire.core :as json]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.tools.cli :as cli]
   [eastwood])
  (:gen-class))

(def cli-options [["-h" "--help"]])

(defn usage [options-summary]
  (->> ["Code Climate Eastwood engine"
        ""
        "Usage: java -jar codeclimate-eastwood.jar CONFIG_FILE_PATH WORKSPACE_PATH"
        ""
        "Options:"
        options-summary
        ""]
       (str/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))

(defn run-eastwood
  "Runs the Eastwood lint tool given a path to a Code Climate config.json file."
  [config-file-path]
  (let [config-file (io/file config-file-path)
        config (when (and config-file (.exists config-file))
                 (json/parse-string (slurp config-file) true))]
    (eastwood/lint config)))

(defn exit [status message]
  (println message)
  (System/exit status))

(defn -main [& args]
  (binding [*out* *err*]
    (let [{:keys [options arguments errors summary]} (cli/parse-opts args cli-options)]
      (cond
        (:help options) (exit 0 (usage summary))
        (not= (count arguments) 2) (exit 1 (usage summary))
        errors (exit 0 (error-msg errors)))
      (run-eastwood (first arguments)))))
